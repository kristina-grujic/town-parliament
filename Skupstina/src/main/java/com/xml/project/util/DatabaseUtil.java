package com.xml.project.util;

import com.marklogic.client.DatabaseClientFactory.Authentication;

public class DatabaseUtil {

	private String host = "192.168.1.2";

	private String username = "kristina";

	private int port = 8000;

	private String password = "password";

	private String database = "xmldb";

	private String auth_type = "digest";

	private Authentication authType;

	public DatabaseUtil() {

		// set the autenticiaton method
		this.authType = Authentication.valueOf(this.auth_type.toUpperCase().trim());

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}

	public String getAuth_type() {
		return auth_type;
	}

	public void setAuth_type(String auth_type) {
		this.auth_type = auth_type;
	}

	public Authentication getAuthType() {
		return authType;
	}

	public void setAuthType(Authentication authType) {
		this.authType = authType;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

}
