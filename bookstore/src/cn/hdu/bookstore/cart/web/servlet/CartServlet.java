package cn.hdu.bookstore.cart.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hdu.bookstore.book.domain.Book;
import cn.hdu.bookstore.book.service.BookService;
import cn.hdu.bookstore.cart.domain.Cart;
import cn.hdu.bookstore.cart.domain.CartItem;
import cn.itcast.servlet.BaseServlet;

public class CartServlet extends BaseServlet {
	/**
	 * 清除指定条目
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 得到车
		 * 得到bid
		 */
		Cart cart=(Cart) request.getSession().getAttribute("cart");
		String bid=request.getParameter("bid");
		cart.delete(bid);
		return "f:/jsps/cart/list.jsp";
	}
	
	/**
	 * 清空购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 得到购物车
		 */
		Cart cart=(Cart) request.getSession().getAttribute("cart");
		cart.clear();
		return "f:/jsps/cart/list.jsp";
	}
    /**
     * 向购物车中添加条目
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	       Cart cart=(Cart) request.getSession().getAttribute("cart");//得到车
	       String bid=request.getParameter("bid");
	       Book book=new BookService().load(bid);//得到图书信息
	       int count=Integer.parseInt(request.getParameter("count"));//得到图书数量
	       /*
	        * 得到条目
	        */
	       CartItem cartItem=new CartItem();
	       cartItem.setBook(book);
	       cartItem.setCount(count);
	       /*
	        * 将条目添加到车中
	        */
	       cart.add(cartItem);
	       return "f:/jsps/cart/list.jsp";
	}
	

}
