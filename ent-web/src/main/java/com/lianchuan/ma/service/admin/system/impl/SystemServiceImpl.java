package com.lianchuan.ma.service.admin.system.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.lianchuan.common.entity.po.ma.MaManagerPO;
import com.lianchuan.common.entity.po.ma.MaTokenPO;
import com.lianchuan.common.entity.type.ma.EnableType;
import com.lianchuan.common.entity.type.ma.Menu;
import com.lianchuan.common.entity.type.ma.NeedChangePasswordType;
import com.lianchuan.common.exception.ErrorId;
import com.lianchuan.common.exception.ParamException;
import com.lianchuan.common.utils.UUIDUtils;
import com.lianchuan.ma.entity.bo.Manager;
import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import com.lianchuan.ma.entity.vo.param.system.EditPasswordParam;
import com.lianchuan.ma.entity.vo.param.system.LoginParam;
import com.lianchuan.ma.entity.vo.result.BaseMaResult;
import com.lianchuan.ma.entity.vo.result.system.GetMenuListResult;
import com.lianchuan.ma.entity.vo.result.system.GetOperatorInfoResult;
import com.lianchuan.ma.entity.vo.result.system.LoginResult;
import com.lianchuan.ma.entity.vo.result.system.MenuVO;
import com.lianchuan.ma.entity.vo.result.system.OperatorInfoVO;
import com.lianchuan.ma.entity.vo.result.system.SpecialPermissionVO;
import com.lianchuan.ma.service.AbstractMaService;
import com.lianchuan.ma.service.admin.system.SystemService;
import com.lianchuan.ma.utils.PasswordUtils;

@Service("systemService")
public class SystemServiceImpl extends AbstractMaService implements SystemService {

	@Override
	public LoginResult login(LoginParam param, String ip) {
		checkString(param.getUsername());
		checkString(param.getPassword());

		MaManagerPO maManagerPO = maManagerDaoService.findManager(param.getUsername());
		if (maManagerPO == null) {
			throw new ParamException("管理员不存在");
		}

		String password = PasswordUtils.encrypt(param.getPassword(), maManagerPO.getUsername());
		boolean flag = maManagerPO.getPassword().equals(password);
		if (!flag) {
			throw new ParamException("密码错误");
		}
		
		if (EnableType.DISABLE_2.getValue() == maManagerPO.getEnableStatus()) {
			throw new ParamException("用户已被禁止登录");
		}
		
		MaTokenPO maTokenPO = maManagerDaoService.findToken(maManagerPO.getId());
		String token = UUIDUtils.uuid();

		maManagerDaoService.updateToken(maTokenPO, true, token, ip);

		Manager manager = getManager(maManagerPO.getId(), token);

		LoginResult result = new LoginResult();
		result.setToken(token);
		result.setOperatorId(manager.getId());
		result.setOperatorInfo(getOperatorInfo(manager, maManagerPO));
		if (NeedChangePasswordType.NEED_1.getValue() == maManagerPO.getNeedChangePwd()) {
			maManagerDaoService.updateManagerNeedChangePwd(maManagerPO, NeedChangePasswordType.NO_NEED_0);
			result.setStatus(ErrorId.INFO_SYS_1);
			result.setDes("新用户首次登录建议修改密码");
			return result;
		}
		return result;
	}

