package com.plateno.booking.internal.service.logistics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.plateno.booking.internal.base.constant.PackageStatusEnum;
import com.plateno.booking.internal.base.constant.PlateFormEnum;
import com.plateno.booking.internal.base.mapper.LogisticsPackageMapper;
import com.plateno.booking.internal.base.mapper.MLogisticsMapper;
import com.plateno.booking.internal.base.mapper.OrderMapper;
import com.plateno.booking.internal.base.pojo.LogisticsPackage;
import com.plateno.booking.internal.base.pojo.LogisticsPackageExample;
import com.plateno.booking.internal.base.pojo.MLogistics;
import com.plateno.booking.internal.base.pojo.MLogisticsExample;
import com.plateno.booking.internal.base.pojo.Order;
import com.plateno.booking.internal.bean.config.Config;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants.MsgCode;
import com.plateno.booking.internal.bean.contants.OperateLogEnum;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.bean.exception.OrderException;
import com.plateno.booking.internal.bean.request.custom.DeliverGoodParam;
import com.plateno.booking.internal.bean.request.custom.MOperateLogParam;
import com.plateno.booking.internal.bean.request.custom.MOrderParam;
import com.plateno.booking.internal.bean.request.custom.ReceiptParam;
import com.plateno.booking.internal.bean.request.logistics.OrderLogisticsQueryReq;
import com.plateno.booking.internal.bean.response.custom.OrderDetail.ProductInfo;
import com.plateno.booking.internal.bean.response.logistics.PackageProduct;
import com.plateno.booking.internal.conf.data.LogisticsTypeData;
import com.plateno.booking.internal.dao.mapper.LogisticsMapperExt;
import com.plateno.booking.internal.email.model.DeliverGoodContent;
import com.plateno.booking.internal.email.service.PhoneMsgService;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;
import com.plateno.booking.internal.service.log.OperateLogService;
import com.plateno.booking.internal.service.log.OrderLogService;
import com.plateno.booking.internal.service.order.OrderProductService;
import com.plateno.booking.internal.service.order.OrderUpdateService;
import com.plateno.booking.internal.validator.order.MOrderValidate;

@Service
public class LogisticsService {

    protected final Logger logger = Logger.getLogger(LogisticsService.class);

    @Autowired
    private MLogisticsMapper mLogisticsMapper;
    @Autowired
    private LogisticsPackageMapper logisticsPackageMapper;
    @Autowired
    private OrderMapper mallOrderMapper;
    @Autowired
    private MOrderValidate orderValidate;
    @Autowired
    private OrderLogService orderLogService;
    @Autowired
    private PhoneMsgService phoneMsgService;
    @Autowired
    private OperateLogService operateLogService;
    @Autowired
    private OrderProductService orderProductService;
    @Autowired
    private LogisticsMapperExt logisticsMapperExt;
    @Autowired
    private OrderUpdateService orderUpdateService;
    
    
    /**
     * 
    * @Title: queryOrderLogistics 
    * @Description: 查询物流信息
    * @param @param param
    * @param @return    
    * @return List<PackageProduct>    
    * @throws
     */
    public List<PackageProduct> queryOrderLogistics(OrderLogisticsQueryReq param) {
        //根据父订单号查询
        if(StringUtils.isNotBlank(param.getOrderNo()))
            return findByOrderNo(param.getOrderNo());
        //根据子订单查询
        if(StringUtils.isNotBlank(param.getOrderSubNo()))
            return findByOrderSubNo(param.getOrderSubNo());
        return new ArrayList<PackageProduct>();
    }

