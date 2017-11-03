package com.lianchuan.ma.service.admin.push.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.fastjson.JSONObject;
import com.lianchuan.common.entity.po.ent.EntApplyPO;
import com.lianchuan.common.entity.po.ent.EntUploadInfoPO;
import com.lianchuan.common.exception.EntException;
import com.lianchuan.common.oss.OssBizService;
import com.lianchuan.common.service.dao.ent.EntCommonDaoService;
import com.lianchuan.common.utils.MD5Utils;
import com.lianchuan.common.utils.ZipUtils;
import com.lianchuan.ma.entity.vo.push.PushDataVO;
import com.lianchuan.ma.entity.vo.push.PushEntAccountVO;
import com.lianchuan.ma.entity.vo.push.PushEntCompanyVO;
import com.lianchuan.ma.entity.vo.push.PushEntContractVO;
import com.lianchuan.ma.entity.vo.push.PushEntExtendVO;
import com.lianchuan.ma.entity.vo.push.PushEntOrderVO;
import com.lianchuan.ma.entity.vo.push.PushEntVO;
import com.lianchuan.ma.service.AbstractMaService;
import com.lianchuan.ma.service.admin.push.PushRenzhongService;

public class PushRenzhongServiceImpl extends AbstractMaService implements PushRenzhongService, InitializingBean {


	/** 连接超时设置 */
	public static final int SOCKET_TIMEOUT = 120000;
	public static final int CONNECT_TIMEOUT = 120000;
	/** 平台标识 */
	private static final String EXTEND_SOURCE = "LIANCHUAN";


