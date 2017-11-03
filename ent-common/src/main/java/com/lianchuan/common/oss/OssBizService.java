package com.lianchuan.common.oss;

/**
 * 业务层获取OSS文件服务类
 */
public interface OssBizService {

	public String uploadContractFile(int year, int month,int day, String ext, byte[] bytes);
	
	public byte[] getContractFile(String path);
	
	public String getContractFileUrl(String path);
	
	public void deleteContractFile(String path);
	
	public String uploadZipFile(String year, String month, String day, Long applyId, byte[] bytes);
	
	public byte[] getZipFile(String year, String month, String day, Long applyId);
	
	// 以下暂不使用------------
	@Deprecated
	public byte[] getContractFile(String year, Long applyId, String name, String ext);
	@Deprecated
	public String getContractFileUrl(String year, Long applyId, String name, String ext);
	@Deprecated
	public void deleteContractFile(String year, Long applyId, String name, String ext);
	
}
