package com.lianchuan.ma.action.admin.system;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lianchuan.ma.action.BaseMaAction;
import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.EditPasswordParam;
import com.lianchuan.ma.entity.vo.param.system.LoginParam;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.system.GetMenuListResult;
import com.lianchuan.ma.entity.vo.result.system.GetOperatorInfoResult;
import com.lianchuan.ma.entity.vo.result.system.LoginResult;
import com.lianchuan.ma.service.admin.system.SystemService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags= {"system"})
@Controller
@RequestMapping(value = "/system")
public class SystemAction extends BaseMaAction {

	@Autowired
	private SystemService systemService;
	
	@ApiOperation(value = "登陆", notes = "登陆status=1为新用户，需要跳转修改密码页面", consumes="application/json", response = LoginResult.class)
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	@ResponseBody
	public void login(@ApiParam(required = true, value = "搜索参数") @RequestBody LoginParam param) throws Exception {
		LoginResult result = systemService.login(param, getIp());
		writeValue(result);
	}
	@ApiOperation(value = "登出", notes = "登出", consumes="application/json", response = BaseMaResult.class)
	@RequestMapping(value = "/logout", method = { RequestMethod.POST })
	@ResponseBody
	public void logout(@ApiParam(required = true, value = "搜索参数") @RequestBody BaseOperatorParam param) throws Exception {
		try {
			systemService.logout(param, getIp());
		} catch (Exception e) {
			logger.error("", e);
		}
		BaseMaResult result = new BaseMaResult();
		writeValue(result);
	}
	@ApiOperation(value = "获取管理员信息", notes = "获取管理员信息", consumes="application/json", response = GetOperatorInfoResult.class)
	@RequestMapping(value = "/getOperatorInfo", method = RequestMethod.POST)
	@ResponseBody
	public void getOperatorInfo(@ApiParam(required = true, value = "搜索参数") @RequestBody BaseOperatorParam param) throws Exception {
		GetOperatorInfoResult result = systemService.getOperatorInfo(param);
		writeValue(result);
	}
	@ApiOperation(value = "获取菜单列表", notes = "获取菜单列表", consumes="application/json", response = GetMenuListResult.class)
	@RequestMapping(value = "/getMenuList", method = RequestMethod.POST)
	@ResponseBody
	public void getMenuList(@ApiParam(required = true, value = "搜索参数") @RequestBody BaseOperatorParam param) throws Exception {
		GetMenuListResult result = systemService.getMenuList(param);
		writeValue(result);
	}
	@ApiOperation(value = "修改密码", notes = "修改密码", consumes="application/json", response = BaseMaResult.class)
	@RequestMapping(value = "/editPassword", method = RequestMethod.POST)
	@ResponseBody
	public void editPassword(@ApiParam(required = true, value = "搜索参数") @RequestBody EditPasswordParam param) throws Exception {
		BaseMaResult result = systemService.editPassword(param);
		writeValue(result);
	}

}
