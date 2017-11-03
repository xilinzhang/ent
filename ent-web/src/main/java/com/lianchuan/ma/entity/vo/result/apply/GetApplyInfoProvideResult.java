package com.lianchuan.ma.entity.vo.result.apply;

import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 老数据提供的信息
 * Created by zxl on 2017-10-31
 **/
@ApiModel(value = "GetApplyInfoProvideResult", description = "老数据提供的信息")
public class GetApplyInfoProvideResult extends BaseMaResult {

    @ApiModelProperty(value = "唯一标识id", required = false)
    private Long id;
    @ApiModelProperty(value = "全称或简称", required = false)
    private String name;
    @ApiModelProperty(value = "法定代表人", required = false)
    private String legalRepresentative;
    @ApiModelProperty(value = "机构联系人手机", required = false)
    private String contactPhone;
    @ApiModelProperty(value = "统一社会信用代码", required = false)
    private String socialCreditIdentifier;
    @ApiModelProperty(value = "营业执照代码", required = false)
    private String businessRegistrationNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getSocialCreditIdentifier() {
        return socialCreditIdentifier;
    }

    public void setSocialCreditIdentifier(String socialCreditIdentifier) {
        this.socialCreditIdentifier = socialCreditIdentifier;
    }

    public String getBusinessRegistrationNo() {
        return businessRegistrationNo;
    }

    public void setBusinessRegistrationNo(String businessRegistrationNo) {
        this.businessRegistrationNo = businessRegistrationNo;
    }
}
