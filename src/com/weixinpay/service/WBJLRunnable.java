package com.weixinpay.service;

import org.apache.log4j.Logger;
import cn.com.hq.util.StringUtil;
import com.weixinpay.model.OrderInfoView;
import com.weixinpay.model.WXJL;

public class WBJLRunnable  implements Runnable {
    private String orderId = "";
	private PayService payService = new PayService();   
	private static Logger logger = Logger.getLogger(WBJLRunnable.class);
    @Override
    public void run() {
        	try {
        		Thread.sleep(10000);
        		OrderInfoView orderView = payService.getQueryOrderViewByorderId(orderId);
    			String queryType = orderView.getQueryType();
    			//样例数据querycondition:&vin=LHGGK5831F2005506&enginno=1005567
    			String querycondition = orderView.getQuerycondition();
    			String ordercontent = orderView.getContent();
    			String content = "用户：" + orderView.getUserid() + "	\n";
    			logger.info("orderid:"+orderId+"----querycondition:"+querycondition+"----ordercontent:"+ordercontent+"----paytime:"+orderView.getPaytime());
    			if ("ZJHY".equals(queryType)) {
    				content = content + "升级为中级会员	\n";
    			}else if("GJHY".equals(queryType)){
    				content = content + "升级为高级会员	\n";
    			}else if("CLZT".equals(queryType)){
    				content = content + "车辆状态查询	\n";
    			}else if("BYJL".equals(queryType)){
    				content = content + "维保信息查询	\n";
    				if(StringUtil.isEmpty(ordercontent)){
    					logger.info("用户付款后没有执行查询，所以进行第二次查询。。。");
    					String[] conditions = querycondition.split("&");
    					String licenseplate = null;
    					String enginno = null;
    					String vin = null;
    					if(conditions.length>=2 && !StringUtil.isEmpty(conditions[1])){
    						vin = conditions[1].replace("vin=", "");
    					}
    					if(conditions.length>=3 && !StringUtil.isEmpty(conditions[2])){
    						vin = conditions[2].replace("enginno=", "");
    					}
    					String result = WXJL.executeQuery(orderId, vin, enginno, licenseplate);
    					logger.info("维保信息再次查询结果:"+result);
    				}
    				
    			}else if("CXJL".equals(queryType)){
    				content = content + "出险信息查询	\n";
    			}else if("TBXX".equals(queryType)){
    				content = content + "投保信息查询	\n";
    			}
    			content = content + "支付金额：" + (orderView.getFee()*1.00/100) + "元";
    			logger.info("-------支付信息:"+content);
    			payService.insertWXMessage(orderView,content);
        	} catch (Exception e) {
    			e.printStackTrace();
    			logger.error(StringUtil.errInfo(e));
    		}
    }
    
    
	public WBJLRunnable(String orderId) {
		super();
		this.orderId = orderId;
	}


	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
    
    
}
