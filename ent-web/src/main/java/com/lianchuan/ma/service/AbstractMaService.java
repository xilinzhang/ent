package com.lianchuan.ma.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lianchuan.common.entity.po.BasePO;
import com.lianchuan.common.entity.po.ma.MaManagerPO;
import com.lianchuan.common.entity.po.ma.MaRolePermissionPO;
import com.lianchuan.common.entity.po.ma.MaTokenPO;
import com.lianchuan.common.entity.sql.Page;
import com.lianchuan.common.entity.type.ma.Menu;
import com.lianchuan.common.exception.ParamException;
import com.lianchuan.common.service.AbstractBizService;
import com.lianchuan.common.service.dao.ma.MaManagerDaoService;
import com.lianchuan.ma.Global;
import com.lianchuan.ma.entity.bo.Manager;
import com.lianchuan.ma.entity.vo.param.BaseMaPageParam;
import com.lianchuan.ma.entity.vo.param.BaseOperatorParam;
import com.lianchuan.ma.entity.vo.result.BaseMaPageResult;
import com.lianchuan.ma.exception.TokenException;

/**
 * ma业务层基础服务类
 */
public abstract class AbstractMaService extends AbstractBizService {

	/** token重新查询时间:600分钟 */
	private static final long TOKEN_TIME = 600 * 60 * 1000;

	protected static Map<Long, Manager> managers = new ConcurrentHashMap<Long, Manager>();

	@Autowired
	protected MaManagerDaoService maManagerDaoService;
	
	/**
	 * 设置分页返回参数<br>
	 * 当前页,最大页数,最大条数
	 * 
	 * @param result
	 * @param page
	 */
	protected <T extends BasePO, Q> void setPageArgument(BaseMaPageResult<Q> result, Page<T> page) {
		result.setPageMax(page.getPageMax());
		result.setPageNo(page.getPageNo());
		result.setTotalNum(page.getCount());
	}

	/**
	 * 检查分页参数
	 * 
	 * @param param
	 */
	protected void checkPage(BaseMaPageParam param) {
		if (param.getPageNo() < 1) {
			throw new ParamException("页数错误");
		}
		if (param.getPageSize() < 1) {
			throw new ParamException("每页最大数错误");
		}
		if (param.getPageSize() > 1000) {
			throw new ParamException("每页最大数错误");
		}
	}

	/**
	 * 管理员缓存
	 * 
	 * @param managerId
	 * @param token
	 * @return
	 */
	protected Manager getManager(Long managerId, String token) {
		MaManagerPO maManagerPO = maManagerDaoService.findManager(managerId);
		Long roleId = maManagerPO.getRoleId();
		List<Menu> permission = new ArrayList<Menu>();
		if (roleId.longValue() == Global.SYSTEM_ROLE_ID.longValue()) {
			permission.addAll(Arrays.asList(Menu.values()));
		} else {
			List<MaRolePermissionPO> permissionPOs = maManagerDaoService.findRolePermissions(roleId);
			for (MaRolePermissionPO po : permissionPOs) {
				Menu menu = Menu.getMenu(po.getPermissionId());
				permission.add(menu);
			}
		}
		Manager manager = new Manager();
		manager.setId(managerId);
		manager.setToken(token);
		manager.setRoleId(roleId);
		manager.setTokenTime(System.currentTimeMillis());
		manager.setPermission(permission);
		managers.put(managerId, manager);
		return manager;
	}

	/**
	 * 验证operatorId和token<br>
	 * 业务中使用operatorId都必须使用此方法
	 * 
	 * @param param
	 * @param menus
	 *            权限验证
	 * @return
	 */
	protected Manager getManager(BaseOperatorParam param, Menu... menus) {
		if (param.getOperatorId() == null) {
			throw new TokenException("请重新登录:T00");
		}
		if (StringUtils.isBlank(param.getToken())) {
			throw new TokenException("请重新登录:T00");
		}

		Long id = param.getOperatorId();
		String token = param.getToken();
		Manager manager = managers.get(id);
		if (manager != null) {
			if (System.currentTimeMillis() - manager.getTokenTime() < TOKEN_TIME) {
				if (manager.getToken().equals(token)) {
					checkMenuRight(manager, menus);
					return manager;
				}
			}
		}

		MaTokenPO maTokenPO = maManagerDaoService.findToken(id);
		if (!maTokenPO.getFlag()) {
			throw new TokenException("请重新登录:T02");
		}
		if (maTokenPO.getToken().equals(token)) {
			manager = getManager(maTokenPO.getManagerId(), token);
		} else {
			throw new TokenException("请重新登录:T01");
		}

//		if (menus != null && menus.length > 0) {
//			boolean flag = false;
//			for (Menu menu : menus) {
//				flag = manager.getPermission().contains(menu);
//				if (flag) {
//					break;
//				}
//			}
//			if (!flag) {
//				throw new ParamException("没有权限");
//			}
//		}

		checkMenuRight(manager, menus);
		
		return manager;
	}
	
	private void checkMenuRight(Manager manager, Menu... menus) {
		if (menus != null && menus.length > 0) {
			boolean flag = false;
			for (Menu menu : menus) {
				flag = manager.getPermission().contains(menu);
				if (flag) {
					break;
				}
			}
			if (!flag) {
				throw new ParamException("没有权限");
			}
		}
	}
	
	protected void removeManagers(Long key) {
		managers.remove(key);
	}

	
	
	
}
