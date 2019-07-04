package com.cmbsh.RedisDemo.bean;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Book2 implements Serializable{
	
	private Integer id;
	private String name;
	private String author;

}
