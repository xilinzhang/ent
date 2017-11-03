package com.lianchuan.common.oss.impl;

import org.junit.Assert;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.lianchuan.common.oss.OssBizService;
import com.lianchuan.common.oss.OssService;
import com.lianchuan.common.service.AbstractService;
import com.lianchuan.common.utils.UUIDUtils;


public class OssBizServiceImpl extends AbstractService implements OssBizService, InitializingBean {

	/** 根目录路径 */
	private String basePath;

	/** 合同文件目录 */
	private final static String CONTRACT_PATH = "contract/";
	
	/** 合同zip文件目录 */
	private final static String CONTRACT_ZIP_PATH = "contract_zip/";

	@Autowired
	private OssService ossService;

	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.assertNotNull("业务OSS的basePath不能为空", basePath);
	}

	@Override
	public String uploadContractFile(int year, int month,int day, String ext, byte[] bytes) {
		String name = UUIDUtils.uuid();
		String resourcePath = CONTRACT_PATH + year + "/" + month+"/"+day + "/" + name + ext;
		String path = basePath + resourcePath;
		ossService.uploadByte(path, bytes);
		logger.debug("合同文件上传路径:{}", path);
		return resourcePath;
	}
	
	
	@Override
	public byte[] getContractFile(String resourcePath) {
		String path = basePath + resourcePath;
		logger.debug("合同文件下载路径:{}", path);
		return ossService.downloadByte(path);
	}
	
	@Override
	public String getContractFileUrl(String resourcePath) {
		String path = basePath + resourcePath;
		String url = ossService.getUrl(path);
		logger.debug("合同文件访问路径:{}", url);
		return url;
	}
	
	@Override
	public void deleteContractFile(String path) {
		path = basePath + path;
		ossService.deleteFile(path);
		logger.debug("合同文件删除:{}", path);
	}
	
	
	@Override
	public String uploadZipFile(String year, String month, String day, Long applyId, byte[] bytes) {
		String resourcePath = CONTRACT_ZIP_PATH + year + "/" + month+"/"+day + "/" + applyId + ".zip";
		String path = basePath + resourcePath;
		ossService.uploadByte(path, bytes);
		logger.debug("合同zip文件上传路径:{}", path);
		return resourcePath;
	}
	
	@Override
	public byte[] getZipFile(String year, String month, String day, Long applyId) {
		String path = basePath + CONTRACT_ZIP_PATH + year + "/" + month+"/"+day + "/" + applyId + ".zip";
		logger.debug("合同zip文件下载路径:{}", path);
		return ossService.downloadByte(path);
	}
	
	
	
	// 以下暂不使用------------
	
	@Override
	public byte[] getContractFile(String year, Long applyId, String name, String ext) {
		String path = basePath + CONTRACT_PATH + year + "/" + applyId + "/" + name + ext;
		logger.debug("合同文件下载路径:{}", path);
		return ossService.downloadByte(path);
	}

	@Override
	public String getContractFileUrl(String year, Long applyId, String name, String ext) {
		String path = basePath + CONTRACT_PATH + year + "/" + applyId + "/" + name + ext;
		String url = ossService.getUrl(path);
		logger.debug("合同文件访问路径:{}", url);
		return url;
	}
	
	@Override
	public void deleteContractFile(String year, Long applyId, String name, String ext) {
		String path = basePath + CONTRACT_PATH + year + "/" + applyId + "/" + name + ext;
		ossService.deleteFile(path);
		logger.debug("合同文件删除:{}", path);
	}
	

}
