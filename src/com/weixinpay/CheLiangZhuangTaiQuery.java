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
public class CheLiangZhuangTaiQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PayService payService = new PayService();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheLiangZhuangTaiQuery() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 * 名称		类型		必填	说明
 	 * key		string	是	您申请的key
 	 * number	string	是	车牌号码
 	 * type		string	否	车辆类型，默认02(小型车)，详见数据字典附录1
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String useAppId = request.getParameter("useAppId");
		String number = request.getParameter("number");
		String type = request.getParameter("type");
		String orderId = request.getParameter("orderId");
		/*******查询用户是否支付成功，成功后才进行查询*******/
		boolean isOrderFirstQuery = payService.isOrderFirstQuery(orderId);
		/*******查询用户是否支付成功，成功后才进行查询*******/
		if(isOrderFirstQuery){
			OrderInfo order = payService.getQueryOrderByorderId(orderId);
			String result = order.getQueryCondition();
			System.out.println(result);
	        response.getWriter().append(result);
			/*String url = QueryAppKeyLib.cheliangzhuangtaiQueryUrl+"key="+QueryAppKeyLib.cheliangzhuangtaiQueryAppKey+"&number"+number;
			if(!StringUtil.isEmpty(type)){
				url = url+"&type="+type;
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
