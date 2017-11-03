package com.lianchuan.ma.action.admin.judge;

import com.lianchuan.common.entity.type.ent.ApplyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lianchuan.common.entity.type.ma.Menu;
import com.lianchuan.ma.action.admin.ent.BaseEntAction;


import com.lianchuan.ma.entity.vo.param.apply.RiskJudgeParam;

import com.lianchuan.ma.entity.vo.param.apply.GetApplyInfoListParam;
import com.lianchuan.ma.entity.vo.param.apply.GetEntApplyDetailParam;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.apply.GetApplyInfoListResult;
import com.lianchuan.ma.entity.vo.result.apply.GetEntApplyDetailResult;
import com.lianchuan.ma.service.admin.apply.EntCommonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.ArrayList;
import java.util.List;

@Api(tags= {"judge"})
@Controller
@RequestMapping(value = "/judge")
public class EntJudgeAction extends BaseEntAction {
	@Autowired
	private EntCommonService entCommonService;

	@ApiOperation(value = "审批列表", notes = "审批列表", consumes = "application/json", response = GetApplyInfoListResult.class)
	@RequestMapping(value = "/getEntInfoList", method = RequestMethod.POST)
	@ResponseBody
	public void getEntInfoList(@ApiParam(required = true, value = "搜索参数") @RequestBody GetApplyInfoListParam param)
			throws Exception {
		List<Integer> applyStatusList = new ArrayList<Integer>();
		if(param.getApplyStatus()==null){
			applyStatusList.add(ApplyStatus.WAIT_AUDIT.getValue());
			applyStatusList.add(ApplyStatus.AUDIT_FAIL.getValue());
		}else {
			applyStatusList.add(param.getApplyStatus());
		}
		String orderBy = "order by apply_status,modify_date asc";
		GetApplyInfoListResult result = entCommonService.getEntInfoList(param, Menu.M_120100,applyStatusList,orderBy);
		writeValue(result);
	}

	@ApiOperation(value = "获取审批详情", notes = "获取审批详情", consumes = "application/json", response = GetEntApplyDetailResult.class)
	@RequestMapping(value = "/getEntDetail", method = RequestMethod.POST)
	@ResponseBody
	public void getEntDetail(@ApiParam(required = true, value = "搜索参数") @RequestBody GetEntApplyDetailParam param)
			throws Exception {
		GetEntApplyDetailResult result = entCommonService.getEntDetail(param,Menu.M_120100);
		writeValue(result);
	}

	@ApiOperation(value = "风控审批", notes = "风控审批", consumes = "application/json", response = BaseMaResult.class)
	@RequestMapping(value = "/entJudge", method = RequestMethod.POST)
	@ResponseBody
	public void entJudge(@ApiParam(required = true, value = "搜索参数") @RequestBody RiskJudgeParam param)
			throws Exception {
		BaseMaResult result = entCommonService.entJudge(param);
		writeValue(result);
	}

	

}
