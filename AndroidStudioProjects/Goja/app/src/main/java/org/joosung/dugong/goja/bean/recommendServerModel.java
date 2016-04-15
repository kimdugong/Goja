package org.joosung.dugong.goja.bean;

import java.io.Serializable;

public class recommendServerModel implements Serializable{
	
	private static final long serialVersionUID = 1050450872;
	
	private String member_id;
	private String prodect_id;
	private String recommend_date;
	private int Functionstate;
	
	public String getRecommend_date() {
		return recommend_date;
	}

	public void setRecommend_date(String recommend_date) {
		this.recommend_date = recommend_date;
	}

	public recommendServerModel(){}
	
	public recommendServerModel(recommendTableModel rtm){
		setMember_id(rtm.getMember_id());
		setProdect_id(rtm.getProdect_id());
	}	
	
	public int getFunctionstate() {
		return Functionstate;
	}
	public void setFunctionstate(int functionstate) {
		Functionstate = functionstate;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getProdect_id() {
		return prodect_id;
	}
	public void setProdect_id(String prodect_id) {
		this.prodect_id = prodect_id;
	}

	public void setCol(recommendTableModel rtm){
		setMember_id(rtm.getMember_id());
		setProdect_id(rtm.getProdect_id());
	}	
}
