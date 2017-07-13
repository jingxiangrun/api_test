package cn.hdu.bookstore.user.service;

import cn.hdu.bookstore.user.dao.UserDao;
import cn.hdu.bookstore.user.domain.User;
import cn.hdu.bookstore.user.service.UserException;
public class UserService {
     private UserDao userdao=new UserDao();

	public void regist(User form) throws UserException {
	     User user=userdao.findByUsername(form.getUsername());
	     if(user!=null) throw new UserException("用户名已被注册");
	     User email=userdao.findByUseremail(form.getEmail());
	     if(email!=null) throw new UserException("邮箱已被注册");
	     userdao.add(form);
		
	}

	public void active(String code) throws UserException {
		User user=userdao.findByCode(code);
		if(user==null) throw new UserException("激活码无效");
		if(user.isState()) throw new UserException("您已激活，不能重复激活");
		userdao.updateState(user.getUid(), true);
	}

//	public User login(User form) throws UserException {
//		User user=userdao.findByUsername(form.getUsername());
//		if(user==null) throw new UserException("用户不存在");
//		if(!user.getPassword().equals(form.getPassword())) throw new UserException("密码输入错误");
//		if(!user.isState()) throw new UserException("你还未激活，请先去邮箱激活");
//		return user;
//	}
     
	public User login(User form) throws UserException {
		/*
		 * 1. 使用username查询，得到User
		 * 2. 如果user为null，抛出异常（用户名不存在）
		 * 3. 比较form和user的密码，若不同，抛出异常（密码错误）
		 * 4. 查看用户的状态，若为false，抛出异常（尚未激活）
		 * 5. 返回user
		 */
		User user = userdao.findByUsername(form.getUsername());
		if(user == null) throw new UserException("用户名不存在！");
		if(!user.getPassword().equals(form.getPassword()))
			throw new UserException("密码错误！");
		if(!user.isState()) throw new UserException("尚未激活！");
		
		return user;
	}
}
