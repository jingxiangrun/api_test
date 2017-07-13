package cn.hdu.bookstore.order.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hdu.bookstore.cart.domain.Cart;
import cn.hdu.bookstore.cart.domain.CartItem;
import cn.hdu.bookstore.order.domain.Order;
import cn.hdu.bookstore.order.domain.OrderItem;
import cn.hdu.bookstore.order.service.OrderService;
import cn.hdu.bookstore.user.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class OrderServlet extends BaseServlet {
	private OrderService orderService=new OrderService();
	/**
	 * 我的订单
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String myOrder(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return null;
	}
	
    /**
     * 添加订单
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/*
		 * 从session中获取cart
		 */
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		/*
		 * 创建order对象
		 */
		Order order=new Order();
		order.setOid(CommonUtils.uuid());
		order.setOrdertime(new Date());//设置订单下单时间
		User user=(User) request.getSession().getAttribute("session_user");
		order.setOwner(user);
		order.setState(1);
		order.setTotal(cart.getTotal());
		/*
		 * 创建订单条目集合
		 */
		List<OrderItem> orderItemList=new ArrayList<OrderItem>();
		/*
		 * 循环遍历每个cartItem，为每个cartItem条目创建订单条目
		 */
		for(CartItem cartItem:cart.getCartItems()){
			OrderItem orderItem=new OrderItem();
			orderItem.setIid(CommonUtils.uuid());
			orderItem.setCount(cartItem.getCount());
			orderItem.setBook(cartItem.getBook());
			orderItem.setOrder(order);
			orderItem.setSubtotal(cartItem.getSubTotal());
			orderItemList.add(orderItem);//把订单条目添加到集合中
		}
		order.setOrderItemList(orderItemList);//把所有订单条目添加到订单中
		cart.clear();//清空购物车
		orderService.add(order);
		request.setAttribute("order", order);
		return "f:/jsps/order/desc.jsp";
		
	}
	
}
