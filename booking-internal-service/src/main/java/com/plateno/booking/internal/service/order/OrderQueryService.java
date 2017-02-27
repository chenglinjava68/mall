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
import com.plateno.booking.internal.base.mapper.MLogisticsMapper;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderPayLogMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.model.SelectOrderParam;
import com.plateno.booking.internal.base.model.bill.OrderProductInfo;
import com.plateno.booking.internal.base.pojo.MLogistics;
import com.plateno.booking.internal.base.pojo.MLogisticsExample;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderPayLog;
import com.plateno.booking.internal.base.pojo.OrderPayLogExample;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.base.vo.MOrderSearchVO;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.response.custom.OrderDetail;
import com.plateno.booking.internal.bean.response.custom.SelectOrderResponse;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ConsigneeInfo;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.DeliverDetail;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.OrderInfo;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;
import com.plateno.booking.internal.conf.data.LogisticsTypeData;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
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
    
    /**
     * 查询订单信息,并支持分页处理
     * 
     * @param param
     * @return
     * @throws Exception
     */
    public ResultVo<LstOrder<SelectOrderResponse>> queryOrderByPage(SelectOrderParam param) throws Exception {
        LstOrder<SelectOrderResponse> lst = new LstOrder<SelectOrderResponse>();
        ResultVo<LstOrder<SelectOrderResponse>> vo ;
        List<SelectOrderResponse> list = new ArrayList<SelectOrderResponse>();
        if(param.getPageNo()==null||param.getPageNo()==0)param.setPageNo(1);
        lst.setPageIndex(param.getPageNo());
        
        vo = orderValidate.customQueryValidate(param,lst);
        if(vo!=null){
            return vo;
        }
        
        //商城前端查询，不显示删除的订单
        if(param.getPlateForm() == null || param.getPlateForm() == PlateFormEnum.USER.getPlateForm() || param.getPlateForm() == PlateFormEnum.APP.getPlateForm()) {
            param.setQueryDel(false);
        }
        
        //显示状态转变成数据库记录的状态
        if(param.getViewStatus() != null) {
            List<Integer> payStatus = PayStatusEnum.toPayStatus(param.getViewStatus());
            if(param.getStatusList() != null) {
                param.getStatusList().addAll(payStatus);
            } else {
                param.setStatusList(payStatus);
            }
        }
        
        List<Order> orderReturns = mallOrderMapper.getPageOrders(param);
        vo=new ResultVo<LstOrder<SelectOrderResponse>>();
        /*if (CollectionUtils.isEmpty(orderReturns)){
            return vo;
        }*/
        
        for(Order order:orderReturns){
            paramsDeal(order,list);
        }
        
        //获取符合条件的订单总数
        Integer count=mallOrderMapper.getCountOrder(param);
        Double num = (Double.valueOf(count)/Double.valueOf(param.getPageNumber()));
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
    public ResultVo<Object> getOrderInfo(final MOrderParam orderParam) throws Exception{
        ResultVo<Object> output = new ResultVo<Object>();
        //校验订单是否可被处理
        List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(), orderParam.getMemberId(), orderParam.getChannelId());
        if(CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        OrderProductExample example=new OrderProductExample();
        example.createCriteria().andOrderNoEqualTo(listOrder.get(0).getOrderNo());
        List<OrderProduct> listPro=orderProductMapper.selectByExample(example);
        
        OrderProductInfo orderProductInfo=new OrderProductInfo();
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
    public ResultVo<OrderDetail> getOrderDetail(MOrderParam orderParam) throws OrderException, Exception {
        ResultVo<OrderDetail> output = new ResultVo<OrderDetail>();
        List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(), orderParam.getMemberId(), orderParam.getChannelId());
        if(CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        
        //如果是前端查询，删除状态不返回
        if(orderParam.getPlateForm() == null || orderParam.getPlateForm().equals(PlateFormEnum.APP.getPlateForm()) || orderParam.getPlateForm().equals(PlateFormEnum.USER.getPlateForm())) {
            if(listOrder.get(0).getLogicDel().equals(LogicDelEnum.DEL.getType())) {
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
    public ResultVo<OrderDetail> getPaySuccessDetail(MOrderParam orderParam) throws OrderException, Exception {
        ResultVo<OrderDetail> output = new ResultVo<OrderDetail>();
        List<Order> listOrder=mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(), orderParam.getMemberId(), orderParam.getChannelId());
        if(CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        
        //如果是前端查询，删除状态不返回
        if(orderParam.getPlateForm() == null || orderParam.getPlateForm().equals(PlateFormEnum.APP.getPlateForm()) || orderParam.getPlateForm().equals(PlateFormEnum.USER.getPlateForm())) {
            if(listOrder.get(0).getLogicDel().equals(LogicDelEnum.DEL.getType())) {
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
     * 分页查询订单
     * @param search
     * @return
     */
    public ResultVo<PageInfo<SelectOrderResponse>> queryOrderList(
            MOrderSearchVO svo) {
        
        PageInfo<SelectOrderResponse> paginInfo = new PageInfo<SelectOrderResponse>();
        
        if(svo.getPlateForm() == null || svo.getPlateForm() == PlateFormEnum.USER.getPlateForm() || svo.getPlateForm() == PlateFormEnum.APP.getPlateForm()) {
            svo.setQueryDel(false);
        }
        
        List<Order> list = mallOrderMapper.list(svo);
        
        
        List<SelectOrderResponse> volist = new ArrayList<SelectOrderResponse>();
        for (Order po : list) {
            paramsDeal(po, volist);
        }
        paginInfo.setList(volist);

        if (list.size() > 0) {
            int totalNum = mallOrderMapper.count(svo);
            int totalPage = totalNum % svo.getSize() == 0 ? totalNum
                    / svo.getSize() : totalNum / svo.getSize() + 1;

            paginInfo.setCount(totalNum);
            paginInfo.setPage(svo.getPage());
            paginInfo.setPageCount(totalPage);
        } else {
            paginInfo.setCount(0);
            paginInfo.setPage(svo.getPage());
            paginInfo.setPageCount(0);
        }
        
        ResultVo<PageInfo<SelectOrderResponse>> result = new ResultVo<>();
        result.setData(paginInfo);
        return result;
    }
    
    private OrderDetail beansDeal(List<Order> listOrder, Integer plateForm) {
        Order order=listOrder.get(0);
        OrderDetail  orderDetail=new OrderDetail();
        
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setCreateDate(order.getCreateTime().getTime());
        orderInfo.setOrderNo(order.getOrderNo());
        orderInfo.setPayAmount(order.getPayMoney());
        orderInfo.setPoint(order.getPoint());
        orderInfo.setOrderAmount(order.getAmount());
        orderInfo.setPayType(order.getPayType());
        
        orderInfo.setPayStatus(order.getPayStatus());
        //待付款， 如果是已经有支付记录，显示成已经失败
        if(order.getPayStatus().equals(PayStatusEnum.PAY_STATUS_1.getPayStatus())) {
            if(plateForm != null && plateForm == 3) {
                OrderPayLogExample example = new OrderPayLogExample();
                example.createCriteria().andOrderIdEqualTo(order.getId()).andTypeEqualTo(1).andStatusEqualTo(3);
                List<OrderPayLog> listpayLog = orderPayLogMapper.selectByExample(example);
                if(listpayLog.size() > 0) {
                    orderInfo.setPayStatus(PayStatusEnum.PAY_STATUS_12.getPayStatus());
                }
            }
        }
        
        orderInfo.setPayTime(order.getPayTime().getTime());
        orderInfo.setName(order.getName());
        orderInfo.setMobile(order.getMobile());
        
        MLogisticsExample mLogisticsExample = new MLogisticsExample();
        mLogisticsExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
        List<MLogistics> listLogistic=mLogisticsMapper.selectByExample(mLogisticsExample);
        if(CollectionUtils.isNotEmpty(listLogistic)){
            MLogistics logc=listLogistic.get(0);
            orderInfo.setFee(logc.getExpressFee());
            ConsigneeInfo consigneeInfo=new ConsigneeInfo();
            DeliverDetail deliverDetail=new DeliverDetail();
            consigneeInfo.setConsigneeAddress(logc.getProvince() + logc.getCity() + logc.getArea() + logc.getConsigneeAddress());
            consigneeInfo.setConsigneeName(logc.getConsigneeName());
            consigneeInfo.setMobile(logc.getConsigneeMobile());
            
            //针对商城前端，如果地址已经修改了，返回修改后的地址
            if(plateForm != null && plateForm == 3 && StringUtils.isNotBlank(logc.getConsigneeNewMobile())) {
                consigneeInfo.setConsigneeAddress(logc.getNewProvince() + logc.getNewCity() + logc.getNewArea() + logc.getConsigneeNewaddress());
                consigneeInfo.setConsigneeName(logc.getConsigneeNewName());
                consigneeInfo.setMobile(logc.getConsigneeNewMobile());
            }
            consigneeInfo.setNewAddress(logc.getNewProvince() + logc.getNewCity() + logc.getNewArea() + logc.getConsigneeNewaddress());
            consigneeInfo.setNewName(logc.getConsigneeNewName());
            consigneeInfo.setNewMobile(logc.getConsigneeNewMobile());
            deliverDetail.setDeliverNo(logc.getLogisticsNo());
            
            if(order.getPayStatus() == PayStatusEnum.PAY_STATUS_4.getPayStatus() || order.getPayStatus() == PayStatusEnum.PAY_STATUS_5.getPayStatus()) {
                deliverDetail.setLogisticsType(logc.getLogisticsType());
                
                //数据库维护物流信息
                //deliverDetail.setLogisticsTypeDesc(LogisticsEnum.getNameBytype(logc.getLogisticsType()));
                deliverDetail.setLogisticsTypeDesc(LogisticsTypeData.getDataMap().get(logc.getLogisticsType()));
            }
            if(order.getDeliverTime()!=null)deliverDetail.setDeliverDate(order.getDeliverTime().getTime());
            orderDetail.setConsigneeInfo(consigneeInfo);
            orderDetail.setDeliverDetail(deliverDetail);
        }
        
        OrderProductExample orderProductExample=new OrderProductExample();
        orderProductExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
        List<OrderProduct> list=orderProductMapper.selectByExample(orderProductExample);
        List<ProductInfo> productInfoList=new ArrayList<ProductInfo>();

        int deductPrice = 0;
        if(CollectionUtils.isNotEmpty(list)){
            for(OrderProduct orderProduct:list){
                ProductInfo productInfo=new ProductInfo();
                productInfo.setProductId(orderProduct.getProductId());
                productInfo.setCount(orderProduct.getSkuCount());
                productInfo.setPrice(orderProduct.getPrice());
                productInfo.setProductName(orderProduct.getProductName());
                productInfo.setProductPropertis(orderProduct.getProductProperty());
                productInfo.setPoint(orderProduct.getPoint());
                productInfo.setSellStrategy(orderProduct.getSellStrategy());
                productInfo.setDisImages(orderProduct.getDisImages());
                productInfoList.add(productInfo);
                
                deductPrice += orderProduct.getDeductPrice() * orderProduct.getSkuCount();
            }
        }
        orderDetail.setProductInfo(productInfoList);
        orderInfo.setDeductPrice(deductPrice);

        
        if(order.getPayStatus().equals(1)){
            orderInfo.setOrderDetailRemark("待付款，请你在30分钟内支付，否则订单取消");
        }else if(order.getPayStatus().equals(2)){
            orderInfo.setOrderDetailRemark("已取消，由于未在规定时间内进行支付，订单已自动取消");
        }else if(order.getPayStatus().equals(3)){
            orderInfo.setOrderDetailRemark("待发货，快递公司将会在三个工作日内进行发货");
        }else if(order.getPayStatus().equals(4)){
            orderInfo.setOrderDetailRemark("待收货，请留意电话进行快递查收");
        }else if(order.getPayStatus().equals(5)){
            orderInfo.setOrderDetailRemark("已完成，已确认收货，欢迎下次购买");
        }else if(order.getPayStatus().equals(6)){
            orderInfo.setOrderDetailRemark("退款中，您的退款正在申请中");
        }else if(order.getPayStatus().equals(7)){
            orderInfo.setOrderDetailRemark("已退款，退款金额￥"+order.getPayMoney()+"元,+积分,"+order.getPoint()+"已原路退回您支付时使用的账户");
        }else if(order.getPayStatus().equals(8) || order.getPayStatus().equals(BookingConstants.PAY_STATUS_13)){
            orderInfo.setOrderDetailRemark("审核不通过，如有问题，请联系铂涛会客服");
        }else if(order.getPayStatus().equals(BookingConstants.PAY_STATUS_13)){ //退款失败
            orderInfo.setOrderDetailRemark("审核失败，如有问题，请联系铂涛会客服");
        }
        orderInfo.setFailReason(order.getRefundFailReason());
        orderInfo.setRefundTime(order.getRefundTime() == null ? null : order.getRefundTime().getTime());
        orderInfo.setRefundSuccessTime(order.getRefundSuccesstime() == null ? null : order.getRefundSuccesstime().getTime());
        orderInfo.setRefundAmount(order.getRefundAmount());
        orderInfo.setRefundReason(order.getRefundReason());
        orderInfo.setViewStatus(PayStatusEnum.toViewStatus(order.getPayStatus()));
        orderInfo.setCouponAmount(order.getCouponAmount());
        
        orderDetail.setOrderInfo(orderInfo);
        return orderDetail;
    }
    
    private void paramsDeal(Order order,List<SelectOrderResponse> list) {
        SelectOrderResponse sc=new SelectOrderResponse();
        OrderProductExample example=new OrderProductExample();
        example.createCriteria().andOrderNoEqualTo(order.getOrderNo());
        //Integer count = orderProductMapper.countByExample(example);
        List<OrderProduct> listProduct=orderProductMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(listProduct)){
            sc.setGoodsName(listProduct.get(0).getProductName());
            sc.setGoodsProperties(listProduct.get(0).getProductProperty());
            sc.setQuatity(listProduct.get(0).getSkuCount());
            sc.setDisImage(listProduct.get(0).getDisImages());
            sc.setGoodsUrl(Config.MALL_H5_URL + "/goods.html#/goodsDetail?productId=" + listProduct.get(0).getProductId());
            //积分抵扣金额
            sc.setPointMoney(listProduct.get(0).getDeductPrice());
        }
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
        sc.setSubResource(order.getSubResource());
        
        //退款金额（如果已经生成退款金额，就是实际退款的金额，否则是可以退款的金额）
        int refundAmount = 0;
        if(order.getRefundAmount() != null && order.getRefundAmount() > 0) {
            refundAmount = order.getRefundAmount();
        } else {
            refundAmount = order.getPayMoney();
        }
        
        sc.setRefundAmount(refundAmount);
        sc.setViewStatus(PayStatusEnum.toViewStatus(order.getPayStatus()));
        sc.setLogicDel(order.getLogicDel());
        
        sc.setDeliverDate(order.getDeliverTime());
        
        //查询物流信息
        MLogisticsExample mLogisticsExample = new MLogisticsExample();
        mLogisticsExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
        List<MLogistics> listLogistic = mLogisticsMapper.selectByExample(mLogisticsExample);
        if(listLogistic.size() > 0) {
            MLogistics mLogistics = listLogistic.get(0);
            sc.setDeliverNo(mLogistics.getLogisticsNo());
            if(order.getPayStatus() == PayStatusEnum.PAY_STATUS_4.getPayStatus() || order.getPayStatus() == PayStatusEnum.PAY_STATUS_5.getPayStatus()) {
                sc.setLogisticsType(mLogistics.getLogisticsType());
                sc.setLogisticsTypeDesc(LogisticsTypeData.getDataMap().get(mLogistics.getLogisticsType()));
            }
            
            if(StringUtils.isNotBlank(mLogistics.getConsigneeNewMobile())) {
                sc.setConsigneeName(mLogistics.getConsigneeNewName());
                sc.setConsigneeMobile(mLogistics.getConsigneeNewMobile());
                sc.setConsigneeAddress(mLogistics.getNewProvince() + mLogistics.getNewCity() + mLogistics.getNewArea() + mLogistics.getConsigneeNewaddress());
            } else {
                sc.setConsigneeName(mLogistics.getConsigneeName());
                sc.setConsigneeMobile(mLogistics.getConsigneeMobile());
                sc.setConsigneeAddress(mLogistics.getProvince() + mLogistics.getCity() + mLogistics.getArea() + mLogistics.getConsigneeAddress());
            }
            //总的快递费
            sc.setTotalExpressAmount(mLogistics.getExpressFee());
        }
        //优惠券金额
        sc.setCouponAmount(order.getCouponAmount());
        list.add(sc);
    }
    
}
