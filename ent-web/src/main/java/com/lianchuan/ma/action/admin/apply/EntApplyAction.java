package com.lianchuan.ma.action.admin.apply;


import java.util.ArrayList;
import java.util.List;

import com.lianchuan.ma.entity.vo.param.apply.*;
import com.lianchuan.ma.entity.vo.result.apply.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.lianchuan.common.entity.type.ent.ApplyStatus;
import com.lianchuan.common.entity.type.ma.Menu;
import com.lianchuan.ma.action.admin.ent.BaseEntAction;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.service.admin.apply.ApplyService;
import com.lianchuan.ma.service.admin.apply.EntCommonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags= {"apply"})
@Controller
@RequestMapping(value = "/apply")
public class EntApplyAction extends BaseEntAction {

    @Autowired
    private EntCommonService entCommonService;
    @Autowired
    private ApplyService applyService;

    @ApiOperation(value = "获取业务发起信息列表", notes = "获取业务发起信息列表", consumes = "application/json", response = GetApplyInfoListResult.class)
    @RequestMapping(value = "/getApplyInfoList", method = RequestMethod.POST)
    @ResponseBody
    public void getApplyInfoList(@ApiParam(required = true, value = "搜索参数") @RequestBody GetApplyInfoListParam param) throws Exception {
        List<Integer> applyStatusList = new ArrayList<Integer>();
        if(param.getApplyStatus()==null){
            applyStatusList.add(ApplyStatus.NEW_1.getValue());
            applyStatusList.add(ApplyStatus.WAIT_AUDIT.getValue());
            applyStatusList.add(ApplyStatus.AUDIT_PASS.getValue());
            applyStatusList.add(ApplyStatus.AUDIT_FAIL.getValue());
            applyStatusList.add(ApplyStatus.PUSH_SUCCESS.getValue());
        }else {
            applyStatusList.add(param.getApplyStatus());
        }
        String orderBy = "order by modify_date desc";
        GetApplyInfoListResult result = entCommonService.getEntInfoList(param, Menu.M_110100,applyStatusList,orderBy);
        writeValue(result);
    }


    @ApiOperation(value = "获取业务发起信息详情", notes = "获取业务发起信息详情", consumes = "application/json", response = GetEntApplyDetailResult.class)
    @RequestMapping(value = "/getApplyInfoById", method = RequestMethod.POST)
    @ResponseBody
    public void getApplyInfoById(@ApiParam(required = true, value = "搜索参数") @RequestBody GetEntApplyDetailParam param) throws Exception {
        GetEntApplyDetailResult result = entCommonService.getEntDetail(param,Menu.M_110100);
        writeValue(result);
    }

    @ApiOperation(value = "新增或者编辑标的信息", notes = "新增或者编辑标的信息", consumes = "application/json", response = EditApplyInfoResult.class)
    @RequestMapping(value = "/editApplyInfo", method = RequestMethod.POST)
    @ResponseBody
    public void editApplyInfo(@ApiParam(required = true, value = "编辑标的信息参数") @RequestBody EditApplyInfoParam param) throws Exception {
        EditApplyInfoResult result = applyService.editApplyInfo(param);
        writeValue(result);
    }

    @ApiOperation(value = "新增或者编辑合同信息", notes = "新增或者编辑合同信息", consumes = "application/json", response = EditContractInfoResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "contractId", value = "合同ID 注意:修改时传", required = false, dataType = "Long",  paramType="query"),
//            @ApiImplicitParam(name = "contractFileType", value = "合同文件类型  1:销售合同 2:应收账款转让清单 3:应收账款转让通知书 4:融资服务居间协议 " +
//                    "5:法人身份证 6:营业执照 7:开户许可证 8:公司章程 9:借款合同 10:委托融资服务协议 11:担保函" +
//                    "12:水泥购销合同 13:借款人身份证 14:银行分期付款业务申请 15:借款协议 16:委托支付申请书 17:委托合同", required = true, dataType = "int",  paramType="query"),
            @ApiImplicitParam(name = "operatorId", value = "管理员ID", required = true, dataType = "Long",  paramType="query"),
            @ApiImplicitParam(name = "token", value = "令牌", required = true, dataType = "String",  paramType="query"),

    })
    @RequestMapping(value = "/editContractInfo", method = RequestMethod.POST)
    @ResponseBody
    public void editContractInfo(@ApiParam(required = true, value = "上传的文件")  @RequestParam MultipartFile file, Long contractId, Long operatorId, String token) throws Exception {
    	EditContractInfoParam param=new EditContractInfoParam();
    	param.setContractId(contractId);
//    	param.setContractFileType(contractFileType);
    	param.setOperatorId(operatorId);
    	param.setToken(token);
    	param.setMultipartFile(file);
        EditContractInfoResult result = applyService.editContractInfo(param);
        writeValue(result);
    }

    @ApiOperation(value = "提交审核申请信息", notes = "提交审核申请信息", consumes = "application/json", response = BaseMaResult.class)
    @RequestMapping(value = "/reviewApplyInfo", method = RequestMethod.POST)
    @ResponseBody
    public void reviewApplyInfo(@ApiParam(required = true, value = "审核参数") @RequestBody ReviewApplyInfoParam param) throws Exception {
        BaseMaResult result = applyService.reviewApplyInfo(param);
        writeValue(result);
    }

    @ApiOperation(value = "删除合同文件信息", notes = "删除合同文件信息", consumes = "application/json", response = BaseMaResult.class)
    @RequestMapping(value = "/delContractInfo", method = RequestMethod.POST)
    @ResponseBody
    public void delContractInfo(@ApiParam(required = true, value = "删除文件请求参数") @RequestBody DelContractInfoParam param) throws Exception{
        BaseMaResult result = applyService.delContractInfo(param);
        writeValue(result);
    }

    @ApiOperation(value = "获取老数据提供的信息", notes = "获取老数据提供的信息", consumes = "application/json", response = GetApplyInfoProvideResult.class)
    @RequestMapping(value = "/getApplyInfoProvideInfo", method = RequestMethod.POST)
    @ResponseBody
    public void getApplyInfoProvideInfo(@ApiParam(required = true, value = "搜索参数") @RequestBody GetApplyInfoProvideParam param) throws Exception{
        GetApplyInfoProvideResult result=applyService.getApplyInfoProvideInfo(param);
        writeValue(result);
    }
}
