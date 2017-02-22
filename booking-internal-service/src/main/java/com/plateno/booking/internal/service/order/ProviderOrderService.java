package com.plateno.booking.internal.service.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.constant.PayStatusEnum;
import com.plateno.booking.internal.base.mapper.LogisticsPackageMapper;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.model.ProviderOrderParam;
import com.plateno.booking.internal.base.pojo.LogisticsPackageExample;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.request.common.LstOrder;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;
import com.plateno.booking.internal.dao.mapper.ProviderOrderMapper;
import com.plateno.booking.internal.dao.pojo.ProviderOrderResponse;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;

@Service
public class ProviderOrderService {

    @Autowired
    private OrderMapper mallOrderMapper;
    
    @Autowired
    private ProviderOrderMapper providerOrderMapper;
    
    @Autowired
    private OrderProductMapper orderProductMapper;
    
    @Autowired
    private LogisticsPackageMapper packageMapper;
    
    public ResultVo<LstOrder<ProviderOrderResponse>> queryOrderByPage(ProviderOrderParam param){
        ResultVo<LstOrder<ProviderOrderResponse>> vo = new ResultVo<LstOrder<ProviderOrderResponse>>();
        LstOrder<ProviderOrderResponse> lst = new LstOrder<ProviderOrderResponse>();
        List<ProviderOrderResponse> list = new ArrayList<ProviderOrderResponse>();
        // 显示状态转变成数据库记录的状态
        if (param.getViewStatus() != null) {
            List<Integer> payStatus = PayStatusEnum.toPayStatus(param.getViewStatus());
            if (param.getStatusList() != null) {
                param.getStatusList().addAll(payStatus);
            } else {
                param.setStatusList(payStatus);
            }
        }
        
        list = providerOrderMapper.queryProviderOrder(param);
        buildProviderOrder(list);
        
        Double num = (Double.valueOf(5) / Double.valueOf(param.getPageNumber()));
        lst.setPageSize(param.getPageNumber());
        lst.setTotal(5);
        lst.setOrderList(list);
        lst.setTotalPage(new Double(Math.ceil(num)).intValue());
        vo.setData(lst);
        return vo;
    }
    
    private void buildProviderOrder(List<ProviderOrderResponse> list){
        for(ProviderOrderResponse provider : list){
            
            provider.setViewStatus(PayStatusEnum.toViewStatus(provider.getSubPayStatus()));
            //如父订单状态为已发货，则查询是否有包裹，如无，则修改为未发货
            if(provider.getViewStatus() == PayStatusEnum.PAY_STATUS_4.getViewStstus()){
                LogisticsPackageExample example = new LogisticsPackageExample();
                example.createCriteria().andOrderSubNoEqualTo(provider.getOrderSubNo());
                List packageList = packageMapper.selectByExample(example);
                if(CollectionUtils.isNotEmpty(packageList))
                    provider.setViewStatus(PayStatusEnum.PAY_STATUS_3.getViewStstus());
            }
            
            
            //查询商品信息
            OrderProductExample example = new OrderProductExample();
            example.createCriteria().andOrderSubNoEqualTo(provider.getOrderSubNo());
            List<OrderProduct> listProduct = orderProductMapper.selectByExample(example);
            List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
            Integer sumAmount = 0;//计算子订单实付金额
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
                productInfo.setCoupouReduceAmount(orderProduct.getCoupouReduceAmount());
                productInfo.setDeductPrice(orderProduct.getDeductPrice());
                productInfo.setExpressAmount(orderProduct.getExpressAmount());
                productInfoList.add(productInfo);
                
                //商品实付金额
                int productPay = productInfo.getPrice();
                //优惠券优惠金额
                if(null != productInfo.getCoupouReduceAmount())
                    productPay -= productInfo.getCoupouReduceAmount();
                //积分抵扣金额
                if(null != productInfo.getDeductPrice())
                    productPay -= productInfo.getDeductPrice();
                productPay = productPay * productInfo.getCount();
                //快递费
                if(null != productInfo.getExpressAmount())
                    productPay += productInfo.getExpressAmount();
                
            }
            provider.setProductInfos(productInfoList);
            
        }
        
        
    }
    
    
}
