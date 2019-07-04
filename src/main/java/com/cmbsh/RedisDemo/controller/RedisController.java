package com.cmbsh.RedisDemo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cmbsh.RedisDemo.bean.Book;
import com.cmbsh.RedisDemo.bean.BookDao;
import com.cmbsh.RedisDemo.util.RedisUtil;

@Controller("RedisController")
public class RedisController {
	@Autowired
	private RedisUtil redisUtil;
	@Autowired
	BookDao bookDao;

	/**
	 * 简单测试, 存取各种类型的数据
	 */
	@RequestMapping("/test1")
	public void test1() {
		//String类型
		redisUtil.set("name", "liujing");
		Book book1 = new Book();
		book1.setId(1);
		book1.setName("三国演义");
		book1.setAuthor("罗贯中");
		//String类型
		redisUtil.set("book1", book1);
		String name = (String) redisUtil.get("name");
		System.out.println(name);
		System.out.println(redisUtil.get("book1").toString());
		//Hash类型，即多个键值对
		redisUtil.hset("userInfo", "userName", "hongda5521");
		redisUtil.hset("userInfo", "password", "123456");
		String userName  = (String) redisUtil.hget("userInfo", "userName");
		String password  = (String) redisUtil.hget("userInfo", "password");
		System.out.println("userInfo: "+userName+" "+ password);
		
		//Hash类型，值为一个map(map中存放很多键值对)
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("userName1", "lijing");
		map.put("password1", "333444");
		redisUtil.hmset("userInfo1", map);
		String userName1  = (String) redisUtil.hget("userInfo1", "userName1");
		String password1  = (String) redisUtil.hget("userInfo1", "password1");
		System.out.println("userInfo1: "+userName1+" "+ password1);
		
		//List类型
		/*
		 * ArrayList<String> list = new ArrayList<String>(); list.add("apple");
		 * list.add("orange"); list.add("iphone"); list.add("huawei");
		 * list.add("huawei"); redisUtil.lSet("orderList", list);
		 * System.out.println(redisUtil.lGetListSize("orderList"));
		 */
		//这里reList指的是key为orderList 的值，添加两次后得到
		//[[apple, orange, iphone, huawei, huawei], [apple, orange, iphone, huawei, huawei]]
		List<Object> resList = redisUtil.lGet("orderList", 0, -1);
		/*
		 * for (Object object : resList) {
		 * 
		 * System.out.println(object);//第一个[apple, orange, iphone, huawei, huawei] }
		 */
		for (int i = 0; i < resList.size(); i++) {
			List tempList = (List) resList.get(i);
			int size = ((List) resList.get(i)).size();
			for (int j = 0; j < size; j++) {
				System.out.println(tempList.get(j));
			}
		}
		
		//set类型
		redisUtil.sSet("mySet", "setValue1","setValue2","setValue3","setValue1");
		Set<Object> resSet = redisUtil.sGet("mySet");
		//Set<String> tepmSet = resSet;
		for (Object object : resSet) {
			System.out.println(object);
		}
	}
	/**
	 * 测试Redis缓存
	 */
	@RequestMapping("/testRedisCache")
	public void contentLoads() {
		bookDao.getBookById(1);
		bookDao.getBookById(1);
		bookDao.deleteBookById(1);
		Book b3 = bookDao.getBookById(1);
		System.out.println("b3"+b3);
		Book b = new Book();
		b.setName("三国演义");
		b.setAuthor("罗贯中");
		b.setId(1);
		bookDao.updateBookById(b);
		Book b4 = bookDao.getBookById(1);
		System.out.println("b4"+b4);
	}
}
