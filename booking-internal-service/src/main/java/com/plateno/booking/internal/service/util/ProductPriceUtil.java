package com.plateno.booking.internal.service.util;

import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;

public class ProductPriceUtil {

    /**
     * 
    * @Title: calProductPayMoney 
    * @Description: 计算商品实付金额
    * @param @param productInfo
    * @param @return    
    * @return Integer    
    * @throws
     */
    public static Integer calProductPayMoney(ProductInfo productInfo){
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
        return productPay;
    }
    
}
