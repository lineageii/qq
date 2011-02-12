package com.sitepk.qq;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import org.xml.sax.SAXException;

import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

public class PasswordEncrypt {
private static final String WEBQQ_ENCRYPT_JS_RUI = "http://imgcache.qq.com/ptlogin/js/comm.js";

private static WebConversation wc;
private static ScriptEngineManager sem;
private static SimpleBindings bindings;
private static ScriptEngine scriptEngine;
private static Invocable inv;

static {
	wc = new WebConversation();
	
	HttpUnitOptions.setDefaultCharacterSet("utf-8");
	HttpUnitOptions.setScriptingEnabled(false);
	HttpUnitOptions.setExceptionsThrownOnScriptError(false);
	//HttpUnitOptions.setLoggingHttpHeaders(true);
	HttpUnitOptions.setAcceptCookies(true);
	wc.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.12 (KHTML, like Gecko) Chrome/9.0.587.0 Safari/534.12");
   try {
	loadJSCode(getJSCode(wc));
	} catch (Exception e) {
		e.printStackTrace();
	}
}

private static String getJSCode(WebConversation wc) throws Exception {
	return wc.getResponse(WEBQQ_ENCRYPT_JS_RUI).getText();
}

private static void loadJSCode(String encrypt_js_code) {
   sem = new ScriptEngineManager();
   bindings = new SimpleBindings(); 
   scriptEngine = sem.getEngineByExtension("js");
   scriptEngine.setBindings(bindings, ScriptContext.ENGINE_SCOPE);
  
   try {
    scriptEngine.eval(encrypt_js_code);
    inv = (Invocable) scriptEngine; 
   } catch (ScriptException e) {
    e.printStackTrace();
   }
}

private static String webQQ_md5_3(String data) {
   return executJSFunction("md5_3", data);
}

private static String webQQ_md5(String data) {
   return executJSFunction("md5", data);
}

private static String executJSFunction(String funcationName, String param) {
   String result = "";
  
   try {
    Object obj = inv.invokeFunction(funcationName, param);
    result = obj.toString();
   } catch (ScriptException e) {
    e.printStackTrace();
   } catch (NoSuchMethodException e) {
    e.printStackTrace();
   }
  
   return result;
}

public static String passwordEcrypt (String password, String verifyCode) {
	return webQQ_md5(webQQ_md5_3(password) + verifyCode.toUpperCase()); 
}
}