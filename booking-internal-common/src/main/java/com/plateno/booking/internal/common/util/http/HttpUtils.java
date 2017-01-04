package com.plateno.booking.internal.common.util.http;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.plateno.booking.internal.bean.contants.HttpContants;
import com.plateno.booking.internal.bean.exception.OrderException;

/**
 * 用于http请求的工具类
 * 
 * @author jicexosl
 * 
 *         2014-5-4
 */

@SuppressWarnings("deprecation")
public final class HttpUtils {

	// 禁止实例化和继承
	private HttpUtils() {
	}

	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	public static String httpPostRequest(String url, String params) {
		return httpRequest(false, "POST", params, url, 0, 0);
	}

	public static String httpPostRequest(String url, String params,HashMap<String, String> headers) {
		return httpRequest(false, "POST", params, url, 0, 0, headers);
	}

	public static String httpPostRequest(String url, String params,int socketTimeout, int connectTimeout) {
		return httpRequest(false, "POST", params, url, socketTimeout,connectTimeout);
	}

	public static String httpPostOrderRequest(String url, String params,int socketTimeout, int connectTimeout) {
		return httpRequest(false, "httpPostOrderRequest", params, url,socketTimeout, connectTimeout);
	}

	public static String httpPostNotJsonRequest(String url, String params) {
		return httpRequest(false, "POSTNOTJSON", params, url, 0, 0);
	}

	public static String httpPostNoCharsetRequest(String url, String params,int socketTimeout, int connectTimeout) {
		return httpRequest(false, "POSTNOCHARSET", params, url, socketTimeout,connectTimeout);
	}

	public static String httpGetRequest(String url) {
		return httpRequest(false, "GET", null, url, 0, 0);
	}

	public static String httpGetRequestWithTime(String url, int socketTimeout,int connectTimeout) {
		return httpRequest(false, "GET", null, url, socketTimeout,connectTimeout);
	}

	public static String httpGetRequest(String url, Map<String, String> params) {
		String requestUrl = getUrl(url, params);
		return httpGetRequest(requestUrl);
	}

	public static String httpGetRequestWithTime(String url,Map<String, String> params, int socketTimeout, int connectTimeout) {
		String requestUrl = getUrl(url, params);
		return httpGetRequestWithTime(requestUrl, socketTimeout, connectTimeout);
	}

	public static String httpsPostRequest(String url, String params) {
		return httpRequest(true, "POST", params, url, 0, 0);
	}

	public static String httpsGetRequest(String url) {
		return httpRequest(true, "GET", null, url, 0, 0);
	}

	public static String httpsGetRequest(String url, Map<String, String> params) {
		String requestUrl = getUrl(url, params);
		return httpsGetRequest(requestUrl);
	}

