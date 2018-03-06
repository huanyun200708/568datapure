package com.weixinpay;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.hq.util.StringUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.weixinpay.common.StreamUtil;
import com.weixinpay.model.PayResultBean;
import com.weixinpay.service.PayService;
import com.weixinpay.service.WBJLRunnable;

/**
 * 接收支付结果
 */
public class PayResult extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger L = Logger.getLogger(PayResult.class);
	private PayService payService = new PayService();   
	private static Logger logger = Logger.getLogger(PayResult.class);   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayResult() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String reqParams = StreamUtil.read(request.getInputStream());
		L.info("-------支付结果:"+reqParams);
		System.out.println("-------支付结果:"+reqParams);
		try {
			XStream xStream = new XStream(new DomDriver());
			xStream.alias("xml", PayResultBean.class); 
			PayResultBean  order = (PayResultBean)xStream.fromXML(reqParams);
			StringBuffer sb = new StringBuffer("<xml><return_code>SUCCESS</return_code><return_msg>OK</return_msg></xml>");
			response.getWriter().append(sb.toString());
			List<Map<String,String>> messageList = payService.getWXMessageByorderId(order.getOut_trade_no());
			if(messageList.size()>0){
				return;
			}
			
			Runnable myRunnable = new WBJLRunnable(order.getOut_trade_no());
			Thread thread1 = new Thread(myRunnable);
			thread1.start();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(StringUtil.errInfo(e));
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
