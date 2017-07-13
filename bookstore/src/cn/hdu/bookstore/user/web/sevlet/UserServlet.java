package cn.hdu.bookstore.user.web.sevlet;

//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Properties;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import cn.hdu.bookstore.user.domain.User;
//import cn.hdu.bookstore.user.service.UserException;
//import cn.hdu.bookstore.user.service.UserService;
//import cn.itcast.commons.CommonUtils;
//import cn.itcast.servlet.BaseServlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import cn.hdu.bookstore.cart.domain.Cart;
import cn.hdu.bookstore.user.domain.User;
import cn.hdu.bookstore.user.service.UserException;
import cn.hdu.bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;
public class UserServlet extends BaseServlet {
      private UserService userService=new UserService();
      
      
      /**
       * 退出功能
       */
    public String quit(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
           request.getSession().invalidate();
           return "r:/index.jsp";
    }
      
      
      /**
       * 登陆功能
       */
      /*
		 * 1. 封装表单数据到form中
		 * 2. 输入校验（不写了）
		 * 3. 调用service完成激活
		 *   > 保存错误信息、form到request，转发到login.jsp
		 * 4. 保存用户信息到session中，然后重定向到index.jsp
		 */
  
//    public void login(HttpServletRequest request, HttpServletResponse response)
//    		throws ServletException, IOException {
//    	 User form=CommonUtils.toBean(request.getParameterMap(), User.class);
//    	 try{
//    	 User user=userService.login(form);
//    	 request.getSession().setAttribute("session_user", user);
//    	 response.sendRedirect("/bookstore/index.jsp");
//    	 } catch(UserException e){
//    		 request.setAttribute("msg", e.getMessage());
//    		 request.setAttribute("form", form);
//    		 request.getRequestDispatcher("/jsps/user/login.jsp");
//    	 }
//    }
//      
      