	private static String httpRequest(Boolean isHttps, String method,String params, String strUrl, int socketTimeout, int connectTimeout) {
		try {
			if (socketTimeout == 0) {
				socketTimeout = HttpContants.CONNECT_SOKET_TIME_OUT_LONG;
			}
			if (connectTimeout == 0) {
				connectTimeout = HttpContants.CONNECT_TIME_OUT_LONG;
			}
			CloseableHttpClient client = getClient(isHttps);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
			CloseableHttpResponse response = null;
			if ("GET".equals(method)) {
				HttpGet get = null;
				get = new HttpGet(strUrl);

				get.setConfig(requestConfig);
				get.setHeader("Accept", "application/json");
				get.setHeader("Content-type", "application/json;charset=utf-8");
				get.setHeader("Authorization", "k21313kasdfdssaassss");
				response = client.execute(get);
				if (HttpContants.VERIFICATION_CODE_DEBUG == 1) {
					logger.info("提交getUrl:\n" + strUrl);
				}

			} else {
				HttpPost post = new HttpPost(strUrl);
				post.setConfig(requestConfig);
				HttpEntity postEntity = null;

				if ("POSTNOTJSON".equals(method)) {// 非Json格式的参数
					List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
					String[] paramArray = params.split("&");
					for (String param : paramArray) {
						String[] temp = param.split("=");
						nvps.add(new BasicNameValuePair(temp[0], temp.length > 1 ? temp[1] : ""));
					}
					postEntity = new UrlEncodedFormEntity(nvps, "utf-8");
				} else if ("httpPostOrderRequest".equals(method)) {
					postEntity = new StringEntity(params,ContentType.APPLICATION_JSON);

				} else {
					post.setHeader("Accept", "application/json");
					if ("POSTNOCHARSET".equals(method)) {
						post.setHeader("Content-type", "application/json");
					} else {
						post.setHeader("Content-type","application/json;charset=utf-8");
					}
					post.setHeader("Authorization", "k21313kasdfdssaassss");
					postEntity = new StringEntity(params,ContentType.APPLICATION_JSON);
				}
				// 测试环境下，打印url
				if (HttpContants.VERIFICATION_CODE_DEBUG == 1) {
					byte[] b = new byte[(int) postEntity.getContentLength()];
					postEntity.getContent().read(b);
					logger.info("postUrl:\n"+ strUrl+ "\n"+ new String(b, 0, (int) postEntity.getContentLength()));
				}
				post.setEntity(postEntity);
				response = client.execute(post);
			}
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity, "UTF-8");
					if (HttpContants.VERIFICATION_CODE_DEBUG == 1) {
						logger.info("请求接口结果:\n" + result);
					}
					return result;
				} else {

				}
			} finally {
				response.close();
				client.close();
			}
		} catch (Exception e) {

			logger.error("Method:" + method + " isHttps: " + isHttps+ " params: " + params + " url: " + strUrl+ " request failed", e);
		}
		return null;
	}

	/**
	 * 
	 * @param isHttps
	 * @param method
	 * @param params
	 * @param strUrl
	 * @param socketTimeout
	 * @param connectTimeout
	 * @param headers
	 * @return
	 * 
	 *         添加设置header 的设置
	 */
	@SuppressWarnings({ "rawtypes" })
	private static String httpRequest(Boolean isHttps, String method,String params, String strUrl, int socketTimeout,int connectTimeout, HashMap<String, String> headers) {
		try {
			if (socketTimeout == 0) {
				socketTimeout = HttpContants.CONNECT_SOKET_TIME_OUT_LONG;
			}
			if (connectTimeout == 0) {
				connectTimeout = HttpContants.CONNECT_TIME_OUT_LONG;
			}
			CloseableHttpClient client = getClient(isHttps);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
			CloseableHttpResponse response = null;
			if ("GET".equals(method)) {
				HttpGet get = null;
				get = new HttpGet(strUrl);
				get.setConfig(requestConfig);
				get.setHeader("Accept", "application/json");
				get.setHeader("Content-type", "application/json;charset=utf-8");
				if (headers != null && headers.entrySet().size() > 0) {
					Iterator iter = headers.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						get.setHeader((String) entry.getKey(),(String) entry.getValue());
					}
				}
				response = client.execute(get);
				// 测试环境下，打印url
				if (HttpContants.VERIFICATION_CODE_DEBUG == 1) {
					logger.info("提交getUrl:\n" + strUrl);
				}

			} else {
				HttpPost post = new HttpPost(strUrl);
				post.setConfig(requestConfig);
				HttpEntity postEntity = null;

				if ("POSTNOTJSON".equals(method)) {// 非Json格式的参数
					List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
					String[] paramArray = params.split("&");
					for (String param : paramArray) {
						String[] temp = param.split("=");
						nvps.add(new BasicNameValuePair(temp[0], temp[1]));
					}
					postEntity = new UrlEncodedFormEntity(nvps, "utf-8");
				} else {
					post.setHeader("Accept", "application/json");
					if ("POSTNOCHARSET".equals(method)) {
						post.setHeader("Content-type", "application/json");
					} else {
						post.setHeader("Content-type","application/json;charset=utf-8");
					}
					postEntity = new StringEntity(params,ContentType.APPLICATION_JSON);
				}
				if (headers != null && headers.entrySet().size() > 0) {
					Iterator iter = headers.entrySet().iterator();
					while (iter.hasNext()) {
						Map.Entry entry = (Map.Entry) iter.next();
						post.setHeader((String) entry.getKey(),(String) entry.getValue());
					}
				}
				// 测试环境下，打印url
				if (HttpContants.VERIFICATION_CODE_DEBUG == 1) {
					byte[] b = new byte[(int) postEntity.getContentLength()];
					postEntity.getContent().read(b);
					logger.info("postUrl:\n"+ strUrl+ "\n"+ new String(b, 0, (int) postEntity.getContentLength()));
				}

				post.setEntity(postEntity);
				response = client.execute(post);
			}
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity, "UTF-8");
					if (HttpContants.VERIFICATION_CODE_DEBUG == 1) {
						logger.info("请求接口结果:\n" + result);
					}
					return result;
				} else {

				}
			} finally {
				response.close();
				client.close();
			}
		} catch (Exception e) {

			logger.error("Method:" + method + " isHttps: " + isHttps
					+ " params: " + params + " url: " + strUrl
					+ " request failed", e);
		}
		return null;
	}

	private static CloseableHttpClient getClient(boolean isHttps) {
		if (isHttps) {
			return Objects.requireNonNull(createSSLInsecureClient());
		}
		return HttpClients.createDefault();
	}

	private static CloseableHttpClient createSSLInsecureClient() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(
					null, new TrustStrategy() {
						// 信任所有证书
						public boolean isTrusted(X509Certificate[] chain,
								String authType) throws CertificateException {
							return true;
						}
					}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
					sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			logger.error("get ssl client failed", e);
		}
		return null;
	}

	private static String getUrl(String url, Map<String, String> params) {
		StringBuilder requestUrl = new StringBuilder(url);
		int i = 0;
		for (Entry<String, String> entry : params.entrySet()) {
			if (entry.getValue() == null || "".equals(entry.getValue())) {
				continue;
			}
			if (i == 0) {
				if (url.matches(".*\\?.*")) {
					requestUrl.append("&");
				} else {
					requestUrl.append("?");
				}
				requestUrl.append(entry.getKey()).append("=")
						.append(entry.getValue());
			} else {
				requestUrl.append("&").append(entry.getKey()).append("=")
						.append(entry.getValue());
			}
			i++;
		}
		// return URLEncoder.encode(requestUrl.toString(),"UTF-8");
		return requestUrl.toString();
	}

	/**
	 * 验证网址Url
	 * 
	 * @param 待验证的字符串
	 * @return 如果是符合格式的字符串,返回 <b>true </b>,否则为 <b>false </b>
	 */
	public static boolean IsUrl(String str) {
		String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
		return match(regex, str);
	}

	/**
	 * @param regex
	 *            正则表达式字符串
	 * @param str
	 *            要匹配的字符串
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false;
	 */
	private static boolean match(String regex, String str) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 
	 * @Description: 专提供给订单校验 下单用
	 * @param @param targetURL
	 * @param @param urlParameters
	 * @param @return
	 * @return String
	 * @throws
	 * @author jerome.jiang
	 * @date 2016年3月24日
	 */
	public static String excutePost(String targetURL, String urlParameters) {
		logger.info("请求url:" + targetURL);
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(targetURL);

			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(HttpContants.CONNECT_TIME_OUT_LONG);
			connection.setReadTimeout(HttpContants.CONNECT_SOKET_TIME_OUT_LONG);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=utf-8");
			connection.setRequestProperty("Content-Length",
					Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();

			return response.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	/**
	 * 同程下单请求 json
	 * 
	 * @param url
	 * @param json
	 * @return
	 * @throws OrderException
	 */
	public static String doPostJson(String url, String json)
			throws OrderException {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = null;
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			RequestConfig config = RequestConfig.custom()
					.setSocketTimeout(HttpContants.CONNECT_SOKET_TIME_OUT_LONG)
					.setConnectTimeout(HttpContants.CONNECT_TIME_OUT_LONG).build();
			httpPost.setConfig(config);
			// 创建请求内容
			StringEntity entity = new StringEntity(json,
					ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {

			logger.error(String.format("tcOrder Http exception[%s]", e));
			throw new OrderException("tcOrder http Exception:" + e.getMessage());

		} finally {
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}

		return resultString;
	}

	public static String excuteJsonPost(String targetURL, String json) {
		long startTime = System.currentTimeMillis();
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL(targetURL);

			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(99999999);
			connection.setReadTimeout(99999999);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/json;charset=utf-8");
			connection.setRequestProperty("Content-Length",
					Integer.toString(json.getBytes().length));
			System.out.println("urlParameters.getBytes().length:"
					+ json.getBytes().length);
			connection.setRequestProperty("Content-Language", "en-US");
			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(json);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is,
					"UTF-8"));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			long endTime = System.currentTimeMillis();

			logger.info("服务调用,执行时间 　: " + (endTime - startTime) / 1000);
			return response.toString();

		} catch (Exception e) {

			e.printStackTrace();
			return null;

		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
	
	
	/**
	 * post发送json
	 * @param strUrl
	 * @param json
	 * @param headers
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public static String postJson(String strUrl, String json, HashMap<String, String> headers) {
		try {
			int socketTimeout = HttpContants.CONNECT_SOKET_TIME_OUT_LONG;
			int connectTimeout = HttpContants.CONNECT_TIME_OUT_LONG;
			CloseableHttpClient client = getClient(false);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
			CloseableHttpResponse response = null;
			
			HttpPost post = new HttpPost(strUrl);
			post.setConfig(requestConfig);
			HttpEntity postEntity = null;

			post.setHeader("Accept", "application/json");
			post.setHeader("Content-type","application/json;charset=UTF-8");
			
			postEntity = new StringEntity(json, ContentType.APPLICATION_JSON);

			if (headers != null && headers.entrySet().size() > 0) {
				Iterator iter = headers.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					post.setHeader((String) entry.getKey(),(String) entry.getValue());
				}
			}
			
			// 测试环境下，打印url
			if (HttpContants.VERIFICATION_CODE_DEBUG == 1) {
				byte[] b = new byte[(int) postEntity.getContentLength()];
				postEntity.getContent().read(b);
				logger.info("postUrl:\n"+ strUrl+ "\n"+ new String(b, 0, (int) postEntity.getContentLength()));
			}

			post.setEntity(postEntity);
			response = client.execute(post);

			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					String result = EntityUtils.toString(entity, "UTF-8");
					if (HttpContants.VERIFICATION_CODE_DEBUG == 1) {
						logger.info("请求接口结果:\n" + result);
					}
					return result;
				} 
			} finally {
				response.close();
				client.close();
			}
		} catch (Exception e) {
			logger.error("post json exception", e);
		}
		return null;
	}
}
