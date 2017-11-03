package com.lianchuan.common.entity.sql;

/**
 * SQL参数
 */
public class SqlParam {

	/** 是否使用只读连接,默认为true */
	private boolean readOnly = true;

	private SqlParam() {
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	/**
	 * 获取默认参数:<br>
	 * readOnly = true<br>
	 * 
	 * @return
	 */
	public static SqlParam getDefault() {
		SqlParam sqlParam = new SqlParam();
		return sqlParam;
	}

	/**
	 * 获取主库连接参数:<br>
	 * readOnly = false<br>
	 * 
	 * @return
	 */
	public static SqlParam getMain() {
		SqlParam sqlParam = new SqlParam();
		sqlParam.readOnly = false;
		return sqlParam;
	}

}
