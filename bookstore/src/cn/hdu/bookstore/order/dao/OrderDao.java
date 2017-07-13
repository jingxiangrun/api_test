package cn.hdu.bookstore.order.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;

import com.sun.jmx.snmp.Timestamp;

import cn.hdu.bookstore.order.domain.Order;
import cn.hdu.bookstore.order.domain.OrderItem;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {
	private QueryRunner qr=new TxQueryRunner();
    /**
     * 添加订单
     * @param order
     */
	public void addOrder(Order order) {
		try {
//			String sql="insert into orders values=(?,?,?,?,?,?)";
			/*
			 * 将util中的时间转换成sql中的时间Timestamp
			 */
//			Timestamp timestamp=new Timestamp(order.getOrdertime().getTime());
//			Object[] params={order.getOid(),timestamp,order.getTotal(),
//					order.getState(),order.getOwner().getUid(),order.getAddress()};
			String sql = "insert into orders values(?,?,?,?,?,?)";
			/*
			 * 处理util的Date转换成sql的Timestamp
			 */
			Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
			Object[] params = {order.getOid(), timestamp, order.getTotal(),
					order.getState(), order.getOwner().getUid(),
					order.getAddress()};
		    System.out.println("往数据库录入");
			qr.update(sql, params);
			System.out.println("往数据库录入完毕");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    /**
     * 插入订单条目
     * @param orderItemList
     */
//	public void addOrderItemList(List<OrderItem> orderItemList) {
//		try {
//			String sql="insert into orderitem values(?,?,?,?,?)";
//			Object[][] params=new Object[orderItemList.size()][];
//			/*
//			 * 循环遍历每个orderItemList，使用orderItem为每个params赋值
//			 */
//			for (int i = 0; i < orderItemList.size(); i++) {
//				OrderItem order=orderItemList.get(i);//orderItemList为订单集合，orderItem为每个订单条目的具体项
//				params[i]=new Object[]{order.getIid(),order.getCount(),order.getTotal(),order.getOrder().getOid(),order.getBook().getBid()};
//				
//			}
//			System.out.println("插入订单条目开始");
//			qr.batch(sql, params);
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//		
//	}
//	
	
	public void addOrderItemList(List<OrderItem> orderItemList) {
		/**
		 * QueryRunner类的batch(String sql, Object[][] params)
		 * 其中params是多个一维数组！
		 * 每个一维数组都与sql在一起执行一次，多个一维数组就执行多次
		 */
		try {
			System.out.println("开始插入订单条目");
			String sql = "insert into orderitem values(?,?,?,?,?)";
			/*
			 * 把orderItemList转换成两维数组
			 *   把一个OrderItem对象转换成一个一维数组
			 */
			Object[][] params = new Object[orderItemList.size()][];
			// 循环遍历orderItemList，使用每个orderItem对象为params中每个一维数组赋值
			for(int i = 0; i < orderItemList.size(); i++) {
				OrderItem item = orderItemList.get(i);
				params[i] = new Object[]{item.getIid(), item.getCount(), 
						item.getSubtotal(), item.getOrder().getOid(),
						item.getBook().getBid()}; 
			}
			System.out.println("开始进行批处理");
			qr.batch(sql, params);//执行批处理
			System.out.println("批处理结束");
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
