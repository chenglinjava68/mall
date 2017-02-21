package com.plateno.booking.internal.service.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.constant.LogicDelEnum;
import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.constant.PlateFormEnum;
import com.plateno.booking.internal.base.mapper.LogisticsPackageMapper;
import com.plateno.booking.internal.base.mapper.MLogisticsMapper;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderPayLogMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.model.SelectOrderParam;
import com.plateno.booking.internal.base.model.bill.OrderProductInfo;
import com.plateno.booking.internal.base.pojo.LogisticsPackageExample;
import com.plateno.booking.internal.base.pojo.MLogistics;
import com.plateno.booking.internal.base.pojo.MLogisticsExample;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.base.vo.MOrderSearchVO;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.response.custom.OrderDetail;
import com.plateno.booking.internal.bean.response.custom.SelectOrderResponse;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;
import com.plateno.booking.internal.conf.data.LogisticsTypeData;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.order.build.OrderBuildService;
import com.plateno.booking.internal.util.vo.PageInfo;
import com.plateno.booking.internal.validator.order.MOrderValidate;

@Service
public class OrderQueryService {

    @Autowired
    private MOrderValidate orderValidate;
    @Autowired
    private OrderMapper mallOrderMapper;
    @Autowired
    private OrderProductMapper orderProductMapper;
    @Autowired
    private MLogisticsMapper mLogisticsMapper;
    @Autowired
    private OrderPayLogMapper orderPayLogMapper;
    @Autowired
    private OrderBuildService orderBuildService;
    @Autowired
    private LogisticsPackageMapper packageMapper;
    
    
    /**
     * 查询订单信息,并支持分页处理
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public ResultVo<LstOrder<SelectOrderResponse>> queryOrderByPage(SelectOrderParam param)
            throws Exception {
        LstOrder<SelectOrderResponse> lst = new LstOrder<SelectOrderResponse>();
        ResultVo<LstOrder<SelectOrderResponse>> vo;
        List<SelectOrderResponse> list = new ArrayList<SelectOrderResponse>();
        if (param.getPageNo() == null || param.getPageNo() == 0)
            param.setPageNo(1);
        lst.setPageIndex(param.getPageNo());

        vo = orderValidate.customQueryValidate(param, lst);
        if (vo != null) {
            return vo;
        }

        // 商城前端查询，不显示删除的订单
        if (param.getPlateForm() == null
                || param.getPlateForm() == PlateFormEnum.USER.getPlateForm()
                || param.getPlateForm() == PlateFormEnum.APP.getPlateForm()) {
            param.setQueryDel(false);
        }

        // 显示状态转变成数据库记录的状态
        if (param.getViewStatus() != null) {
            List<Integer> payStatus = PayStatusEnum.toPayStatus(param.getViewStatus());
            if (param.getStatusList() != null) {
                param.getStatusList().addAll(payStatus);
            } else {
                param.setStatusList(payStatus);
            }
        }

        List<Order> orderReturns = mallOrderMapper.getPageOrders(param);
        vo = new ResultVo<LstOrder<SelectOrderResponse>>();

        for (Order order : orderReturns) {
            paramsDeal(order, list);
        }

        // 获取符合条件的订单总数
        Integer count = mallOrderMapper.getCountOrder(param);
        Double num = (Double.valueOf(count) / Double.valueOf(param.getPageNumber()));
        lst.setPageSize(param.getPageNumber());
        lst.setTotal(count);
        lst.setOrderList(list);
        lst.setTotalPage(new Double(Math.ceil(num)).intValue());
        vo.setData(lst);
        return vo;
    }

    /**
     * 查询订单状态
     * 
     * @param orderParam
     * @return
     * @throws Exception
     */
    public ResultVo<Object> getOrderInfo(final MOrderParam orderParam) throws Exception {
        ResultVo<Object> output = new ResultVo<Object>();
        // 校验订单是否可被处理
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        OrderProductExample example = new OrderProductExample();
        example.createCriteria().andOrderNoEqualTo(listOrder.get(0).getOrderNo());
        List<OrderProduct> listPro = orderProductMapper.selectByExample(example);

        OrderProductInfo orderProductInfo = new OrderProductInfo();
        orderProductInfo.setOrder(listOrder.get(0));
        orderProductInfo.setOrderProductList(listPro);
        output.setData(orderProductInfo);

        return output;
    }

    /**
     * 获取订单信息
     * 
     * @param orderParam
     * @return
     * @throws OrderException
     * @throws Exception
     */
    public ResultVo<OrderDetail> getOrderDetail(MOrderParam orderParam) throws OrderException,
            Exception {
        ResultVo<OrderDetail> output = new ResultVo<OrderDetail>();
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }

        // 如果是前端查询，删除状态不返回
        if (orderParam.getPlateForm() == null
                || orderParam.getPlateForm().equals(PlateFormEnum.APP.getPlateForm())
                || orderParam.getPlateForm().equals(PlateFormEnum.USER.getPlateForm())) {
            if (listOrder.get(0).getLogicDel().equals(LogicDelEnum.DEL.getType())) {
                output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
                output.setResultMsg("订单查询失败,获取不到订单");
                return output;
            }
        }

