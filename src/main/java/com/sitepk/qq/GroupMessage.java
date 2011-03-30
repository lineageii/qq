package com.sitepk.qq;

public class GroupMessage {
	private String group_uin= "";
	private String content = "";
	private String msg_id ="53820001";
	private String clientid;
	private String psessionid;
	//{"group_uin":419781577,"content":"[\"test\",\"\\n【提示：此用户正在使用WebQQ：http://web2.qq.com/】\",[\"font\",{\"name\":\"宋体\",\"size\":\"10\",\"style\":[0,0,0],\"color\":\"000000\"}]]","msg_id":53820001,"clientid":"35382267","psessionid":"8368046764001e636f6e6e7365727665725f7765627171403137322e32372e3138312e3835000041c3000006b9036e0400cff54f726d0000000a4078634e623274354f336d00000028af267c138f85c8a4fa3439bdcc9ec386b52013efbf2e657eb494cdb721e032d1ea8bf5e093a931c0"}
	public String getContent() {
		return content;
	}
	public String getGroup_uin() {
		return group_uin;
	}
	public void setGroup_uin(String group_uin) {
		this.group_uin = group_uin;
	}
	public void setContent(String content) {
		this.content = "[\"" + content +"\",\"\\n【提示：此用户正在使用QQ群发系统：业务联系QQ:63090585】\",[\"font\",{\"name\":\"宋体\",\"size\":\"10\",\"style\":[0,0,0],\"color\":\"000000\"}]]";;
	}
	public String getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getPsessionid() {
		return psessionid;
	}
	public void setPsessionid(String psessionid) {
		this.psessionid = psessionid;
	}

}
