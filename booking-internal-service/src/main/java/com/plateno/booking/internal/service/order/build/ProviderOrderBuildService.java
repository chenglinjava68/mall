package com.plateno.booking.internal.service.order.build;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.plateno.booking.internal.base.mapper.LogisticsPackageMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.pojo.LogisticsPackage;
import com.plateno.booking.internal.base.pojo.LogisticsPackageExample;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;
import com.plateno.booking.internal.bean.response.logistics.PackageProduct;
import com.plateno.booking.internal.conf.data.LogisticsTypeData;
import com.plateno.booking.internal.dao.pojo.ProviderOrder;
import com.plateno.booking.internal.dao.pojo.ProviderOrderDetail;
import com.plateno.booking.internal.service.order.OrderProductService;
import com.plateno.booking.internal.service.util.ProductPriceUtil;

@Service
public class ProviderOrderBuildService {

    @Autowired
    private OrderProductMapper orderProductMapper;
    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private LogisticsPackageMapper packageMapper;
    
    public void buildProductInfosAndCal(ProviderOrder provider){
      //查询商品信息
        List<OrderProduct> listProduct = orderProductService.queryOrderProductByOrderSubNo(provider.getOrderSubNo());
        List<ProductInfo> productInfoList = new ArrayList<ProductInfo>();
        Integer sumAmount = 0;//计算子订单实付金额
        Integer productAmount = 0;//商品总额
        Integer couponAmout = 0;//优惠券优惠金额
        Integer fee = 0;//快递费
        Integer deductPrice = 0;//积分抵扣金额
        for (OrderProduct orderProduct : listProduct) {
            ProductInfo productInfo = new ProductInfo();
            orderProductService.copyOrderProduct(productInfo, orderProduct);
            productInfoList.add(productInfo);
            //计算金额
            sumAmount += ProductPriceUtil.calProductPayMoney(productInfo);
            productAmount += orderProduct.getPrice() * orderProduct.getSkuCount();
            if(null != orderProduct.getCoupouReduceAmount())
                couponAmout += orderProduct.getCoupouReduceAmount() * orderProduct.getSkuCount();
            if(null != orderProduct.getExpressAmount())
                fee += orderProduct.getExpressAmount();
            if(null != orderProduct.getDeductPrice())
                deductPrice += orderProduct.getDeductPrice();
        }
        //子订单实付金额
        provider.setSubPayMoney(sumAmount);
        provider.setProductAmout(productAmount);
        provider.setCouponAmount(couponAmout);
        provider.setFee(fee);
        provider.setDeductPrice(deductPrice);
        //子订单
        provider.setProductInfos(productInfoList);
    }
    
    public void buildPackage(ProviderOrderDetail detail){
        LogisticsPackageExample example = new LogisticsPackageExample();
        example.createCriteria().andOrderSubNoEqualTo(detail.getOrderSubNo());
        List<LogisticsPackage> logisticsPackageList = packageMapper.selectByExample(example);
        List<PackageProduct> packageProductList = Lists.newArrayList();
        for (LogisticsPackage logisticsPackage : logisticsPackageList) {
            PackageProduct packageProduct = new PackageProduct();
            packageProduct.setLogisticsNo(logisticsPackage.getLogisticsNo());
            packageProduct.setLogisticsType(logisticsPackage.getLogisticsType());
            packageProduct.setLogisticsName(LogisticsTypeData.getDataMap().get(
                    logisticsPackage.getLogisticsType()));
            packageProduct.setExpressFee(logisticsPackage.getExpressFee());
            //查询商品集合，根据包裹id
            packageProduct.setProducts(orderProductService.queryProductInfosByPackageId(logisticsPackage.getId()));
            packageProductList.add(packageProduct);
        }
        detail.setPackageProducts(packageProductList);
    }
    
}
