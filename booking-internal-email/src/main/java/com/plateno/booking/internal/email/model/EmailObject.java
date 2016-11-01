package com.plateno.booking.internal.email.model;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class EmailObject implements Serializable {
	private static final long serialVersionUID = 6615599184981351639L;

	//收件人
	private String[] sendTo;

	//发送人标示和applicationContext-email.xml 配置文件中的username保持一致
	// private String sendFrom;

	//邮件主题（注意：必填）
	private String subject;

	// 邮件模板方式的Mime邮件使用
	private Map<String, Object> contentMap;

	// 直接发送邮件内容时使用
	private String content;

	//邮件附件
	private LinkedHashMap<String, File> attamentMap = new LinkedHashMap<String, File>();

	//抄送人
	private String[] cc;

	//密送人
	private String[] bcc;

	public String[] getSendTo() {
		return sendTo;
	}

	public void setSendTo(String[] sendTo) {
		this.sendTo = sendTo;
	}

	// public String getSendFrom() {
	// return sendFrom;
	// }
	//
	// public void setSendFrom(String sendFrom) {
	// this.sendFrom = sendFrom;
	// }

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Map<String, Object> getContentMap() {
		return contentMap;
	}

	/*
	 * 发送Mime Email时： FreeMarker邮件占位符
	 */
	public void setContentMap(Map<String, Object> contentMap) {
		this.contentMap = contentMap;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public String getContent() {
		return content;
	}

	/**
	 * 纯文本邮件内容， 和setContentArray任选其一
	 * 
	 * @param content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	public void addAttachment(String fileName, File file) {
		attamentMap.put(fileName, file);

	}

	public LinkedHashMap<String, File> getAttamentMap() {
		return attamentMap;
	}
}