    /**
     * 
    * @Title: findByOrderSubNo 
    * @Description: 根据子订单号查询包裹信息
    * @param @param orderSubNo
    * @param @return    
    * @return List<PackageProduct>    
    * @throws
     */
    private List<PackageProduct> findByOrderSubNo(String orderSubNo){
        List<PackageProduct> packageProductList = Lists.newArrayList();
        LogisticsPackageExample logisticsPackageExample = new LogisticsPackageExample();
        logisticsPackageExample.createCriteria().andOrderSubNoEqualTo(orderSubNo);
        List<LogisticsPackage> logisticsPackageList =
                logisticsPackageMapper.selectByExample(logisticsPackageExample);
        for (LogisticsPackage logisticsPackage : logisticsPackageList) {
            PackageProduct packageProduct = new PackageProduct();
            copyPackage(logisticsPackage, packageProduct);
            packageProduct.setProducts(orderProductService.queryProductInfosByOrderSubNo(orderSubNo));
            packageProductList.add(packageProduct);
        }
        return packageProductList;
    }

    /**
     * 
    * @Title: findByOrderNo 
    * @Description: 根据父订单查询包裹信息
    * @param @param orderNo
    * @param @return    
    * @return List<PackageProduct>    
    * @throws
     */
    private List<PackageProduct> findByOrderNo(String orderNo) {
        List<PackageProduct> packageProductList = Lists.newArrayList();
        // 兼容旧的数据，旧的数据只有一个包裹
        MLogisticsExample mLogisticsExample = new MLogisticsExample();
        mLogisticsExample.createCriteria().andOrderNoEqualTo(orderNo);
        List<MLogistics> mLogisticsList = mLogisticsMapper.selectByExample(mLogisticsExample);
        if (CollectionUtils.isEmpty(mLogisticsList))
            return packageProductList;

        MLogistics mLogistics = mLogisticsList.get(0);
        // 假如存在快递单号，则为旧的数据，则直接从m_logistics取数据
        if (StringUtils.isNotBlank(mLogistics.getLogisticsNo())) {
            PackageProduct packageProduct = new PackageProduct();
            packageProduct.setLogisticsNo(mLogistics.getLogisticsNo());
            packageProduct.setLogisticsType(mLogistics.getLogisticsType());
            packageProduct.setLogisticsName(LogisticsTypeData.getDataMap().get(
                    mLogistics.getLogisticsType()));
            packageProduct.setExpressFee(mLogistics.getExpressFee());
            Order order = mallOrderMapper.getOrderByNo(orderNo).get(0);
            packageProduct.setCreateTime(order.getDeliverTime());
            packageProduct.setProducts(orderProductService.queryProductInfosByOrderNo(orderNo));
            packageProductList.add(packageProduct);
        } else {
            LogisticsPackageExample logisticsPackageExample = new LogisticsPackageExample();
            logisticsPackageExample.createCriteria().andOrderNoEqualTo(orderNo);
            List<LogisticsPackage> logisticsPackageList =
                    logisticsPackageMapper.selectByExample(logisticsPackageExample);
            for (LogisticsPackage logisticsPackage : logisticsPackageList) {
                PackageProduct packageProduct = new PackageProduct();
                copyPackage(logisticsPackage, packageProduct);
                packageProduct.setProducts(orderProductService.queryProductInfosByOrderNo(orderNo));
                packageProductList.add(packageProduct);
            }
        }
        return packageProductList;
    }

    /**
     * 
    * @Title: copyPackage 
    * @Description: 拷贝属性
    * @param @param logisticsPackage
    * @param @param packageProduct    
    * @return void    
    * @throws
     */
    private void copyPackage(LogisticsPackage logisticsPackage,PackageProduct packageProduct){
        packageProduct.setLogisticsNo(logisticsPackage.getLogisticsNo());
        packageProduct.setLogisticsType(logisticsPackage.getLogisticsType());
        packageProduct.setLogisticsName(LogisticsTypeData.getDataMap().get(
                logisticsPackage.getLogisticsType()));
        packageProduct.setExpressFee(logisticsPackage.getExpressFee());
        packageProduct.setCreateTime(logisticsPackage.getCreateTime());
        packageProduct.setPackageFlagName(PackageStatusEnum.from(logisticsPackage
                .getPackageFlag()));
    }
    
