package com.lianchuan.common.entity.po.ma;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lianchuan.common.dao.DaoGlobal;
import com.lianchuan.common.entity.po.BasePO;

@Entity
@Table(name = DaoGlobal.MA_TABLE_BASE_NAME + "token")
public class MaTokenPO extends BasePO {

	private static final long serialVersionUID = -6439170756995298912L;

	/** 管理员ID */
	@Column(nullable = false, unique = true)
	private Long managerId;
	/** 在线标识:true:在线,false:下线 */
	@Column(nullable = false)
	private Boolean flag;
	/** 登陆令牌 */
	@Column(nullable = false, length = 50)
	private String token;
	/** 登陆IP地址 */
	@Column
	private String loginIp;
	/** 登出IP地址 */
	@Column
	private String logoutIp;
	/** 登陆时间 */
	@Column
	private Date loginTime;
	/** 登出时间 */
	@Column
	private Date logoutTime;

	public Long getManagerId() {
		return managerId;
	}

	public void setManagerId(Long managerId) {
		this.managerId = managerId;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLogoutIp() {
		return logoutIp;
	}

	public void setLogoutIp(String logoutIp) {
		this.logoutIp = logoutIp;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

}
