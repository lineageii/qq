package com.sitepk.qq.test;

import org.junit.Assert;
import org.junit.Test;

import com.sitepk.qq.PasswordEncrypt;

public class TestPassword {
	@Test
	public void testPasswordEncrypt(){
		String password = PasswordEncrypt.passwordEcrypt("", "!3DF");
		Assert.assertEquals("0B434562D44B84F6B4E1339C0B3577D2", password);
	}
}
