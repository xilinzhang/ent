package com.lianchuan.ma.service.admin.system;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.AddOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.EditOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.EnableOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.GetOperatorListParam;
import com.lianchuan.ma.entity.vo.param.system.ResetPasswordParam;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.system.GetOperatorListResult;
import com.lianchuan.ma.entity.vo.result.system.GetRoleListResult;

/**
 * 管理员信息
 */
public interface ManagerServcie {

	/**
	 * 获取管理员列表
	 * 
	 * @param param
	 * @return
	 */
	GetOperatorListResult getOperatorList(GetOperatorListParam param);

	/**
	 * 获取所有角色列表
	 * 
	 * @param param
	 * @return
	 */
	GetRoleListResult getRoleList(BaseOperatorParam param);

	/**
	 * 添加管理员
	 * 
	 * @param param
	 * @return
	 */
	BaseMaResult addOperator(AddOperatorParam param);

	/**
	 * 修改用户角色
	 * 
	 * @param param
	 * @return
	 */
	BaseMaResult editOperator(EditOperatorParam param);

	/**
	 * 重置管理员密码
	 * 
	 * @param param
	 * @return
	 */
	BaseMaResult resetPassword(ResetPasswordParam param);

	/**
	 * 启用或禁用
	 * 
	 * @param param
	 * @return
	 */
	BaseMaResult enableOperator(EnableOperatorParam param);
}
