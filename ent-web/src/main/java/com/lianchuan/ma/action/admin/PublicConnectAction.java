package com.lianchuan.ma.action.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lianchuan.common.action.BaseAction;
import com.lianchuan.ma.service.admin.apply.EntCommonService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


@Controller
@RequestMapping(value = "/download")
public class PublicConnectAction extends BaseAction{

	@Autowired
	private EntCommonService entCommonService;
	
	@ApiOperation(value = "给人众公开下载文件",notes = "公开下载文件")
    @ApiImplicitParams({  
            @ApiImplicitParam(name = "applyId", value = "applyId", required = true, dataType = "Long",  paramType="query"),
    })
	@RequestMapping(value = "/ent/{applyId}" ,method = { RequestMethod.POST, RequestMethod.GET })
	public void downloadZipFile(@PathVariable("applyId") Long applyId, HttpServletRequest request, HttpServletResponse response) throws Exception{
		entCommonService.downloadEntFile(applyId, request, response);
	}
	

}
