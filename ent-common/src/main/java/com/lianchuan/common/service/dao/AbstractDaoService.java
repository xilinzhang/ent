package com.lianchuan.common.service.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.InitializingBean;

import com.lianchuan.common.dao.BaseDao;
import com.lianchuan.common.entity.po.BasePO;
import com.lianchuan.common.entity.sql.Page;
import com.lianchuan.common.entity.sql.SqlParam;
import com.lianchuan.common.exception.EntException;
import com.lianchuan.common.service.AbstractService;

public abstract class AbstractDaoService extends AbstractService implements InitializingBean {

	protected static final int PAGE_SIZE = 2000;

	@Resource(name = "dataSource")
	private BasicDataSource dataSource;

	@Resource(name = "dataReadOnlySource")
	private BasicDataSource dataReadOnlySource;

	protected final static String ID = "id";

	protected static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	protected final static Object[] NULL_PARAM = new Object[] {};

	@Override
	public void afterPropertiesSet() throws Exception {
	}

	/***
	 * 转换为map
	 * 
	 * @param list
	 * @return
	 */
	protected <T extends BasePO> Map<Long, T> toMap(List<T> list) {
		Map<Long, T> map = new HashMap<Long, T>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (T t : list) {
				map.put(t.getId(), t);
			}
		}
		return map;
	}

	/**
	 * 执行无返回的SQL
	 * 
	 * @param sql
	 *            完整的SQL语句
	 */
	protected void execute(String sql) {
		execute(sql, NULL_PARAM, SqlParam.getMain());
	}

	/**
	 * 执行更新语句
	 * 
	 * @param sql
	 *            不需要"where id = ?"部分的SQL语句
	 * @param params
	 *            参数列表
	 * @param po
	 */
	protected void executeUpdate(String sql, Object[] params, BasePO po) {
		executeUpdate(sql, params, Arrays.asList(po));
	}

	/**
	 * 执行批量更新语句
	 * 
	 * @param sql
	 *            不需要"where id = ?"部分的SQL语句
	 * @param params
	 *            参数列表
	 * @param pos
	 */
	protected void executeUpdate(String sql, Object[] params, List<BasePO> pos) {
		if (CollectionUtils.isEmpty(pos)) {
			return;
		}
		List<Object> objects = asList(params);
		StringBuilder builder = new StringBuilder(sql);
		builder.append(" where id in (");
		for (BasePO po : pos) {
			objects.add(po.getId());
			builder.append("?,");
		}
		builder.deleteCharAt(builder.length() - 1);
		builder.append(")");
		execute(builder.toString(), objects);
	}

	/**
	 * 执行无返回的SQL
	 * 
	 * @param sql
	 *            完整的SQL语句
	 * @param params
	 *            参数列表
	 */
	protected void execute(String sql, List<Object> params) {
		execute(sql, params, SqlParam.getMain());
	}

	/**
	 * 执行无返回的SQL
	 * 
	 * @param sql
	 *            完整的SQL语句
	 * @param params
	 *            参数列表
	 */
	protected void execute(String sql, Object[] params) {
		execute(sql, params, SqlParam.getMain());
	}

	/**
	 * 执行无返回的SQL
	 * 
	 * @param sql
	 *            完整的SQL语句
	 * @param params
	 *            参数列表
	 * @param sqlParam
	 *            数据库连接属性
	 */
	protected void execute(String sql, Object[] params, SqlParam sqlParam) {
		execute(sql, asList(params), sqlParam);
	}

	/**
	 * 执行无返回的SQL
	 * 
	 * @param sql
	 *            完整的SQL语句
	 * @param params
	 *            参数列表
	 * @param sqlParam
	 *            数据库连接属性
	 */
	protected void execute(String sql, List<Object> params, SqlParam sqlParam) {
		Connection c = null;
		PreparedStatement p = null;
		ResultSet r = null;
		long start = System.currentTimeMillis();
		try {
			c = getConnection(sqlParam.isReadOnly());

			Pair<String, List<Object>> pair = checkSql(sql, params);
			String executeSql = pair.getLeft();
			List<Object> executeParams = pair.getRight();
			p = c.prepareStatement(executeSql);
			if (CollectionUtils.isNotEmpty(executeParams)) {
				for (int i = 0; i < executeParams.size(); i++) {
					int index = i + 1;
					setStatementParam(p, index, executeParams.get(i));
				}
			}
			p.execute();
			long time = System.currentTimeMillis() - start;
			if (time > 1000) {
				logger.warn("\nSQL:[{}],参数:{},执行时间:{}ms\n转换后SQL:[{}],参数:{}", sql, params, time, executeSql, toString(executeParams));
			} else if (logger.isDebugEnabled()) {
				logger.debug("\nSQL:[{}],参数:{},执行时间:{}ms\n转换后SQL:[{}],参数:{}", sql, params, time, executeSql, toString(executeParams));
			}
		} catch (Exception e) {
			logger.error("SQL[{}],参数:[{}],执行异常", sql, params, e);
			throw new EntException(e);
		} finally {
			close(c, p, r);
		}
	}

	/**
	 * 列表转换字符串
	 * 
	 * @param list
	 * @return
	 */
	private String toString(List<Object> list) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		if (CollectionUtils.isNotEmpty(list)) {
			for (Object object : list) {
				if (object == null) {
					builder.append("(null)");
				} else {
					if (object instanceof Date) {
						builder.append(DateFormatUtils.format((Date) object, "yyyy-MM-dd HH:mm:ss"));
					} else {
						builder.append(object.toString());
					}
				}
				builder.append(",");
			}
			builder.deleteCharAt(builder.length() - 1);
		}
		builder.append("]");
		return builder.toString();
	}

	protected List<Map<String, Object>> executeSql(String sql, Object[] params, Map<String, Class<?>> result) {
		return executeSql(sql, params, result, SqlParam.getDefault());
	}

	protected List<Map<String, Object>> executeSql(String sql, Object[] params, Map<String, Class<?>> result, SqlParam sqlParam) {
		return executeSql(sql, asList(params), result, SqlParam.getDefault());
	}

	/**
	 * 使用默认语句配置执行SQL语句
	 * 
	 * @param sql
	 *            标准SQL语句
	 * @param params
	 *            参数列表
	 * @param result
	 *            返回值类型列表
	 * @return
	 */
	protected List<Map<String, Object>> executeSql(String sql, List<Object> params, Map<String, Class<?>> result) {
		return executeSql(sql, params, result, SqlParam.getDefault());
	}

	/**
	 * 执行SQL语句
	 * 
	 * @param sql
	 *            标准SQL语句
	 * @param params
	 *            参数列表
	 * @param result
	 *            返回值类型列表
	 * @param sqlParam
	 *            执行SQL语句配置
	 * @return
	 */
	protected List<Map<String, Object>> executeSql(String sql, List<Object> params, Map<String, Class<?>> result, SqlParam sqlParam) {
		Connection c = null;
		PreparedStatement p = null;
		ResultSet r = null;
		long start = System.currentTimeMillis();
		try {
			c = getConnection(sqlParam.isReadOnly());
			long time1 = System.currentTimeMillis() - start;
			Pair<String, List<Object>> pair = checkSql(sql, params);
			String executeSql = pair.getLeft();
			List<Object> executeParams = pair.getRight();
			p = c.prepareStatement(executeSql);
			if (CollectionUtils.isNotEmpty(executeParams)) {
				for (int i = 0; i < executeParams.size(); i++) {
					int index = i + 1;
					setStatementParam(p, index, executeParams.get(i));
				}
			}
			long time2 = System.currentTimeMillis() - start;
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			r = p.executeQuery();
			long time3 = System.currentTimeMillis() - start;
			while (r.next()) {
				Map<String, Object> map = new HashMap<String, Object>();
				for (String s : result.keySet()) {
					Class<?> clazz = result.get(s);
					Object object = getResultSetValue(s, clazz, r);
					map.put(s, object);
				}
				list.add(map);
			}
			long time = System.currentTimeMillis() - start;
			if (time > 1000) {
				logger.warn("\nSQL:[{}],参数:{},执行时间:{}ms,按步骤:{}ms,{}ms,{}ms,\n转换后SQL:[{}],参数:{}", sql, params, time, time1, time2, time3, executeSql, toString(executeParams));
			} else if (logger.isDebugEnabled()) {
				logger.debug("\nSQL:[{}],参数:{},执行时间:{}ms\n转换后SQL:[{}],参数:{}", sql, params, time, executeSql, toString(executeParams));
			}
			return list;
		} catch (Exception e) {
			logger.error("SQL[{}],参数:[{}],执行异常", sql, params, e);
			throw new EntException(e);
		} finally {
			close(c, p, r);
		}
	}

	/**
	 * 获取ResultSet中指定列的值
	 * 
	 * @param s
	 *            列名
	 * @param clazz
	 *            列值的类型
	 * @param r
	 * @return
	 * @throws Exception
	 */
	private Object getResultSetValue(String s, Class<?> clazz, ResultSet r) throws Exception {
		String type = clazz.getName();
		Object object = r.getObject(s);
		if (object == null) {
			return null;
		}
		if ("java.lang.Byte".equals(type)) {
			return r.getByte(s);
		} else if ("java.lang.Double".equals(type)) {
			return r.getDouble(s);
		} else if ("java.lang.Float".equals(type)) {
			return r.getFloat(s);
		} else if ("java.lang.Integer".equals(type)) {
			return r.getInt(s);
		} else if ("java.lang.Long".equals(type)) {
			return r.getLong(s);
		} else if ("java.lang.Short".equals(type)) {
			return r.getShort(s);
		} else if ("java.lang.String".equals(type)) {
			return r.getString(s);
		} else if ("java.util.Date".equals(type)) {
			return new Date(r.getTimestamp(s).getTime());
		} else {
			throw new EntException("返回未知数据类型:" + type);
		}
	}

	/**
	 * 设置占位符参数
	 * 
	 * @param p
	 * @param index
	 *            从1开始
	 * @param object
	 *            参数
	 * @return 参数的toString()
	 */
	private void setStatementParam(PreparedStatement p, int index, Object object) throws Exception {
		if (object == null) {
			p.setNull(index, Types.VARCHAR);
			return;
		}
		String type = object.getClass().getName();
		if ("java.lang.Byte".equals(type)) {
			p.setByte(index, (Byte) object);
		} else if ("java.lang.Double".equals(type)) {
			p.setDouble(index, (Double) object);
		} else if ("java.lang.Float".equals(type)) {
			p.setFloat(index, (Float) object);
		} else if ("java.lang.Integer".equals(type)) {
			p.setInt(index, (Integer) object);
		} else if ("java.lang.Long".equals(type)) {
			p.setLong(index, (Long) object);
		} else if ("java.lang.Short".equals(type)) {
			p.setShort(index, (Short) object);
		} else if ("java.lang.String".equals(type)) {
			p.setString(index, object.toString());
		} else if ("java.util.Date".equals(type)) {
			p.setTimestamp(index, new Timestamp(((Date) object).getTime()));
		} else if ("java.lang.Boolean".equals(type)) {
			p.setBoolean(index, (Boolean) object);
		} else {
			throw new EntException("请求未知数据类型:" + type);
		}
	}

	/**
	 * 转换为标准SQL<br>
	 * 例如:select * from table where id in (?),params:[1,2]<br>
	 * 转化为:select * from table where id in (?,?),params:[1,2]<br>
	 * 
	 * @param sql
	 *            标准SQL语句
	 * @param params
	 *            参数列表
	 * @return 标准化后的SQL语句和参数列表
	 */
	@SuppressWarnings("unchecked")
	private Pair<String, List<Object>> checkSql(String sql, List<Object> params) {
		String newSql = sql;
		List<Object> newParams = new ArrayList<Object>();
		if (CollectionUtils.isNotEmpty(params)) {
			StringBuilder builder = new StringBuilder();
			// key:数组数据所在params的下标,value:数组大小
			Map<Integer, Integer> indexs = new HashMap<Integer, Integer>();
			for (int i = 0; i < params.size(); i++) {
				Object object = params.get(i);
				if (object == null) {
					newParams.add(object);
					continue;
				}
				String type = object.getClass().getName();
				Set<String> types = getAllInterfaces(object);
				if (type.startsWith("[L")) {
					Object[] objects = (Object[]) object;
					if (objects.length == 0) {
						throw new EntException("数组数据类型不能为空");
					}
					indexs.put(i, objects.length);
					for (Object o : objects) {
						newParams.add(o);
					}
				} else if (types.contains("java.util.Collection")) {
					Collection<Object> collection = (Collection<Object>) object;
					if (CollectionUtils.isEmpty(collection)) {
						throw new EntException("数组数据类型不能为空");
					}
					indexs.put(i, collection.size());
					for (Object o : collection) {
						newParams.add(o);
					}
				} else {
					newParams.add(object);
				}
			}
			if (!indexs.isEmpty()) {
				int index = 0;
				for (int i = 0; i < sql.length(); i++) {
					char c = sql.charAt(i);
					if (c == '?') {
						Integer count = indexs.get(index);
						index++;
						if (count != null) {
							builder.append("(");
							for (int j = 0; j < count; j++) {
								builder.append("?,");
							}
							builder.deleteCharAt(builder.length() - 1);
							builder.append(")");
						} else {
							builder.append(c);
						}
					} else {
						builder.append(c);
					}
				}
				newSql = builder.toString();
			}
		}
		Pair<String, List<Object>> pair = new MutablePair<String, List<Object>>(newSql, newParams);
		return pair;
	}

	/**
	 * 获取该对象的类,类的所有实现接口,类的父类的名称
	 * 
	 * @param object
	 * @return
	 */
	private static Set<String> getAllInterfaces(Object object) {
		Set<String> types = new HashSet<String>();
		Stack<Class<?>> stack = new Stack<Class<?>>();
		stack.push(object.getClass());
		while (!stack.empty()) {
			Class<?> c = stack.pop();
			types.add(c.getName());
			Class<?> superClass = c.getSuperclass();
			if (superClass != null) {
				stack.push(superClass);
			}
			Class<?>[] cs = c.getInterfaces();
			for (Class<?> superClazzs : cs) {
				stack.push(superClazzs);
			}
		}
		return types;
	}

	/**
	 * 查询ID
	 * 
	 * @param sql
	 *            返回值只有id的SQL语句
	 * @param params
	 * @return
	 */
	protected Long queryOne(String sql, Object[] objects) {
		return queryOne(sql, asList(objects));
	}

	/**
	 * 查询ID
	 * 
	 * @param sql
	 *            返回值只有id的SQL语句
	 * @param params
	 * @return
	 */
	protected Long queryOne(String sql, List<Object> params) {
		return queryOne(sql, params, SqlParam.getDefault());
	}

	/**
	 * 查询ID
	 * 
	 * @param sql
	 *            返回值只有id的SQL语句
	 * @param params
	 * @param sqlParam
	 * @return
	 */
	protected Long queryOne(String sql, List<Object> params, SqlParam sqlParam) {
		if (StringUtils.startsWithIgnoreCase(sql, "from")) {
			sql = "select id " + sql;
		}
		Map<String, Class<?>> map = new HashMap<String, Class<?>>();
		map.put(ID, Long.class);
		List<Map<String, Object>> list = executeSql(sql, params, map, sqlParam);
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, Object> m = list.get(0);
			return (Long) m.get(ID);
		}
		return null;
	}

	/**
	 * 查询数量
	 * 
	 * @param sql
	 *            返回值只有count的SQL语句<br>
	 *            例如:select count(1) as count from api_user where phone = ?<br>
	 *            "select count(1) as count "可以省略
	 * @param params
	 * @return
	 */
	protected Long queryCount(String sql, Object[] params) {
		return queryCount(sql, asList(params));
	}

	/**
	 * 查询数量
	 * 
	 * @param sql
	 *            返回值只有count的SQL语句<br>
	 *            例如:select count(1) as count from api_user where phone = ?<br>
	 *            "select count(1) as count "可以省略
	 * @param params
	 * @return
	 */
	protected Long queryCount(String sql, List<Object> params) {
		return queryCount(sql, params, SqlParam.getDefault());
	}

	/**
	 * 查询数量
	 * 
	 * @param sql
	 *            返回值只有count的SQL语句<br>
	 *            例如:select count(1) as count from api_user where phone = ?<br>
	 *            "select count(1) as count "可以省略
	 * @param params
	 * @param sqlParam
	 * @return
	 */
	protected Long queryCount(String sql, Object[] params, SqlParam sqlParam) {
		return queryCount(sql, asList(params), sqlParam);
	}

	/**
	 * 查询数量
	 * 
	 * @param sql
	 *            返回值只有count的SQL语句<br>
	 *            例如:select count(1) as count from api_user where phone = ?<br>
	 *            "select count(1) as count "可以省略
	 * @param params
	 * @param sqlParam
	 * @return
	 */
	protected Long queryCount(String sql, List<Object> params, SqlParam sqlParam) {
		if (StringUtils.startsWithIgnoreCase(sql, "from")) {
			sql = "select count(1) as count " + sql;
		}
		Map<String, Class<?>> map = new HashMap<String, Class<?>>();
		map.put("count", Long.class);
		List<Map<String, Object>> list = executeSql(sql, params, map, sqlParam);
		if (CollectionUtils.isNotEmpty(list)) {
			Map<String, Object> m = list.get(0);
			return (Long) m.get("count");
		}
		return null;
	}

	/**
	 * 查询ID
	 * 
	 * @param sql
	 *            返回值只有id的SQL语句<br>
	 *            例如:select id from api_user where phone = ?<br>
	 *            "select id "可以省略
	 * @param params
	 * @return
	 */
	protected List<Long> queryList(String sql, Object[] params) {
		return queryList(sql, asList(params));
	}

	/**
	 * 查询ID
	 * 
	 * @param sql
	 *            返回值只有id的SQL语句<br>
	 *            例如:select id from api_user where phone = ?<br>
	 *            "select id "可以省略
	 * @param params
	 * @return
	 */
	protected List<Long> queryList(String sql, List<Object> params) {
		return queryList(sql, params, SqlParam.getDefault());
	}

	/**
	 * 查询ID
	 * 
	 * @param sql
	 *            返回值只有id的SQL语句<br>
	 *            例如:select id from api_user where phone = ?<br>
	 *            "select id "可以省略
	 * @param params
	 * @param sqlParam
	 * @return
	 */
	protected List<Long> queryList(String sql, Object[] params, SqlParam sqlParam) {
		return queryList(sql, asList(params), sqlParam);
	}

	/**
	 * 查询ID
	 * 
	 * @param sql
	 *            返回值只有id的SQL语句<br>
	 *            例如:select id from api_user where phone = ?<br>
	 *            "select id "可以省略
	 * @param params
	 * @param sqlParam
	 * @return
	 */
	protected List<Long> queryList(String sql, List<Object> params, SqlParam sqlParam) {
		if (StringUtils.startsWithIgnoreCase(sql, "from")) {
			sql = "select id " + sql;
		}
		List<Long> ids = new ArrayList<Long>();
		Map<String, Class<?>> map = new HashMap<String, Class<?>>();
		map.put(ID, Long.class);
		List<Map<String, Object>> list = executeSql(sql, params, map, sqlParam);
		if (CollectionUtils.isNotEmpty(list)) {
			for (Map<String, Object> m : list) {
				ids.add((Long) m.get(ID));
			}
		}
		return ids;
	}
	
	/**
	 * 查询ID
	 * 
	 * @param sql
	 *            返回值只有id的SQL语句<br>
	 *            例如:select id from api_user where phone = ?<br>
	 *            "select id "可以省略
	 * @param orderBy       
	 * @param params
	 * @param sqlParam
	 * @return
	 */
	protected List<Long> queryList(String sql, String orderBy, List<Object> params, SqlParam sqlParam) {
		if (StringUtils.startsWithIgnoreCase(sql, "from")) {
			sql = "select id " + sql;
		}
		List<Long> ids = new ArrayList<Long>();
		Map<String, Class<?>> map = new HashMap<String, Class<?>>();
		map.put(ID, Long.class);
		List<Map<String, Object>> list = executeSql(sql + " " + orderBy, params, map, sqlParam);
		if (CollectionUtils.isNotEmpty(list)) {
			for (Map<String, Object> m : list) {
				ids.add((Long) m.get(ID));
			}
		}
		return ids;
	}

	/**
	 * 获取连接
	 * 
	 * @param readOnly
	 *            是否获取只读连接接
	 * @return
	 * @throws Exception
	 */
	protected Connection getConnection(boolean readOnly) throws Exception {
		if (readOnly) {
			return dataReadOnlySource.getConnection();
		}
		return dataSource.getConnection();
	}

	/**
	 * 关闭连接
	 * 
	 * @param c
	 * @param p
	 * @param r
	 */
	protected static void close(Connection c, PreparedStatement p, ResultSet r) {
		try {
			if (r != null) {
				r.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (p != null) {
				p.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if (c != null) {
				c.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询单条数据,若查询语句返回多条,则仅返回第一条数据
	 * 
	 * @param baseDao
	 * @param sql
	 *            标准SQL语句,参数占位符用"?"<br>
	 *            例如: select id from api_user where phone = ?
	 * @param params
	 *            查询参数列表
	 * @param sqlParam
	 *            执行SQL语句配置
	 * @return
	 */
	protected <T extends BasePO> T findOne(BaseDao<T> baseDao, String sql, Object[] params, SqlParam sqlParam) {
		return findOne(baseDao, sql, asList(params));
	}

	protected <T extends BasePO> T findOne(BaseDao<T> baseDao, String sql, Object[] params) {
		return findOne(baseDao, sql, asList(params));
	}

	protected <T extends BasePO> T findOne(BaseDao<T> baseDao, String sql, List<Object> params) {
		return findOne(baseDao, sql, params, SqlParam.getDefault());
	}

	protected <T extends BasePO> T findOne(BaseDao<T> baseDao, String sql, List<Object> params, SqlParam sqlParam) {
		Long id = queryOne(sql, params, sqlParam);
		if (id != null) {
			return baseDao.findOne(id);
		}
		return null;
	}

	/**
	 * 查询多条数据
	 * 
	 * @param baseDao
	 * @param sql
	 *            标准SQL语句,参数占位符用"?"<br>
	 *            例如: select id from api_user where phone = ?
	 * @param params
	 *            参数列表
	 * @return
	 */
	protected <T extends BasePO> List<T> findList(BaseDao<T> baseDao, String sql, Object[] params, SqlParam sqlParam) {
		return findList(baseDao, sql, asList(params), sqlParam);
	}

	protected <T extends BasePO> List<T> findList(BaseDao<T> baseDao, String sql, Object[] params) {
		return findList(baseDao, sql, asList(params));
	}

	protected <T extends BasePO> List<T> findList(BaseDao<T> baseDao, String sql, List<Object> params) {
		return findList(baseDao, sql, params, SqlParam.getDefault());
	}

	protected <T extends BasePO> List<T> findList(BaseDao<T> baseDao, String sql, List<Object> params, SqlParam sqlParam) {
		List<T> list = new ArrayList<T>();
		List<Long> ids = queryList(sql, params, sqlParam);
		if (CollectionUtils.isNotEmpty(ids)) {
			return findAll(ids, baseDao);
		}
		return list;
	}
	
	
	/**
	 * 查询多条数据,可以排序
	 * 
	 * @param baseDao
	 * @param sql
	 *            标准SQL语句,参数占位符用"?"<br>
	 *            例如: select id from api_user where phone = ?
	 * @param orderBy
	 * @param params
	 *            参数列表
	 * @return
	 */
	protected <T extends BasePO> List<T> findList(BaseDao<T> baseDao, String sql, String orderBy, Object[] params, SqlParam sqlParam) {
		return findList(baseDao, sql, orderBy, asList(params), sqlParam);
	}

	protected <T extends BasePO> List<T> findList(BaseDao<T> baseDao, String sql, String orderBy, Object[] params) {
		return findList(baseDao, sql, orderBy, asList(params));
	}

	protected <T extends BasePO> List<T> findList(BaseDao<T> baseDao, String sql, String orderBy, List<Object> params) {
		return findList(baseDao, sql, orderBy, params, SqlParam.getDefault());
	}

	protected <T extends BasePO> List<T> findList(BaseDao<T> baseDao, String sql, String orderBy, List<Object> params, SqlParam sqlParam) {
		List<T> list = new ArrayList<T>();
		List<Long> ids = queryList(sql, orderBy, params, sqlParam);
		if (CollectionUtils.isNotEmpty(ids)) {
			return findAllList(ids, baseDao);
		}
		return list;
	}
	

	/***
	 * 分页查询数据
	 * 
	 * @param baseDao
	 * @param sql
	 *            不包含order by的查询语句
	 * @param params
	 * @param page
	 *            页数,从1开始
	 * @param pageSize
	 *            每页大小
	 * @param sqlParam
	 * @return
	 */
	protected <T extends BasePO> Page<T> findPage(BaseDao<T> baseDao, String sql, Object[] params, int page, int pageSize, SqlParam sqlParam) {
		return findPage(baseDao, sql, asList(params), page, pageSize, sqlParam);
	}

	/**
	 * 分页查询数据
	 * 
	 * @param baseDao
	 * @param sql
	 *            不包含order by的查询语句
	 * @param params
	 * @param page
	 *            页数,从1开始
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	protected <T extends BasePO> Page<T> findPage(BaseDao<T> baseDao, String sql, Object[] params, int page, int pageSize) {
		return findPage(baseDao, sql, asList(params), page, pageSize);
	}

	/**
	 * 分页查询数据
	 * 
	 * @param baseDao
	 * @param sql
	 *            不包含order by的查询语句
	 * @param params
	 * @param page
	 *            页数,从1开始
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	protected <T extends BasePO> Page<T> findPage(BaseDao<T> baseDao, String sql, List<Object> params, int page, int pageSize) {
		return findPage(baseDao, sql, params, page, pageSize, SqlParam.getDefault());
	}

	/**
	 * 分页查询数据
	 * 
	 * @param baseDao
	 * @param sql
	 *            不包含order by的查询语句
	 * @param params
	 * @param page
	 *            页数,从1开始
	 * @param pageSize
	 *            每页大小
	 * @param sqlParam
	 * @return
	 */
	protected <T extends BasePO> Page<T> findPage(BaseDao<T> baseDao, String sql, List<Object> params, int page, int pageSize, SqlParam sqlParam) {
		return findPage(baseDao, sql, "", params, page, pageSize, sqlParam);
	}

	/**
	 * 分页查询多条数据
	 * 
	 * @param baseDao
	 * @param sql
	 *            标准SQL语句,参数占位符用"?",但是,开头的select id不用写,后面的limit也不用写<br>
	 *            例如: from api_user where phone = ?
	 * @param orderBy
	 *            排序语句
	 * @param params
	 *            参数列表
	 * @param page
	 *            页数,从1开始
	 * @param pageSize
	 *            每页数量
	 * @return
	 */
	protected <T extends BasePO> Page<T> findPage(BaseDao<T> baseDao, String sql, String orderBy, Object[] params, int page, int pageSize, SqlParam sqlParam) {
		return findPage(baseDao, sql, orderBy, asList(params), page, pageSize, sqlParam);
	}

	/**
	 * 分页查询数据
	 * 
	 * @param baseDao
	 * @param sql
	 * @param orderBy
	 * @param params
	 * @param page
	 *            页数,从1开始
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	protected <T extends BasePO> Page<T> findPage(BaseDao<T> baseDao, String sql, String orderBy, Object[] params, int page, int pageSize) {
		return findPage(baseDao, sql, orderBy, asList(params), page, pageSize);
	}

	/**
	 * 分页查询数据
	 * 
	 * @param baseDao
	 * @param sql
	 * @param orderBy
	 * @param params
	 * @param page
	 *            页数,从1开始
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	protected <T extends BasePO> Page<T> findPage(BaseDao<T> baseDao, String sql, String orderBy, List<Object> params, int page, int pageSize) {
		return findPage(baseDao, sql, orderBy, params, page, pageSize, SqlParam.getDefault());
	}

	/**
	 * 分页查询数据
	 * 
	 * @param baseDao
	 * @param sql
	 * @param orderBy
	 * @param params
	 * @param page
	 *            页数,从1 开始
	 * @param pageSize
	 *            每页大小
	 * @param sqlParam
	 * @return
	 */
	protected <T extends BasePO> Page<T> findPage(BaseDao<T> baseDao, String sql, String orderBy, List<Object> params, int page, int pageSize, SqlParam sqlParam) {
		if (page <= 0) {
			throw new EntException("SQL[" + sql + "]分页查询页数错误:" + page);
		}
		if (pageSize <= 0) {
			throw new EntException("SQL[" + sql + "]分页查询每页数量错误:" + pageSize);
		}
		Page<T> pageResult = new Page<T>();
		pageResult.setPageNo(page);
		long count = queryCount("select count(1) as count " + sql, params, sqlParam);
		if (count <= 0) {
			return pageResult;
		}
		int pageMax = (int) (count % pageSize == 0 ? count / pageSize : (count / pageSize + 1));
		pageResult.setCount(count);
		pageResult.setPageMax(pageMax);
		if (page > pageMax) {
			return pageResult;
		}

		params.add((page - 1) * pageSize);
		params.add(pageSize);
		List<Long> ids = queryList("select id " + sql + " " + orderBy + " limit ?,?", params, sqlParam);
		if (CollectionUtils.isNotEmpty(ids)) {
			List<T> list = baseDao.findAll(ids);
			if (CollectionUtils.isNotEmpty(list)) {
				Map<Long, T> map = new HashMap<Long, T>();
				for (T t : list) {
					map.put(t.getId(), t);
				}
				for (Long id : ids) {
					pageResult.getInfo().add(map.get(id));
				}
			} else {
				logger.warn("SQL[{}]查询ID后查询列表返回为空", sql);
			}
		}
		return pageResult;
	}

	/**
	 * 数组转换为List
	 * 
	 * @param objects
	 * @return
	 */
	protected List<Object> asList(Object[] objects) {
		if (objects == null) {
			return null;
		}
		List<Object> list = new ArrayList<Object>();
		for (Object object : objects) {
			list.add(object);
		}
		return list;
	}

	protected List<Object> toList(Object... objects) {
		List<Object> list = new ArrayList<Object>();
		if (objects == null) {
			return null;
		}
		for (Object object : objects) {
			list.add(object);
		}
		return list;
	}

	/**
	 * 查询所有的对象<br>
	 * 底层已分批查询
	 * 
	 * @param ids
	 * @return
	 */
	protected <T extends BasePO> List<T> findAll(Collection<Long> ids, BaseDao<T> baseDao) {
		List<T> list = new ArrayList<T>();
		if (CollectionUtils.isEmpty(ids)) {
			return list;
		}
		List<Long> idList = new ArrayList<Long>();
		idList.addAll(ids);
		int start = 0;
		int end = 0;
		while (true) {
			if (start >= ids.size()) {
				break;
			}
			end += PAGE_SIZE;
			end = Math.min(end, idList.size());
			List<Long> subList = idList.subList(start, end);
			List<T> temps = baseDao.findAll(subList);
			list.addAll(temps);
			start = end;
		}
		return list;
	}
	
	/**
	 * 查询所有的对象<br>
	 * 底层已分批查询
	 * 按ids的排序
	 * @param ids
	 * @return
	 */
	protected <T extends BasePO> List<T> findAllList(List<Long> ids, BaseDao<T> baseDao) {
		List<T> list = new ArrayList<T>();
		if (CollectionUtils.isEmpty(ids)) {
			return list;
		}
		List<Long> idList = new ArrayList<Long>();
		idList.addAll(ids);
		int start = 0;
		int end = 0;
		while (true) {
			if (start >= ids.size()) {
				break;
			}
			end += PAGE_SIZE;
			end = Math.min(end, idList.size());
			List<Long> subList = idList.subList(start, end);
			List<T> temps = baseDao.findAll(subList);
			Map<Long, T> map = toMap(temps);
			for (Long id : ids) {
				list.add(map.get(id));
			}
			start = end;
		}
		return list;
	}

	/**
	 * 截取指定长度的字符串
	 * 
	 * @param s
	 * @param length
	 * @return
	 */
	protected String getChar(String s, int length) {
		if (s == null) {
			return null;
		}
		if (length <= 0) {
			return "";
		}
		s = utf8mb4(s);
		if (s.length() > length) {
			return s.substring(0, length);
		}
		return s;
	}

	/**
	 * UTF8MD4转码(转成"#")<br>
	 * 应对智能手机表情格式(utf8mb4)
	 * 
	 * @param s
	 * @return
	 */
	protected String utf8mb4(String s) {
		if (s == null) {
			return null;
		}
		byte[] b = s.getBytes(CHARSET);
		for (int i = 0; i < b.length; i++) {
			if ((b[i] & 0xF8) == 0xF0) {
				for (int j = 0; j < 4; j++) {
					if (i + j < b.length) {
						b[i + j] = 0x23;
					}
				}
				i += 3;
			}
		}
		s = new String(b, CHARSET);
		return s;
	}

	/**
	 * 截取默认长度(255)的字符串,并进行UTF8MD4转码(转成"#")
	 * 
	 * @param s
	 * @return
	 */
	protected String getChar(String s) {
		return getChar(s, 255);
	}

	protected <T extends BasePO> T newInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new EntException(e);
		}
	}
}
