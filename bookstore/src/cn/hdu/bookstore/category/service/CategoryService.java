package cn.hdu.bookstore.category.service;

import java.util.List;

import cn.hdu.bookstore.category.dao.CategoryDao;
import cn.hdu.bookstore.category.domain.Category;

public class CategoryService {
	private CategoryDao categoryDao= new CategoryDao();
    /**
     * 查询所有分类
     * @return
     */
	public List<Category> findAll() {
		return categoryDao.findAll();
	}
	
}
