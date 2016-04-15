package org.joosung.dugong.goja.bean;

public class recommendTableModel {

	private String member_id;
	private String prodect_id;
	private String recommend_date;
	
	public String getRecommend_date() {
		return recommend_date;
	}
	public void setRecommend_date(String recommend_date) {
		this.recommend_date = recommend_date;
	}
	public recommendTableModel(){}
	public recommendTableModel(recommendServerModel rsm){
		setMember_id(rsm.getMember_id());
		setProdect_id(rsm.getProdect_id());
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
	
	public void setCol(recommendServerModel rsm){
		setMember_id(rsm.getMember_id());
		setProdect_id(rsm.getProdect_id());
	}
	
}
