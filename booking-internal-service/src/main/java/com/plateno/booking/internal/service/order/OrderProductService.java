package com.plateno.booking.internal.service.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;

@Service
public class OrderProductService {


    @Autowired
    private OrderProductMapper orderProductMapper;

    /**
     * 
    * @Title: queryProductInfosByOrderSubNo 
    * @Description: 根据子订单号查询productInfos
    * @param @param orderSubNo
    * @param @return    
    * @return List<ProductInfo>    
    * @throws
     */
    public List<ProductInfo> queryProductInfosByOrderSubNo(String orderSubNo) {
        // 根据子订单号查询订单商品数据
        List<ProductInfo> productInfos = Lists.newArrayList();
        OrderProductExample orderproductExample = new OrderProductExample();
        orderproductExample.createCriteria().andOrderSubNoEqualTo(orderSubNo);
        List<OrderProduct> orderProducts = orderProductMapper.selectByExample(orderproductExample);
        for (OrderProduct temp : orderProducts) {
            ProductInfo productInfo = new ProductInfo();
            copyOrderProduct(productInfo, temp);
            productInfos.add(productInfo);
        }
        return productInfos;
    }
    
    
    public List<Integer> queryProductInfosIntByOrderSubNo(String orderSubNo){
        List<ProductInfo> productInfos = queryProductInfosByOrderSubNo(orderSubNo);
        List<Integer> productInfoInts = Lists.newArrayList();
        for(ProductInfo productInfo : productInfos){
            productInfoInts.add(productInfo.getOrderProductId());
        }
        return productInfoInts;
    }
    
    
    /**
     * 
    * @Title: getProductNameByOrderSubNo 
    * @Description: 根据子订单号获取商品名称
    * @param @param orderSubNo
    * @param @return    
    * @return String    
    * @throws
     */
    public String getProductNameByOrderSubNo(String orderSubNo){
        List<ProductInfo> productInfos = this.queryProductInfosByOrderSubNo(orderSubNo);
        StringBuilder sb = new StringBuilder();
        for (ProductInfo orderProduct : productInfos) {
            sb.append(orderProduct.getProductName()).append(";");
        }
        return sb.toString();
    }
    
    /**
     * 
    * @Title: queryProductInfosByOrderNo 
    * @Description: 根据订单号获取productInfos
    * @param @param orderNo
    * @param @return    
    * @return List<ProductInfo>    
    * @throws
     */
    public List<ProductInfo> queryProductInfosByOrderNo(String orderNo) {
        // 根据子订单号查询订单商品数据
        List<ProductInfo> productInfos = Lists.newArrayList();
        OrderProductExample orderproductExample = new OrderProductExample();
        orderproductExample.createCriteria().andOrderNoEqualTo(orderNo);
        List<OrderProduct> orderProducts = orderProductMapper.selectByExample(orderproductExample);
        for (OrderProduct temp : orderProducts) {
            ProductInfo productInfo = new ProductInfo();
            copyOrderProduct(productInfo, temp);
            productInfos.add(productInfo);
        }
        return productInfos;
    }
    
    /**
     * 
    * @Title: queryProductInfoByOrderNo 
    * @Description: 根据订单号获取单个productInfo
    * @param @param orderNo
    * @param @return    
    * @return ProductInfo    
    * @throws
     */
    public ProductInfo queryProductInfoByOrderNo(String orderNo){
        List<ProductInfo> productInfos = this.queryProductInfosByOrderNo(orderNo);
        return productInfos.get(0);
    }
    
    /**
     * 
    * @Title: copyOrderProduct 
    * @Description: 复制orderProduct到productInfo
    * @param @param productInfo
    * @param @param orderProduct    
    * @return void    
    * @throws
     */
    public void copyOrderProduct(ProductInfo productInfo,OrderProduct orderProduct){
        productInfo.setOrderProductId(orderProduct.getId());
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
        productInfo.setOrderProductId(orderProduct.getId());
    }
    
    /**
     * 
    * @Title: queryProductInfosByPackageId 
    * @Description: 根据包裹id查询商品信息集合
    * @param @param packageId
    * @param @return    
    * @return List<ProductInfo>    
    * @throws
     */
    public List<ProductInfo> queryProductInfosByPackageId(Integer packageId) {
        // 根据包裹号
        List<ProductInfo> productInfos = Lists.newArrayList();
        List<OrderProduct> orderProducts = orderProductMapper.queryProductByPackageId(packageId);
        for (OrderProduct temp : orderProducts) {
            ProductInfo productInfo = new ProductInfo();
            copyOrderProduct(productInfo, temp);
            productInfos.add(productInfo);
        }
        return productInfos;
    }
    
    public List<OrderProduct> queryOrderProductByOrderNo(String orderNo){
        OrderProductExample example = new OrderProductExample();
        example.createCriteria().andOrderNoEqualTo(orderNo);
        return orderProductMapper.selectByExample(example);
    }
    
    public List<OrderProduct> queryOrderProductByOrderSubNo(String orderSubNo){
        OrderProductExample example = new OrderProductExample();
        example.createCriteria().andOrderSubNoEqualTo(orderSubNo);
        return orderProductMapper.selectByExample(example);
    }
    
}
