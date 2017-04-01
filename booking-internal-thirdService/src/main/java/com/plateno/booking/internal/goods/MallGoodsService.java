package com.plateno.booking.internal.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.MOrderGoodsParam;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.http.HttpUtils;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.goods.vo.OrderCheckDetail;
import com.plateno.booking.internal.goods.vo.OrderCheckReq;
import com.plateno.booking.internal.goods.vo.OrderCheckResponse;
import com.plateno.booking.internal.goods.vo.ProductSkuBean;
import com.plateno.booking.internal.goods.vo.SkuBean;
import com.plateno.booking.internal.goods.vo.SkuStock;
import com.plateno.booking.internal.goods.vo.ProductSkuBean.SkuPropertyInfos;

@Service
public class MallGoodsService {

    protected final static Logger logger = LoggerFactory.getLogger(MallGoodsService.class);

    public SkuBean getSkuProperty(String goodId) throws OrderException {
        SkuBean skuBean = new SkuBean();
        String response = "";
        try {
            ObjectNode node = JsonUtils.createObjectNode();
            node.put("productId", goodId);
            String url = Config.MALL_GOODS_URL + "/productService/skuProperty/" + goodId; // 商品服务接口
            LogUtils.httpLoggerInfo("发起商品查询请求的参数:" + JsonUtils.toJsonString(node));
            response = HttpUtils.httpGetRequest(url);
            LogUtils.httpLoggerInfo("底层商品服务返回数据结构:" + response);
            if (response != null && !StringUtils.isBlank(response)) {
                JsonNode responseNode = JsonUtils.parseJson(response);
                Integer resultCode = responseNode.path("resultCode").asInt();
                if (!resultCode.equals(BookingConstants.TRIP_GOOD_SERVER_SUCCESS_CODE)) {
                    String resultMsg = responseNode.path("resultMsg").asText();
                    LogUtils.sysErrorLoggerError("底层商品服务出错，接口返回错误信息：" + resultMsg, null);
                    return null;
                } else {
                    if (!responseNode.has("data"))
                        return null;
                    if (responseNode.path("data").isNull())
                        return null;
                    // BeanUtils.copyProperties(responseNode.path("data"),skuBean);
                    skuBean = JsonUtils.jsonToObject(responseNode.path("data"), SkuBean.class);
                    return skuBean;
                }
            } else {
                LogUtils.sysErrorLoggerError("商品服务返回数据异常", null);
                return null;
            }
        } catch (Exception e) {
            LogUtils.sysErrorLoggerError(String.format("底层商品服务请求失败,%s", response), e);
            return null;
        }
    }


    public SkuStock getSkuStock(String goodId, String skuProperties) throws OrderException {
        SkuStock skuStock = new SkuStock();
        String response = "";
        try {
            ObjectNode node = JsonUtils.createObjectNode();
            node.put("productId", goodId);
            node.put("sku_properties", skuProperties);
            String url = Config.MALL_GOODS_URL + "/productService/sku/"; // 商品服务接口
            LogUtils.httpLoggerInfo("商品sku库存价格查询请求的参数:" + JsonUtils.toJsonString(node));
            response =
                    HttpUtils.httpPostRequest(url, JsonUtils.toJsonString(node),
                            Config.CONNECT_SOKET_TIME_OUT_LONG, Config.CONNECT_TIME_OUT_LONG);
            LogUtils.httpLoggerInfo("底层商品sku库存接口返回数据结构:" + response);
            if (response != null && !StringUtils.isBlank(response)) {
                JsonNode responseNode = JsonUtils.parseJson(response);
                Integer resultCode = responseNode.path("resultCode").asInt();
                if (!resultCode.equals(BookingConstants.TRIP_GOOD_SERVER_SUCCESS_CODE)) {
                    String resultMsg = responseNode.path("resultMsg").asText();
                    LogUtils.sysErrorLoggerError("底层商品sku库存接口出错，接口返回错误信息：" + resultMsg, null);
                    return null;
                } else {
                    if (!responseNode.has("data"))
                        return null;
                    if (responseNode.path("data").isNull())
                        return null;
                    skuStock = JsonUtils.jsonToObject(responseNode.path("data"), SkuStock.class);
                    return skuStock;
                }
            } else {
                LogUtils.sysErrorLoggerError("底层商品sku库存接口返回数据异常", null);
                return null;
            }
        } catch (Exception e) {
            LogUtils.sysErrorLoggerError(String.format("底层商品sku库存接口请求失败,%s", response), e);
            return null;
        }
    }