	/**
	 * 获取视图层管理员信息
	 * 
	 * @param manager
	 * @return
	 */
	private OperatorInfoVO getOperatorInfo(Manager manager, MaManagerPO maManagerPO) {
		OperatorInfoVO vo = new OperatorInfoVO();
		vo.setOperatorId(maManagerPO.getId());
		vo.setRoleId(maManagerPO.getRoleId());
		vo.setName(maManagerPO.getName());
		vo.setUsername(maManagerPO.getUsername());
		vo.setPhone(maManagerPO.getPhone());
		List<MenuVO> menuVOs = new ArrayList<MenuVO>();
		Map<Menu, List<MenuVO>> menuItemMap = new HashMap<Menu, List<MenuVO>>();
		for (Menu menu : manager.getPermission()) {
			if (menu.isMenu()) {
				MenuVO menuVO = new MenuVO();
				menuVO.setIndex(menu.getValue());
				menuVO.setIcon(menu.getIcon());
				menuVO.setLeaf(menu.isLeaf());
				menuVO.setName(menu.getName());
				menuVO.setText(menu.getText());
				if (menuVO.isLeaf()) {
					List<MenuVO> list = menuItemMap.get(menu.getParent());
					if (list == null) {
						list = new ArrayList<MenuVO>();
						menuItemMap.put(menu.getParent(), list);
					}
					list.add(menuVO);
				} else {
					menuVOs.add(menuVO);
				}
			} else {
				SpecialPermissionVO specialPermissionVO = new SpecialPermissionVO();
				specialPermissionVO.setIndex(menu.getValue());
				specialPermissionVO.setName(menu.getName());
				specialPermissionVO.setText(menu.getText());
				vo.getSpecialPermissions().add(specialPermissionVO);
			}
		}
		
		
		if (!menuItemMap.containsKey(Menu.M_101000)) {
			List<MenuVO> list = new ArrayList<MenuVO>();
			MenuVO menuVO = new MenuVO();
			menuVO.setIndex(Menu.M_101000.getValue());
			menuVO.setIcon(Menu.M_101000.getIcon());
			menuVO.setLeaf(Menu.M_101000.isLeaf());
			menuVO.setName(Menu.M_101000.getName());
			menuVO.setText(Menu.M_101000.getText());
			menuVOs.add(menuVO);
			
			MenuVO leafVO = new MenuVO();
			leafVO.setIndex(Menu.M_101100.getValue());
			leafVO.setIcon(Menu.M_101100.getIcon());
			leafVO.setLeaf(Menu.M_101100.isLeaf());
			leafVO.setName(Menu.M_101100.getName());
			leafVO.setText(Menu.M_101100.getText());
			list.add(leafVO);
			menuItemMap.put(Menu.M_101000, list);
		}
		
		if (!menuItemMap.containsKey(Menu.M_102000)) {
			MenuVO menuVO = new MenuVO();
			menuVO.setIndex(Menu.M_102000.getValue());
			menuVO.setIcon(Menu.M_102000.getIcon());
			menuVO.setLeaf(Menu.M_102000.isLeaf());
			menuVO.setName(Menu.M_102000.getName());
			menuVO.setText(Menu.M_102000.getText());
			menuVOs.add(menuVO);
		}
		
		
		Collections.sort(menuVOs, menuComparator);

		for (MenuVO menuVO : menuVOs) {
			Menu menu = Menu.getMenu(menuVO.getIndex());
			List<MenuVO> menuItems = menuItemMap.get(menu);
			if (menu != null && menuItems != null) {
				Collections.sort(menuItems, menuComparator);
				menuVO.setItems(menuItems);
			}
		}
		vo.setMenus(menuVOs);
		return vo;
	}

	private Comparator<MenuVO> menuComparator = new Comparator<MenuVO>() {
		@Override
		public int compare(MenuVO o1, MenuVO o2) {
			if (o1.getIndex() > o2.getIndex()) {
				return 1;
			}
			if (o2.getIndex() > o1.getIndex()) {
				return -1;
			}
			return 0;
		}
	};

	@Override
	public void logout(BaseOperatorParam param, String ip) {
		Manager manager = getManager(param);

		MaTokenPO maTokenPO = maManagerDaoService.findToken(manager.getId());
		maManagerDaoService.updateToken(maTokenPO, false, "", ip);

		managers.remove(manager.getId());
	}

	@Override
	public GetOperatorInfoResult getOperatorInfo(BaseOperatorParam param) {
		Manager manager = getManager(param);

		MaManagerPO maManagerPO = maManagerDaoService.findManager(manager.getId());

		GetOperatorInfoResult result = new GetOperatorInfoResult();
		result.setOperatorInfo(getOperatorInfo(manager, maManagerPO));
		return result;
	}

	@Override
	public GetMenuListResult getMenuList(BaseOperatorParam param) {
		Manager manager = getManager(param);

		MaManagerPO maManagerPO = maManagerDaoService.findManager(manager.getId());

		GetMenuListResult result = new GetMenuListResult();
		OperatorInfoVO operatorInfoVO = getOperatorInfo(manager, maManagerPO);
		result.setMenus(operatorInfoVO.getMenus());
		return result;
	}

	@Override
	public BaseMaResult editPassword(EditPasswordParam param) {
		checkString(param.getNewPassword());
		checkString(param.getOldPassword());

		Manager manager = getManager(param, new Menu[] {});

		MaManagerPO maManagerPO = maManagerDaoService.findManager(manager.getId());

		String password = PasswordUtils.encrypt(param.getOldPassword(), maManagerPO.getUsername());
		boolean flag = maManagerPO.getPassword().equals(password);
		if (!flag) {
			throw new ParamException("密码错误");
		}

		String newPassword = PasswordUtils.encrypt(param.getNewPassword(), maManagerPO.getUsername());

		maManagerDaoService.updateManagerPassword(maManagerPO, newPassword);

		BaseMaResult result = new BaseMaResult();
		return result;
	}

}
