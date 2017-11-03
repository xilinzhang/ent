package com.lianchuan.ma.service.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lianchuan.common.entity.po.ma.MaManagerPO;
import com.lianchuan.common.entity.po.ma.MaManagerRolePO;
import com.lianchuan.common.entity.type.ma.EnableType;
import com.lianchuan.common.service.AbstractService;
import com.lianchuan.common.service.dao.ma.MaManagerDaoService;
import com.lianchuan.ma.Global;
import com.lianchuan.ma.utils.PasswordUtils;

/***
 * 初始化数据
 */
@Service("managerDataService")
public class ManagerDataService extends AbstractService implements InitializingBean {

	@Autowired
	private MaManagerDaoService maManagerDaoService;

	@Override
	public void afterPropertiesSet() throws Exception {
		initManagerRole();
		initManager();
	}

	/**
	 * 初始化管理员
	 */
	private void initManager() {
		MaManagerPO maManagerPO = maManagerDaoService.findManager(Global.SYSTEM_ID);
		if (maManagerPO == null) {
			String password = PasswordUtils.encrypt(Global.DEFAULT_PASSWORD, Global.SYSTEM_USERNAME);
			maManagerDaoService.saveSystemAdmin(Global.SYSTEM_ID, Global.SYSTEM_NAME, Global.SYSTEM_USERNAME, password, Global.SYSTEM_ROLE_ID, EnableType.ENABLE_1.getValue(), "");
		}
	}

	/**
	 * 初始化管理员角色
	 */
	private void initManagerRole() {
		MaManagerRolePO maManagerRolePO = maManagerDaoService.findManagerRole(Global.SYSTEM_ROLE_ID);
		if (maManagerRolePO == null) {
			maManagerDaoService.saveSystemAdminRole(Global.SYSTEM_ROLE_ID, Global.SYSTEM_ROLE_NAME);
		}
	}

}
