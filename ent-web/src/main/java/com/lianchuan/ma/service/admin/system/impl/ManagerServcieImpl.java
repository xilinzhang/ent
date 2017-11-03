package com.lianchuan.ma.service.admin.system.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.lianchuan.common.entity.po.ma.MaManagerPO;
import com.lianchuan.common.entity.po.ma.MaManagerRolePO;
import com.lianchuan.common.entity.sql.Page;
import com.lianchuan.common.entity.type.ma.Dept;
import com.lianchuan.common.entity.type.ma.EnableType;
import com.lianchuan.common.entity.type.ma.Menu;
import com.lianchuan.common.entity.type.ma.search.ManagerSearchType;
import com.lianchuan.common.exception.ParamException;
import com.lianchuan.ma.Global;
import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.AddOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.EditOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.EnableOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.GetOperatorListParam;
import com.lianchuan.ma.entity.vo.param.system.ResetPasswordParam;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.system.GetOperatorListResult;
import com.lianchuan.ma.entity.vo.result.system.GetRoleListResult;
import com.lianchuan.ma.entity.vo.result.system.ManagerVO;
import com.lianchuan.ma.entity.vo.result.system.RoleVO;
import com.lianchuan.ma.service.AbstractMaService;
import com.lianchuan.ma.service.admin.system.ManagerServcie;
import com.lianchuan.ma.utils.PasswordUtils;

@Service("managerServcie")
public class ManagerServcieImpl extends AbstractMaService implements ManagerServcie {

	@Override
	public GetOperatorListResult getOperatorList(GetOperatorListParam param) {
		checkPage(param);

		ManagerSearchType managerSearchType = null;
		if (param.getSearchType() != null) {
			managerSearchType = ManagerSearchType.getType(param.getSearchType());
		}

		getManager(param, Menu.M_100300);
		Page<MaManagerPO> page = maManagerDaoService.findManager(managerSearchType, param.getSearchValue(), param.getPageNo(), param.getPageSize());

		GetOperatorListResult result = new GetOperatorListResult();
		if (CollectionUtils.isNotEmpty(page.getInfo())) {
			Set<Long> roleIds = new HashSet<Long>();
			for (MaManagerPO po : page.getInfo()) {
				roleIds.add(po.getRoleId());
			}
			Map<Long, MaManagerRolePO> roleMap = maManagerDaoService.findManagerRoleMap(roleIds);

			for (MaManagerPO po : page.getInfo()) {
				ManagerVO vo = new ManagerVO();
				vo.setId(po.getId());
				vo.setName(po.getName());
				vo.setUsername(po.getUsername());
				vo.setPhone(po.getPhone());
				vo.setEnableStatus(po.getEnableStatus());
				vo.setModifyDate(po.getModifyDate());
				vo.setNo(po.getId());
				vo.setCreateDate(po.getCreateDate());
				vo.setRoleId(po.getRoleId());
				MaManagerRolePO rolePO = roleMap.get(po.getRoleId());
				vo.setRoleName(rolePO.getName());
				if (rolePO.getDept() != null) {
					Dept dept = Dept.getType(rolePO.getDept());
					vo.setDeptName(dept.getName());
				}
				result.getInfos().add(vo);
			}
		}
		setPageArgument(result, page);
		return result;
	}

	@Override
	public GetRoleListResult getRoleList(BaseOperatorParam param) {
		//getManager(param, Menu.M_100300);

		List<MaManagerRolePO> list = maManagerDaoService.findManagerRole();

		GetRoleListResult result = new GetRoleListResult();

		for (MaManagerRolePO po : list) {
			RoleVO vo = new RoleVO();
			vo.setId(po.getId());
			vo.setName(po.getName());
			if (po.getDept() != null) {
				Dept dept = Dept.getType(po.getDept());
				vo.setDeptName(dept.getName());
			}
			result.getRoles().add(vo);
		}
		return result;
	}

