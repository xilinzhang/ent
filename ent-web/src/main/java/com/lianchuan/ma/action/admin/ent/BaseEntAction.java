package com.lianchuan.ma.action.admin.ent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lianchuan.ma.action.BaseMaAction;
import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import com.lianchuan.ma.entity.vo.param.apply.FileDownloadParam;
import com.lianchuan.ma.entity.vo.result.apply.GetAllManagerResult;
import com.lianchuan.ma.service.admin.apply.EntCommonService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RequestMapping(value = "/")
public abstract class BaseEntAction extends BaseMaAction {

	@Autowired
	private EntCommonService entCommonService;
	
	
	@ApiOperation(value = "下载文件",notes = "下载文件")
    @ApiImplicitParams({  
            @ApiImplicitParam(name = "fileId", value = "文件id", required = true, dataType = "Long",  paramType="query"),
    })
	@RequestMapping(value = "/downloadFile" ,method = { RequestMethod.POST })
	public void downloadContract(FileDownloadParam param, HttpServletRequest request, HttpServletResponse response) throws Exception{
		entCommonService.downloadFile(param, request, response);
	}
	
	@ApiOperation(value = "获取所有用户", notes = "获取所有用户", consumes = "application/json", response = GetAllManagerResult.class)
	@RequestMapping(value = "/getAllUser", method = RequestMethod.POST)
	@ResponseBody
	public void getAllUser(@ApiParam(required = true, value = "搜索参数") @RequestBody BaseOperatorParam param)
			throws Exception {
		GetAllManagerResult result = entCommonService.getAllUser(param);
		writeValue(result);
	}
}