    /**
     * 更新订单状态(发货通知)
     * 
     * @param orderParam
     * @return
     * @throws Exception
     */
    @Transactional
    public ResultVo<Object> deliverOrder(final MOrderParam orderParam){
        ResultVo<Object> output = new ResultVo<Object>();
        // 校验订单是否可被处理
        List<Order> listOrder =
                mallOrderMapper.queryOrderByOrderSubNo(orderParam.getOrderSubNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        Order order = listOrder.get(0);
        orderValidate.checkDeliverOrder(order, output);
        if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
            return output;
        }
        // 修改订单状态为待收货状态
        orderUpdateService.updateToPayStatus_4(order);
        orderLogService.saveGSOrderLogWithOrderSubNo(order.getOrderNo(),
                BookingResultCodeContants.PAY_STATUS_4, "发货操作", "发货成功", 0,
                ViewStatusEnum.VIEW_STATUS_DELIVERS.getCode(),orderParam.getOrderSubNo());
        
        //删除子订单下旧的包裹数据
        logisticsMapperExt.delPackageByOrderSubNo(orderParam.getOrderSubNo());
        //批量新增包裹
        insertPackageBatch(order, orderParam);
        // 发送到短信
        sendMsg(orderParam, order);
        // 记录操作日志
        recoredDeliverLog(orderParam, order);
        return output;
    }

    /**
     * 
    * @Title: sendMsg 
    * @Description: 发送短信
    * @param @param orderParam
    * @param @param order    
    * @return void    
    * @throws
     */
    private void sendMsg(MOrderParam orderParam,Order order){
        DeliverGoodContent content = new DeliverGoodContent();
        content.setObjectNo(order.getOrderNo());
        content.setOrderCode(order.getOrderNo());
        content.setName(orderProductService.getProductNameByOrderSubNo(orderParam.getOrderSubNo()));
        content.setExpress(LogisticsTypeData.getDataMap().get(orderParam.getLogisticsType()));
        content.setExpressCode(StringUtils.isBlank(orderParam.getLogisticsNo()) ? "无" : orderParam
                .getLogisticsNo());
        phoneMsgService.sendPhoneMessageAsync(order.getMobile(), Config.SMS_SERVICE_TEMPLATE_SEVEN,
                content);
    }
    
    /**
     * 
    * @Title: recoredDeliverLog 
    * @Description: 记录发货日志
    * @param @param orderParam
    * @param @param order    
    * @return void    
    * @throws
     */
    private void recoredDeliverLog(MOrderParam orderParam,Order order){
        MOperateLogParam paramlog = new MOperateLogParam();
        paramlog.setOperateType(OperateLogEnum.DELIVER_ORDER.getOperateType());
        paramlog.setOperateUserid(orderParam.getOperateUserid());
        paramlog.setOperateUsername(orderParam.getOperateUsername());
        paramlog.setOrderCode(order.getOrderNo());
        paramlog.setPlateForm(orderParam.getPlateForm());
        paramlog.setRemark(OperateLogEnum.DELIVER_ORDER.getOperateName());
        operateLogService.saveOperateLog(paramlog);
    }
    
