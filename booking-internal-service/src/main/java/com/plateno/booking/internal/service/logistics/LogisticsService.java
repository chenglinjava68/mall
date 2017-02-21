package com.plateno.booking.internal.service.logistics;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.plateno.booking.internal.base.mapper.LogisticsPackageMapper;
import com.plateno.booking.internal.base.mapper.LogisticsProductMapper;
import com.plateno.booking.internal.base.mapper.MLogisticsMapper;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.pojo.LogisticsPackage;
import com.plateno.booking.internal.base.pojo.LogisticsPackageExample;
import com.plateno.booking.internal.base.pojo.MLogistics;
import com.plateno.booking.internal.base.pojo.MLogisticsExample;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.bean.request.logistics.OrderLogisticsQueryReq;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;
import com.plateno.booking.internal.bean.response.logistics.PackageProduct;
import com.plateno.booking.internal.conf.data.LogisticsTypeData;

@Service
public class LogisticsService {

    protected final Logger logger = Logger.getLogger(LogisticsService.class);
 
    @Autowired
    private MLogisticsMapper mLogisticsMapper;
    @Autowired
    private LogisticsPackageMapper logisticsPackageMapper;
    @Autowired
    private LogisticsProductMapper logisticsProductMapper;
    @Autowired
    private OrderProductMapper orderProductMapper;
    
    public List<PackageProduct> queryOrderLogistics(OrderLogisticsQueryReq  param){
        List<PackageProduct> packageProductList = Lists.newArrayList();
        //兼容旧的数据，旧的数据只有一个包裹
        MLogisticsExample mLogisticsExample = new MLogisticsExample();
        mLogisticsExample.createCriteria().andOrderNoEqualTo(param.getOrderNo());
        List<MLogistics>  mLogisticsList = mLogisticsMapper.selectByExample(mLogisticsExample);
        if(CollectionUtils.isEmpty(mLogisticsList))
            return packageProductList;
        
        MLogistics mLogistics = mLogisticsList.get(0);
        //假如存在快递单号，则为旧的数据
        if(StringUtils.isNotBlank(mLogistics.getLogisticsNo())){
            PackageProduct packageProduct = new PackageProduct();
            packageProduct.setLogisticsNo(mLogistics.getLogisticsNo());
            packageProduct.setLogisticsType(mLogistics.getLogisticsType());
            packageProduct.setLogisticsName(LogisticsTypeData.getDataMap().get(mLogistics.getLogisticsType()));
            packageProduct.setExpressFee(mLogistics.getExpressFee());
            List<ProductInfo> productInfos = Lists.newArrayList();
            ProductInfo productInfo = new ProductInfo();
            
            OrderProductExample orderProductExample = new OrderProductExample();
            orderProductExample.createCriteria().andOrderNoEqualTo(param.getOrderNo());
            List<OrderProduct> list = orderProductMapper.selectByExample(orderProductExample);
            OrderProduct orderProduct = list.get(0);
            productInfo.setProductId(orderProduct.getProductId());
            productInfo.setCount(orderProduct.getSkuCount());
            productInfo.setPrice(orderProduct.getPrice());
            productInfo.setProductName(orderProduct.getProductName());
            productInfo.setProductPropertis(orderProduct.getProductProperty());
            productInfo.setPoint(orderProduct.getPoint());
            productInfo.setDisImages(orderProduct.getDisImages());
            productInfos.add(productInfo);
            
            packageProduct.setProducts(productInfos);
            packageProductList.add(packageProduct);
        }else{
            LogisticsPackageExample logisticsPackageExample = new LogisticsPackageExample();
            logisticsPackageExample.createCriteria().andOrderNoEqualTo(param.getOrderNo());
            List<LogisticsPackage> logisticsPackageList = logisticsPackageMapper.selectByExample(logisticsPackageExample);
            for(LogisticsPackage logisticsPackage : logisticsPackageList){
                PackageProduct packageProduct = new PackageProduct();
                packageProduct.setLogisticsNo(logisticsPackage.getLogisticsNo());
                packageProduct.setLogisticsType(logisticsPackage.getLogisticsType());
                packageProduct.setLogisticsName(LogisticsTypeData.getDataMap().get(logisticsPackage.getLogisticsType()));
                packageProduct.setExpressFee(logisticsPackage.getExpressFee());
                
                //根据子订单号查询订单商品数据
                List<ProductInfo> productInfos = Lists.newArrayList();
                OrderProductExample orderproductExample = new OrderProductExample();
                orderproductExample.createCriteria().andOrderSubNoEqualTo(logisticsPackage.getOrderSubNo());
                List<OrderProduct> orderProducts = orderProductMapper.selectByExample(orderproductExample);
                for(OrderProduct temp : orderProducts){
                    ProductInfo productInfo = new ProductInfo();
                    productInfo.setProductId(temp.getProductId());
                    productInfo.setCount(temp.getSkuCount());
                    productInfo.setPrice(temp.getPrice());
                    productInfo.setProductName(temp.getProductName());
                    productInfo.setProductPropertis(temp.getProductProperty());
                    productInfo.setPoint(temp.getPoint());
                    productInfo.setDisImages(temp.getDisImages());
                    productInfos.add(productInfo);
                }
                packageProduct.setProducts(productInfos);
                packageProductList.add(packageProduct);
            }
        }
        
        return packageProductList;
    }
    
}
