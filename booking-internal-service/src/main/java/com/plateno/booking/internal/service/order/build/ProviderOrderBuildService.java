package com.plateno.booking.internal.service.order.build;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;
import com.plateno.booking.internal.dao.pojo.ProviderOrder;
import com.plateno.booking.internal.service.util.ProductPriceUtil;

@Service
public class ProviderOrderBuildService {

    @Autowired
    private OrderProductMapper orderProductMapper;
    
    public void buildProductInfos(ProviderOrder provider){
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
            
            sumAmount += ProductPriceUtil.calProductPayMoney(productInfo);
            
        }
        provider.setSubPayMoney(sumAmount);
        provider.setProductInfos(productInfoList);
    }
    
}
