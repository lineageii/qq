package com.sitepk.qq;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void testLogin() throws Exception {
		User user = new User();
		user.setId("hujiag@qq.com");
		user.setPassword("huyang,./");
		user.login();
		
		user.sendMsg("2608667081","测试中文消息");
		//
		//user.sendGroupMsg("1666263578","我是胡佳。zb");
		//user.sendGroupMsg("419781577","测试QQ群发消息");
		//user.sendGroupMsg("829759806","测试QQ群发消息");
	}

}
