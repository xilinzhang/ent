package com.lianchuan.common.oss.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.springframework.beans.factory.InitializingBean;

import com.lianchuan.common.entity.type.ent.UploadFileType;
import com.lianchuan.common.exception.EntException;
import com.lianchuan.common.oss.OssService;
import com.lianchuan.common.service.AbstractService;

/**
 * 本地OSS服务类<br>
 * 一般用于测试环境
 */
public class OssServiceLocalImpl extends AbstractService implements OssService, InitializingBean {

	private String localPath;
	private String localUrl;

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	public void setLocalUrl(String localUrl) {
		this.localUrl = localUrl;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.assertNotNull("本地OSS的localPath不能为空", localPath);
		Assert.assertNotNull("本地OSS的localUrl不能为空", localUrl);
	}

	@Override
	public void deleteFile(String path) {
		File file = new File(localPath + path);
		boolean flag = false;
		if (file.exists()) {
			flag = file.delete();
		}
		logger.debug("文件:[{}],删除结果:{}", file.getAbsolutePath(), flag);
	}

	@Override
	public void uploadByte(String path, byte[] value) {
		uploadByte(path, value, null);
	}

	@Override
	public void uploadByte(String path, byte[] value, String disposition) {
		uploadByte(path, null, value, null);
	}

	@Override
	public void uploadByte(String path, UploadFileType type, byte[] value, String disposition) {
		File file = new File(localPath + path);
		try {
			FileUtils.writeByteArrayToFile(file, value);
		} catch (IOException e) {
			throw new EntException(e);
		}
	}

	@Override
	public byte[] downloadByte(String path) {
		try {
			return FileUtils.readFileToByteArray(new File(localPath + path));
		} catch (IOException e) {
			throw new EntException(e);
		}
	}

	@Override
	public String getUrl(String path) {
		return localUrl + path;
	}

	@Override
	public List<String> getUrls(String path) {
		List<String> list = new ArrayList<String>();
		List<File> files = getFiles(new File(localPath + path));
		for (File file : files) {
			String url = localUrl + getRelativePath(file);
			list.add(url);
		}
		return list;
	}

	@Override
	public Map<String, byte[]> downloadBytes(String path) {
		Map<String, byte[]> map = new HashMap<String, byte[]>();
		List<File> files = getFiles(new File(localPath + path));
		for (File file : files) {
			try {
				map.put(getRelativePath(file), FileUtils.readFileToByteArray(file));
			} catch (IOException e) {
				throw new EntException(e);
			}
		}
		return map;
	}

	/**
	 * 获取文件相对本地OSS的路径<br>
	 * 例如: localPath=e:/a/b/<br>
	 * file=e:/a/b/c/1.txt<br>
	 * return 'c/1.txt'
	 * 
	 * @param file
	 * @return
	 */
	private String getRelativePath(File file) {
		String s = file.getAbsolutePath();
		return s.substring(localPath.length(), s.length());
	}

	/**
	 * 获取文件夹下的所有文件
	 * 
	 * @param file
	 * @return
	 */
	private List<File> getFiles(File file) {
		List<File> list = new ArrayList<File>();
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				list.add(f);
			}
		}
		return list;
	}

}
