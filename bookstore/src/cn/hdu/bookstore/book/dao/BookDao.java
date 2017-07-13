package cn.hdu.bookstore.book.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.hdu.bookstore.book.domain.Book;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
	private QueryRunner qr=new TxQueryRunner();
    /**
     * 按分类进行查询
     * @param cid
     * @return
     */
	public List<Book> findByCategory(String cid) {
		String sql="select * from book where cid=?";
		try {
			return qr.query(sql, new BeanListHandler<Book>(Book.class),cid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 查询所有图书
	 * @return
	 */
	public List<Book> findAll() {
		String sql="select * from book ";
		try {
			return qr.query(sql, new BeanListHandler<Book>(Book.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * 加载图书明细
	 * @param bid
	 * @return
	 */
	public Book load(String bid) {
		String sql="select * from book where bid=?";
		try {
			return qr.query(sql, new BeanHandler<Book>(Book.class),bid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
