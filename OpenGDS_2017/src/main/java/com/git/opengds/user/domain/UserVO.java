package com.git.opengds.user.domain;

public class UserVO {
	
	public enum EnUserType {
		GENERAL("general",0);

		private final String typeName;
		private final int auth;

		private EnUserType(String typeName, int auth) {
			this.typeName = typeName;
			this.auth = auth;
		}

		public static EnUserType get(String typeName) {
			for (EnUserType type : values()) {
				if(type.typeName.equals(typeName))
					return type;
			}
			return null;
		}
		
		
		public String getTypeName(){
			return this.typeName;
		}
		
		public int getAuth(){
			return this.auth;
		}
	};
	
	private String uid;
	private String id;
	private String pw;
	private int auth;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public int getAuth() {
		return auth;
	}
	public void setAuth(int auth) {
		this.auth = auth;
	}
}
