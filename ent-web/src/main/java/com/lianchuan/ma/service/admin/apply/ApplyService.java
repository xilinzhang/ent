package com.lianchuan.ma.service.admin.apply;

import com.lianchuan.ma.entity.vo.param.apply.*;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.apply.EditApplyInfoResult;
import com.lianchuan.ma.entity.vo.result.apply.EditContractInfoResult;
import com.lianchuan.ma.entity.vo.result.apply.GetApplyInfoProvideResult;

/**
 * Created by zxl on 2017-10-23
 **/
public interface ApplyService {

    /**
     * 提交审核
     * @param param
     * @return
     */
    BaseMaResult reviewApplyInfo(ReviewApplyInfoParam param);

    /**
     * 新增或者编辑标的信息
     * @param param
     * @return
     */
    EditApplyInfoResult editApplyInfo(EditApplyInfoParam param);

    /**
     * 新增或者编辑合同文件
     * @param param
     * @return
     */
    EditContractInfoResult editContractInfo(EditContractInfoParam param);

    /**
     * 删除合同文件
     * @param param
     * @return
     */
    BaseMaResult delContractInfo(DelContractInfoParam param);

    /**
     * 获取老数据提供的公司信息
     * @param param
     * @return
     */
    GetApplyInfoProvideResult getApplyInfoProvideInfo(GetApplyInfoProvideParam param);
}
