package com.plateno.booking.internal.service.order.build;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.mapper.MLogisticsMapper;
import com.plateno.booking.internal.base.mapper.OrderPayLogMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.pojo.MLogistics;
import com.plateno.booking.internal.base.pojo.MLogisticsExample;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.base.pojo.OrderPayLog;
import com.plateno.booking.internal.base.pojo.OrderPayLogExample;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ConsigneeInfo;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.OrderInfo;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;
import com.plateno.booking.internal.bean.response.custom.SubOrderDetail;

@Service
public class OrderBuildService {

    @Autowired
    private OrderPayLogMapper orderPayLogMapper;
    
    @Autowired
    private MLogisticsMapper mLogisticsMapper;
    
    @Autowired
    private OrderProductMapper orderProductMapper;
    
    /**
     * 
    * @Title: buildOrderInfo 
    * @Description: 构建orderInfo
    * @param @param order
    * @param @param plateForm
    * @param @return    
    * @return OrderInfo    
    * @throws
     */
    public OrderInfo buildOrderInfo(Order order,Integer plateForm){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(order.getCreateTime().getTime());
        orderInfo.setOrderNo(order.getOrderNo());
        orderInfo.setPayType(order.getPayType());

        orderInfo.setPayStatus(order.getPayStatus());
        // 待付款， 如果是已经有支付记录，显示成已经失败
        if (order.getPayStatus().equals(PayStatusEnum.PAY_STATUS_1.getPayStatus())) {
            if (plateForm != null && plateForm == 3) {
                OrderPayLogExample example = new OrderPayLogExample();
                example.createCriteria().andOrderIdEqualTo(order.getId()).andTypeEqualTo(1)
                        .andStatusEqualTo(3);
                List<OrderPayLog> listpayLog = orderPayLogMapper.selectByExample(example);
                if (listpayLog.size() > 0) {
                    orderInfo.setPayStatus(PayStatusEnum.PAY_STATUS_12.getPayStatus());
                }
            }
        }

        orderInfo.setPayTime(order.getPayTime().getTime());
        orderInfo.setName(order.getName());
        orderInfo.setMobile(order.getMobile());
        orderInfo.setFee(order.getTotalExpressCost());
        if (order.getPayStatus().equals(1)) {
            orderInfo.setOrderDetailRemark("待付款，请你在30分钟内支付，否则订单取消");
        } else if (order.getPayStatus().equals(2)) {
            orderInfo.setOrderDetailRemark("已取消，由于未在规定时间内进行支付，订单已自动取消");
        } else if (order.getPayStatus().equals(3)) {
            orderInfo.setOrderDetailRemark("待发货，快递公司将会在三个工作日内进行发货");
        } else if (order.getPayStatus().equals(4)) {
            orderInfo.setOrderDetailRemark("待收货，请留意电话进行快递查收");
        } else if (order.getPayStatus().equals(5)) {
            orderInfo.setOrderDetailRemark("已完成，已确认收货，欢迎下次购买");
        } else if (order.getPayStatus().equals(6)) {
            orderInfo.setOrderDetailRemark("退款中，您的退款正在申请中");
        } else if (order.getPayStatus().equals(7)) {
            orderInfo.setOrderDetailRemark("已退款，退款金额￥" + order.getPayMoney() + "元,+积分,"
                    + order.getPoint() + "已原路退回您支付时使用的账户");
        } else if (order.getPayStatus().equals(8)
                || order.getPayStatus().equals(BookingConstants.PAY_STATUS_13)) {
            orderInfo.setOrderDetailRemark("审核不通过，如有问题，请联系铂涛会客服");
        } else if (order.getPayStatus().equals(BookingConstants.PAY_STATUS_13)) { // 退款失败
            orderInfo.setOrderDetailRemark("审核失败，如有问题，请联系铂涛会客服");
        }
        orderInfo.setFailReason(order.getRefundFailReason());
        orderInfo.setRefundTime(order.getRefundTime() == null ? null : order.getRefundTime()
                .getTime());
        orderInfo.setRefundSuccessTime(order.getRefundSuccesstime() == null ? null : order
                .getRefundSuccesstime().getTime());
        orderInfo.setRefundAmount(order.getRefundAmount());
        orderInfo.setRefundReason(order.getRefundReason());
        orderInfo.setViewStatus(PayStatusEnum.toViewStatus(order.getPayStatus()));
        orderInfo.setPoint(order.getPoint());
        orderInfo.setOrderAmount(order.getAmount());//订单总金额
        orderInfo.setPayAmount(order.getPayMoney());//实付金额
        orderInfo.setCouponAmount(order.getCouponAmount());//优惠券金额
        orderInfo.setFee(order.getTotalExpressCost());//运费
        orderInfo.setCurrencyDepositAmount(order.getCurrencyDepositAmount());//储值金额
        orderInfo.setGatewayAmount(order.getGatewayAmount());//网关支付金额
        return orderInfo;
    }
    
