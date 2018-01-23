package com.weixinpay;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.com.hq.util.QueryAppKeyLib;
import cn.com.hq.util.SSLUtil;
import cn.com.hq.util.StringUtil;

import com.weixinpay.common.Configure;
import com.weixinpay.model.OrderInfo;
import com.weixinpay.service.PayService;

/**
 * Servlet implementation class GetOpenId
 */
public class TouBaoXinXiQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PayService payService = new PayService();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TouBaoXinXiQuery() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 名称				类型		必填	说明
 	 * licenseNo		String	否	车牌号（字母全部大写）
 	 * carVin			String	否	车架号，一般次新车多用这俩参数，此时车牌号非必填 （新车上保险的时候是没有车牌号的，是根据车架号和发动机号上的保险，第二次上保险的时候就叫次新车，这时候需要用车架号和发动机号才能带出来续保信息）
 	 * engineNo			String	否	发动机号
 	 * renewalCarType	Int		否	0小车，1大车，默认0
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String useAppId = request.getParameter("useAppId");
		String licenseNo = request.getParameter("licenseNo");
		String carVin = request.getParameter("carVin");
		String engineNo = request.getParameter("engineNo");
		String renewalCarType = request.getParameter("renewalCarType");
		String orderId = request.getParameter("orderId");
		/*******查询用户是否支付成功，成功后才进行查询*******/
		boolean isOrderFirstQuery = payService.isOrderFirstQuery(orderId);
		/*******查询用户是否支付成功，成功后才进行查询*******/
		if(isOrderFirstQuery){
			OrderInfo order = payService.getQueryOrderByorderId(orderId);
			String result = order.getQueryCondition();
			System.out.println(result);
	        response.getWriter().append(result);
			/*String url = QueryAppKeyLib.toubaoxinxiQueryUrl+"key="+QueryAppKeyLib.toubaoxinxiQueryAppKey;
			if(!StringUtil.isEmpty(licenseNo)){
				url = url+"&licenseNo="+licenseNo;
			}
			if(!StringUtil.isEmpty(carVin)){
				url = url+"&carVin="+carVin;
			}
			if(!StringUtil.isEmpty(engineNo)){
				url = engineNo+"&type="+engineNo;
			}
			if(!StringUtil.isEmpty(renewalCarType)){
				url = url+"&renewalCarType="+renewalCarType;
			}
			HttpGet httpGet = new HttpGet(url);
	        //设置请求器的配置
			try {
				HttpClient httpClient = SSLUtil.getHttpClient();
		        HttpResponse res = httpClient.execute(httpGet);
		        HttpEntity entity = res.getEntity();
		        String result = EntityUtils.toString(entity, "UTF-8");
		        System.out.println(result);
		        response.getWriter().append(result);
			} catch (Exception e) {
				e.printStackTrace();
			}*/
		}else{
			response.getWriter().append("\"error\":\"has queried\"");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
