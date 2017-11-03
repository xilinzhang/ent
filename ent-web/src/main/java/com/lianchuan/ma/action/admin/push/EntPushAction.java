package com.lianchuan.ma.action.admin.push;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lianchuan.common.entity.type.ent.ApplyStatus;
import com.lianchuan.common.entity.type.ma.Menu;
import com.lianchuan.ma.action.admin.ent.BaseEntAction;
import com.lianchuan.ma.entity.vo.param.apply.GetApplyInfoListParam;
import com.lianchuan.ma.entity.vo.param.apply.GetEntApplyDetailParam;
import com.lianchuan.ma.entity.vo.param.apply.ReviewApplyInfoListParam;
import com.lianchuan.ma.entity.vo.param.apply.ReviewApplyInfoParam;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.apply.GetApplyInfoListResult;
import com.lianchuan.ma.entity.vo.result.apply.GetEntApplyDetailResult;
import com.lianchuan.ma.service.admin.apply.EntCommonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags= {"push"})
@Controller
@RequestMapping(value = "/push")
public class EntPushAction extends BaseEntAction{
	
	@Autowired
	private EntCommonService entCommonService;
	
	@ApiOperation(value = "推送列表", notes = "推送列表", consumes="application/json", response = GetApplyInfoListResult.class)
	@RequestMapping(value = "/getBidInfoList",method = RequestMethod.POST)
	@ResponseBody
	public void getBidInfoList(@ApiParam(required = true, value = "搜索参数") @RequestBody GetApplyInfoListParam param) throws Exception {
		List<Integer> applyStatusList = new ArrayList<Integer>();
		if(param.getApplyStatus()==null){
			applyStatusList.add(ApplyStatus.AUDIT_PASS.getValue());
			applyStatusList.add(ApplyStatus.PUSH_FAIL.getValue());
		}else {
			applyStatusList.add(param.getApplyStatus());
		}
		String orderBy = "order by modify_date asc";
		GetApplyInfoListResult result = entCommonService.getEntInfoList(param,Menu.M_130000,applyStatusList,orderBy);
		writeValue(result);
	}
	@ApiOperation(value = "推送详情",notes = "推送详情",consumes="application/json",response =GetEntApplyDetailResult.class)
	@RequestMapping(value = "/getBidDetail",method = RequestMethod.POST)
	@ResponseBody
	public void getBidDetail(@ApiParam(required = true, value = "搜索参数") @RequestBody GetEntApplyDetailParam param) throws Exception {
		GetEntApplyDetailResult result = entCommonService.getEntDetail(param,Menu.M_130000);
		writeValue(result);
	}
	@ApiOperation(value = "标的推送",notes = "标的推送",consumes="application/json",response = BaseMaResult.class)
	@RequestMapping(value = "/entPush",method = RequestMethod.POST)
	@ResponseBody
	public void entPush(@ApiParam(required = true, value = "搜索参数") @RequestBody ReviewApplyInfoParam param) throws Exception {
		BaseMaResult result = entCommonService.entPush(param);
		writeValue(result);
	}
	@ApiOperation(value = "标的批量推送",notes = "标的批量推送",consumes="application/json",response = BaseMaResult.class)
	@RequestMapping(value = "/entPushList",method = RequestMethod.POST)
	@ResponseBody
	public void entPushList(@ApiParam(required = true, value = "搜索参数") @RequestBody ReviewApplyInfoListParam param) throws Exception {
		BaseMaResult result = entCommonService.entPushList(param);
		writeValue(result);
	}
}