	private OssBizService ossBizService;
	private EntCommonDaoService entCommonDaoService;
	private String pushUrl;
	private String signKey; // 私钥
	private String downloadUrl;//下载附件的前缀地址
	

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.assertNotNull("人众renzhongUrl不能为空", pushUrl);
		Assert.assertNotNull("人众signKey不能为空", signKey);
	}

	public void setOssBizService(OssBizService ossBizService) {
		this.ossBizService = ossBizService;
	}

	public void setEntCommonDaoService(EntCommonDaoService entCommonDaoService) {
		this.entCommonDaoService = entCommonDaoService;
	}


	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}
	

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public Pair<Boolean, String> pushData(Long applyId) {
		CloseableHttpClient client = null;
		// 设置请求和传输超时时间
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIMEOUT)
				.setConnectTimeout(CONNECT_TIMEOUT).build();
		
		DateFormat yyyyFormat = new SimpleDateFormat("yyyy");
		DateFormat mmFormat = new SimpleDateFormat("MM");
		DateFormat ddFormat = new SimpleDateFormat("dd");
		
		try {
			client = HttpClientBuilder.create().build();
			HttpPost post = new HttpPost(pushUrl);
			post.addHeader("Content-Type", "application/json");
			post.setConfig(requestConfig);

			Map<String, byte[]> dataMap = new HashMap<String, byte[]>();
			List<EntUploadInfoPO> fileList = entCommonDaoService.findEntUploadInfoByApplyId(applyId);
			if (CollectionUtils.isEmpty(fileList)) {
				return Pair.of(false, "至少有一个附件！");
			}
			EntApplyPO applyPO = entCommonDaoService.findEntApplyById(applyId);
			
			//----------------以下是文件有类型的时候——--------------
//			Map<Integer, List<EntUploadInfoPO>> contractTypeMap = new HashMap<Integer, List<EntUploadInfoPO>>();
//			List<EntUploadInfoPO> uploadInfoPOs = new ArrayList<EntUploadInfoPO>();
//			for (EntUploadInfoPO entUploadInfoPO : fileList) {
//				if (contractTypeMap.containsKey(entUploadInfoPO.getContractFileType())) {
//					uploadInfoPOs = contractTypeMap.get(entUploadInfoPO.getContractFileType());
//				} else {
//					uploadInfoPOs = new ArrayList<EntUploadInfoPO>();
//					contractTypeMap.put(entUploadInfoPO.getContractFileType(), uploadInfoPOs);
//				}
//				uploadInfoPOs.add(entUploadInfoPO);
//			}
//			int index = 0;
//			for (Map.Entry<Integer, List<EntUploadInfoPO>> entry : contractTypeMap.entrySet()) {
//				uploadInfoPOs = entry.getValue();
//				index = 0;
//				for (EntUploadInfoPO entUploadInfoPO : uploadInfoPOs) {
//					ContractFileType contractType = ContractFileType.getType(entry.getKey());
//					if (contractType == null) {
//						continue;
//					}
//					UploadFileType fileType = UploadFileType.getType( entUploadInfoPO.getFileType());
//					
//					String fileName = contractType.getName();
//					int dotIndex = contractType.getName().lastIndexOf(".");
//					if (dotIndex > -1) {
//						fileName = fileName.substring(0, dotIndex) + "_" + index + fileName.substring(dotIndex);
//					} else {
//						fileName = fileName + "_" + index;
//					}
//					fileName = fileName + "." + fileType.getName();
//					byte[] fileData = ossBizService.getContractFile(entUploadInfoPO.getFilePath());
//					dataMap.put(fileName, fileData);
//				}
//			}
			
			//----------------以下是文件没有类型的时候——--------------
			int index = 0;
			Map<String, Integer> fileNameCountMap = new HashMap<String, Integer>();
			for (EntUploadInfoPO entUploadInfoPO : fileList) {
				String fileName = entUploadInfoPO.getFileName();
				if (fileNameCountMap.containsKey(fileName)) {
					index = fileNameCountMap.get(fileName);
				} else {
					index = 0;
				}
				fileNameCountMap.put(fileName, ++index);
				int dotIndex = fileName.lastIndexOf(".");
				if (index > 0) {
					if (dotIndex > -1) {
						fileName = fileName.substring(0, dotIndex) + "_" + index + fileName.substring(dotIndex);
					} else {
						fileName = fileName + "_" + index;
					}
				}
				byte[] fileData = ossBizService.getContractFile(entUploadInfoPO.getFilePath());
				dataMap.put(fileName, fileData);
			}
			
			// 压缩
			byte[] zipBin = ZipUtils.zipData(dataMap);
			Date createDate = applyPO.getCreateDate();
			String year = yyyyFormat.format(createDate);
			String month = mmFormat.format(createDate);
			String day = ddFormat.format(createDate);
			ossBizService.uploadZipFile(year, month, day, applyId, zipBin);
			String downloadZipUrl = downloadUrl + applyId;

			PushDataVO dataVO = new PushDataVO();
			PushEntVO vo = new PushEntVO();
			PushEntExtendVO extendVO = new PushEntExtendVO();
			extendVO.setSource(EXTEND_SOURCE);
			extendVO.setDownloadUrl(downloadZipUrl);
			dataVO.setExtend(extendVO);
			
			PushEntOrderVO orderVO = getPushEntOrderVO(applyPO);
			PushEntCompanyVO companyVO = getPushEntCompanyVO(applyPO);
			PushEntAccountVO accountVO = getPushEntAccountVO(applyPO);
			PushEntContractVO contractVO = getPushEntContractVO(applyPO);
			
			dataVO.setOrder(orderVO);
			dataVO.setCompany(companyVO);
			dataVO.setAccount(accountVO);
			dataVO.setContract(contractVO);
			dataVO.setNotify_url(null);
			vo.setData(dataVO);
			String sign = MD5Utils.encrypt(signKey + JSONObject.toJSONString(dataVO));
			vo.setSign(sign);
			
			String body = JSONObject.toJSONString(vo);
			if (logger.isDebugEnabled()) {
				logger.debug("request url:{},body:{}", pushUrl, body);
			}
			post.setEntity(new StringEntity(body, "UTF-8"));
			CloseableHttpResponse response = client.execute(post);
			String content = EntityUtils.toString(response.getEntity(), "UTF-8");

			logger.info("人众返回信息:{}", content);
			return Pair.of(true, content);
		} catch (EntException e) {
			throw e;
		} catch (Exception e) {
			throw new EntException(e);
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
	}
	
	
	private PushEntOrderVO getPushEntOrderVO(EntApplyPO applyPO) {
		PushEntOrderVO orderVO = new PushEntOrderVO();
		orderVO.setFz(1);
		orderVO.setOrderNo(applyPO.getCode());
		orderVO.setOrderPeriod(applyPO.getLoanPeriod()+"");
		orderVO.setOrderTime(applyPO.getCreateDate().getTime());
		orderVO.setOrderTotal(new BigDecimal(applyPO.getLoanMoney()).multiply(new BigDecimal(100)).longValue());
		return orderVO;
	}
	
	private PushEntCompanyVO getPushEntCompanyVO(EntApplyPO applyPO) {
		DateFormat yyyyMMddFormat = new SimpleDateFormat("yyyy-MM-dd");
		PushEntCompanyVO companyVO = new PushEntCompanyVO();
		companyVO.setAddress("");
		companyVO.setBusLicence(applyPO.getBusinessRegistrationNo());
		companyVO.setCompanyName(applyPO.getName());
		companyVO.setEmail("");
		companyVO.setEstablishmentData(yyyyMMddFormat.format(applyPO.getFoundDate()));
		companyVO.setIdCard("");
		companyVO.setLinkman(applyPO.getLegalRepresentative());
		companyVO.setLinkmanMobile(applyPO.getContactPhone());
		companyVO.setMainBusiness("");
		companyVO.setName(applyPO.getLegalRepresentative());
		companyVO.setOrgCode(applyPO.getSocialCreditIdentifier());
		companyVO.setRegistrationMoney(applyPO.getRegistMoney());
		companyVO.setRegistrationPlace(applyPO.getAddress());
		return companyVO;
	}
	
	private PushEntAccountVO getPushEntAccountVO(EntApplyPO applyPO) {
		PushEntAccountVO accountVO = new PushEntAccountVO();
		accountVO.setAccountType("1");
		accountVO.setBankCardNo(applyPO.getBankNo());
		accountVO.setBankName(applyPO.getBankName());
		accountVO.setName(applyPO.getBankAccountName());
		return accountVO;
	}
	
	private PushEntContractVO getPushEntContractVO(EntApplyPO applyPO) {
		PushEntContractVO contractVO = new PushEntContractVO();
		contractVO.setBorrowContent("");
		contractVO.setBorrowStyle(applyPO.getBorrowStyle());
		contractVO.setBorrowUse(applyPO.getLoanUsage());
		contractVO.setContractInterest(0);
		contractVO.setContractNo(applyPO.getContractNo());
		contractVO.setStartTime(applyPO.getLoanDate().getTime());
		DateTime endTime = new DateTime(applyPO.getLoanDate()).plusDays(applyPO.getLoanPeriod());
		contractVO.setEndTime(endTime.getMillis());
		return contractVO;
	}
	
	
	
	

}
