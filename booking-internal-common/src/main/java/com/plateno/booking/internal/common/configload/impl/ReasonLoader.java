package com.plateno.booking.internal.common.configload.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Service;

import com.plateno.booking.internal.common.configload.Loader;
import com.plateno.booking.internal.common.util.json.JsonUtils;

@Service("reasonLoader")
public class ReasonLoader extends Loader {

	public static JsonNode reasonLoader;

	private JsonNode getJson(){
		InputStream in = null;
        try {
        	in = new BufferedInputStream( new FileInputStream(new File("config/refundReason_config.json")));
        	JsonNode reasonLoaderTemp = JsonUtils.getJsonNodefromStream(in);
        	return reasonLoaderTemp;
        } catch (IOException e) {
            e.printStackTrace();
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
