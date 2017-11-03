package com.lianchuan.ma.service.admin.system.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.lianchuan.common.entity.po.ma.MaManagerPO;
import com.lianchuan.common.entity.po.ma.MaManagerRolePO;
import com.lianchuan.common.entity.po.ma.MaRolePermissionPO;
import com.lianchuan.common.entity.sql.Page;
import com.lianchuan.common.entity.type.ma.Dept;
import com.lianchuan.common.entity.type.ma.Menu;
import com.lianchuan.common.exception.ParamException;
import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.AddRoleParam;
import com.lianchuan.ma.entity.vo.param.system.DeleteRoleParam;
import com.lianchuan.ma.entity.vo.param.system.EditRoleParam;
import com.lianchuan.ma.entity.vo.param.system.GetRoleListParam;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.system.DeptVO;
import com.lianchuan.ma.entity.vo.result.system.GetDeptListResult;
import com.lianchuan.ma.entity.vo.result.system.GetPermissionListResult;
import com.lianchuan.ma.entity.vo.result.system.GetRolePermissionListParam;
import com.lianchuan.ma.entity.vo.result.system.PageRoleListResult;
import com.lianchuan.ma.entity.vo.result.system.PermissionVO;
import com.lianchuan.ma.entity.vo.result.system.RoleVO;
import com.lianchuan.ma.service.AbstractMaService;
import com.lianchuan.ma.service.admin.system.RoleService;

@Service("roleService")
public class RoleServiceImpl extends AbstractMaService implements RoleService {

	@Override
	public PageRoleListResult pageRoleList(GetRoleListParam param) {
		checkPage(param);

		getManager(param, Menu.M_100200);

		Page<MaManagerRolePO> page = maManagerDaoService.findManagerRole(param.getSearchValue(), param.getPageNo(), param.getPageSize());

		List<RoleVO> roles = new ArrayList<RoleVO>();
		List<Long> operatorIds = new ArrayList<Long>();
		
		for (MaManagerRolePO po : page.getInfo()) {
			if (po.getLastEditOperatorId() != null) {
				operatorIds.add(po.getLastEditOperatorId());
			}
		}
		
		Map<Long, MaManagerPO> operatorMap = new HashMap<Long, MaManagerPO>();
		if (CollectionUtils.isNotEmpty(operatorIds)) {
			operatorMap = maManagerDaoService.findManagers(operatorIds);
		}
		
		for (MaManagerRolePO po : page.getInfo()) {
			RoleVO vo = new RoleVO();
			vo.setId(po.getId());
			vo.setName(po.getName());
			vo.setCreateDate(po.getCreateDate());
			vo.setModifyDate(po.getModifyDate());
			
			if (po.getDept() != null) {
				Dept dept = Dept.getType(po.getDept());
				vo.setDeptName(dept.getName());
			}
			roles.add(vo);
			if (operatorMap.containsKey(po.getLastEditOperatorId())) {
				MaManagerPO managerPO = operatorMap.get(po.getLastEditOperatorId());
				vo.setLastEditUser(managerPO.getName());
			}
		}
		PageRoleListResult result = new PageRoleListResult();
		result.setRoles(roles);
		setPageArgument(result, page);
		return result;
	}

	@Override
	public GetPermissionListResult getPermissionList(BaseOperatorParam param) {
		getManager(param);

		GetPermissionListResult result = new GetPermissionListResult();
		for (Menu menu : Menu.values()) {
			if (menu.isLeaf()) {
				if (menu == Menu.M_101100) {
					continue;
				}
				PermissionVO vo = new PermissionVO();
				vo.setId(menu.getValue());
				vo.setName(menu.getText());
				result.getInfos().add(vo);
			}
		}
		return result;
	}

	@Override
	public GetDeptListResult getDeptList(BaseOperatorParam param) {
		getManager(param);
		GetDeptListResult result = new GetDeptListResult();
		for (Dept dept : Dept.values()) {
			DeptVO vo = new DeptVO();
			vo.setId(dept.getValue());
			vo.setName(dept.getName());
			result.getInfos().add(vo);
		}
		return result;
	}

