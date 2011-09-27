package com.sitepk.qq;

import org.junit.Test;

public class UserTest {

	@Test
	public void testLogin() throws Exception {
		WebQQClient user = new WebQQClient();
		user.setId("hujiag@qq.com");
		user.setPassword("huyang,./");
		user.login();
		user.getAllFriends();
		user.sendMsg("63090585", "测试中文消息");
		//user.sendMsg("2608667081", "测试中文消息");
		//
		//user.sendGroupMsg("1666263578", "我是胡佳。zb");
		//user.sendGroupMsg("419781577","测试QQ群发消息");
		//user.sendGroupMsg("829759806","测试QQ群发消息");
	}
}
