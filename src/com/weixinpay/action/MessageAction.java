package com.weixinpay.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import cn.com.hq.util.JsonUtils;
import cn.com.hq.util.StringUtil;

import com.alibaba.fastjson.JSONObject;
import com.weixinpay.model.Daijinquan;
import com.weixinpay.model.DaijinquanShare;
import com.weixinpay.model.Message;
import com.weixinpay.service.MessageService;

public class MessageAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(MessageAction.class);
	private MessageService service = new MessageService();
	public void getMessages(){
		HttpServletRequest reguest= super.getRequest();
		String openId = reguest.getParameter("openId");
		List<JSONObject> json= service.getMessageByOpenId(openId);
		
		if(json != null){
			responseWriter(JsonUtils.toJson(json));
		}else{
			responseWriter("{\"success\":false,\"message\":\"result is null\"}");
		}
	}
	
	public void addMessage(){
			try {
			HttpServletRequest reguest= super.getRequest();
			String openId = reguest.getParameter("openId");
			String message = reguest.getParameter("message");
			logger.info(openId+" start addMessage...");
			
			Message m = new Message();
			m.setMessage(message);
			m.setOpenid(openId);;
			boolean result= service.insertMessage(m);
			
			if(result){
				logger.info(openId+"  has new Message:"+message);
				responseWriter("{\"success\":true,\"message\":\"addMessage success\"}");
			}else{
				responseWriter("{\"success\":false,\"message\":\"addMessage failed\"}");
			}
			} catch (Exception e) {
	        	logger.info(StringUtil.errInfo(e));
	        	responseWriter("{\"success\":false,\"message\":\"addMessage failed\"}");
	        }
	}
	
	public void deleteMessage(){
		try {
		HttpServletRequest reguest= super.getRequest();
		String id = reguest.getParameter("id");
		logger.info(" start deleteMessage...");
		
		Message m = new Message();
		m.setId(Long.valueOf(id));
		boolean result= service.insertMessage(m);
		
		if(result){
			logger.info("deleteMessage id:"+id);
			responseWriter("{\"success\":true,\"message\":\"deleteMessage success\"}");
		}else{
			responseWriter("{\"success\":false,\"message\":\"deleteMessage failed\"}");
		}
		} catch (Exception e) {
        	logger.info(StringUtil.errInfo(e));
        	responseWriter("{\"success\":false,\"message\":\"deleteMessage failed\"}");
        }
}
}
