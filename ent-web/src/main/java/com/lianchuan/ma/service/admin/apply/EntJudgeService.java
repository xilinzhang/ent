package com.lianchuan.ma.service.admin.apply;

import com.lianchuan.common.entity.type.ma.Menu;
import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import com.lianchuan.ma.entity.vo.param.apply.RiskJudgeParam;
import com.lianchuan.ma.entity.vo.param.apply.GetApplyInfoListParam;
import com.lianchuan.ma.entity.vo.param.apply.GetEntApplyDetailParam;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.apply.GetAllManagerResult;
import com.lianchuan.ma.entity.vo.result.apply.GetApplyInfoListResult;
import com.lianchuan.ma.entity.vo.result.apply.GetEntApplyDetailResult;

/*
 * 风控审批
 */
public interface EntJudgeService {
	/*
	 * 获取企业工商审批信息列表
	 * 
	 * @param param
	 * @return
	 */
	GetApplyInfoListResult getEntInfoList(GetApplyInfoListParam param,Menu menu);
	/*
	 * @param param
	 * @return
	 * 获取风控审批详情
	 */
	GetEntApplyDetailResult getEntDetail(GetEntApplyDetailParam param);
	
	/*
	 * @param param
	 * @return
	 * 风控审批
	 */
	BaseMaResult diskJudge(RiskJudgeParam param);
	/*
	 * 获取所有的创建人
	 * @param param
	 * @return
	 */
	GetAllManagerResult getAllCreateManager(BaseOperatorParam param);
}
