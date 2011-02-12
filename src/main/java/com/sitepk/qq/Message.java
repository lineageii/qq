package com.sitepk.qq;

public class Message {
	/** 收信人ID */
	private String to = "237365307";
	private String face = "669";
	private String content = "";
	private String msg_id ="22900005";
	private String clientid;
	private String psessionid;
	//{"to":237365307,"face":669,"content":"[\"c\",[\"font\",{\"name\":\"宋体\",\"size\":\"10\",\"style\":[0,0,0],\"color\":\"000000\"}]]","msg_id":22900004,"clientid":"88290700","psessionid":"8368046764001e636f6e6e7365727665725f7765627171403137322e32372e3138312e383500006192000001f4036203c2af996d0000000a404e63676f41676673536d000000281c18a9266a4af38cd09c397abe0c22983dc08effac89541cf97511f6798299577f42b9dbc5586767"}
	//{"to":237365307,"face":669,"content":"[\"1\",[\"font\",{\"name\":\"宋体\",\"size\":\"10\",\"style\":[0,0,0],\"color\":\"000000\"}]]","msg_id":22900007,"clientid":"88290700","psessionid":"8368046764001e636f6e6e7365727665725f7765627171403137322e32372e3138312e383500006192000001f4036203c2af996d0000000a404e63676f41676673536d000000281c18a9266a4af38cd09c397abe0c22983dc08effac89541cf97511f6798299577f42b9dbc5586767"}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = "[\"" + content +"\",[\"font\",{\"name\":\"宋体\",\"size\":\"10\",\"style\":[0,0,0],\"color\":\"000000\"}]]";;
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
