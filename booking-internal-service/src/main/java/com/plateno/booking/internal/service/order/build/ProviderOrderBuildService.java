package com.plateno.booking.internal.service.order.build;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;
import com.plateno.booking.internal.dao.pojo.ProviderOrder;
import com.plateno.booking.internal.service.order.OrderProductService;
import com.plateno.booking.internal.service.util.ProductPriceUtil;

@Service
public class ProviderOrderBuildService {

    @Autowired
    private OrderProductMapper orderProductMapper;
    @Autowired
    private OrderProductService orderProductService;
    
    public void buildProductInfos(ProviderOrder provider){
      //查询商品信息
        OrderProductExample example = new OrderProductExample();
        example.createCriteria().andOrderSubNoEqualTo(provider.getOrderSubNo());
        List<OrderProduct> listProduct = orderProductMapper.selectByExample(example);
        List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
        Integer sumAmount = 0;//计算子订单实付金额
        for (OrderProduct orderProduct : listProduct) {
            ProductInfo productInfo = new ProductInfo();
            orderProductService.copyOrderProduct(productInfo, orderProduct);
            productInfoList.add(productInfo);
            //计算金额
            sumAmount += ProductPriceUtil.calProductPayMoney(productInfo);
        }
        //子订单实付金额
        provider.setSubPayMoney(sumAmount);
        //子订单
        provider.setProductInfos(productInfoList);
    }
    
}
