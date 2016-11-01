package com.plateno.booking.internal.email;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.plateno.booking.internal.email.model.EmailCallback;
import com.plateno.booking.internal.email.model.EmailObject;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 纯文本和MIME邮件服务类. 由Freemarker引擎生成的的html格式邮件, 并带有附件.
 * 
 */
@Component
public class MailService{

	private Logger log = Logger.getLogger(getClass());

	private static final String DEFAULT_ENCODING = "UTF-8";

	@Autowired
	@Qualifier("mailSenderX")
	private JavaMailSender mailSender;

	//注入Freemarker引擎配置,构造Freemarker 邮件内容模板.
	@Autowired
	private Configuration configuration;

	@Value("${mail.username}")
	private String fromEmail;

	@Autowired
	private TaskExecutor taskExecutor;

	@Value("${mail.personal}")
	private String personal;

	/*
	 * 发送纯文本邮件
	 */
	public void sendSimpleEmail(EmailObject eo) {
		SimpleMailMessage msg = new SimpleMailMessage();
		String from = this.fromEmail;
		msg.setFrom(from);
		msg.setTo(eo.getSendTo());
		if (ArrayUtils.isNotEmpty(eo.getCc())) {
			msg.setCc(eo.getCc());
		}
		if (ArrayUtils.isNotEmpty(eo.getBcc())) {
			msg.setBcc(eo.getBcc());
		}
		msg.setSubject(eo.getSubject());

		msg.setText(eo.getContent());
		setGmailProperties();
		try {
			mailSender.send(msg);
			if (log.isInfoEnabled()) {
				log.info("HTML版邮件已发送至{" + ArrayUtils.toString(msg.getTo()) + "}");
			}
		} catch (Exception e) {
			log.error("发送邮件失败", e);
		}
	}

	/**
	 * 异步发送纯文本邮件
	 */
	public void sendSimpleEmailAsyn(final EmailObject eo) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				sendSimpleEmail(eo);
			}
		});
	}

	/**
	 * 发送MIME格式的邮件. 1，Mime邮件发送使用FreeMarker模板
	 */
	public void sendMimeMail(EmailObject eo, String templateName) {
		doSendEmail(eo, templateName, null);
	}

	public void sendMimeMailAsyn(final EmailObject eo, final String templateName) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				sendMimeMail(eo, templateName);
			}
		});

	}

	/**
	 * 同步发送MIME格式的邮件，邮件内容存储在EmailObject.content，为FreeMarker模板文件内容
	 */
	public void sendMimeEmail(EmailObject eo) {
		doSendEmail(eo, null, null);

	}

	public void sendMimeMailAsyn(final EmailObject eo) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				sendMimeEmail(eo);
			}
		});

	}

	public void sendMimeEmail(EmailObject eo, EmailCallback ec) {
		doSendEmail(eo, null, ec);

	}

	public void sendMimeMailAsyn(final EmailObject eo, final EmailCallback ec) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				sendMimeEmail(eo, ec);
			}
		});
	}

	private void doSendEmail(EmailObject eo, String templateName, EmailCallback ec) {
		boolean isSuccess = true;
		try {
			if (ec != null) {
				// 邮件发送前处理
				ec.preSendEmail(eo);
			}
			MimeMessage msg = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, DEFAULT_ENCODING);
			// 接收者
			helper.setTo(eo.getSendTo());
			// 抄送
			if (ArrayUtils.isNotEmpty(eo.getCc())) {
				helper.setCc(eo.getCc());
			}
			// 密送
			if (ArrayUtils.isNotEmpty(eo.getBcc())) {
				helper.setBcc(eo.getBcc());
			}
			// 发送者
			if (StringUtils.isNotBlank(this.personal)) {
				helper.setFrom(fromEmail, this.personal);
			} else {
				helper.setFrom(fromEmail);
			}
			// 邮件主题
			helper.setSubject(eo.getSubject());
			// 邮件内容
			String content = null;
			// 从文件路径或者文本流构造FreeMarker模板
			if (StringUtils.isNotBlank(templateName)) {
				content = generateContent(eo.getContentMap(), templateName);
			} else {
				content = generateContent(eo);
			}

			helper.setText(content, true);
			// 邮件附件
			LinkedHashMap<String, File> attamentMap = eo.getAttamentMap();
			if (!attamentMap.isEmpty()) {
				for (Map.Entry<String, File> attachEntry : attamentMap.entrySet()) {
					helper.addAttachment(attachEntry.getKey(), attachEntry.getValue());
				}
			}
			setGmailProperties();

			mailSender.send(msg);
			log.info("HTML版邮件已发送至{" + ArrayUtils.toString(eo.getSendTo()) + "}");
			if (ec != null) {
				// 经过FreeMarker填充的邮件内容
				eo.setContent(content);
			}
		} catch (MessagingException e) {
			isSuccess = false;
			log.error("构造邮件失败", e);
		} catch (Exception e) {
			log.error("发送邮件失败", e);
			isSuccess = false;
		}
		if (null != ec) {
			ec.postSendEmail(eo, isSuccess);
		}
	}

	private void setGmailProperties() {
		JavaMailSenderImpl jMailSender = (JavaMailSenderImpl) mailSender;
		if (jMailSender.getHost().indexOf("smtp.gmail.com") >= 0) {
			Properties properties = jMailSender.getJavaMailProperties();
			properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.setProperty("mail.smtp.socketFactory.fallback", "false");
			properties.setProperty("mail.smtp.port", "465");
			properties.setProperty("mail.smtp.socketFactory.port", "465");
			jMailSender.setJavaMailProperties(properties);
		}
	}

	/**
	 * 使用Freemarker生成html格式内容.
	 */
	private String generateContent(Map<String, Object> context, String templateName) throws MessagingException {
		try {
			Template template = this.configuration.getTemplate(templateName, DEFAULT_ENCODING);
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
		} catch (IOException e) {
			log.error("生成邮件内容失败, FreeMarker模板不存在", e);
			throw new MessagingException("FreeMarker模板不存在", e);
		} catch (TemplateException e) {
			log.error("生成邮件内容失败, FreeMarker处理失败", e);
			throw new MessagingException("FreeMarker处理失败", e);
		}
	}

	/**
	 * 使用Freemarker生成html格式内容.
	 */
	public String generateContent(EmailObject eo) throws MessagingException {
		try {
			Template template = new Template("", eo.getContent(), this.configuration);
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, eo.getContentMap());
		} catch (IOException e) {
			log.error("生成邮件内容失败, FreeMarker模板不存在", e);
			throw new MessagingException("FreeMarker模板不存在", e);
		} catch (TemplateException e) {
			log.error("生成邮件内容失败, FreeMarker处理失败", e);
			throw new MessagingException("FreeMarker处理失败", e);
		}
	}

}
