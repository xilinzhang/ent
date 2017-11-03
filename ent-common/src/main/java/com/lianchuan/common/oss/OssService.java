package com.lianchuan.common.oss;

import java.util.List;
import java.util.Map;

import com.lianchuan.common.entity.type.ent.UploadFileType;



public interface OssService {

	/**
	 * 删除OSS上的文件
	 * 
	 * @param file
	 *            OSS目标文件相对路径(不用'/'开头),分隔符用"/"
	 */
	public void deleteFile(String path);

	/**
	 * 直接上传数据到OSS
	 * 
	 * @param path
	 *            OSS目标文件相对路径(不用'/'开头),分隔符用"/"
	 * @param value
	 */
	public void uploadByte(String path, byte[] value);

	/**
	 * 直接上传数据到OSS
	 * 
	 * @param path
	 *            OSS目标文件相对路径(不用'/'开头),分隔符用"/"
	 * @param value
	 * @param disposition
	 *            下载时显示的名称
	 */
	public void uploadByte(String path, byte[] value, String disposition);

	/**
	 * 直接上传数据到OSS
	 * 
	 * @param path
	 *            OSS目标文件相对路径(不用'/'开头),分隔符用"/"
	 * @param type
	 *            直接请求时hearder中的content-type
	 * @param value
	 * @param disposition
	 *            下载时显示的名称
	 */
	public void uploadByte(String path, UploadFileType type, byte[] value, String disposition);

	/**
	 * 直接读取OSS上的文件
	 * 
	 * @param path
	 *            OSS目标文件夹相对路径(不用'/'开头),分隔符用"/"
	 * @return
	 */
	public byte[] downloadByte(String path);

	/**
	 * 获取OSS上的文件的url<br>
	 * url有效期:20分钟
	 * 
	 * @param path
	 *            OSS目标文件相对路径(不用'/'开头),分隔符用"/"
	 * @return
	 */
	public String getUrl(String path);

	/**
	 * 获取OSS上的文件夹下所有文件的url<br>
	 * url有效期:20分钟
	 * 
	 * @param path
	 *            OSS目标文件夹相对路径(不用'/'开头),分隔符用"/"
	 * @return
	 */
	public List<String> getUrls(String path);

	/**
	 * 直接读取OSS上的文件夹
	 * 
	 * @param path
	 *            OSS目标文件夹相对路径(不用'/'开头),分隔符用"/"
	 * @return key:文件相对路径
	 */
	public Map<String, byte[]> downloadBytes(String path);

}
