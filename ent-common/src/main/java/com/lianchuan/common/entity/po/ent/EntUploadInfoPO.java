package com.lianchuan.common.entity.po.ent;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lianchuan.common.dao.DaoGlobal;
import com.lianchuan.common.entity.po.BasePO;

/***
 * 企业工商信息申请提交
 */
@Entity
@Table(name = DaoGlobal.ENT_TABLE_BASE_NAME + "upload_info")
public class EntUploadInfoPO extends BasePO {

	private static final long serialVersionUID = 209009406078181496L;

	/** 标的ID */
	@Column(nullable = false)
	private Long applyId;

	/** 上传人 */
	@Column(nullable = false)
	private Long uploadManagerId;

	/** 文件名 */
	@Column(nullable = false, length = 255)
	private String fileName;
	
//	/** 文件协议类型 {@link com.lianchuan.common.entity.type.ent.ContractFileType} */
//	@Column(nullable = false)
//	private Integer contractFileType;
	
	/** 文件类型 {@link com.lianchuan.common.entity.type.ent.UploadFileType} */
	@Column(nullable = false)
	private Integer fileType;
	/** 文件大小 */
	@Column
	private Long fileSize;
	
	/** 文件路径 */
	@Column
	private String filePath;

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public Long getUploadManagerId() {
		return uploadManagerId;
	}

	public void setUploadManagerId(Long uploadManagerId) {
		this.uploadManagerId = uploadManagerId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

//	public Integer getContractFileType() {
//		return contractFileType;
//	}
//
//	public void setContractFileType(Integer contractFileType) {
//		this.contractFileType = contractFileType;
//	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

}
