package cn.hdu.bookstore.book.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hdu.bookstore.book.service.BookService;
import cn.itcast.servlet.BaseServlet;

public class BookServlet extends BaseServlet {
    private BookService bookService =new BookService();
    /**
     * 加载图书详细信息
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String load(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	request.setAttribute("book", bookService.load(request.getParameter("bid")));
    	return "f:/jsps/book/desc.jsp";
    }
    /**
     * 查询所有
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		    request.setAttribute("bookList", bookService.findAll());
		    return "f:/jsps/book/list.jsp";
	}
    
    /**
     * 按分类进行查询 
     */
	public String findByCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid=request.getParameter("cid");
		request.setAttribute("bookList", bookService.findByCategory(cid));
		return "f:/jsps/book/list.jsp";
		
	}
}
