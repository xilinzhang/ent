package com.lianchuan.ma.action.admin.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lianchuan.ma.action.BaseMaAction;
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
import com.lianchuan.ma.service.admin.system.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags= {"role"})
@Controller
@RequestMapping(value = "/system")
public class RoleAction extends BaseMaAction {

	@Autowired
	private RoleService roleService;

	@ApiOperation(value = "获取角色列表", notes = "获取角色列表", consumes="application/json", response = PageRoleListResult.class)
	@RequestMapping(value = "/pageRoleList", method = RequestMethod.POST)
	@ResponseBody
	public void pageRoleList(@ApiParam(required = true,value = "搜索参数") @RequestBody GetRoleListParam param) throws Exception {
		PageRoleListResult result = roleService.pageRoleList(param);
		writeValue(result);
	}

	@ApiOperation(value = "获取权限列表", notes = "获取权限列表", consumes="application/json", response = GetPermissionListResult.class)
	@RequestMapping(value = "/getPermissionList", method = RequestMethod.POST)
	@ResponseBody
	public void getPermissionList(@ApiParam(required = true,value = "搜索参数") @RequestBody BaseOperatorParam param) throws Exception {
		GetPermissionListResult result = roleService.getPermissionList(param);
		writeValue(result);
	}

	@ApiOperation(value = "获取部门列表", notes = "获取部门列表", consumes="application/json", response = GetDeptListResult.class)
	@RequestMapping(value = "/getDeptList", method = RequestMethod.POST)
	@ResponseBody
	public void getDeptList(@ApiParam(required = true,value = "搜索参数") @RequestBody BaseOperatorParam param) throws Exception {
		GetDeptListResult result = roleService.getDeptList(param);
		writeValue(result);
	}

	@ApiOperation(value = "创建角色", notes = "创建角色", consumes="application/json", response = BaseMaResult.class)
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	@ResponseBody
	public void addRole(@ApiParam(required = true,value = "搜索参数") @RequestBody AddRoleParam param) throws Exception {
		BaseMaResult result = roleService.addRole(param);
		writeValue(result);
	}

	@ApiOperation(value = "修改角色", notes = "修改角色", consumes="application/json", response = BaseMaResult.class)
	@RequestMapping(value = "/editRole", method = RequestMethod.POST)
	@ResponseBody
	public void editRole(@ApiParam(required = true,value = "搜索参数") @RequestBody EditRoleParam param) throws Exception {
		BaseMaResult result = roleService.editRole(param);
		writeValue(result);
	}

	@ApiOperation(value = "获取角色权限列表", notes = "获取角色权限列表", consumes="application/json", response = GetPermissionListResult.class)
	@RequestMapping(value = "/getRolePermissionList", method = RequestMethod.POST)
	@ResponseBody
	public void getRolePermissionList(@ApiParam(required = true,value = "搜索参数") @RequestBody GetRolePermissionListParam param) throws Exception {
		GetPermissionListResult result = roleService.getRolePermissionList(param);
		writeValue(result);
	}
	
	@ApiOperation(value = "删除角色", notes = "删除角色", consumes="application/json", response = BaseMaResult.class)
	@RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
	@ResponseBody
	public void deleteRole(@ApiParam(required = true,value = "搜索参数") @RequestBody DeleteRoleParam param) throws Exception {
		BaseMaResult result = roleService.deleteRole(param);
		writeValue(result);
	}
	

}
