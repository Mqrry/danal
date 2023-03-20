package com.ksk.danal;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class StartData {
	@JsonSetter("serverinfo")
	private String serverInfo;
	@JsonSetter("dndata")
	private String dnData;

	public String getServerInfo() {
		return this.serverInfo;
	}
	public String getDnData() { return this.dnData; }
}