      public String login(HttpServletRequest request, HttpServletResponse response)
  			throws ServletException, IOException {
  		/*
  		 * 1. 封装表单数据到form中
  		 * 2. 输入校验（不写了）
  		 * 3. 调用service完成激活
  		 *   > 保存错误信息、form到request，转发到login.jsp
  		 * 4. 保存用户信息到session中，然后重定向到index.jsp
  		 */
  		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
  		try {
  			User user = userService.login(form);
  			request.getSession().setAttribute("session_user", user);
  			//登陆成功后给用户分配一辆购物车
  			request.getSession().setAttribute("cart", new Cart());
  			return "r:/index.jsp";
  		} catch (UserException e) {
  			request.setAttribute("msg", e.getMessage());
  			request.setAttribute("form", form);
  			return "f:/jsps/user/login.jsp";
  		}
  	}
      
      
      /**
       * 激活功能
       */
    public void active(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	String code=request.getParameter("code");
    	try {
			userService.active(code);
			request.setAttribute("msg", "恭喜，您激活成功了！请马上登录！");
		} catch (Exception e) {
			request.setAttribute("msg", e.getMessage());
		}
    	request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
    }
      
      
     /**
      * 注册功能
      * @param request
      * @param response
      * @throws ServletException
      * @throws IOException
      */
    public String regist(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	/*
		 * 1. 封装表单数据到form对象中
		 * 2. 补全：uid、code
		 * 3. 输入校验
		 *   > 保存错误信息、form到request域，转发到regist.jsp
		 * 4. 调用service方法完成注册
		 *   > 保存错误信息、form到request域，转发到regist.jsp
		 * 5. 发邮件
		 * 6. 保存成功信息转发到msg.jsp
		 */
    	//封装表单数据
    	User form=CommonUtils.toBean(request.getParameterMap(), User.class);
    	//补全uid和code
    	form.setUid(CommonUtils.uuid());
    	form.setCode(CommonUtils.uuid()+CommonUtils.uuid());
    	//创建一个map用来封装错误信息
    	Map<String,String> errors=new HashMap();
    	String username=form.getUsername();
    	if(username==null||username.trim().isEmpty()){
    		errors.put("username", "用户名不能为空");
    	}else if(username.length()<3||username.length()>10){
    		errors.put("username", "用户名长度必须为3~10");
    	}
    	String password=form.getPassword();
    	if(password==null||password.trim().isEmpty()){
    		errors.put("password", "密码不能为空");
    	}else if(password.length()<3||password.length()>10){
    		errors.put("password", "密码长度必须为3~10");
    	}
    	String email=form.getEmail();
    	if(email==null||email.trim().isEmpty()){
    		errors.put("email", "邮箱不能为空");
    	}else if(!email.matches("\\w+@\\w+\\.\\w+")){
    		errors.put("email", "邮箱格式不正确");
    	}
    	if(errors.size()>0){
    		request.setAttribute("errors", errors);
    		request.setAttribute("form", form);
    		return "f:/jsps/user/regist.jsp";
//    		request.getRequestDispatcher("/jsps/user/regist.jsp").forward(request, response);
    	
    	}
    	try{
    		userService.regist(form);
    	}catch(UserException e){
    		request.setAttribute("msg", e.getMessage());
    		request.setAttribute("form", form);
    		return "f:/jsps/user/regist.jsp";
//    		request.getRequestDispatcher("/jsps/user/regist.jsp").forward(request, response);
    		
    	}
    	
    	 /**
         * 发送邮件
         */
        //获取配置文件
        	Properties props=new Properties();
        	props.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
        	String host=props.getProperty("host");
        	String uname=props.getProperty("uname");
        	String pwd=props.getProperty("pwd");
        	String from=props.getProperty("from");
        	String to=form.getEmail();
        	String subject=props.getProperty("subject");
        	String content=props.getProperty("content");
        	content=MessageFormat.format(content, form.getCode());
        	Session session=MailUtils.createSession(host, uname, pwd);
        	Mail mail=new Mail(from, to, subject, content);
        	try {
				MailUtils.send(session, mail);
			} catch (MessagingException e) {
				throw new RuntimeException();
			}
        	request.setAttribute("msg", "恭喜注册成功快去邮箱激活吧");
//        	request.getRequestDispatcher("/jsps/msg.jsp").forward(request, response);
        	return "f:/jsps/msg.jsp";
    }
    
   
//    public String regist(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		/*
//		 * 1. 封装表单数据到form对象中
//		 * 2. 补全：uid、code
//		 * 3. 输入校验
//		 *   > 保存错误信息、form到request域，转发到regist.jsp
//		 * 4. 调用service方法完成注册
//		 *   > 保存错误信息、form到request域，转发到regist.jsp
//		 * 5. 发邮件
//		 * 6. 保存成功信息转发到msg.jsp
//		 */
//		// 封装表单数据
//		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
//		// 补全
//		form.setUid(CommonUtils.uuid());
//		form.setCode(CommonUtils.uuid() + CommonUtils.uuid());
//		/*
//		 * 输入校验
//		 * 1. 创建一个Map，用来封装错误信息，其中key为表单字段名称，值为错误信息
//		 */
//		Map<String,String> errors = new HashMap<String,String>();
//		/*
//		 * 2. 获取form中的username、password、email进行校验
//		 */
//		String username = form.getUsername();
//		if(username == null || username.trim().isEmpty()) {
//			errors.put("username", "用户名不能为空！");
//		} else if(username.length() < 3 || username.length() > 10) {
//			errors.put("username", "用户名长度必须在3~10之间！");
//		}
//		
//		String password = form.getPassword();
//		if(password == null || password.trim().isEmpty()) {
//			errors.put("password", "密码不能为空！");
//		} else if(password.length() < 3 || password.length() > 10) {
//			errors.put("password", "密码长度必须在3~10之间！");
//		}
//		
//		String email = form.getEmail();
//		if(email == null || email.trim().isEmpty()) {
//			errors.put("email", "Email不能为空！");
//		} else if(!email.matches("\\w+@\\w+\\.\\w+")) {
//			errors.put("email", "Email格式错误！");
//		}
//		/*
//		 * 3. 判断是否存在错误信息
//		 */
//		if(errors.size() > 0) {
//			// 1. 保存错误信息
//			// 2. 保存表单数据
//			// 3. 转发到regist.jsp
//			request.setAttribute("errors", errors);
//			request.setAttribute("form", form);
//			return "f:/jsps/user/regist.jsp";
//		}
//		
//		/*
//		 * 调用service的regist()方法
//		 */
//		try {
//			userService.regist(form);
//		} catch (UserException e) {
//			/*
//			 * 1. 保存异常信息
//			 * 2. 保存form
//			 * 3. 转发到regist.jsp
//			 */
//			request.setAttribute("msg", e.getMessage());
//			request.setAttribute("form", form);
//			return "f:/jsps/user/regist.jsp";
//		}
//		
//		/*
//		 * 发邮件
//		 * 准备配置文件！
//		 */
//		// 获取配置文件内容
//		Properties props = new Properties();
//		props.load(this.getClass().getClassLoader()
//				.getResourceAsStream("email_template.properties"));
//		String host = props.getProperty("host");//获取服务器主机
//		String uname = props.getProperty("uname");//获取用户名
//		String pwd = props.getProperty("pwd");//获取密码
//		String from = props.getProperty("from");//获取发件人
//		String to = form.getEmail();//获取收件人
//		String subject = props.getProperty("subject");//获取主题
//		String content = props.getProperty("content");//获取邮件内容
//		content = MessageFormat.format(content, form.getCode());//替换{0}
//		
//		Session session = MailUtils.createSession(host, uname, pwd);//得到session
//		Mail mail = new Mail(from, to, subject, content);//创建邮件对象
//		try {
//			MailUtils.send(session, mail);//发邮件！
//		} catch (MessagingException e) {
//		}
//		
//		
//		/*
//		 * 1. 保存成功信息
//		 * 2. 转发到msg.jsp
//		 */
//		request.setAttribute("msg", "恭喜，注册成功！请马上到邮箱激活");
//		return "f:/jsps/msg.jsp";
//	}
    
    
    
}
