package com.lianchuan.common.service;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基础服务类
 */
public abstract class AbstractService {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected final static String ENCODE = "UTF-8";

	protected final static Charset CHARSET = Charset.forName(ENCODE);

}
