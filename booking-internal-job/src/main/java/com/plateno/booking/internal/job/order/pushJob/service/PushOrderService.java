/*package com.plateno.booking.internal.job.order.pushJob.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.plateno.booking.internal.base.mapper.MOrderJobMapper;
import com.plateno.booking.internal.base.pojo.MOrderJob;
import com.plateno.booking.internal.bean.contants.BookingConstants;
import com.plateno.booking.internal.common.util.redis.RedisUtils;
import com.plateno.booking.internal.dao.mapper.BillMapper;
import com.plateno.booking.internal.dao.pojo.BillDetails;

*//**
 * 生成异常订单入redis
 * 
 * @author user
 *
 *//*
@Service
public class PushOrderService {
	
	@Autowired
	private BillMapper billMapper;
	@Autowired
	private RedisUtils redisUtils;
	@Autowired
	private MOrderJobMapper orderJobMapper;
	
	public void selectBillByStatus(Integer time){
		List<BillDetails> billDetails = billMapper.selectBillByTime(time, BookingConstants.PUSH_ORDER_STATUS);
		if (billDetails == null || billDetails.size() == 0)
			return;
		//删除表记录
		orderJobMapper.deleteByExample(null);
		for(BillDetails billDetail : billDetails){
			//网关支付中200,且状态停留时间已超过15分钟
			if (billDetail.getStatus().equals(BookingConstants.PAY_STATUS_200)) {
				//60 * 15 * 1000 == 15min
				if ((System.currentTimeMillis() - billDetail.getCreateTime().getTime()) > 15 * 60 * 1000 ) {
					redisUtils.sadd(BookingConstants.ORDER_STATUS_200, billDetail.getTradeNo());
					insertOrderJob(billDetail,BookingConstants.PAY_STATUS_200);
				}
			}
			
			//OTA支付中300,且状态停留时间已超过15分钟
			if (billDetail.getStatus().equals(BookingConstants.PAY_STATUS_300)) {
				if ((System.currentTimeMillis() - billDetail.getUpTime().getTime()) > 15 * 60 * 1000 ) {
					redisUtils.sadd(BookingConstants.ORDER_STATUS_300, billDetail.getTradeNo());
					insertOrderJob(billDetail,BookingConstants.PAY_STATUS_300);
				}
			}
			
			//网关支付成功300,但OTA返回支付失败302
			if (billDetail.getStatus().equals(BookingConstants.PAY_STATUS_302)) {
				redisUtils.sadd(BookingConstants.ORDER_STATUS_302, billDetail.getTradeNo());
				insertOrderJob(billDetail,BookingConstants.PAY_STATUS_302);
			}
			
			//OTA出票中303,且下单时间已超过30分钟
			if (billDetail.getStatus().equals(BookingConstants.PAY_STATUS_303)) {
				if ((System.currentTimeMillis() - billDetail.getUpTime().getTime()) > 30 * 60 * 1000 ) {
					redisUtils.sadd(BookingConstants.ORDER_STATUS_303, billDetail.getTradeNo());
					insertOrderJob(billDetail,BookingConstants.PAY_STATUS_303);
				}
			}
			
			//OTA退款申请中400,且状态停留时间已超过72小时
			if (billDetail.getStatus().equals(BookingConstants.PAY_STATUS_400)) {
				if ((System.currentTimeMillis() - billDetail.getUpTime().getTime()) > 24 * 60 * 60 * 1000 * 3 ) {
					redisUtils.sadd(BookingConstants.ORDER_STATUS_400, billDetail.getTradeNo());
					insertOrderJob(billDetail,BookingConstants.PAY_STATUS_400);
				}
			}
			
			//网关退款申请中500,且状态停留时间已超过24小时
			if (billDetail.getStatus().equals(BookingConstants.PAY_STATUS_500)) {
				if ((System.currentTimeMillis() - billDetail.getUpTime().getTime()) > 24 * 60 * 60 * 1000 ) {
					redisUtils.sadd(BookingConstants.ORDER_STATUS_500, billDetail.getTradeNo());
					insertOrderJob(billDetail,BookingConstants.PAY_STATUS_500);
				}
			}
			
			//网关退款失败502
			if (billDetail.getStatus().equals(BookingConstants.PAY_STATUS_502)) {
				redisUtils.sadd(BookingConstants.ORDER_STATUS_502, billDetail.getTradeNo());
				insertOrderJob(billDetail,BookingConstants.PAY_STATUS_502);
			}
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	private void insertOrderJob(BillDetails billDetail,Integer status){
		try {
			MOrderJob job = new MOrderJob();
			job.setBillid(billDetail.getBillId());
			job.setType(status);
			orderJobMapper.insertSelective(job);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
*/