	@Override
	public BaseMaResult addRole(AddRoleParam param) {
		getManager(param, Menu.M_100200);
		checkString(param.getName());

// 部门暂时不要
//		checkObject(param.getDeptId());
//		Dept dept = Dept.getType(param.getDeptId());
//		checkObject(dept);
		// 默认一个部门
		Dept dept = Dept.D_1;

		Set<Menu> menus = new HashSet<Menu>();
		checkList(param.getPermissions());
		for (Integer permission : param.getPermissions()) {
			Menu menu = Menu.getMenu(permission);
			if (menu != null) {
				menus.add(menu);
				if (menu.getParent() != null) {
					menus.add(menu.getParent());
				}
			}
		}
		checkList(menus);

		getManager(param, Menu.M_100200);

		
		maManagerDaoService.saveRole(param.getName(), dept, menus, param.getOperatorId());

		BaseMaResult result = new BaseMaResult();
		return result;
	}

	
	@Override
	public BaseMaResult deleteRole(DeleteRoleParam param) {
		checkObject(param.getRoleId());
		getManager(param, Menu.M_100200);
		
		List<MaManagerPO> managerList = maManagerDaoService.findManagerListByRoleId(param.getRoleId());
		if (CollectionUtils.isNotEmpty(managerList)) {
			throw new ParamException("已经有用户使用该角色！");
		}
		maManagerDaoService.deleteRole(param.getRoleId());
		BaseMaResult result = new BaseMaResult();
		return result;
	}
	
	@Override
	public BaseMaResult editRole(EditRoleParam param) {
		checkObject(param.getRoleId());
		checkString(param.getName());

//		checkObject(param.getDeptId());
//		Dept dept = Dept.getType(param.getDeptId());
//		checkObject(dept);
// 暂时默认
		Dept dept = Dept.D_1;

		Set<Menu> menus = new HashSet<Menu>();
		checkList(param.getPermissions());
		for (Integer permission : param.getPermissions()) {
			Menu menu = Menu.getMenu(permission);
			if (menu != null) {
				menus.add(menu);
				if (menu.getParent() != null) {
					menus.add(menu.getParent());
				}
			}
		}
		checkList(menus);

		getManager(param, Menu.M_100200);

		MaManagerRolePO maManagerRolePO = maManagerDaoService.findManagerRole(param.getRoleId());
		if (maManagerRolePO == null) {
			throw new ParamException("角色不存在");
		}

		Set<Integer> menuValues = new HashSet<Integer>();
		for (Menu menu : menus) {
			menuValues.add(menu.getValue());
		}

		List<MaRolePermissionPO> permissionPOs = maManagerDaoService.findRolePermissions(maManagerRolePO.getId());
		List<MaRolePermissionPO> delete = new ArrayList<MaRolePermissionPO>();
		Set<Integer> permissionValues = new HashSet<Integer>();
		for (MaRolePermissionPO po : permissionPOs) {
			if (!menuValues.contains(po.getPermissionId())) {
				delete.add(po);
			} else {
				permissionValues.add(po.getPermissionId());
			}
		}

		List<Menu> add = new ArrayList<Menu>();
		menuValues.removeAll(permissionValues);
		for (Integer value : menuValues) {
			add.add(Menu.getMenu(value));
		}
		maManagerRolePO.setLastEditOperatorId(param.getOperatorId());
		maManagerDaoService.updateRole(maManagerRolePO, param.getName(), dept, add, delete);
		List<MaManagerPO> managerList = maManagerDaoService.findManagerListByRoleId(param.getRoleId());
		for (MaManagerPO maManagerPO : managerList) {
			removeManagers(maManagerPO.getId());
		}
		BaseMaResult result = new BaseMaResult();
		return result;
	}

	@Override
	public GetPermissionListResult getRolePermissionList(GetRolePermissionListParam param) {
		checkObject(param.getRoleId());

		getManager(param, Menu.M_100200);

		MaManagerRolePO maManagerRolePO = maManagerDaoService.findManagerRole(param.getRoleId());
		if (maManagerRolePO == null) {
			throw new ParamException("角色不存在");
		}

		List<MaRolePermissionPO> permissionPOs = maManagerDaoService.findRolePermissions(maManagerRolePO.getId());

		GetPermissionListResult result = new GetPermissionListResult();
		for (MaRolePermissionPO po : permissionPOs) {
			PermissionVO vo = new PermissionVO();
			Menu menu = Menu.getMenu(po.getPermissionId());
			if (menu.isLeaf()) {
				vo.setId(menu.getValue());
				vo.setName(menu.getName());
				result.getInfos().add(vo);
			}
		}

		return result;
	}

}