	@Override
	public BaseMaResult addOperator(AddOperatorParam param) {
		checkString(param.getName());
		checkString(param.getUsername());
		checkObject(param.getRoleId());
		checkPhone(param.getPhone());

		getManager(param, Menu.M_100300);

		if (param.getRoleId().longValue() == Global.SYSTEM_ROLE_ID.longValue()) {
			throw new ParamException("非法操作");
		}

		MaManagerPO maManagerPO = maManagerDaoService.findManager(param.getUsername());
		if (maManagerPO != null) {
			throw new ParamException("账号已存在");
		}

// 姓名暂时可以一样
//		maManagerPO = maManagerDaoService.findManagerName(param.getName());
//		if (maManagerPO != null) {
//			throw new ParamException("名称已存在");
//		}

		MaManagerRolePO rolePO = maManagerDaoService.findManagerRole(param.getRoleId());
		if (rolePO == null) {
			throw new ParamException("角色不存在");
		}
		String password = PasswordUtils.encrypt(Global.DEFAULT_PASSWORD, param.getUsername());
		maManagerDaoService.saveManager(param.getName(), param.getUsername(), password, rolePO.getId(), param.getPhone());

		BaseMaResult result = new BaseMaResult();
		return result;
	}

	@Override
	public BaseMaResult editOperator(EditOperatorParam param) {
		checkObject(param.getId());
		checkObject(param.getRoleId());
//		checkObject(param.getUsername());
		checkObject(param.getName());
		checkObject(param.getPhone());

		getManager(param, Menu.M_100300);

		if (param.getId().longValue() == Global.SYSTEM_ID.longValue()) {
			throw new ParamException("非法操作");
		}

//		MaManagerPO maManagerPO = maManagerDaoService.findManager(param.getUsername());
//		if (maManagerPO != null && !maManagerPO.getId().equals(param.getId())) {
//			throw new ParamException("账号已存在");
//		}
		
		MaManagerPO maManagerPO = maManagerDaoService.findManager(param.getId());
		if (maManagerPO == null) {
			throw new ParamException("用户不存在");
		}

		MaManagerRolePO rolePO = maManagerDaoService.findManagerRole(param.getRoleId());
		if (rolePO == null) {
			throw new ParamException("角色不存在");
		}
		
		//maManagerDaoService.updateManagerRoleId(maManagerPO, rolePO.getId());
		maManagerDaoService.updateManagerAll(maManagerPO, rolePO.getId(), param.getName(), param.getPhone());
		removeManagers(maManagerPO.getId());
		BaseMaResult result = new BaseMaResult();
		return result;
	}

	@Override
	public BaseMaResult resetPassword(ResetPasswordParam param) {
		checkObject(param.getId());

		getManager(param, Menu.M_100300);

		MaManagerPO maManagerPO = maManagerDaoService.findManager(param.getId());
		if (maManagerPO == null) {
			throw new ParamException("管理员不存在");
		}

		String password = PasswordUtils.encrypt(Global.DEFAULT_PASSWORD, maManagerPO.getUsername());
		maManagerDaoService.updateManagerPassword(maManagerPO, password);

		BaseMaResult result = new BaseMaResult();
		return result;
	}
	
	
	@Override
	public BaseMaResult enableOperator(EnableOperatorParam param) {
		checkObject(param.getId());
		checkObject(param.getEnableStatus());
		EnableType enableType = EnableType.getType(param.getEnableStatus());
		if (enableType == null) {
			throw new ParamException("状态不正确");
		}

		getManager(param, Menu.M_100300);

		MaManagerPO maManagerPO = maManagerDaoService.findManager(param.getId());
		if (maManagerPO == null) {
			throw new ParamException("管理员不存在");
		}

		maManagerDaoService.updateManagerEnableStatus(maManagerPO, enableType.getValue());
		BaseMaResult result = new BaseMaResult();
		return result;
	}

}
