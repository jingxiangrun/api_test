package cn.hdu.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	private Map<String,CartItem> map=new LinkedHashMap<String, CartItem>();
	/**
	 * 计算总额
	 */
	public double getTotal(){
		BigDecimal total=new BigDecimal("0");
		for(CartItem cartItem:map.values()){
			BigDecimal subTotal=new BigDecimal(cartItem.getSubTotal()+"");
			total=total.add(subTotal);
		}
		return total.doubleValue();
	}
	/**
	 * 添加条目到车中
	 */
	public void add(CartItem cartItem){
		if(map.containsKey(cartItem.getBook().getBid())){//如果购物车中包含该条目，则返回该条目
			CartItem _cartItem=map.get(cartItem.getBook().getBid());
			_cartItem.setCount(_cartItem.getCount()+cartItem.getCount());
			map.put(cartItem.getBook().getBid(), _cartItem);
			
		} else {
			map.put(cartItem.getBook().getBid(), cartItem);
		}
	}
	/**
	 * 清空所有条目
	 */
	public void clear(){
		map.clear();
	}
	/**
	 * 清除指定条目
	 */
	public void delete(String bid){
		map.remove(bid);
	}
	/**
	 * 获取所有条目
	 */
	public Collection<CartItem> getCartItems(){
		return map.values();
	}
}
