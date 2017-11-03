package com.lianchuan.ma.service.admin.system;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.EditPasswordParam;
import com.lianchuan.ma.entity.vo.param.system.LoginParam;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.system.GetMenuListResult;
import com.lianchuan.ma.entity.vo.result.system.GetOperatorInfoResult;
import com.lianchuan.ma.entity.vo.result.system.LoginResult;

/**
 * 登录登出服务
 */
public interface SystemService {

	/**
	 * 登录
	 * 
	 * @param param
	 * @param ip
	 * @return
	 */
	LoginResult login(LoginParam param, String ip);

	/**
	 * 登出
	 * 
	 * @param param
	 * @param ip
	 * @return
	 */
	void logout(BaseOperatorParam param, String ip);

	/**
	 * 获取管理员信息
	 * 
	 * @param param
	 * @return
	 */
	GetOperatorInfoResult getOperatorInfo(BaseOperatorParam param);

	/**
	 * 获取菜单列表
	 * 
	 * @param param
	 * @return
	 */
	GetMenuListResult getMenuList(BaseOperatorParam param);

	/**
	 * 修改密码
	 * 
	 * @param param
	 * @return
	 */
	BaseMaResult editPassword(EditPasswordParam param);

}
