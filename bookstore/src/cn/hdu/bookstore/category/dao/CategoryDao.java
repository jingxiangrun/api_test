package cn.hdu.bookstore.category.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.hdu.bookstore.category.domain.Category;
import cn.itcast.jdbc.TxQueryRunner;

public class CategoryDao {
     private QueryRunner qr=new TxQueryRunner();
    /**
     * 查询所有分类
     * @return
     */
	public List<Category> findAll() {
		String sql="select * from category";
		try {
			return qr.query(sql,new BeanListHandler<Category>(Category.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
