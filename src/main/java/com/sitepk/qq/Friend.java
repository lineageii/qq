package com.sitepk.qq;

public class Friend {
	private String uin;
	private String ruin;

	public String getUin() {
		return uin;
	}

	public Friend setUin(String uin) {
		this.uin = uin;
		return this;
	}

	public String getRuin() {
		return ruin;
	}

	public Friend setRuin(String ruin) {
		this.ruin = ruin;
		return this;
	}

	@Override
	public String toString() {
		return "Friend [uin=" + uin + ", ruin=" + ruin + "]";
	}

}
