package com.plateno.booking.internal.common.configload;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service("configLoader")
public class ConfigLoader{

	private List<Loader> loaders = new ArrayList<Loader>();
	
	public void register(Loader loader){
		loaders.add(loader);
	}
	
	public void remove(Loader loader){
		loaders.remove(loader);
	}
	
	public boolean init(){
		for (Loader loader : loaders) {
			if (!loader.load()) {
				return false;
			}
		}
		return true;
	}

	public boolean reload(){
		for (Loader loader:loaders){
			if (!loader.reload()){
				return false;
			}
		}
		return true;
	}

}
