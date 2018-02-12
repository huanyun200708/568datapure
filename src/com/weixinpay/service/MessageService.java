package com.weixinpay.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.com.hq.dao.Dao;

import com.alibaba.fastjson.JSONObject;
import com.weixinpay.model.Message;

public class MessageService {
	private static Logger logger = Logger.getLogger(MessageService.class);
	private Dao dao = new Dao();
	
	public List<JSONObject> getMessageByOpenId(String openid){
		 List<JSONObject> results = new ArrayList<JSONObject>();
		String sql = "SELECT id, `openid`,`message` FROM `voucher` where (open_id=? or open_id='ALL')";
		Connection connection =  dao.getDBConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, openid);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				JSONObject json = new JSONObject();
				Long id = rs.getLong(1);
				String openid1 = rs.getString(2);
				String message = rs.getString(3);
				
				json.put("id", id);
				json.put("openid", openid1);
				json.put("message", message);
				
				results.add(json);
		    }
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}
	
	public boolean insertMessage(Message m){
		boolean result = false;
		String sql = "INSERT INTO message (`openid`,`message`) VALUES (?,?)";
		Connection connection =  dao.getDBConnection();
		PreparedStatement  ps;
		try {
			ps = connection.prepareStatement(sql);
			ps.setString(1, m.getOpenid());
			ps.setString(2,m.getOpenid());
			result = ps.executeUpdate() > 0;
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	public boolean deleteMessage(Message m) {
		boolean result = false;
		String sql = "DELETE FROM message where id=?";
		Connection connection =  dao.getDBConnection();
		PreparedStatement  ps;
		try {
			ps = connection.prepareStatement(sql);
			ps.setLong(1, m.getId());
			result = ps.executeUpdate() > 0;
			dao.closeStatement(ps);
			Dao.releaseConnection(connection);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
