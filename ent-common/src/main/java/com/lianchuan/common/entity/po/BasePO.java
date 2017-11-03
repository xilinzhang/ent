package com.lianchuan.common.entity.po;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 基础持久对象类
 */
@MappedSuperclass
public abstract class BasePO implements Serializable {

	private static final long serialVersionUID = -1139568144348402546L;

	@Id
	@GeneratedValue
	private Long id;

	/** 创建时间 */
	@Column(nullable = false)
	private Date createDate = new Date();

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
