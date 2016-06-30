package com.dh.superxz_bottom.event;

public class ApptestEvent {

	public static final int TEST = 1;
	private int type;
	private String str;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public ApptestEvent(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
}
