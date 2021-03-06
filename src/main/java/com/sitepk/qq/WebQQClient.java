package com.sitepk.qq;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

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
public class WebQQClient {
	private Log log = LogFactory.getLog(this.getClass());
	private String id;
	private String uid;
	private String password;

	private String psessionid;

	private String vfwebqq;

	private WebConversation wc;

	private Map<String, Friend> firendsRuid = new HashMap<String, Friend>();
	private Map<String, Friend> firendsUid = new HashMap<String, Friend>();

	public WebQQClient() {
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

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public String toString() {
		return "id:" + this.id + " password:" + this.password;
	}

	private void setCookie(WebResponse rs) {
		String[] cookies = rs.getHeaderFields("set-cookie");
		for (String cookie : cookies) {
			String firstCookie = cookie.split(";")[0];
			String[] cookieStr = firstCookie.split("=");
			if (cookieStr.length == 2) {
				wc.putCookie(cookieStr[0], cookieStr[1]);
			} else {
				wc.putCookie(cookieStr[0], "");
			}

		}
	}

	public void login() throws Exception {
		wc = new WebConversation();
		//wc.setProxyServer("127.0.0.1", 8888);
		HttpUnitOptions.setDefaultCharacterSet("utf-8");
		HttpUnitOptions.setScriptingEnabled(false);
		HttpUnitOptions.setExceptionsThrownOnScriptError(false);
		//HttpUnitOptions.setLoggingHttpHeaders(true);
		HttpUnitOptions.setAcceptCookies(true);
		wc.setUserAgent("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.83 Safari/535.11");
		ClientProperties.getDefaultProperties().setAcceptCookies(true);

		WebResponse rs;
		//rs= wc.getResponse("http://web2.qq.com/");
		//setCookie(rs);

		String url = "http://ptlogin2.qq.com/check?uin=" + this.id + "&appid=1003903&r=0.2842747748363763";
		rs = wc.getResponse(url);
		setCookie(rs);
		log.info("response:" + rs.getText());
		String verifycode = rs.getText().split(",")[1].replaceAll("'", "").replace(");", "");
		log.info(this.toString() + " verifycode:" + verifycode);

		// 需要输入验证码
		if (verifycode.length() > 10) {
			log.error("需要验证码!");
			log.info("验证码地址:" + "http://captcha.qq.com/getimage?aid=1003903&r=0.3318515357095748&uin=" + this.id);
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			verifycode = stdin.readLine();
			//throw new Exception("需要验证码!");
		}

		String p = PasswordEncrypt.passwordEcrypt(this.password, verifycode);
		log.info("p:" + p);;

		GetMethodWebRequest get = new GetMethodWebRequest("http://ptlogin2.qq.com/login");
		get.setParameter("u", this.id);
		get.setParameter("p", p);
		get.setParameter("verifycode", verifycode);
		get.setParameter("webqq_type", "10");
		get.setParameter("remember_uin", "1");
		get.setParameter("login2qq", "1");
		get.setParameter("aid", "1003903");
		get.setParameter("u1", "http://web2.qq.com/loginproxy.html?login2qq=1&webqq_type=10");
		get.setParameter("h", "1");
		get.setParameter("ptredirect", "0");
		get.setParameter("ptlang", "2052");
		get.setParameter("from_ui", "1");
		get.setParameter("pttype", "1");
		get.setParameter("dumy", "");
		get.setParameter("fp", "loginerroralert");
		get.setParameter("action", "2-21-6717");
		get.setParameter("mibao_css", "m_webqq");

		rs = wc.getResponse(get);
		setCookie(rs);
		log.info("response:" + rs.getText());

		rs = wc.getResponse("http://web2.qq.com/loginproxy.html?login_level=3");
		setCookie(rs);

		PostMethodWebRequest post = new PostMethodWebRequest("http://d.web2.qq.com/channel/login2");
		String ptwebqq = wc.getCookieValue("ptwebqq");
		log.info("ptwebqq:" + ptwebqq);
		String r = "{\"status\":\"online\",\"ptwebqq\":\"" + ptwebqq
				+ "\",\"passwd_sig\":\"\",\"clientid\":\"64768904\",\"psessionid\":null}";
		//{"status":"online","ptwebqq":"94355c82e5692825e17a1d95c672b6371e71dec45399845b6d3f14fb23557dfb","passwd_sig":"","clientid":"42599326","psessionid":null}
		//{"status":"","ptwebqq":"7896466821f021f41ba65eb83d689e671d7b08eb02c0c165ef8589bb772cf9db","passwd_sig":"","clientid":"64768904","psessionid":null}
		post.setParameter("r", r);

		wc.setHeaderField("Referer", "http://d.web2.qq.com/proxy.html?v=20110331002&callback=2");
		//setCookie(rs);
		//wc.putCookie("pgv_pvid", "215336480");
		//wc.putCookie("pgv_flv", "10.1 r102");
		//wc.putCookie("pgv_info", "pgvReferrer=&ssid=s7792437458");

		rs = wc.getResponse(post);

		log.info("login response:" + rs.getText());
		JSONObject result = new JSONObject(rs.getText()).getJSONObject("result");
		this.psessionid = result.getString("psessionid");
		this.vfwebqq = result.getString("vfwebqq");
		log.info("psessionid:" + psessionid);
	}

	//登陆成功 取QQ好友
	public void getAllFriends() throws Exception {
		String get_user_friends2 = "http://s.web2.qq.com/api/get_user_friends2";
		String get_user_friends = "http://s.web2.qq.com/api/get_user_friends";
		//firends
		Map<String, Friend> friendsMap2 = getFriendMap(getUserFriends(get_user_friends2)); //
		Map<String, Friend> friendsMap = getFriendMap(getUserFriends(get_user_friends)); //真正的QQ号码

		if (friendsMap != null && friendsMap2 != null && friendsMap.size() == friendsMap2.size()) {
			for (String key : friendsMap2.keySet()) {
				Friend friend = friendsMap2.get(key);
				friend.setRuin(friendsMap.get(key).getRuin());
				firendsRuid.put(friend.getRuin(), friend);
				firendsUid.put(friend.getUin(), friend);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Friend> getFriendMap(String result) throws Exception {
		Map<String, Friend> friendMap = new HashMap<String, Friend>(500);
		JSONObject retJson = new JSONObject(result);
		if (retJson.getInt("retcode") == 0) {
			JSONArray infos = retJson.getJSONObject("result").getJSONArray("info");
			for (int i = 0; i < infos.length(); i++) {
				JSONObject obj = infos.getJSONObject(i);
				Friend friend = new Friend().setUin(obj.getString("uin"));
				friendMap.put(obj.getString("nick") + obj.getString("flag"), friend);
			}
		}
		return friendMap;
	}

	public String getUserFriends(String url) throws Exception {
		//String url = "http://s.web2.qq.com/api/get_user_friends2";
		PostMethodWebRequest post = new PostMethodWebRequest(url);
		String r = "{\"h\":\"hello\",\"vfwebqq\":\"" + this.vfwebqq + "\"}";
		post.setParameter("r", r);
		WebResponse rs = wc.getResponse(post);
		log.info("getUserFriends2 response:" + rs.getText());
		return rs.getText();
	}

	public void sendMsg(String to, String msgParam) throws Exception {
		String url = "http://s.web2.qq.com/channel/send_msg2";
		PostMethodWebRequest post = new PostMethodWebRequest(url);
		Message message = new Message();
		message.setTo(firendsRuid.get(to).getUin());
		message.setClientid("64768904");
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
	public void sendGroupMsg(String to, String msgParam) throws Exception {
		String url = "http://d.web2.qq.com/channel/send_qun_msg2";
		PostMethodWebRequest post = new PostMethodWebRequest(url);
		GroupMessage message = new GroupMessage();
		message.setGroup_uin(Long.valueOf(to));
		message.setClientid("64768904");
		message.setPsessionid(this.psessionid);
		message.setContent(msgParam);
		String msg = new JSONObject(message).toString();
		log.info(msg);
		post.setParameter("r", msg);
		post.setParameter("clientid", "64768904");
		post.setParameter("psessionid", this.psessionid);
		WebResponse rs;

		//wc.putCookie("pgv_pvid", "6477164270");
		//wc.putCookie("pgv_flv", "10.1 r102");
		//wc.putCookie("pgv_info", "pgvReferrer=&ssid=s6494109392");
		wc.setHeaderField("Referer", "http://d.web2.qq.com/proxy.html?v=20110331002&callback=2");

		rs = wc.getResponse(post);
		log.info("sendMsg response:" + rs.getText());
	}
	
	@SuppressWarnings("unchecked")
	public List<Group> getAllGroups() throws Exception {
		//{"vfwebqq":"3e99140e076c4dc3c8a774f1e3a37518055f3f39f94428a8eaa07c1f7c0ebfea3a18792878f2c061"}
		
		String url = "http://s.web2.qq.com/api/get_group_name_list_mask2";
		String content = "{\"vfwebqq\":\"" + vfwebqq + "\"}";
		PostMethodWebRequest post = new PostMethodWebRequest(url);
		post.setParameter("r", content);

		wc.setHeaderField("Referer",
				"http://s.web2.qq.com/proxy.html?v=20110412001&callback=1&id=2");

		WebResponse rs = wc.getResponse(post);
		log.info("sendMsg response:" + rs.getText());
		
		List<Group> groups = new ArrayList<Group>();
		
		JSONObject retJson = new JSONObject(rs.getText());
		if (retJson.getInt("retcode") == 0) {
			JSONArray infos = retJson.getJSONObject("result").getJSONArray("gnamelist");
			for (int i = 0; i < infos.length(); i++) {
				JSONObject obj = infos.getJSONObject(i);
				Group group = new Group();
				group.setGid(obj.getString("gid"));
				group.setName(obj.getString("name"));
				group.setCode(obj.getString("code"));
				groups.add(group);
			}
		}
		return groups;
	}


}
