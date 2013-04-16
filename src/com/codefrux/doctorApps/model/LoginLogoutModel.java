package com.codefrux.doctorApps.model;

public class LoginLogoutModel {
   private	String Id,Doctor_name,PassWord,Login_date,Login_time,Logout_date,Logout_time;

	public String getId() {
		return Id;
	}

	public String getPassWord() {
		return PassWord;
	}

	public void setPassWord(String passWord) {
		PassWord = passWord;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getDoctor_name() {
		return Doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		Doctor_name = doctor_name;
	}

	public String getLogin_date() {
		return Login_date;
	}

	public void setLogin_date(String login_date) {
		Login_date = login_date;
	}

	public String getLogin_time() {
		return Login_time;
	}

	public void setLogin_time(String login_time) {
		Login_time = login_time;
	}

	public String getLogout_date() {
		return Logout_date;
	}

	public void setLogout_date(String logout_date) {
		Logout_date = logout_date;
	}

	public String getLogout_time() {
		return Logout_time;
	}

	public void setLogout_time(String logout_time) {
		Logout_time = logout_time;
	}

}
