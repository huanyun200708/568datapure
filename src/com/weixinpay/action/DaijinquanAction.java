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
import com.weixinpay.service.DaijinquanService;

public class DaijinquanAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DaijinquanAction.class);
	private DaijinquanService service = new DaijinquanService();
	public void getDaijinquansByOpenId(){
		HttpServletRequest reguest= super.getRequest();
		String openId = reguest.getParameter("openId");
		logger.info(openId+" start getDaijinquansByOpenId...");
		List<JSONObject> json= service.getDaijinquansByOpenId(openId);
		
		if(json != null){
			responseWriter(JsonUtils.toJson(json));
		}else{
			responseWriter("{\"success\":false,\"message\":\"result is null\"}");
		}
	}
	
	public void shareDaijinquan(){
			try {
			HttpServletRequest reguest= super.getRequest();
			String openId = reguest.getParameter("openId");
			logger.info(openId+" start share...");
			
			DaijinquanShare share = new DaijinquanShare();
			share.setFrom_openid(openId);
			share.setShare_success("1");
			String voucher_code = reguest.getParameter("voucher_code");
			share.setVoucher_code(voucher_code);
			boolean result= service.insertDaijinquanShare(share);
			
			if(result){
				logger.info(openId+"  share sucess and voucher_code : "+voucher_code);
				responseWriter("{\"success\":true,\"message\":\"share success\",\"voucher_code\":\""+voucher_code+"\"}");
			}else{
				responseWriter("{\"success\":false,\"message\":\"share failed\"}");
			}
			} catch (Exception e) {
	        	logger.info(StringUtil.errInfo(e));
	        	responseWriter("{\"success\":false,\"message\":\"share failed\"}");
	        }
	}
	
	public void acceptShareDaijinquan(){
		try {
			HttpServletRequest reguest= super.getRequest();
			String fromopenId = reguest.getParameter("fromopenId");
			String toopenId = reguest.getParameter("toopenId");
			String voucher_code = reguest.getParameter("voucher_code");
			logger.info(toopenId+" accept share start...");
			if(fromopenId.equals(toopenId)){
				responseWriter("{\"success\":false,\"message\":\"fromopenId equals toopenId\"}");
				return;
			}
			List<JSONObject> json= service.getSharedDaijinquans(fromopenId, toopenId);
			if(json.size()>0){
				logger.error(fromopenId+" has get shareDaijinquan");
				responseWriter("{\"success\":false,\"message\":\"has get shareDaijinquan\"}");
				return;
			}
			
			DaijinquanShare share = new DaijinquanShare();
			share.setFrom_openid(fromopenId);
			share.setTo_openid(toopenId);
			share.setShare_success("1");
			share.setAccept_success("1");
			share.setVoucher_code(voucher_code);
			boolean result= service.updateDaijinquanShare(share);
			
			if(result){
				logger.info(toopenId+" accept "+fromopenId+ "share");
				Daijinquan daijinquan = new Daijinquan();
				daijinquan.setOpen_id(fromopenId);
				daijinquan.setVoucher_code(voucher_code);
				daijinquan.setVoucher_fee("100");
				
				DateFormat format1 = new SimpleDateFormat("yyyyMMdd");
				Date d1 = new Date();
				Calendar calendar1 = Calendar.getInstance();
				calendar1.setTime(d1);
				calendar1.add(Calendar.DAY_OF_YEAR, 7);
				
				daijinquan.setStart_date(format1.format(d1));
				daijinquan.setEnd_date(format1.format(calendar1.getTime()));
				service.insertDaijinquan(daijinquan);
				responseWriter("{\"success\":true,\"message\":\"accept share success\"}");
			}else{
				responseWriter("{\"success\":false,\"message\":\"accept share failed\"}");
			}
        } catch (Exception e) {
        	logger.info(StringUtil.errInfo(e));
        	responseWriter("{\"success\":false,\"message\":\"share failed\"}");
        }
		
	}
}
