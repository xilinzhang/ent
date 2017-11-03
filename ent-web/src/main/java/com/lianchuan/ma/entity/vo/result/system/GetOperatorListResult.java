package com.lianchuan.ma.entity.vo.result.system;

        import com.lianchuan.ma.entity.vo.result.BaseMaPageResult;
        import io.swagger.annotations.ApiModel;

/**
 * 获取管理员列表
 */
@ApiModel(value = "GetOperatorListResult", description = "获取管理员列表")
public class GetOperatorListResult extends BaseMaPageResult<ManagerVO> {

}
