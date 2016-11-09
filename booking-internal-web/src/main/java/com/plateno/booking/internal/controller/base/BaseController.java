package com.plateno.booking.internal.controller.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.plateno.booking.internal.base.constant.PlateFormEnum;
import com.plateno.booking.internal.base.model.BaseParam;
import com.plateno.booking.internal.bean.contants.BookingResultCodeContants;
import com.plateno.booking.internal.common.util.json.JsonUtils;
import com.plateno.booking.internal.interceptor.adam.common.bean.ResultVo;

public class BaseController{
	
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	
	protected void bindingResultHandler(BindingResult result) throws Exception{
		if(result.getErrorCount()>0){
			List<ObjectError> errors = result.getAllErrors();
			for(ObjectError error:errors){
				String errorMsg = "";
				if(error instanceof FieldError){
					FieldError fe = (FieldError)error;
					errorMsg = String.format("%s:%s",fe.getField(),error.getDefaultMessage());
					log.error(errorMsg);
				}else{
					errorMsg = String.format("%s:%s",error.getCode(),error.getDefaultMessage());
					log.error(errorMsg);
				}
				throw new Exception(errorMsg);
			}
		}
	}
	
	@ExceptionHandler
	public ResultVo<Object> handleException(Exception e){
		ResultVo<Object> response = new ResultVo<Object>();
		response.setResultCode(this.getClass(),BookingResultCodeContants.MsgCode.BAD_REQUEST.getMsgCode());
		if (e instanceof NullPointerException) {
			response.setResultMsg("参数空指针异常");
		}if(e instanceof HttpMessageNotReadableException){
			response.setResultMsg("请求参数匹配错误,"+e.getLocalizedMessage());
		}else{
			response.setResultMsg(e.getMessage());
		}
		log.error(String.format("请求异常[%s]",e), e);
		return response;
	}
	
	protected JsonNode getRequestNode(HttpServletRequest request)
			throws IOException {
		
		String result = getRequestString(request);
		log.info("请求入参：" + result);
		JsonNode params = (ObjectNode) JsonUtils.parseJson(result);
		return params;
	}
	
	private String getRequestString(HttpServletRequest request) {
		BufferedReader br = null;
		try {
			br = request.getReader();
			String tmp;
			StringBuilder sb = new StringBuilder();
			while ((tmp = br.readLine()) != null) {
				sb.append(tmp);
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if (br!=null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 检查基本参数是否正确
	 * @param baseParam
	 * @return
	 * @throws Exception
	 */
	public void checkBaseParam(BaseParam baseParam) throws Exception {
		
		if(baseParam.getPlateForm() == null || baseParam.getPlateForm() <= 0) {
			log.error("plateForm 格式不正确:{}", baseParam.getPlateForm());
			throw new Exception("请输入正确的plateForm：" + baseParam.getPlateForm());
		}
        if(!PlateFormEnum.has(baseParam.getPlateForm())) {
        	log.error("plateForm 无法识别:{}", baseParam.getPlateForm());
			throw new Exception("请输入正确的plateForm：" + baseParam.getPlateForm());
        }
        
        switch (PlateFormEnum.from(baseParam.getPlateForm())) {
		case PROVIDER_ADMIN: //商城前端
			
			if(baseParam.getChannelId() == null || baseParam.getChannelId() <= 0) {
				log.error("channelId 格式不正确:{}", baseParam.getChannelId());
				throw new Exception("请输入正确的channelId：" + baseParam.getChannelId());
			}
			
			break;
		case USER: //供应商后台
			
			if(baseParam.getMemberId() == null || baseParam.getMemberId() <= 0) {
				log.error("memberId 格式不正确:{}", baseParam.getMemberId());
				throw new Exception("请输入正确的memberId：" + baseParam.getMemberId());
			}
			break;

		default:
			break;
		}
        
    }
}
