package com.weixinpay;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.hq.util.StringUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.weixinpay.common.StreamUtil;
import com.weixinpay.model.OrderInfoView;
import com.weixinpay.model.PayResultBean;
import com.weixinpay.model.WXJL;
import com.weixinpay.service.PayService;

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
			Thread.sleep(10000);
			OrderInfoView orderView = payService.getQueryOrderViewByorderId(order.getOut_trade_no());
			String queryType = orderView.getQueryType();
			//querycondition:&vin=LHGGK5831F2005506&enginno=1005567
			String querycondition = orderView.getQuerycondition();
			String ordercontent = orderView.getContent();
			String content = "用户：" + orderView.getUserid() + "	\n";
			L.info("orderid:"+order.getOut_trade_no()+"----querycondition:"+querycondition+"----ordercontent:"+ordercontent+"----paytime:"+orderView.getPaytime());
			if ("ZJHY".equals(queryType)) {
				content = content + "升级为中级会员	\n";
			}else if("GJHY".equals(queryType)){
				content = content + "升级为高级会员	\n";
			}else if("CLZT".equals(queryType)){
				content = content + "车辆状态查询	\n";
			}else if("BYJL".equals(queryType)){
				content = content + "维保信息查询	\n";
				if(StringUtil.isEmpty(ordercontent)){
					L.info("用户付款后没有执行查询，所以进行第二次查询。。。");
					String[] conditions = querycondition.split("&");
					String orderId = order.getOut_trade_no();
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
					L.info("维保信息再次查询结果:"+result);
				}
				
			}else if("CXJL".equals(queryType)){
				content = content + "出险信息查询	\n";
			}else if("TBXX".equals(queryType)){
				content = content + "投保信息查询	\n";
			}
			content = content + "支付金额：" + (orderView.getFee()*1.00/100) + "元";
			L.info("-------支付信息:"+content);
			payService.insertWXMessage(orderView,content);
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
