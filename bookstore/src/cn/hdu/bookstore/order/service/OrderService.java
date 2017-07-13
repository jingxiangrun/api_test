package cn.hdu.bookstore.order.service;

import java.sql.SQLException;

import cn.hdu.bookstore.order.dao.OrderDao;
import cn.hdu.bookstore.order.domain.Order;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {

	private OrderDao orderDao=new OrderDao();

	public void add(Order order) {
		try {
			/*
			 * 开启事务
			 */
			System.out.println("开启事务");
			JdbcUtils.beginTransaction();
			orderDao.addOrder(order);
			orderDao.addOrderItemList(order.getOrderItemList());
			System.out.println("开始提交");
			JdbcUtils.commitTransaction();
			System.out.println("提交成功");
		} catch (Exception e) {
			try {
				System.out.println("回滚");
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e2) {
				throw new RuntimeException(e2);
			}
		}
		
	}
	
	
}