    public ProductSkuBean getProductAndskuStock(String goodId) throws OrderException {
        ProductSkuBean skuStock = new ProductSkuBean();
        String response = "";
        try {
            String url = Config.MALL_GOODS_URL + "/productService/goods/" + goodId; // 商品服务接口
            LogUtils.httpLoggerInfo("查询请求的参数:" + JsonUtils.toJsonString(goodId));
            response = HttpUtils.httpGetRequest(url);
            LogUtils.httpLoggerInfo("商品库存接口返回数据结构:" + response);
            if (response != null && !StringUtils.isBlank(response)) {
                JsonNode responseNode = JsonUtils.parseJson(response);
                Integer resultCode = responseNode.path("resultCode").asInt();
                if (!resultCode.equals(BookingConstants.TRIP_GOOD_SERVER_SUCCESS_CODE)) {
                    String resultMsg = responseNode.path("resultMsg").asText();
                    LogUtils.sysErrorLoggerError("商品sku接口出错，接口返回错误信息：" + resultMsg, null);
                    return null;
                } else {
                    if (!responseNode.has("data"))
                        return null;
                    if (responseNode.path("data").isNull())
                        return null;
                    // BeanUtils.copyProperties(responseNode.path("data").path("goodsInfo"),skuStock);
                    skuStock =
                            JsonUtils.jsonToObject(responseNode.path("data").path("goodsInfo"),
                                    ProductSkuBean.class);
                    List<SkuPropertyInfos> list =
                            JsonUtils.jsonToObject(
                                    responseNode.path("data").path("skuPropertyInfos"), List.class,
                                    SkuPropertyInfos.class);
                    if (skuStock != null) {
                        skuStock.setSkuPropertyInfos(list);
                    }

                    return skuStock;
                }
            } else {
                LogUtils.sysErrorLoggerError("底层商品库存接口返回数据异常", null);
                return null;
            }
        } catch (Exception e) {
            LogUtils.sysErrorLoggerError(String.format("底层商品库存接口请求失败,%s", response), e);
            return null;
        }
    }


    /**
     * 退还库存
     * 
     * @param goodId
     * @param quantity
     * @return
     * @throws OrderException
     */
    public boolean modifyStock(String goodId, Integer quantity) throws OrderException {
        String response = "";
        try {
            ObjectNode node = JsonUtils.createObjectNode();
            node.put("skuId", goodId);
            node.put("quantity", quantity);
            String url =
                    Config.MALL_GOODS_URL + "/productService/goods/modifyStock?skuId=" + goodId
                            + "&quantity=" + quantity; // 商品服务接口
            LogUtils.httpLoggerInfo("查询请求的参数:" + JsonUtils.toJsonString(node));
            response = HttpUtils.httpGetRequest(url);
            LogUtils.httpLoggerInfo("商品sku库存接口返回数据结构:" + response);
            if (response != null && !StringUtils.isBlank(response)) {
                JsonNode responseNode = JsonUtils.parseJson(response);
                Integer resultCode = responseNode.path("resultCode").asInt();
                if (!resultCode.equals(BookingConstants.TRIP_GOOD_SERVER_SUCCESS_CODE)) {
                    String resultMsg = responseNode.path("resultMsg").asText();
                    LogUtils.sysErrorLoggerError("修改商品sku库存接口出错，接口返回错误信息：" + resultMsg, null);
                    return false;
                } else {
                    return true;
                }
            } else {
                LogUtils.sysErrorLoggerError("修改商品sku库存接口返回数据异常", null);
                return false;
            }
        } catch (Exception e) {
            LogUtils.sysErrorLoggerError(String.format("修改商品sku库存接口请求失败,%s", response), e);
            return false;
        }
    }