        OrderDetail orderDetail = beansDeal(listOrder, orderParam.getPlateForm());
        output.setData(orderDetail);
        return output;
    }


    /**
     * 获取支付成功信息
     * 
     * @param orderParam
     * @return
     * @throws OrderException
     * @throws Exception
     */
    public ResultVo<OrderDetail> getPaySuccessDetail(MOrderParam orderParam) throws OrderException,
            Exception {
        ResultVo<OrderDetail> output = new ResultVo<OrderDetail>();
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }

        // 如果是前端查询，删除状态不返回
        if (orderParam.getPlateForm() == null
                || orderParam.getPlateForm().equals(PlateFormEnum.APP.getPlateForm())
                || orderParam.getPlateForm().equals(PlateFormEnum.USER.getPlateForm())) {
            if (listOrder.get(0).getLogicDel().equals(LogicDelEnum.DEL.getType())) {
                output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
                output.setResultMsg("订单查询失败,获取不到订单");
                return output;
            }
        }

        OrderDetail orderDetail = beansDeal(listOrder, orderParam.getPlateForm());
        output.setData(orderDetail);
        return output;
    }

    

    private OrderDetail beansDeal(List<Order> listOrder, Integer plateForm) {
        Order order = listOrder.get(0);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setSubOrderDetails(orderBuildService.buildSubOrderDetail(order));
        //子订单数量
        int count = orderBuildService.buildSubOrderDetail(order).size();
        orderDetail.setOrderInfo(orderBuildService.buildOrderInfo(order, plateForm , count));
        orderDetail.setConsigneeInfo(orderBuildService.buildConsigneeInfo(order, plateForm));
        return orderDetail;
    }

    private void paramsDeal(Order order, List<SelectOrderResponse> list) {
        SelectOrderResponse sc = new SelectOrderResponse();
        sc.setPoint(order.getPoint());
        sc.setAmount(order.getAmount());
        sc.setMemberId(Long.parseLong(order.getMemberId().toString()));
        sc.setOrderNo(order.getOrderNo());
        sc.setPayStatus(order.getPayStatus());
        sc.setBookingName(order.getName());
        sc.setResource(order.getResource());
        sc.setMobile(order.getMobile());
        sc.setPayType(order.getPayType());
        sc.setCreateTime(order.getCreateTime().getTime());

        sc.setPayMoney(order.getPayMoney());
        sc.setCurrencyDepositAmount(order.getCurrencyDepositAmount());
        sc.setSubResource(order.getSubResource());

        // 退款金额（如果已经生成退款金额，就是实际退款的金额，否则是可以退款的金额）
        int refundAmount = 0;
        if (order.getRefundAmount() != null && order.getRefundAmount() > 0) {
            refundAmount = order.getRefundAmount();
        } else {
            refundAmount = order.getPayMoney();
        }

        sc.setRefundAmount(refundAmount);
        sc.setViewStatus(PayStatusEnum.toViewStatus(order.getPayStatus()));
        //已发货，查询是否所有子订单都有订单号
        if(sc.getViewStatus() == PayStatusEnum.PAY_STATUS_4.getPayStatus()){
            int count = orderProductMapper.queryOrderSubNoCount(order.getOrderNo());
            LogisticsPackageExample example = new LogisticsPackageExample();
            example.createCriteria().andOrderNoEqualTo(order.getOrderNo());
            List packageList = packageMapper.selectByExample(example);
            //部分订单未发货
            if(count != packageList.size()){
                sc.setViewStatus(PayStatusEnum.PAY_STATUS_14.getPayStatus());
            }
        }
        sc.setLogicDel(order.getLogicDel());

        // 查询物流信息
        MLogisticsExample mLogisticsExample = new MLogisticsExample();
        mLogisticsExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
        List<MLogistics> listLogistic = mLogisticsMapper.selectByExample(mLogisticsExample);
        if (listLogistic.size() > 0) {
            MLogistics mLogistics = listLogistic.get(0);
            if (StringUtils.isNotBlank(mLogistics.getConsigneeNewMobile())) {
                sc.setConsigneeName(mLogistics.getConsigneeNewName());
                sc.setConsigneeMobile(mLogistics.getConsigneeNewMobile());
                sc.setConsigneeAddress(mLogistics.getNewProvince() + mLogistics.getNewCity()
                        + mLogistics.getNewArea() + mLogistics.getConsigneeNewaddress());
            } else {
                sc.setConsigneeName(mLogistics.getConsigneeName());
                sc.setConsigneeMobile(mLogistics.getConsigneeMobile());
                sc.setConsigneeAddress(mLogistics.getProvince() + mLogistics.getCity()
                        + mLogistics.getArea() + mLogistics.getConsigneeAddress());
            }
        }

        //查询商品信息
        OrderProductExample example = new OrderProductExample();
        example.createCriteria().andOrderNoEqualTo(order.getOrderNo());
        List<OrderProduct> listProduct = orderProductMapper.selectByExample(example);
        List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
        for (OrderProduct orderProduct : listProduct) {
            ProductInfo productInfo = new ProductInfo();
            productInfo.setProductId(orderProduct.getProductId());
            productInfo.setCount(orderProduct.getSkuCount());
            productInfo.setPrice(orderProduct.getPrice());
            productInfo.setProductName(orderProduct.getProductName());
            productInfo.setProductPropertis(orderProduct.getProductProperty());
            productInfo.setDisImages(orderProduct.getDisImages());
            productInfo.setGoodsUrl(Config.MALL_H5_URL + "/goods.html#/goodsDetail?productId="
                    + orderProduct.getProductId());
            productInfoList.add(productInfo);
        }
        sc.setProductInfos(productInfoList);
        list.add(sc);
    }

}