    /**
     * 
    * @Title: buildConsigneeInfo 
    * @Description: 构建ConsigneeInfo
    * @param @param order
    * @param @param plateForm
    * @param @return    
    * @return ConsigneeInfo    
    * @throws
     */
    public ConsigneeInfo buildConsigneeInfo(Order order,Integer plateForm){
        ConsigneeInfo consigneeInfo = new ConsigneeInfo();
        MLogisticsExample mLogisticsExample = new MLogisticsExample();
        mLogisticsExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
        List<MLogistics> listLogistic = mLogisticsMapper.selectByExample(mLogisticsExample);
        if (CollectionUtils.isNotEmpty(listLogistic)) {
            MLogistics logc = listLogistic.get(0);
            consigneeInfo.setConsigneeAddress(logc.getProvince() + logc.getCity() + logc.getArea()
                    + logc.getConsigneeAddress());
            consigneeInfo.setConsigneeName(logc.getConsigneeName());
            consigneeInfo.setMobile(logc.getConsigneeMobile());

            // 针对商城前端，如果地址已经修改了，返回修改后的地址
            if (plateForm != null && plateForm == 3
                    && StringUtils.isNotBlank(logc.getConsigneeNewMobile())) {
                consigneeInfo.setConsigneeAddress(logc.getNewProvince() + logc.getNewCity()
                        + logc.getNewArea() + logc.getConsigneeNewaddress());
                consigneeInfo.setConsigneeName(logc.getConsigneeNewName());
                consigneeInfo.setMobile(logc.getConsigneeNewMobile());
            }
            String newAddress =
                    StringUtils.trimToEmpty(logc.getNewProvince())
                            + StringUtils.trimToEmpty(logc.getNewCity())
                            + StringUtils.trimToEmpty(logc.getNewArea())
                            + StringUtils.trimToEmpty(logc.getConsigneeNewaddress());
            if (StringUtils.isBlank(newAddress)) {
                newAddress = null;
            }
            consigneeInfo.setNewAddress(newAddress);
            consigneeInfo.setNewName(logc.getConsigneeNewName());
            consigneeInfo.setNewMobile(logc.getConsigneeNewMobile());
        }
        
        return consigneeInfo;
    }
    
    
    /**
     * 
    * @Title: buildSubOrderDetail 
    * @Description: 构建subOrderDetail
    * @param @param order
    * @param @return    
    * @return List<SubOrderDetail>    
    * @throws
     */
    public List<SubOrderDetail> buildSubOrderDetail(Order order){
        OrderProductExample orderProductExample = new OrderProductExample();
        orderProductExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
        List<OrderProduct> list = orderProductMapper.selectByExample(orderProductExample);

        // 根据仓库分组
        Map<Integer, List<OrderProduct>> orderProductMap = Maps.newHashMap();
        for (OrderProduct temp : list) {
            List<OrderProduct> orderProducts = orderProductMap.get(temp.getChannelId());
            if (CollectionUtils.isEmpty(orderProducts)) {
                orderProducts = Lists.newArrayList();
            }
            orderProducts.add(temp);
            orderProductMap.put(temp.getChannelId(), orderProducts);
        }

        List<SubOrderDetail> subOrderDetails = Lists.newArrayList();
        Iterator<Map.Entry<Integer,List<OrderProduct>>> iter = orderProductMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer,List<OrderProduct>> entry = (Map.Entry<Integer,List<OrderProduct>>) iter.next();
            List<OrderProduct> orderProducts = entry.getValue();
            SubOrderDetail subOrderDetail = new SubOrderDetail();
            subOrderDetail.setChannelId(orderProducts.get(0).getChannelId());
            subOrderDetail.setSubOrderNo(orderProducts.get(0).getOrderSubNo());
            //订单状态暂时取父订单状态
            subOrderDetail.setSubOrderStatus(order.getPayStatus());
            subOrderDetail.setSubViewStatus(PayStatusEnum.toViewStatus(order.getPayStatus()));
            
            List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
            for (OrderProduct orderProduct : orderProducts) {
                ProductInfo productInfo = new ProductInfo();
                productInfo.setProductId(orderProduct.getProductId());
                productInfo.setCount(orderProduct.getSkuCount());
                productInfo.setPrice(orderProduct.getPrice());
                productInfo.setProductName(orderProduct.getProductName());
                productInfo.setProductPropertis(orderProduct.getProductProperty());
                productInfo.setPoint(orderProduct.getPoint());
                productInfo.setSellStrategy(orderProduct.getSellStrategy());
                productInfo.setDisImages(orderProduct.getDisImages());
                productInfoList.add(productInfo);
            }
            subOrderDetail.setProductInfo(productInfoList);
            subOrderDetails.add(subOrderDetail);
        }
        return subOrderDetails;
    }
    
    
}
