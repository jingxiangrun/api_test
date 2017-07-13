package cn.hdu.bookstore.book.service;

import java.util.List;

import cn.hdu.bookstore.book.dao.BookDao;
import cn.hdu.bookstore.book.domain.Book;

public class BookService {
	private BookDao bookDao=new BookDao();
    /**
     * 按分类进行查询
     * @param cid
     * @return
     */
	public List<Book> findByCategory(String cid) {
		
		return bookDao.findByCategory(cid);
	}
	/**
	 * 查询所有图书
	 * @return
	 */
	public List<Book> findAll() {
		return bookDao.findAll();
	}
	/**
	 * 加载图书明细
	 * @param bid
	 * @return
	 */
	public Book load(String bid) {
		return bookDao.load(bid);
	}
	
}
