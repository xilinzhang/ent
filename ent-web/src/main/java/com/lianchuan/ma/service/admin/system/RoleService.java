package com.lianchuan.ma.service.admin.system;

import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.AddRoleParam;
import com.lianchuan.ma.entity.vo.param.system.DeleteRoleParam;
import com.lianchuan.ma.entity.vo.param.system.EditRoleParam;
import com.lianchuan.ma.entity.vo.param.system.GetRoleListParam;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.system.GetDeptListResult;
import com.lianchuan.ma.entity.vo.result.system.GetPermissionListResult;
import com.lianchuan.ma.entity.vo.result.system.GetRolePermissionListParam;
import com.lianchuan.ma.entity.vo.result.system.PageRoleListResult;

/**
 * 角色管理
 */
public interface RoleService {

	/**
	 * 获取角色列表
	 * 
	 * @param param
	 * @return
	 */
	PageRoleListResult pageRoleList(GetRoleListParam param);

	/**
	 * 获取权限列表
	 * 
	 * @param param
	 * @return
	 */
	GetPermissionListResult getPermissionList(BaseOperatorParam param);

	/**
	 * 获取部门列表
	 * 
	 * @param param
	 * @return
	 */
	GetDeptListResult getDeptList(BaseOperatorParam param);

	/**
	 * 创建角色
	 * 
	 * @param param
	 * @return
	 */
	BaseMaResult addRole(AddRoleParam param);

	/**
	 * 修改角色
	 * 
	 * @param param
	 * @return
	 */
	BaseMaResult editRole(EditRoleParam param);

	/**
	 * 获取角色权限列表
	 * 
	 * @param param
	 * @return
	 */
	GetPermissionListResult getRolePermissionList(GetRolePermissionListParam param);

	/**
	 * 删除角色
	 * @param param
	 * @return
	 */
	BaseMaResult deleteRole(DeleteRoleParam param);
}
