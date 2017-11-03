package com.lianchuan.common.service.dao.ma;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lianchuan.common.dao.ma.MaManagerDao;
import com.lianchuan.common.dao.ma.MaManagerRoleDao;
import com.lianchuan.common.dao.ma.MaRolePermissionDao;
import com.lianchuan.common.dao.ma.MaTokenDao;
import com.lianchuan.common.entity.po.ma.MaManagerPO;
import com.lianchuan.common.entity.po.ma.MaManagerRolePO;
import com.lianchuan.common.entity.po.ma.MaRolePermissionPO;
import com.lianchuan.common.entity.po.ma.MaTokenPO;
import com.lianchuan.common.entity.sql.Page;
import com.lianchuan.common.entity.type.ma.Dept;
import com.lianchuan.common.entity.type.ma.EnableType;
import com.lianchuan.common.entity.type.ma.Menu;
import com.lianchuan.common.entity.type.ma.NeedChangePasswordType;
import com.lianchuan.common.entity.type.ma.search.ManagerSearchType;
import com.lianchuan.common.service.dao.AbstractDaoService;




/**
 * 后台管理员
 */
@Component
public class MaManagerDaoService extends AbstractDaoService {

	@Autowired
	private MaManagerDao maManagerDao;
	@Autowired
	private MaManagerRoleDao maManagerRoleDao;
	@Autowired
	private MaRolePermissionDao maRolePermissionDao;
	@Autowired
	private MaTokenDao maTokenDao;

	/**
	 * 保存系统管理员
	 * 
	 * @param id
	 * @param name
	 * @param username
	 * @param password
	 * @param roleId
	 */
	public void saveSystemAdmin(Long id, String name, String username, String password, Long roleId, Integer enableStatus, String phone) {
		execute("INSERT INTO ma_manager(id,create_date,name,username,password,role_id, enable_status, phone, modify_date, need_change_pwd) values(?,now(),?,?,?,?,?,?,now(), 0);",
				new Object[] { id, name, username, password, roleId, enableStatus, phone });
	}

	/**
	 * 保存系统管理员角色
	 * 
	 * @param id
	 * @param name
	 */
	public void saveSystemAdminRole(Long id, String name) {
		execute("INSERT INTO ma_manager_role(id,create_date,name,modify_date) values(?,now(),?, now());", new Object[] { id, name });
	}

	/**
	 * 查询管理员
	 * 
	 * @param managerId
	 * @return
	 */
	public MaManagerPO findManager(Long managerId) {
		return maManagerDao.findOne(managerId);
	}

	/**
	 * 查询管理员
	 * 
	 * @return
	 */
	public List<MaManagerPO> findManagers() {
		return maManagerDao.findAll();
	}

	/**
	 * 查询管理员角色
	 * 
	 * @param roleId
	 * @return
	 */
	public MaManagerRolePO findManagerRole(Long roleId) {
		return maManagerRoleDao.findOne(roleId);
	}

	/**
	 * 查询管理员角色
	 * 
	 * @param roleIds
	 * @return
	 */
	public List<MaManagerRolePO> findManagerRoles(Collection<Long> roleIds) {
		return maManagerRoleDao.findAll(roleIds);
	}

	/**
	 * 查询管理员角色
	 * 
	 * @return
	 */
	public List<MaManagerRolePO> findManagerRoles() {
		return maManagerRoleDao.findAll();
	}

	/**
	 * 查询管理员角色
	 * 
	 * @param roleIds
	 * @return
	 */
	public Map<Long, MaManagerRolePO> findManagerRoleMap(Collection<Long> roleIds) {
		Map<Long, MaManagerRolePO> map = new HashMap<Long, MaManagerRolePO>();
		List<MaManagerRolePO> list = findManagerRoles(roleIds);
		for (MaManagerRolePO po : list) {
			map.put(po.getId(), po);
		}
		return map;
	}

	/**
	 * 查询管理员
	 * 
	 * @param username
	 * @return
	 */
	public MaManagerPO findManager(String username) {
		return findOne(maManagerDao, "from ma_manager where username = ?", new Object[] { username });
	}

	/**
	 * 根据管理员ID查询令牌<br>
	 * 不存在则创建,默认token为空字符串
	 * 
	 * @param managerId
	 * @return
	 */
	public MaTokenPO findToken(Long managerId) {
		MaTokenPO po = findOne(maTokenDao, "from ma_token where manager_id = ?", new Object[] { managerId });
		if (po == null) {
			po = new MaTokenPO();
			po.setFlag(false);
			po.setToken("");
			po.setManagerId(managerId);
			po = maTokenDao.save(po);
		}
		return po;
	}

