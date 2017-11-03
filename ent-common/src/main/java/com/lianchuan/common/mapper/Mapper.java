package com.lianchuan.common.mapper;

import org.springframework.beans.factory.InitializingBean;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.lianchuan.common.exception.EntException;

/***
 * JSON和XML转换javaBean
 */
public class Mapper implements InitializingBean {

	private ObjectMapper objectMapper;

	private XmlMapper xmlMapper;

	@Override
	public void afterPropertiesSet() throws Exception {
		objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.setSerializationInclusion(Include.NON_NULL);

		xmlMapper = new XmlMapper();
		xmlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		xmlMapper.setSerializationInclusion(Include.NON_NULL);
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public XmlMapper getXmlMapper() {
		return xmlMapper;
	}

	/**
	 * 读取JSON数据
	 * 
	 * @param value
	 * @param clazz
	 * @return
	 * @throws EntException
	 *             解析失败
	 */
	public <T> T readValue(String value, Class<T> clazz) throws EntException {
		try {
			return objectMapper.readValue(value, clazz);
		} catch (Exception e) {
			throw new EntException(e);
		}
	}

}