    /**
     * 
    * @Title: insertPackageBatch 
    * @Description: 批量新增包裹
    * @param @param orderParam    
    * @return void    
    * @throws
     */
    private void insertPackageBatch(Order order,MOrderParam orderParam){
        for (DeliverGoodParam deliverGoodParam : orderParam.getDeliverGoodParams()) {
            LogisticsPackage logisticsPackage = new LogisticsPackage();
            logisticsPackage.setLogisticsNo(deliverGoodParam.getLogisticsNo());
            logisticsPackage.setLogisticsType(deliverGoodParam.getLogisticsType());
            logisticsPackage.setCreateTime(new Date());
            logisticsPackage.setPackageFlag(PackageStatusEnum.START.getType());// 发货状态
            logisticsPackage.setOrderSubNo(orderParam.getOrderSubNo());
            logisticsPackage.setOrderNo(order.getOrderNo());
            logisticsPackageMapper.insertSelective(logisticsPackage);
            // 批量新增到logistics_product表中
            logisticsMapperExt.insertBatch(logisticsPackage.getId(),
                    deliverGoodParam.getOrderProductIds());
        }
    }
    
    
    /**
     * 
     * @Title: modifydeliverOrder
     * @Description: 修改发货信息
     * @param @param orderParam
     * @param @return
     * @param @throws Exception
     * @return ResultVo<Object>
     * @throws
     */
    public ResultVo<Object> modifydeliverOrder(final MOrderParam orderParam) throws Exception {
        ResultVo<Object> output = new ResultVo<Object>();
        // 校验订单是否可被处理
        List<Order> listOrder =
                mallOrderMapper.queryOrderByOrderSubNo(orderParam.getOrderSubNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }

        LogisticsPackageExample example = new LogisticsPackageExample();
        example.createCriteria().andOrderSubNoEqualTo(orderParam.getOrderSubNo());
        LogisticsPackage logisticsPackage = new LogisticsPackage();
        // 物流改成自提，需要把原来的快递单号设置成空
        logisticsPackage.setLogisticsNo(StringUtils.trimToEmpty(orderParam.getLogisticsNo()));
        logisticsPackage.setLogisticsType(orderParam.getLogisticsType());
        logisticsPackage.setUpTime(new Date());
        logisticsPackageMapper.updateByExampleSelective(logisticsPackage, example);


        MOperateLogParam paramlog = new MOperateLogParam();
        paramlog.setOperateType(OperateLogEnum.MODIFY_DELIVER_OP.getOperateType());
        paramlog.setOperateUserid(orderParam.getOperateUserid());
        paramlog.setOperateUsername(orderParam.getOperateUsername());
        paramlog.setOrderCode(orderParam.getOrderNo());
        paramlog.setPlateForm(orderParam.getPlateForm());
        String remark =
                OperateLogEnum.MODIFY_DELIVER_OP.getOperateName()
                        + String.format(":%s|%s|%s", orderParam.getLogisticsNo(),
                                orderParam.getLogisticsType(),
                                LogisticsTypeData.getDataMap().get(orderParam.getLogisticsType()));
        remark = remark.length() > 99 ? remark.substring(0, 99) : remark;
        paramlog.setRemark(remark);
        operateLogService.saveOperateLog(paramlog);
        return output;
    }


    @Transactional(rollbackFor = Exception.class)
    public ResultVo<Object> enterReceipt(final MOrderParam orderParam) throws Exception {
        ResultVo<Object> output = new ResultVo<Object>();
        // 校验订单是否可被处理
        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(orderParam.getOrderNo(),
                        orderParam.getMemberId(), orderParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }
        orderValidate.checkEnterReceiptStatus(listOrder.get(0), output);
        if (!output.getResultCode().equals(MsgCode.SUCCESSFUL.getMsgCode())) {
            return output;
        }

        // 修改订单状态为已完成
        Order order = listOrder.get(0);
        orderUpdateService.updateToPayStatus_5(order);
        orderLogService.saveGSOrderLog(orderParam.getOrderNo(),
                BookingResultCodeContants.PAY_STATUS_5, "确认收货", "手动确定收货", 0,
                ViewStatusEnum.VIEW_STATUS_COMPLETE.getCode());
        recordReceiveLog(orderParam);
        return output;
    }