	/**
	 * 更新令牌
	 * 
	 * @param po
	 * @param flag
	 *            在线标识:true:在线,false:下线
	 * @param token
	 *            令牌
	 * @param ip
	 *            操作IP
	 */
	public void updateToken(MaTokenPO po, Boolean flag, String token, String ip) {
		if (flag) {
			po.setLoginTime(new Date());
			po.setLoginIp(getChar(ip));
		} else {
			po.setLogoutTime(new Date());
			po.setLogoutIp(getChar(ip));
		}
		po.setFlag(flag);
		po.setToken(token);
		maTokenDao.save(po);
	}

	/**
	 * 查询角色权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<MaRolePermissionPO> findRolePermissions(Long roleId) {
		return findList(maRolePermissionDao, "from ma_role_permission where role_id = ?", new Object[] { roleId });
	}

	/**
	 * 查询所有角色<br>
	 * 不会查询系统管路员角色
	 * 
	 * @param page
	 *            从1开始
	 * @param pageSize
	 * @return
	 */
	public List<MaManagerRolePO> findManagerRole() {
		return findList(maManagerRoleDao, "from ma_manager_role where id <> -1 order by id desc", NULL_PARAM);
	}

	/**
	 * 分页查询角色<br>
	 * 不会查询系统管路员角色
	 * 
	 * @param page
	 *            从1开始
	 * @param pageSize
	 * @return
	 */
	public Page<MaManagerRolePO> findManagerRole(int page, int pageSize) {
		return findPage(maManagerRoleDao, "from ma_manager_role where id <> -1 ", "order by id desc", NULL_PARAM, page, pageSize);
	}
	
	/**
	 * 分页查询角色<br>
	 * 不会查询系统管路员角色
	 * 
	 * @param page
	 *            从1开始
	 * @param pageSize
	 * @return
	 */
	public Page<MaManagerRolePO> findManagerRole(String searchValue, int pageNo, int pageSize) {
		StringBuilder builder = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		builder.append("from ma_manager_role where id <> -1 ");
		if (StringUtils.isNotBlank(searchValue)) {
			builder.append(" and name = ? ");
			params.add(searchValue);
		}
		return findPage(maManagerRoleDao, builder.toString(), "order by id desc", params, pageNo, pageSize);
	}
	

	/**
	 * 保存角色
	 * 
	 * @param name
	 * @param dept
	 * @param permissions
	 */
	public void saveRole(String name, Dept dept, Collection<Menu> permissions, Long lastEditOperatorId) {
		MaManagerRolePO rolePO = new MaManagerRolePO();
		rolePO.setDept(dept.getValue());
		rolePO.setName(getChar(name));
		rolePO.setLastEditOperatorId(lastEditOperatorId);
		rolePO = maManagerRoleDao.save(rolePO);

		saveRolePermissions(rolePO.getId(), permissions);
	}

	/**
	 * 更新角色
	 * 
	 * @param maManagerRolePO
	 * @param name
	 * @param dept
	 * @param add
	 * @param delete
	 */
	public void updateRole(MaManagerRolePO maManagerRolePO, String name, Dept dept, Collection<Menu> add, List<MaRolePermissionPO> delete) {
		maManagerRolePO.setDept(dept.getValue());
		maManagerRolePO.setName(getChar(name));
		maManagerRolePO.setModifyDate(new Date());
		maManagerRolePO = maManagerRoleDao.save(maManagerRolePO);

		maRolePermissionDao.delete(delete);
		saveRolePermissions(maManagerRolePO.getId(), add);

	}

