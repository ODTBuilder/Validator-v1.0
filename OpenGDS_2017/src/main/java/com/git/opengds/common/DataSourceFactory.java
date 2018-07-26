package com.git.opengds.common;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

abstract public class DataSourceFactory {
	@Autowired
	@Inject
	@Qualifier("adminSqlSession")
	private SqlSession adminSqlSession;
	/*
	 * @Autowired
	 * 
	 * @Inject
	 * 
	 * @Qualifier("admin2SqlSession") private SqlSession admin2SqlSession;
	 * 
	 * @Autowired
	 * 
	 * @Inject
	 * 
	 * @Qualifier("admin3SqlSession") private SqlSession admin3SqlSession;
	 */

	public SqlSession getSqlSession(String id) {
		switch (id) {
		// case "git":
		// return adminSqlSession;
		case "admin":
			return adminSqlSession;
		/*
		 * case "admin2": return admin2SqlSession; case "admin3": return
		 * admin3SqlSession;
		 */
		default:
			return null;
		}
	}
}
