package com.ksk;

import com.google.gson.annotations.SerializedName;

public class StartData {
	@SerializedName("serverinfo")
	private String serverInfo;
	@SerializedName("dndata")
	private String dnData;

	public String getServerInfo() {
		return this.serverInfo;
	}
	public String getDnData() { return this.dnData; }
}
