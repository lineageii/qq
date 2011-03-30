package com.sitepk.qq;

import java.io.IOException;
import java.net.MalformedURLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import com.meterware.httpunit.ClientProperties;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.PostMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

/**
 * qq User
 * @author hujiag@gmail.com
 *
 */
public class User {
	private Log log = LogFactory.getLog(this.getClass());
	private String id;
	private String password;
	
	private String psessionid;
	
	private final WebConversation wc;
	
	public User() {
		wc = new WebConversation();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "id:" + this.id + " password:" + this.password;
	}
	
	private void setCookie(WebResponse rs){
		String[] cookies =rs.getHeaderFields("set-cookie");
		for(String cookie : cookies){
			String firstCookie = cookie.split(";")[0];
			String[] cookieStr = firstCookie.split("=");
			if(cookieStr.length == 2) {
				wc.putCookie(cookieStr[0], cookieStr[1]);
			} else {
				wc.putCookie(cookieStr[0], "");
			}
			
		}
	}
	
	public void login() throws Exception {
		HttpUnitOptions.setDefaultCharacterSet("utf-8");
		HttpUnitOptions.setScriptingEnabled(false);
		HttpUnitOptions.setExceptionsThrownOnScriptError(false);
		//HttpUnitOptions.setLoggingHttpHeaders(true);
		HttpUnitOptions.setAcceptCookies(true);
		wc.setUserAgent("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US) AppleWebKit/534.12 (KHTML, like Gecko) Chrome/9.0.587.0 Safari/534.12");
		ClientProperties.getDefaultProperties().setAcceptCookies(true);
		
		WebResponse rs;
		//rs= wc.getResponse("http://web2.qq.com/");
		//setCookie(rs);
		
		String url = "http://ptlogin2.qq.com/check?uin=" + this.id +  "&appid=1003903&r=0.3622996990703624";
		rs = wc.getResponse(url);
		setCookie(rs);
		log.info("response:" + rs.getText());
		String verifycode = rs.getText().split(",")[1].replaceAll("'", "").replace(");", "");
		log.info(this.toString() + " verifycode:" + verifycode);
		
		// 需要输入验证码
		if(verifycode.length() > 10){
			log.error("需要验证码!");
			throw new Exception("需要验证码!");
		}
		
		String p = PasswordEncrypt.passwordEcrypt(this.password, verifycode);
		log.info("p:" + p);
		
		GetMethodWebRequest get = new GetMethodWebRequest("http://ptlogin2.qq.com/login");
		get.setParameter("u", this.id);
		get.setParameter("p", p);
		get.setParameter("verifycode", verifycode);
		get.setParameter("remember_uin", "1");
		get.setParameter("aid", "1003903");
		get.setParameter("u1", "http://web2.qq.com/loginproxy.html?login_level=3");
		get.setParameter("h", "1");
		get.setParameter("ptredirect", "0");
		get.setParameter("ptlang", "2052");
		get.setParameter("from_ui", "1");
		get.setParameter("pttype", "1");
		get.setParameter("dumy", "");
		get.setParameter("fp", "loginerroralert");
		get.setParameter("mibao_css", "");
		
		rs = wc.getResponse(get);
		setCookie(rs);
		log.info("response:" + rs.getText());
		
		rs = wc.getResponse("http://web2.qq.com/loginproxy.html?login_level=3");
		setCookie(rs);
		
		PostMethodWebRequest post = new PostMethodWebRequest("http://d.web2.qq.com/channel/login2");
		String ptwebqq = wc.getCookieValue("ptwebqq");
		log.info("ptwebqq:" + ptwebqq);
		String r = "{\"status\":\"\",\"ptwebqq\":\"" + ptwebqq + "\",\"passwd_sig\":\"\",\"clientid\":\"292167\",\"psessionid\":null}";
		//{"status":"","ptwebqq":"7896466821f021f41ba65eb83d689e671d7b08eb02c0c165ef8589bb772cf9db","passwd_sig":"","clientid":"292167","psessionid":null}
		post.setParameter("r", r);
		
		wc.setHeaderField("Referer", "http://d.web2.qq.com/proxy.html?v=20101025002");
		wc.putCookie("pgv_pvid", "215336480");
		wc.putCookie("pgv_flv", "10.1 r102");
		wc.putCookie("pgv_info", "pgvReferrer=&ssid=s7792437458");
		
		rs = wc.getResponse(post);
		
		log.info("login response:" + rs.getText());
		this.psessionid = new JSONObject(rs.getText()).getJSONObject("result").getString("psessionid");
		log.info("psessionid:" + psessionid);
	}
	
	public void sendMsg(String to, String msgParam) throws Exception{
		String url = "http://d.web2.qq.com/channel/send_msg2";
		PostMethodWebRequest post = new PostMethodWebRequest(url);
		Message message = new Message();
		message.setTo(to);
		message.setClientid("292167");
		message.setPsessionid(this.psessionid);
		message.setContent(msgParam);
		String msg = new JSONObject(message).toString();
		post.setParameter("r", msg);
		WebResponse rs;
		
		//wc.putCookie("pgv_pvid", "6477164270");
		//wc.putCookie("pgv_flv", "10.1 r102");
		//wc.putCookie("pgv_info", "pgvReferrer=&ssid=s6494109392");
		
		
		rs = wc.getResponse(post);
		log.info("sendMsg response:" + rs.getText());
	}

	
	/**
	 * 发送群消息
	 * @param to
	 * @param msgParam
	 * @throws Exception
	 */
	public void sendGroupMsg(String to, String msgParam) throws Exception{
		String url = "http://d.web2.qq.com/channel/send_group_msg2";
		PostMethodWebRequest post = new PostMethodWebRequest(url);
		GroupMessage message = new GroupMessage();
		message.setGroup_uin(to);
		message.setClientid("292167");
		message.setPsessionid(this.psessionid);
		message.setContent(msgParam);
		String msg = new JSONObject(message).toString();
		post.setParameter("r", msg);
		post.setParameter("clientid", "292167");
		post.setParameter("psessionid", this.psessionid);
		WebResponse rs;
		
		//wc.putCookie("pgv_pvid", "6477164270");
		//wc.putCookie("pgv_flv", "10.1 r102");
		//wc.putCookie("pgv_info", "pgvReferrer=&ssid=s6494109392");
		wc.setHeaderField("Referer", "http://d.web2.qq.com/proxy.html?v=20101025002");
		
		rs = wc.getResponse(post);
		log.info("sendMsg response:" + rs.getText());
	}
	
}
