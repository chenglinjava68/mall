package com.plateno.booking.internal.common.util.redis;

import redis.clients.jedis.JedisShardInfo;


/**
 * ━━━━━━神兽出没━━━━━━
 *　　　┏┓　　　┏┓
 *　　┏┛┻━━━┛┻┓
 *　　┃　　　　　　　┃
 *　　┃　　　━　　　┃
 *　　┃　┳┛　┗┳　┃
 *　　┃　　　　　　　┃
 *　　┃　　　┻　　　┃
 *　　┃　　　　　　　┃
 *　    ┗━┓　　　┏━┛
 *　　　　┃　　　┃神兽保佑, 永无BUG!
 *　　　　┃　　　┃Code is far away from bug with the animal protecting
 *　　　　┃　　　┗━━━┓
 *　　　　┃　　　　　　　┣┓
 *　　　    ┃　　　　　　　┏┛
 *　　　　┗┓┓┏━┳┓┏┛
 *　　　　　┃┫┫　┃┫┫
 *　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * ClassName: JedisShard 
 * @Description: 继承原有的JedisShardInfo类,重写选择redis数据库的  db
 * @author liulianyuan
 * @date 2016年5月30日
 */
public class JedisShard extends JedisShardInfo  {

	private int db;
	
	public int getDb() {
		return db;
	}

	public void setDb(int db) {
		this.db = db;
	}

	public JedisShard(String host, int port) {
		super(host, port);
	}

}
