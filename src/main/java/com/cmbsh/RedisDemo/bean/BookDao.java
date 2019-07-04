package com.cmbsh.RedisDemo.bean;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

@Repository
@CacheConfig(cacheNames = "book_cache")//@CacheConfig 指定缓存名称，若这里不指定，则必须在@Cacheable上指定缓存名称
public class BookDao {
	@Cacheable//表示该方法支持缓存，执行该方法前，先去缓存中找，若缓存中有数据则直接返回缓存数据。
	public Book getBookById(Integer id) {
		System.out.println("getBookById");
		//模拟从数据库获得数据
		Book book = new Book();
		book.setId(id);
		book.setName("三国演义");
		book.setAuthor("罗贯中");
		return book;
	}
	@CachePut(key = "#book.id")//更新数据库操作，@CachePut同时将新的数据进行缓存
	public Book updateBookById(Book book) {
		System.out.println("updateBookById");
		book.setName("三国演义2");
		return book;
	}
	@CacheEvict(key = "#id")//表示执行该方法时，清除book_cache中key为id的缓存
	public void deleteBookById(Integer id) {
		System.out.println("deleteBookById");
	}
}