    /**
     * 
     * @Title: deductBatchStock
     * @Description: 购物车批量扣减库存
     * @param @param goodsList
     * @param @return
     * @return boolean
     * @throws
     */
    public boolean deductBatchStock(List<MOrderGoodsParam> goodsList) {
        String response = "";
        try {
            Map<String, Integer> mapParam = new HashMap<>();
            for (MOrderGoodsParam mOrderGoodsParam : goodsList) {
                mapParam.put(mOrderGoodsParam.getGoodsId() + "", mOrderGoodsParam.getQuantity());
            }

            String url = Config.MALL_GOODS_URL + "/productService/goods/deductBatchStock";
            String jsonString = JsonUtils.toJsonString(mapParam);
            response = HttpUtils.httpPostRequest(url, jsonString);
            if(StringUtils.isBlank(response)){
                logger.error("购物车批量扣减库存异常");
                return false;
            }
            
            JsonNode responseNode = JsonUtils.parseJson(response);
            Integer resultCode = responseNode.path("resultCode").asInt();
            if (!resultCode.equals(BookingConstants.TRIP_GOOD_SERVER_SUCCESS_CODE)) {
                String resultMsg = responseNode.path("resultMsg").asText();
                LogUtils.sysErrorLoggerError("购物车批量扣减库存：" + resultMsg, null);
                return false;
            } else {
                return true;
            }   

        } catch (Exception e) {
            logger.error("购物车批量扣减库存错误", e);
            return false;
        }
        
    }

    /**
     * 
    * @Title: deductBatchStock 
    * @Description: 购物车批量退还库存
    * @param @param goodsList
    * @param @return    
    * @return boolean    
    * @throws
     */
    public boolean returnBatchStock(List<MOrderGoodsParam> goodsList) {
        String response = "";
        try {
            Map<String, Integer> mapParam = new HashMap<>();
            for (MOrderGoodsParam mOrderGoodsParam : goodsList) {
                mapParam.put(mOrderGoodsParam.getGoodsId() + "", mOrderGoodsParam.getQuantity());
            }

            String url = Config.MALL_GOODS_URL + "/productService/goods/returnBatchStock";
            String jsonString = JsonUtils.toJsonString(mapParam);
            response = HttpUtils.httpPostRequest(url, jsonString);
            if(StringUtils.isBlank(response)){
                logger.error("购物车批量退还库存异常");
                return false;
            }
            
            JsonNode responseNode = JsonUtils.parseJson(response);
            Integer resultCode = responseNode.path("resultCode").asInt();
            if (!resultCode.equals(BookingConstants.TRIP_GOOD_SERVER_SUCCESS_CODE)) {
                String resultMsg = responseNode.path("resultMsg").asText();
                logger.error("购物车批量退还库存：{}",resultMsg);
                return false;
            } else {
                return true;
            }   
        } catch (Exception e) {
            logger.error("购物车批量退还库存", e);
            return false;
        }
        
    }
    
    /**
     * 
    * @Title: commitOrder 
    * @Description: 商品详情
    * @param @param req
    * @param @return    
    * @return CommitOrderDetail    
    * @throws
     */
    public OrderCheckDetail orderCheck(OrderCheckReq req){
        OrderCheckDetail orderCheckDetail = null;
        String response = "";
        try{
            String url = Config.MALL_GOODS_URL + "/productService/orderCheck";
            response = HttpUtils.httpPostRequest(url, JsonUtils.toJsonString(req));
            OrderCheckResponse orderCheckResponse= JsonUtils.jsonToObject(response, OrderCheckResponse.class);
            if(null == orderCheckResponse)
                return orderCheckDetail;
            if(!orderCheckResponse.getResultCode().equals("100")){
                logger.error("获取商品信息失败，req:{},res:{}",JsonUtils.toJsonString(req),response);
                return null;
            }
            return orderCheckResponse.getData();
            
        }catch(Exception e){
            logger.error("获取商品信息失败",e);
        }
        
        
        return orderCheckDetail;
    }
    
}
