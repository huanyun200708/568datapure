package com.weixinpay.model;

public class DaijinquanShare {
	private int id;
	private String from_openid;
	private String to_openid;
	private String share_success;
	private String accept_success;
	private String voucher_code;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFrom_openid() {
		return from_openid;
	}
	public void setFrom_openid(String from_openid) {
		this.from_openid = from_openid;
	}
	public String getTo_openid() {
		return to_openid;
	}
	public void setTo_openid(String to_openid) {
		this.to_openid = to_openid;
	}
	public String getVoucher_code() {
		return voucher_code;
	}
	public void setVoucher_code(String voucher_code) {
		this.voucher_code = voucher_code;
	}
	public String getShare_success() {
		return share_success;
	}
	public void setShare_success(String share_success) {
		this.share_success = share_success;
	}
	public String getAccept_success() {
		return accept_success;
	}
	public void setAccept_success(String accept_success) {
		this.accept_success = accept_success;
	}
	
	
}
