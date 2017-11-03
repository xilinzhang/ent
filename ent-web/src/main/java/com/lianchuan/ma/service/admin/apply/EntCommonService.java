package com.lianchuan.ma.service.admin.apply;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lianchuan.common.entity.type.ma.Menu;
import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import com.lianchuan.ma.entity.vo.param.apply.RiskJudgeParam;
import com.lianchuan.ma.entity.vo.param.apply.FileDownloadParam;
import com.lianchuan.ma.entity.vo.param.apply.GetApplyInfoListParam;
import com.lianchuan.ma.entity.vo.param.apply.GetEntApplyDetailParam;
import com.lianchuan.ma.entity.vo.param.apply.ReviewApplyInfoListParam;
import com.lianchuan.ma.entity.vo.param.apply.ReviewApplyInfoParam;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.apply.GetAllManagerResult;
import com.lianchuan.ma.entity.vo.result.apply.GetApplyInfoListResult;
import com.lianchuan.ma.entity.vo.result.apply.GetEntApplyDetailResult;

import java.util.List;

/*
 * 标的通用service
 */
public interface EntCommonService {
	/*
	 * 获取企业工商审批信息列表
	 * 
	 * @param param
	 * 
	 * @return
	 */
	GetApplyInfoListResult getEntInfoList(GetApplyInfoListParam param, Menu menu, List<Integer> applyStatusList,String orderBy);

	/*
	 * @param param
	 * 
	 * @return 获取风控审批详情
	 */
	GetEntApplyDetailResult getEntDetail(GetEntApplyDetailParam param,Menu menu);

	/*
	 * @param param
	 * 
	 * @return 风控审批
	 */
	BaseMaResult entJudge(RiskJudgeParam param);

	/**
	 * 下载文件
	 * 
	 * @param param
	 * @param response
	 * @return
	 */
	BaseMaResult downloadFile(FileDownloadParam param, HttpServletRequest request, HttpServletResponse response);

	public BaseMaResult downloadEntFile(Long applyId, HttpServletRequest request, HttpServletResponse response);
	
	/*
	 * 查询所有用户
	 */
	public GetAllManagerResult getAllUser(BaseOperatorParam param);
	/*
	 * 标的推送
	 */
	BaseMaResult entPush(ReviewApplyInfoParam param);
	/*
	 * 标的批量推送
	 */
	BaseMaResult entPushList(ReviewApplyInfoListParam param);
}
