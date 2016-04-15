package org.joosung.dugong.goja.bean;

import java.io.Serializable;

public class memberTableModel{
	
	private String id; 
	private String pass;
	private String name;
	private String birthdate;
	private int sex;
	private String	phone;
	private String email;
	private int grade;
	private String joindate;
		
	public memberTableModel(memberServerModel msm){
		setBirthdate(msm.getBirthdate());
		setEmail(msm.getEmail());
		setGrade(msm.getGrade());
		setId(msm.getId());
		setJoindate(msm.getJoindate());
		setName(msm.getName());
		setPass(msm.getPass());
		setPhone(msm.getPhone());
		setSex(msm.getSex());
	}
	public memberTableModel(){}
	
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
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
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
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getJoindate() {
		return joindate;
	}
	public void setJoindate(String joindate) {
		this.joindate = joindate;
	}
	
	public void setColume(memberServerModel msm){
		setBirthdate(msm.getBirthdate());
		setEmail(msm.getEmail());
		setGrade(msm.getGrade());
		setId(msm.getId());
		setJoindate(msm.getJoindate());
		setName(msm.getName());
		setPass(msm.getPass());
		setPhone(msm.getPhone());
		setSex(msm.getSex());
	}
	
}
