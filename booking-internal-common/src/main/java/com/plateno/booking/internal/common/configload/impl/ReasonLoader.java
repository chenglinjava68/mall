package com.plateno.booking.internal.common.configload.impl;

import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.common.configload.Loader;
import com.plateno.booking.internal.common.util.json.JsonUtils;

@Service("reasonLoader")
public class ReasonLoader extends Loader {
	
	private final static Logger logger = LoggerFactory.getLogger(ReasonLoader.class);

	public static JsonNode reasonLoader;

	private JsonNode getJson(){
		InputStream in = null;
        try {
        	//in = new BufferedInputStream( new FileInputStream(new File("classpath*:refundReason_config.json")));
        	in = ReasonLoader.class.getResourceAsStream("/refundReason_config.json");
        	JsonNode reasonLoaderTemp = JsonUtils.getJsonNodefromStream(in);
        	logger.info("退款理由json初始化:{}", reasonLoaderTemp.toString());
        	return reasonLoaderTemp;
        } catch (IOException e) {
        	logger.error("退款理由json初始化失败", e);
            return null;
        } finally {
        	if (in != null) {        		
        		try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
	}
	
	@Override
	public boolean reload() {
		JsonNode reasonLoaderTemp = getJson();
		if (reasonLoaderTemp != null) {
			reasonLoader = reasonLoaderTemp;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean load() {
		JsonNode reasonLoaderTemp = getJson();
		if (reasonLoaderTemp != null) {
			reasonLoader = reasonLoaderTemp;
			return true;
		} else {
			return false;
		}
	}

}
