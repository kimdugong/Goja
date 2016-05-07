package org.joosung.dugong.goja.bean;

import java.io.Serializable;

public class memberServerModel implements Serializable{

	private static final long serialVersionUID = 1050450871;
	private String id;
	private String pass;
	private String name;
	private String birthdate;
	private String sex;
	private String phone;
	private String email;
	private String grade="0";
	private String joindate=new java.text.SimpleDateFormat("yyyy-M-d").format(new java.util.Date());

	private int FunctionState = 0;

	public memberServerModel(){}
	public memberServerModel(memberTableModel mtm){
		setBirthdate(mtm.getBirthdate());
		setEmail(mtm.getEmail());
		setGrade(mtm.getGrade());
		setId(mtm.getId());
		setJoindate(mtm.getJoindate());
		setName(mtm.getName());
		setPass(mtm.getPass());
		setPhone(mtm.getPhone());
		setSex(mtm.getSex());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getJoindate() {
		return joindate;
	}

	public void setJoindate(String joindate) {
		this.joindate = joindate;
	}

	public int getFunctionState() {
		return FunctionState;
	}

	public void setFunctionState(int functionState) {
		FunctionState = functionState;
	}

	public void setData(memberTableModel mtm){
		setBirthdate(mtm.getBirthdate());
		setEmail(mtm.getEmail());
		setGrade(mtm.getGrade());
		setId(mtm.getId());
		setJoindate(mtm.getJoindate());
		setName(mtm.getName());
		setPass(mtm.getPass());
		setPhone(mtm.getPhone());
		setSex(mtm.getSex());
	}


}
