package com.lianchuan.ma.action.admin.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lianchuan.ma.action.BaseMaAction;
import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.AddOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.EditOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.EnableOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.GetOperatorListParam;
import com.lianchuan.ma.entity.vo.param.system.ResetPasswordParam;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.system.GetOperatorListResult;
import com.lianchuan.ma.entity.vo.result.system.GetRoleListResult;
import com.lianchuan.ma.service.admin.system.ManagerServcie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags= {"manager"})
@Controller
@RequestMapping(value = "/system")
public class ManagerAction extends BaseMaAction {
	
	@Autowired
	private ManagerServcie managerServcie;
	
	@ApiOperation(value = "获取用户", notes = "获取用户列表", consumes="application/json", response = GetOperatorListResult.class)
	@RequestMapping(value = "/getOperatorList", method = RequestMethod.POST)
	@ResponseBody
	public void getOperatorList(@ApiParam(required = true, value = "搜索参数") @RequestBody GetOperatorListParam param) throws Exception {
		GetOperatorListResult result = managerServcie.getOperatorList(param);
		writeValue(result);
	}
	@ApiOperation(value = "获取所有角色列表", notes = "获取所有角色列表", consumes="application/json", response = GetRoleListResult.class)
	@RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
	@ResponseBody
	public void getRoleList(@ApiParam(required = true,value = "搜索参数") @RequestBody BaseOperatorParam param) throws Exception {
		GetRoleListResult result = managerServcie.getRoleList(param);
		writeValue(result);
	}

	@ApiOperation(value = "添加用户", notes = "添加用户", consumes="application/json", response = BaseMaResult.class)
	@RequestMapping(value = "/addOperator", method = RequestMethod.POST)
	@ResponseBody
	public void addOperator(@ApiParam(required = true,value = "参数") @RequestBody AddOperatorParam param) throws Exception {
		BaseMaResult result = managerServcie.addOperator(param);
		writeValue(result);
	}

	@ApiOperation(value = "修改用户信息", notes = "修改用户信息", consumes="application/json", response = BaseMaResult.class)
	@RequestMapping(value = "/editOperator", method = RequestMethod.POST)
	@ResponseBody
	public void editOperator(@ApiParam(required = true,value = "参数") @RequestBody EditOperatorParam param) throws Exception {
		BaseMaResult result = managerServcie.editOperator(param);
		writeValue(result);
	}

	@ApiOperation(value = "重置用户密码", notes = "重置用户密码", consumes="application/json", response = BaseMaResult.class)
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	@ResponseBody
	public void resetPassword(@ApiParam(required = true,value = "参数") @RequestBody ResetPasswordParam param) throws Exception {
		BaseMaResult result = managerServcie.resetPassword(param);
		writeValue(result);
	}

	@ApiOperation(value = "启用或停用用户", notes = "启用或停用用户", consumes="application/json", response = BaseMaResult.class)
	@RequestMapping(value = "/enableOperator", method = RequestMethod.POST)
	@ResponseBody
	public void enableOperator(@ApiParam(required = true,value = "参数") @RequestBody EnableOperatorParam param) throws Exception {
		BaseMaResult result = managerServcie.enableOperator(param);
		writeValue(result);
	}
	

}
