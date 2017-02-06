package com.plateno.booking.internal.service.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MAddBookingParam;
import com.plateno.booking.internal.bean.request.custom.MOrderGoodsParam;
import com.plateno.booking.internal.bean.vo.order.ProductPriceVo;
import com.plateno.booking.internal.goods.MallGoodsService;
import com.plateno.booking.internal.wechat.model.ProductSkuBean;

/**
 * 
* @ClassName: CalProductService 
* @Description: 计算产品的各种价格
* @author zhengchubin
* @date 2017年1月23日 上午9:27:50 
*
*/
@Service
public class ProductCalService {

    protected final static Logger logger = LoggerFactory.getLogger(ProductCalService.class);
    
    @Autowired
    private MallGoodsService mallGoodsService;
    
    public ProductPriceVo calProduct(MAddBookingParam addBookingParam) throws OrderException{
        ProductPriceVo productPriceVo = new ProductPriceVo();
        int totalProductPrice = 0;
        int totalExpressCost = 0;
        int totalProductCost = 0;
        for(MOrderGoodsParam orderGoodsParam : addBookingParam.getGoodsList()){
            try {
                //todo：多次获取商品服务sku，需要优化，减少获取的次数
                ProductSkuBean pskubean=mallGoodsService.getProductAndskuStock(orderGoodsParam.getGoodsId().toString());
                int expressFee = 0;
                int price = 0;
                if(pskubean.getExpressFee() != null && pskubean.getExpressFee() > 0) {
                    expressFee = pskubean.getExpressFee();
                }
                //判断是否有促销价
                if(pskubean.getPromotPrice() != null && pskubean.getPromotPrice() > 0) {
                    price = pskubean.getPromotPrice();
                } else {
                    price = pskubean.getRegularPrice();
                }
                //商品价格*数量+快递费
                totalProductPrice = totalProductPrice + price * orderGoodsParam.getQuantity() + expressFee;
                //快递费成本
                totalExpressCost = totalExpressCost + pskubean.getCostExpress();
                //商品成本价
                totalProductCost = totalProductCost + pskubean.getCostPrice() * orderGoodsParam.getQuantity();
            } catch (OrderException e) {
                logger.error("获取商品信息失败");
                throw new OrderException("获取商品信息失败");
            }
            
        }
        productPriceVo.setTotalProductPrice(totalProductPrice);
        productPriceVo.setTotalExpressCost(totalExpressCost);
        productPriceVo.setTotalProductCost(totalProductCost);
        return productPriceVo;
    }
    
}