    /**
     * 
    * @Title: recordReceiveLog 
    * @Description: 记录收货日志
    * @param @param orderParam    
    * @return void    
    * @throws
     */
    private void recordReceiveLog(MOrderParam orderParam){
        // 如果是后台操作，记录操作日志
        if (orderParam.getPlateForm() != null
                && (orderParam.getPlateForm() == PlateFormEnum.ADMIN.getPlateForm() || orderParam
                        .getPlateForm() == PlateFormEnum.PROVIDER_ADMIN.getPlateForm())) {
            MOperateLogParam paramlog = new MOperateLogParam();
            paramlog.setOperateType(OperateLogEnum.ENTER_RECEIPT.getOperateType());
            paramlog.setOperateUserid(orderParam.getOperateUserid());
            paramlog.setOperateUsername(orderParam.getOperateUsername());
            paramlog.setOrderCode(orderParam.getOrderNo());
            paramlog.setPlateForm(orderParam.getPlateForm());
            paramlog.setRemark(OperateLogEnum.ENTER_RECEIPT.getOperateName());
            operateLogService.saveOperateLog(paramlog);
        }
    }
    
    
    
    public ResultVo<Object> modifyReceiptInfo(ReceiptParam receiptParam) throws OrderException,
            Exception {
        ResultVo<Object> output = new ResultVo<Object>();

        receiptParam.setReceiptAddress(StringUtils.trimToEmpty(receiptParam.getReceiptAddress()));
        receiptParam.setReceiptMobile(StringUtils.trimToEmpty(receiptParam.getReceiptMobile()));
        receiptParam.setReceiptName(StringUtils.trimToEmpty(receiptParam.getReceiptName()));
        receiptParam.setProvince(StringUtils.trimToEmpty(receiptParam.getProvince()));
        receiptParam.setCity(StringUtils.trimToEmpty(receiptParam.getCity()));
        receiptParam.setArea(StringUtils.trimToEmpty(receiptParam.getArea()));

        List<Order> listOrder =
                mallOrderMapper.getOrderByNoAndMemberIdAndChannelId(receiptParam.getOrderNo(),
                        receiptParam.getMemberId(), receiptParam.getChannelId());
        if (CollectionUtils.isEmpty(listOrder)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("订单查询失败,获取不到订单");
            return output;
        }

        MLogisticsExample mLogisticsExample = new MLogisticsExample();
        mLogisticsExample.createCriteria().andOrderNoEqualTo(receiptParam.getOrderNo());
        List<MLogistics> listLogistic = mLogisticsMapper.selectByExample(mLogisticsExample);
        if (CollectionUtils.isEmpty(listLogistic)) {
            output.setResultCode(getClass(), MsgCode.BAD_REQUEST.getMsgCode());
            output.setResultMsg("查询不到对应的收货信息");
            return output;
        }
        MLogistics logc = listLogistic.get(0);

        // 原始的地址不修改，只是修改最新的地址
        logc.setConsigneeNewaddress(receiptParam.getReceiptAddress());
        logc.setConsigneeNewMobile(receiptParam.getReceiptMobile());
        logc.setConsigneeNewName(receiptParam.getReceiptName());
        logc.setNewProvince(receiptParam.getProvince());
        logc.setNewCity(receiptParam.getCity());
        logc.setNewArea(receiptParam.getArea());
        mLogisticsMapper.updateByPrimaryKeySelective(logc);


        MOperateLogParam paramlog = new MOperateLogParam();
        paramlog.setOperateType(OperateLogEnum.MODIFY_DELIVER_OP.getOperateType());
        paramlog.setOperateUserid(receiptParam.getOperateUserid());
        paramlog.setOperateUsername(receiptParam.getOperateUsername());
        paramlog.setOrderCode(receiptParam.getOrderNo());
        paramlog.setPlateForm(receiptParam.getPlateForm());
        String remark =
                OperateLogEnum.MODIFY_DELIVER_OP.getOperateName()
                        + String.format(":%s|%s|%s|%s|%s|%s", receiptParam.getReceiptName(),
                                receiptParam.getReceiptMobile(), receiptParam.getProvince(),
                                receiptParam.getCity(), receiptParam.getArea(),
                                receiptParam.getReceiptAddress());
        remark = remark.length() > 99 ? remark.substring(0, 99) : remark;
        paramlog.setRemark(remark);
        operateLogService.saveOperateLog(paramlog);

        return output;
    }

}
