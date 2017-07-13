package cn.hdu.bookstore.user.dao;

import java.sql.SQLException;
import java.util.Properties;


import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;



import cn.hdu.bookstore.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {
      private QueryRunner qr=new TxQueryRunner();
      /**
       * 按用户名进行查询
       */
      public User findByUsername(String username){
    	 String sql="select * from tb_user where username=?";
    	 try {
			return qr.query(sql, new BeanHandler<User>(User.class),username);
		} catch (SQLException e) {
			
			throw new RuntimeException(e);
		}
      }
      /**
       * 按email进行查询
       */
      public User findByUseremail(String email){
    	  String sql="select * from tb_user where email=?";
    	  try {
			return qr.query(sql, new BeanHandler<User>(User.class),email);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
      }
      /**
       * 添加用户
       * @param user
       */
      public void add(User user){
    	  try {
    	  String sql="insert into tb_user values(?,?,?,?,?,?)";
    	  Object[] params={user.getUid(),user.getUsername(),
    		            user.getPassword(),user.getEmail(),user.getCode(),user.isState()};
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException();
		}
      }
	public void updateState(String uid, boolean state) {
		
		try {
			String sql="update tb_user state=? where uid=? ";
			qr.update(sql,state,uid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	public User findByCode(String code) {
		
		try {
			String sql="select * from tb_user where code=?";
			return qr.query(sql, new BeanHandler<User>(User.class),code);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	} 
      
      
}
