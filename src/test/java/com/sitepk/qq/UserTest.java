package com.sitepk.qq;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void testLogin() throws Exception {
		User user = new User();
		user.setId("63090585");
		user.setPassword("huyang,./");
		user.login();
		
		user.sendMsg("1474173193","测试中文消失");
	}

}
