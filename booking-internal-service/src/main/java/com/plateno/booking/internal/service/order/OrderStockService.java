package com.plateno.booking.internal.service.order;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.plateno.booking.internal.base.mapper.OrderProductMapper;
import com.plateno.booking.internal.base.pojo.OrderProduct;
import com.plateno.booking.internal.base.pojo.OrderProductExample;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MOrderGoodsParam;
import com.plateno.booking.internal.goods.MallGoodsService;

@Service
public class OrderStockService {

    private final static Logger logger = LoggerFactory.getLogger(OrderStockService.class);
    
    @Autowired
    private MallGoodsService mallGoodsService;
    
    @Autowired
    private OrderProductMapper orderProductMapper;
    
    /**
     * 
    * @Title: returnStock 
    * @Description: 返还库存
    * @param @param orderNo
    * @param @throws OrderException    
    * @return void    
    * @throws
     */
    public List<OrderProduct> returnStock(String orderNo)
            throws OrderException {
        OrderProductExample orderProductExample = new OrderProductExample();
        orderProductExample.createCriteria().andOrderNoEqualTo(orderNo);
        List<OrderProduct> productOrderList =
                orderProductMapper.selectByExample(orderProductExample);
        if (CollectionUtils.isEmpty(productOrderList)) {
            throw new OrderException(501,"订单获取不到对应的产品信息");
        }

        logger.info("取消订单，更新数据库return_sku_count， orderNo:{}", orderNo);
        
        boolean flag = true;
        //批量执行allowMultiQueries=true增加sql注入的风险，不建议使用
        for(OrderProduct orderProduct : productOrderList){
            int row = orderProductMapper.updateReturnSkuCount(orderProduct.getSkuCount(), orderProduct.getId());
            if(row == 0)
                flag = false;
        }
        if(flag){
            logger.info("取消订单，调用商品服务，返回库存， orderNo:{}", orderNo);
            List<MOrderGoodsParam> goodsList = Lists.newArrayList();
            for(OrderProduct orderProduct : productOrderList){
                MOrderGoodsParam orderGoodsParam = new MOrderGoodsParam();
                orderGoodsParam.setGoodsId(Long.valueOf(orderProduct.getSkuid()));
                orderGoodsParam.setQuantity(orderProduct.getSkuCount());
                goodsList.add(orderGoodsParam);
            }
            if(!mallGoodsService.returnBatchStock(goodsList)){
                logger.error("取消订单返回库存失败, orderNo:{}", orderNo);
                throw new OrderException(502,"取消订单返回库存失败, orderNo:"+orderNo);
            }
        }
        return productOrderList;
    }
    
    
}
