package com.weixinpay.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.hq.dao.Dao;

import com.alibaba.fastjson.JSONObject;
import com.weixinpay.common.RandomStringGenerator;
import com.weixinpay.model.Daijinquan;
import com.weixinpay.model.DaijinquanShare;

public class DaijinquanService {
	private static Logger logger = Logger.getLogger(DaijinquanService.class);
	private Dao dao = new Dao();
	
	public List<JSONObject> getDaijinquansByOpenId(String openid){
		 List<JSONObject> results = new ArrayList<JSONObject>();
		String sql = "SELECT `voucher_code`,`voucher_fee`,`start_date`,`end_date`,`open_id` FROM `voucher` where open_id=? ORDER BY end_date";
		Connection connection =  dao.getDBConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, openid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				JSONObject json = new JSONObject();
				String voucher_code = rs.getString(1);
				String voucher_fee = rs.getString(2);
				String start_date = rs.getString(3);
				String end_date = rs.getString(4);
				
				DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
				DateFormat format2  = new SimpleDateFormat("yyyy年MM月dd日");
				
				Date d1 = format1.parse(start_date);
				start_date = format2.format(d1);
				Date d2 = format1.parse(end_date);
				end_date = format2.format(d2);
				
				Calendar calendar1 = Calendar.getInstance();
				Calendar calendar2 = Calendar.getInstance();
				Calendar nowcalendar = Calendar.getInstance();
				calendar1.setTime(d1);
				calendar2.setTime(d2);
				
				if(nowcalendar.after(calendar2)){
					continue;
				}
				
				json.put("voucher_code", voucher_code);
				json.put("voucher_fee", voucher_fee);
				json.put("start_date", start_date);
				json.put("end_date", end_date);
				
				results.add(json);
		    }
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public boolean insertDaijinquan(Daijinquan daijinquan){
		boolean result = false;
		String sql = "INSERT INTO voucher (`voucher_code`,`voucher_fee`,`start_date`,`end_date`,`open_id`) VALUES (?,?,?,?,?)";
		Connection connection =  dao.getDBConnection();
		PreparedStatement  ps;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, daijinquan.getVoucher_code());
			ps.setString(2,daijinquan.getVoucher_fee());
			ps.setString(3, daijinquan.getStart_date());
			ps.setString(4, daijinquan.getEnd_date());
			ps.setString(5,daijinquan.getOpen_id());
			result = ps.executeUpdate() > 0;
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<JSONObject> getSharedDaijinquans(String fromopenid,String toopenid){
		 List<JSONObject> results = new ArrayList<JSONObject>();
		String sql = "SELECT from_openid,to_openid,share_success,accept_success,voucher_code FROM `user_share` where from_openid=? and to_openid=?";
		Connection connection =  dao.getDBConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, fromopenid);
			ps.setString(2, toopenid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				JSONObject json = new JSONObject();
				String from_openid = rs.getString(1);
				String to_openid = rs.getString(2);
				String share_success = rs.getString(3);
				String accept_success = rs.getString(4);
				String voucher_code = rs.getString(5);
				
				json.put("from_openid", from_openid);
				json.put("to_openid", to_openid);
				json.put("share_success", share_success);
				json.put("accept_success", accept_success);
				json.put("voucher_code", voucher_code);
				results.add(json);
		    }
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public boolean insertDaijinquanShare(DaijinquanShare share){
		boolean result = false;
		String sql = "INSERT INTO user_share (from_openid,to_openid,share_success,accept_success,voucher_code) VALUES (?,?,?,?,?)";
		Connection connection =  dao.getDBConnection();
		PreparedStatement  ps;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, share.getFrom_openid());
			ps.setString(2, share.getTo_openid());
			ps.setString(3, share.getShare_success());
			ps.setString(4, share.getAccept_success());
			ps.setString(5, share.getVoucher_code());
			result = ps.executeUpdate() > 0;
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public boolean updateDaijinquanShare(DaijinquanShare share) {
		boolean result = false;
		String sql = "update user_share set from_openid=?,to_openid=?,share_success=?,accept_success=? where voucher_code=?";
		Connection connection =  dao.getDBConnection();
		PreparedStatement  ps;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, share.getFrom_openid());
			ps.setString(2, share.getTo_openid());
			ps.setString(3, share.getShare_success());
			ps.setString(4, share.getAccept_success());
			ps.setString(5, share.getVoucher_code());
			result = ps.executeUpdate() > 0;
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
