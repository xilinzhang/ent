package com.lianchuan.common.oss.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.springframework.beans.factory.InitializingBean;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ListObjectsRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.lianchuan.common.entity.type.ent.UploadFileType;
import com.lianchuan.common.exception.EntException;
import com.lianchuan.common.oss.OssService;
import com.lianchuan.common.service.AbstractService;

/**
 * 阿里云OSS服务
 */
public class OssServiceImpl extends AbstractService implements OssService, InitializingBean {

	/** 每次获取条数 */
	private static final int MAX_KEY = 1000;

	private String bucketName;
	private String accessKeyId;
	private String accessKeySecret;
	private String endpoint;
	private String endpointOut;

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public void setEndpointOut(String endpointOut) {
		this.endpointOut = endpointOut;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.assertNotNull("阿里云OSS的bucketName不能为空", bucketName);
		Assert.assertNotNull("阿里云OSS的accessKeyId不能为空", accessKeyId);
		Assert.assertNotNull("阿里云OSS的accessKeySecret不能为空", accessKeySecret);
		Assert.assertNotNull("阿里云OSS的endpoint不能为空", endpoint);
	}

	@Override
	public void deleteFile(String path) {
		OSSClient client = null;
		try {
			client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			client.deleteObject(bucketName, path);
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
	}

	@Override
	public void uploadByte(String path, UploadFileType type, byte[] value, String disposition) {
		OSSClient client = null;
		InputStream content = null;
		try {
			client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			content = new ByteArrayInputStream(value);

			ObjectMetadata meta = new ObjectMetadata();
			// 必须设置ContentLength
			meta.setContentLength(value.length);

			if (type != null) {
				meta.setContentType(type.getContentType());
			}
			if (StringUtils.isNotBlank(disposition)) {
				meta.setContentDisposition("filename=" + disposition);
				System.out.println(meta.getContentDisposition());
			}
			PutObjectResult result = client.putObject(bucketName, path, content, meta);

			logger.debug("上传数据到[{}],ETag:{}", path, result.getETag());
		} catch (Exception e) {
			throw new EntException(e);
		} finally {
			if (client != null) {
				client.shutdown();
			}
			if (content != null) {
				try {
					content.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
	}

	@Override
	public void uploadByte(String path, byte[] value) {
		uploadByte(path, null, value, null);
	}

	@Override
	public void uploadByte(String path, byte[] value, String disposition) {
		uploadByte(path, null, value, disposition);
	}

	@Override
	public byte[] downloadByte(String path) {
		OSSClient client = null;
		InputStream content = null;
		try {
			client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			OSSObject object = client.getObject(bucketName, path);
			content = object.getObjectContent();
			return IOUtils.toByteArray(content);
		} catch (Exception e) {
			if (e.getMessage().contains("NoSuchKey")) {
				return null;
			}
			throw new EntException(e);
		} finally {
			if (client != null) {
				client.shutdown();
			}
			if (content != null) {
				try {
					content.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
	}

	@Override
	public Map<String, byte[]> downloadBytes(String path) {
		Assert.assertTrue("参数不能为空", StringUtils.isNotBlank(path));

		if (path.charAt(path.length() - 1) != '/') {
			path += "/";
		}

		OSSClient client = null;
		InputStream content = null;
		Map<String, byte[]> map = new HashMap<String, byte[]>();
		try {
			client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

			listObjectsRequest.setMaxKeys(MAX_KEY);

			listObjectsRequest.setDelimiter("/");

			listObjectsRequest.setPrefix(path);

			String marker = "";

			while (true) {
				listObjectsRequest.setMarker(marker);
				ObjectListing listing = client.listObjects(listObjectsRequest);
				if (listing.getObjectSummaries().isEmpty()) {
					break;
				}
				List<OSSObjectSummary> objectSummarys = listing.getObjectSummaries();
				for (OSSObjectSummary objectSummary : objectSummarys) {
					String name = objectSummary.getKey();
					if (path.equals(name)) {
						continue;
					}
					marker = name;
					OSSObject file = client.getObject(bucketName, name);
					content = file.getObjectContent();

					byte[] bytes = IOUtils.toByteArray(content);
					map.put(name, bytes);
					content.close();
				}
				if (objectSummarys.size() < MAX_KEY) {
					break;
				}
			}
		} catch (Exception e) {
			if (e.getMessage().contains("NoSuchKey")) {
				return map;
			}
			throw new EntException(e);
		} finally {
			if (client != null) {
				client.shutdown();
			}
			if (content != null) {
				try {
					content.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
		return map;
	}

	@Override
	public String getUrl(String path) {
		OSSClient client = null;
		try {
			client = new OSSClient(endpointOut == null ? endpoint : endpointOut, accessKeyId, accessKeySecret);
			Date expiration = new Date(new Date().getTime() + 1200 * 1000);
			URL url = client.generatePresignedUrl(bucketName, path, expiration);
			return url.toString();
		} catch (Exception e) {
			throw new EntException(e);
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
	}

	@Override
	public List<String> getUrls(String path) {
		Assert.assertTrue("参数不能为空", StringUtils.isNotBlank(path));

		if (path.charAt(path.length() - 1) != '/') {
			path += "/";
		}
		OSSClient client = null;
		List<String> result = new ArrayList<String>();
		Set<Long> lengthSet = new HashSet<Long>();
		try {
			client = new OSSClient(endpointOut == null ? endpoint : endpointOut, accessKeyId, accessKeySecret);
			ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);

			listObjectsRequest.setMaxKeys(MAX_KEY);

			listObjectsRequest.setDelimiter("/");

			listObjectsRequest.setPrefix(path);

			String marker = "";

			while (true) {
				listObjectsRequest.setMarker(marker);
				ObjectListing listing = client.listObjects(listObjectsRequest);
				if (listing.getObjectSummaries().isEmpty()) {
					break;
				}
				List<OSSObjectSummary> objectSummarys = listing.getObjectSummaries();
				for (OSSObjectSummary objectSummary : objectSummarys) {
					String name = objectSummary.getKey();
					if (path.equals(name)) {
						continue;
					}
					marker = name;
					OSSObject file = client.getObject(bucketName, name);
					file.getObjectMetadata().setContentType("application/json; charset=utf-8");
					if (lengthSet.contains(file.getObjectMetadata().getContentLength())) {
						continue;
					}
					result.add(getUrl(name));
				}
				if (objectSummarys.size() < MAX_KEY) {
					break;
				}
			}
		} catch (Exception e) {
			if (e.getMessage().contains("NoSuchKey")) {
				return result;
			}
			throw new EntException(e);
		} finally {
			if (client != null) {
				client.shutdown();
			}
		}
		return result;
	}

}
