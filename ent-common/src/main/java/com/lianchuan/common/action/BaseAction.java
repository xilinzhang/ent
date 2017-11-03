package com.lianchuan.common.action;

import java.io.File;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lianchuan.common.entity.vo.BaseParam;
import com.lianchuan.common.mapper.Mapper;
import com.lianchuan.common.utils.IpUtils;

@RequestMapping(value = "/")
public abstract class BaseAction {

	/** 日志最大打印长度 */
	protected static final int LOG_PARAM_MAX_LENGTH = 1024;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected final static String ENCODE = "UTF-8";

	protected final static Charset CHARSET = Charset.forName(ENCODE);

	protected static final String APPLICATION_JSON = "application/json; charset=UTF-8";

	protected static final String APPLICATION_OCTET_STREAM = "application/octet-stream";

	protected static final String TEXT_HTML = "text/html; charset=UTF-8";

	protected String applicationJson = APPLICATION_JSON;

	protected String applicationOctetStream = APPLICATION_OCTET_STREAM;

	protected String textHtml = TEXT_HTML;

	@Autowired
	protected Mapper mapper;

	/**
	 * 获取HttpServletRequest
	 * 
	 * @return
	 */
	protected HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获取HttpServletResponse
	 * 
	 * @return
	 */
	protected HttpServletResponse getHttpServletResponse() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
	}

	/**
	 * 从消息体中读取请求参数
	 * 
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public <T extends BaseParam> T readParamByBody(Class<T> clazz) throws Exception {
		HttpServletRequest request = getHttpServletRequest();
		return readParamByBody(request, clazz);
	}

	/**
	 * 从消息体中读取请求参数
	 * 
	 * @param request
	 *            指定request
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	protected <T extends BaseParam> T readParamByBody(HttpServletRequest request, Class<T> clazz) throws Exception {
		String body = IOUtils.toString(request.getInputStream(), CHARSET);

		if (logger.isDebugEnabled()) {
			logger.debug("path:{} ,read body length:{} ,value:\n{}", request.getRequestURI(), body.length(), body.substring(0, Math.min(body.length(), LOG_PARAM_MAX_LENGTH)));
		}

		T t = mapper.readValue(body, clazz);
		if (logger.isDebugEnabled()) {
			String value = mapper.getObjectMapper().writeValueAsString(t);
			logger.debug("path:{} ,read param length:{} ,value:\n{}", request.getRequestURI(), value.length(), value.substring(0, Math.min(value.length(), LOG_PARAM_MAX_LENGTH)));
		}
		return t;
	}

	/**
	 * 读取请求体中的参数
	 * 
	 * @return
	 * @throws Exception
	 */
	protected String readBody() throws Exception {
		return readBody(getHttpServletRequest());
	}

	/**
	 * 读取请求体中的参数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected String readBody(HttpServletRequest request) throws Exception {
		return IOUtils.toString(request.getInputStream(), CHARSET);
	}

	/**
	 * 写出JSON字符串
	 * 
	 * @param value
	 * @throws Exception
	 */
	protected void writeJsonString(String value) throws Exception {
		writeJsonString(getHttpServletResponse(), value);
	}

	/**
	 * 写出JSON字符串
	 * 
	 * @param response
	 *            指定response
	 * @param value
	 * @throws Exception
	 */
	protected void writeJsonString(HttpServletResponse response, String value) throws Exception {
		Assert.notNull(value, "输出JSON字符串不能为空");
		response.setContentType(applicationJson);
		options(response);
		IOUtils.write(value, response.getOutputStream(), CHARSET);
		response.flushBuffer();
		if (logger.isDebugEnabled()) {
			logger.debug("path:{} ,write string length:{} ,value:\n{}", getHttpServletRequest().getRequestURI(), value.length(), value.substring(0, Math.min(value.length(), LOG_PARAM_MAX_LENGTH)));
		}
	}

	/**
	 * 写出字符串
	 * 
	 * @param value
	 * @throws Exception
	 */
	protected void writeString(String value) throws Exception {
		writeString(getHttpServletResponse(), value);
	}

	/**
	 * 写出字符串
	 * 
	 * @param response
	 *            指定response
	 * @param value
	 * @throws Exception
	 */
	protected void writeString(HttpServletResponse response, String value) throws Exception {
		Assert.notNull(value, "输出字符串不能为空");
		response.setContentType(textHtml);
		options(response);
		IOUtils.write(value, response.getOutputStream(), CHARSET);
		response.flushBuffer();
		if (logger.isDebugEnabled()) {
			logger.debug("path:{} ,write string length:{} ,value:\n{}", getHttpServletRequest().getRequestURI(), value.length(), value.substring(0, Math.min(value.length(), LOG_PARAM_MAX_LENGTH)));
		}
	}

	/**
	 * 写出文件
	 * 
	 * @param file
	 * @throws Exception
	 */
	protected void writeFile(File file) throws Exception {
		writeFile(getHttpServletResponse(), file);
	}

	/**
	 * 写出文件
	 * 
	 * @param response
	 *            指定response
	 * @param file
	 * @throws Exception
	 */
	protected void writeFile(HttpServletResponse response, File file) throws Exception {
		Assert.isTrue(file.isFile(), "输出文件必须为文件");
		response.setContentType(applicationOctetStream);
		options(response);
		byte[] bs = FileUtils.readFileToByteArray(file);
		IOUtils.write(bs, response.getOutputStream());
		response.flushBuffer();
		if (logger.isDebugEnabled()) {
			logger.debug("path:{} ,write file length:{}", getHttpServletRequest().getRequestURI(), bs.length);
		}
	}

	/**
	 * 写出bytes
	 * 
	 * @param bytes
	 * @throws Exception
	 */
	protected void writeBytes(byte[] bytes) throws Exception {
		writeBytes(getHttpServletResponse(), bytes);
	}

	/**
	 * 写出bytes
	 * 
	 * @param response
	 *            指定response
	 * @param bytes
	 * @throws Exception
	 */
	protected void writeBytes(HttpServletResponse response, byte[] bytes) throws Exception {
		Assert.notNull(bytes, "输出字节不能为空");
		response.setContentType(applicationOctetStream);
		options(response);
		IOUtils.write(bytes, response.getOutputStream());
		response.flushBuffer();
		if (logger.isDebugEnabled()) {
			logger.debug("path:{} ,write bytes length:{}", getHttpServletRequest().getRequestURI(), bytes.length);
		}
	}

	/**
	 * 对象转换成数据写到输出流
	 * 
	 * @param value
	 * @throws Exception
	 */
	protected void writeValue(Object value) throws Exception {
		writeValue(getHttpServletResponse(), value);
	}

	/**
	 * 对象转换成数据写到输出流
	 * 
	 * @param response
	 *            指定response
	 * @param value
	 * @throws Exception
	 */
	protected void writeValue(HttpServletResponse response, Object value) throws Exception {
		Assert.notNull(value, "输出结果不能为空");
		response.setContentType(applicationJson);
		options(response);
		mapper.getObjectMapper().writeValue(response.getOutputStream(), value);
		if (logger.isDebugEnabled()) {
			String msg = mapper.getObjectMapper().writeValueAsString(value);
			logger.debug("path:{} ,write value length:{} ,value:\n{}", getHttpServletRequest().getRequestURI(), msg.length(), msg.substring(0, Math.min(msg.length(), LOG_PARAM_MAX_LENGTH)));
		}
	}

	/**
	 * 允许微信跨域请求
	 * 
	 * @param response
	 */
	protected void options(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		if ("OPTIONS".equals(getHttpServletRequest().getMethod())) {
			response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
			response.addHeader("Access-Control-Allow-Headers", "Content-Type,X-Requested-With,qqm-client");
		}
	}

	/**
	 * 获取请求IP地址
	 * 
	 * @return
	 */
	protected String getIp() {
		return IpUtils.getIp(getHttpServletRequest());
	}

}
