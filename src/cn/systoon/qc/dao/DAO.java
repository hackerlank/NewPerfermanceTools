package cn.systoon.qc.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.systoon.qc.db.JdbcUtils;
import cn.systoon.qc.utils.ReflectionUtils;

public class DAO <T>{

	private QueryRunner queryRunner = new QueryRunner();
	private Class<T> clazz;
	
	public DAO(){
		clazz = ReflectionUtils.getSuperClassGenricType(getClass(), 0);
	}
	
	
	/**
	 * 返回某一个字段的值
	 * @param sql
	 * @param args
	 * @return
	 */
	public <E> E getForValue(String sql,Object ...args ){
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();
			return (E)queryRunner.query(connection, sql, new ScalarHandler(),args);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtils.releaseConnection(connection);
		}
		return null;
	}
	
	/**
	 * 返回 T 所对应的 List
	 * @param sql
	 * @param args
	 * @return
	 */
	public List<T> getForList(String sql,Object ...args ){
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanListHandler<>(clazz),	args);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtils.releaseConnection(connection);
		}
		return null;
	}
	
	/**
	 * 返回对应 T 的一个实例对象
	 * @param sql
	 * @param args
	 * @return
	 */
	public T get(String sql,Object ... args ){
		
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();
			return queryRunner.query(connection, sql, new BeanHandler<>(clazz),	args);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtils.releaseConnection(connection);
		}
		return null;
	}
	
	
	/**
	 * 该方法封装了 INSTERT,DELETE,UPDATE操作
	 * @param sql : SQL语句
	 * @param args: 填充SQL语句的占位符
	 * @throws SQLException 
	 */
	public void update(String sql,Object ... args) {
		Connection connection = null;
		
		try {
			connection = JdbcUtils.getConnection();
			queryRunner.update(connection, sql, args);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			JdbcUtils.releaseConnection(connection);
		}
	}
	
	
}