	/**
	 * 保存角色权限
	 * 
	 * @param roleId
	 * @param permissions
	 */
	private void saveRolePermissions(Long roleId, Collection<Menu> permissions) {
		for (Menu menu : permissions) {
			MaRolePermissionPO permissionPO = new MaRolePermissionPO();
			permissionPO.setPermissionId(menu.getValue());
			permissionPO.setRoleId(roleId);
			maRolePermissionDao.save(permissionPO);
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param maManagerPO
	 * @param password
	 */
	public void updateManagerPassword(MaManagerPO maManagerPO, String password) {
		maManagerPO.setPassword(getChar(password));
		maManagerPO.setModifyDate(new Date());
		maManagerPO.setNeedChangePwd(NeedChangePasswordType.NO_NEED_0.getValue());
		maManagerDao.save(maManagerPO);
	}

	/**
	 * 查询管理员<br>
	 * 不查询系统管理员
	 * 
	 * @param managerSearchType
	 * @param searchValue
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<MaManagerPO> findManager(ManagerSearchType managerSearchType, String searchValue, int pageNo, int pageSize) {
		StringBuilder builder = new StringBuilder();
		List<Object> params = new ArrayList<Object>();

		builder.append("from ma_manager where id <> -1 ");
		if (managerSearchType != null && StringUtils.isNotBlank(searchValue)) {
			switch (managerSearchType) {
			case S_1:
				builder.append(" and username = ? ");
				params.add(searchValue);
				break;
			case S_2:
				builder.append(" and name = ? ");
				params.add(searchValue);
				break;
			default:
				break;
			}
		}

		return findPage(maManagerDao, builder.toString(), "order by id asc", params, pageNo, pageSize);
	}

	/**
	 * 根据名称查询管理员
	 * 
	 * @param name
	 * @return
	 */
	public MaManagerPO findManagerName(String name) {
		return findOne(maManagerDao, "from ma_manager where name = ?", new Object[] { name });
	}

	/**
	 * 保存管理员
	 * 
	 * @param name
	 * @param username
	 * @param password
	 * @param roleId
	 * @return
	 */
	public Long saveManager(String name, String username, String password, Long roleId, String phone) {
		MaManagerPO maManagerPO = new MaManagerPO();
		maManagerPO.setName(getChar(name));
		maManagerPO.setUsername(getChar(username));
		maManagerPO.setPassword(getChar(password));
		maManagerPO.setRoleId(roleId);
		maManagerPO.setModifyDate(new Date());
		maManagerPO.setPhone(phone);
		maManagerPO.setEnableStatus(EnableType.ENABLE_1.getValue());
		maManagerPO.setNeedChangePwd(NeedChangePasswordType.NEED_1.getValue());
		maManagerPO = maManagerDao.save(maManagerPO);
		return maManagerPO.getId();
	}

	/**
	 * 更新管理员角色
	 * 
	 * @param maManagerPO
	 * @param roleId
	 */
	public void updateManagerAll(MaManagerPO maManagerPO, Long roleId, String name, String phone) {
		maManagerPO.setRoleId(roleId);
		maManagerPO.setName(name);
		maManagerPO.setPhone(phone);
		maManagerPO.setModifyDate(new Date());
		maManagerDao.save(maManagerPO);
	}
	
	/**
	 * 更新管理员角色
	 * 
	 * @param maManagerPO
	 * @param roleId
	 */
	public void updateManagerAll(MaManagerPO maManagerPO, Long roleId, String username, String name, String phone) {
		maManagerPO.setRoleId(roleId);
		maManagerPO.setUsername(username);
		maManagerPO.setName(name);
		maManagerPO.setPhone(phone);
		maManagerPO.setModifyDate(new Date());
		maManagerDao.save(maManagerPO);
	}
	
	/**
	 * 更新管理员角色
	 * 
	 * @param maManagerPO
	 * @param roleId
	 */
	public void updateManagerNeedChangePwd(MaManagerPO maManagerPO, NeedChangePasswordType changePasswordType) {
		maManagerPO.setNeedChangePwd(changePasswordType.getValue());
		maManagerPO.setModifyDate(new Date());
		maManagerDao.save(maManagerPO);
	}
	
	
	/**
	 * 更新用户所有信息
	 * 
	 * @param maManagerPO
	 * @param roleId
	 */
	public void updateManagerRoleId(MaManagerPO maManagerPO, Long roleId) {
		maManagerPO.setRoleId(roleId);
		maManagerPO.setModifyDate(new Date());
		maManagerDao.save(maManagerPO);
	}
	
	/**
	 * 修改启用状态
	 * 
	 * @param maManagerPO
	 * @param enableStatus
	 */
	public void updateManagerEnableStatus(MaManagerPO maManagerPO, Integer enableStatus) {
		maManagerPO.setEnableStatus(enableStatus);
		maManagerPO.setModifyDate(new Date());
		maManagerDao.save(maManagerPO);
	}
	
	

	/**
	 * 
	 * @param ids
	 * @return key:MaManagerPO.id
	 */
	public Map<Long, MaManagerPO> findManagers(Collection<Long> ids) {
		List<MaManagerPO> list = findAll(ids, maManagerDao);
		return toMap(list);
	}

	
	/**
	 * 查询角色id对应的所有用户<br>
	 * 
	 * @param page
	 *            从1开始
	 * @param pageSize
	 * @return
	 */
	public List<MaManagerPO> findManagerListByRoleId(Long roleId) {
		return findList(maManagerDao, "from ma_manager where role_id = ?", new Object[] { roleId });
	}
	
	/**
	 * 保存角色
	 * 
	 * @param name
	 * @param dept
	 * @param permissions
	 */
	public void deleteRole(Long roleId) {
		maManagerRoleDao.delete(roleId);
	}
}
