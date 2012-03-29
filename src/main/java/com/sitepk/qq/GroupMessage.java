package com.sitepk.qq;

public class GroupMessage {
	private long group_uin= 0;
	private String content = "";
	private int msg_id =67680003;
	private String clientid;
	private String psessionid;
	//{"group_uin":3056908941,"content":"[\"test\",\"\\n【提示：此用户正在使用Q+ Web：http://web2.qq.com/】\",[\"font\",{\"name\":\"宋体\",\"size\":\"10\",\"style\":[0,0,0],\"color\":\"000000\"}]]","msg_id":52380003,"clientid":"50238780","psessionid":"8368046764001e636f6e6e7365727665725f77656271714031302e3133332e34312e3230320000147c000003f5016e040091df6a726d0000000a404f6444454237534e5a6d000000289a636e944da5e71ca84194252dcef029b4c54a3da8e0d0d90ca24783fc6db168825cc4f83959fb5a"}
	//{"group_uin":3056908941,"content":"[\"test\",\"\\n【提示：此用户正在使用Q+ Web：http://web2.qq.com/】\",[\"font\",{\"name\":\"宋体\",\"size\":\"10\",\"style\":[0,0,0],\"color\":\"000000\"}]]","msg_id":67680002,"clientid":"64768904","psessionid":"8368046764001e636f6e6e7365727665725f77656271714031302e3133332e34312e323032000014ad000003ed016e040091df6a726d0000000a404f6444454237534e5a6d0000002802a03f9e716c2defc3f1d88ada45fc0111241b79511972d72c7b8b0116f128c46b43223eb90493e0"}
	//{"group_uin":3056908941,"content":"[\"test\",\"\\n【提示：此用户正在使用Q+ Web：http://web2.qq.com/】\",[\"font\",{\"name\":\"宋体\",\"size\":\"10\",\"style\":[0,0,0],\"color\":\"000000\"}]]","msg_id":67680001,"clientid":"64768904","psessionid":"8368046764001e636f6e6e7365727665725f77656271714031302e3133332e34312e32303200004c0f000003eb016e040091df6a726d0000000a404f6444454237534e5a6d000000287928cd44eff8a019976292a6f9a9780ef6c1dbdf7a1481132742b5145b9ea2f86785031191662ba9"}
	//{"group_uin":419781577,"content":"[\"test\",\"\\n【提示：此用户正在使用WebQQ：http://web2.qq.com/】\",[\"font\",{\"name\":\"宋体\",\"size\":\"10\",\"style\":[0,0,0],\"color\":\"000000\"}]]","msg_id":53820001,"clientid":"35382267","psessionid":"8368046764001e636f6e6e7365727665725f7765627171403137322e32372e3138312e3835000041c3000006b9036e0400cff54f726d0000000a4078634e623274354f336d00000028af267c138f85c8a4fa3439bdcc9ec386b52013efbf2e657eb494cdb721e032d1ea8bf5e093a931c0"}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = "[\"" + content +"\",\"\\n\",[\"font\",{\"name\":\"宋体\",\"size\":\"10\",\"style\":[0,0,0],\"color\":\"000000\"}]]";;
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
	public int getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(int msg_id) {
		this.msg_id = msg_id;
	}
	public long getGroup_uin() {
		return group_uin;
	}
	public void setGroup_uin(long group_uin) {
		this.group_uin = group_uin;
	}

}
