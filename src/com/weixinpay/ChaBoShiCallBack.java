package com.weixinpay;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.com.hq.util.JsonUtils;
import cn.com.hq.util.QueryAppKeyLib;

import com.chaboshi.util.CBS;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weixinpay.model.WXJL;
import com.weixinpay.service.PayService;

/**
 * Servlet implementation class GetOpenId
 */
public class ChaBoShiCallBack extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private PayService payService = new PayService();   
	private static Logger logger = Logger.getLogger(ChaBoShiCallBack.class);
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("ChaBoShiCallBack start...............................");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
		String result = request.getParameter("result");
		String message = request.getParameter("message");
		String orderid = request.getParameter("orderid");
		System.out.println("result : "+result + "----message : "+message + "----orderid : "+orderid);
		logger.info("result : "+result + "----message : "+message + "----orderid : "+orderid);
		if(result!=null){
			String callResult = CBS.getInstance(QueryAppKeyLib.baoyangUserId,QueryAppKeyLib.baoyangUserKey).getNewReportJson(orderid);
			System.out.println("callResult:\r\n"+callResult);
			WXJL w = gson.fromJson(callResult, WXJL.class);
			w.translateWBJL(w);
			payService.updateBYJLFinancePayContent(w.getVin(),JsonUtils.toJson(w));
			logger.info("callResult:\r\n"+callResult);
			logger.info("ChaBoShiCallBack end...............................");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public String excutePay(String code){
		return "";
	}
	public static void main(String[] args) {
	  String s = "{\"Code\":1104,\"Message\":\"已出报告\",\"brand\":\"别克\",\"carComponentRecordsFlag\":0,\"carConstructRecordsFlag\":0,\"carFireFlag\":0,\"carOutsideRecordsFlag\":1,\"carType\":\"三厢\",\"carWaterFlag\":0,\"componentAnalyzeRepairRecords\":null,\"constructAnalyzeRepairRecords\":null,\"displacement\":\"1.799\",\"effluentStandard\":null,\"lastMainTainTime\":\"2012-03-22\",\"lastRepairTime\":\"2013-10-11\",\"mainTainTimes\":\"1.6\",\"makeDate\":null,\"makeReportDate\":\"2018-01-24 16:34\",\"manufacturer\":\"上海通用-别克\",\"mileageEstimate\":160775,\"mileageEveryYear\":\"12089\",\"mileageIsNormalFlag\":1,\"modelName\":\"别克SGM7180LE AT轿车\",\"normalRepairRecords\":[{\"content\":\"前杠喷漆;右后门喷漆;\",\"date\":\"2004-04-27\",\"mainTainDate\":1082995200000,\"materal\":null,\"mileage\":960,\"payType\":null,\"remark\":null,\"type\":null},{\"content\":\"前杠喷漆;右后视镜喷漆;后杠喷漆;后杠整形;免费换机油机滤送汽油添加剂;\",\"date\":\"2004-07-08\",\"mainTainDate\":1089216000000,\"materal\":\";汽油添加剂:1;机油:4;机油滤清器 96395221:1;\",\"mileage\":3826,\"payType\":null,\"remark\":null,\"type\":null},{\"content\":\"维修 一般维修;补胎(个);\",\"date\":\"2004-09-16\",\"mainTainDate\":1095264000000,\"materal\":null,\"mileage\":6467,\"payType\":null,\"remark\":null,\"type\":\"维修 一般维修\"},{\"content\":\"维修 一般维修;冬季免费更换机油、机滤、送添加剂(二);\",\"date\":\"2004-10-11\",\"mainTainDate\":1097424000000,\"materal\":\";汽油添加剂:1;机油滤清器 96395221:1;机油(200L):4;\",\"mileage\":7946,\"payType\":null,\"remark\":null,\"type\":\"维修 一般维修\"},{\"content\":\"维修 一般维修;更换三滤、机油;凉车有时CD有时停顿;前杠喷漆;右前叶子板喷漆;右前叶子板钣金;后杠喷漆;后杠钣金;\",\"date\":\"2005-03-15\",\"mainTainDate\":1110816000000,\"materal\":\";机油滤清器:1;机油:4;燃油滤清器:1;空气滤清器过滤单元(总成):1;\",\"mileage\":13168,\"payType\":null,\"remark\":\"凉车有时CD有时停顿;\",\"type\":\"维修 一般维修\"},{\"content\":\"维修 保险公司;右前叶子板钣金修复;右前叶子板喷漆;左前门钣金修复;左前门喷漆;左后门钣金修复;左后门喷漆;右后门钣金修复;右后门喷漆;左后膀子钣金修复;左后膀子喷漆;前机盖钣金修复;前机盖喷漆;后箱盖钣金校正;后箱盖钣金修复;车顶钣金修复;车顶喷漆;右侧流水槽喷漆;右侧流水槽钣金;左侧流水槽钣金;左则流水槽喷漆;结帐取车;被冰雹砸;修校后保险杠;后保险杠喷漆;右后膀子钣金修复;右后膀子喷漆;\",\"date\":\"2005-06-03\",\"mainTainDate\":1117728000000,\"materal\":\";双面胶I（宽）:60;漆辅料:2;\",\"mileage\":16724,\"payType\":null,\"remark\":\"结帐取车;被冰雹砸;\",\"type\":\"维修 保险公司\"},{\"content\":\"维修 维修;更换三滤、机油;\",\"date\":\"2005-10-05\",\"mainTainDate\":1128441600000,\"materal\":\";密封圈（铜）:1;机滤I:1;上海机油 5W-40 (200):4;空滤:1;汽滤:1;\",\"mileage\":20025,\"payType\":null,\"remark\":null,\"type\":\"维修 维修\"},{\"content\":\"维修 维修;更换机油、机滤;清洗发动机润滑系统;拆卸清洗节气门体、怠速马达;\",\"date\":\"2006-03-10\",\"mainTainDate\":1141920000000,\"materal\":\";密封圈（铜）:1;发动机内部清洗剂(AC):1;机滤I:1;上海机油II 5W-30（208L）:4;前刹车片（标准）:1;化油器清洗剂(劲牌）:1;\",\"mileage\":26985,\"payType\":null,\"remark\":null,\"type\":\"维修 维修\"},{\"content\":\"维修 维修;更换三滤、机油;免拆清洗喷油嘴;更换火花塞;更换右前示宽灯泡;检查空调风量及凉度;\",\"date\":\"2006-07-11\",\"mainTainDate\":1152547200000,\"materal\":\";密封圈（铜）:1;机滤I:1;上海机油II 5W-30:4;火花塞:4;空滤:1;灯泡（飞利浦）12499:2;灯泡(12961):1;火花塞I;汽滤:1;喷油嘴清洗剂（佩米）:1;\",\"mileage\":32498,\"payType\":null,\"remark\":\"检查空调风量及凉度;\",\"type\":\"维修 维修\"},{\"content\":\"维修 维修;更换燃油泵;检查不好点火;检查点火有异响;\",\"date\":\"2006-07-30\",\"mainTainDate\":1154188800000,\"materal\":\";汽油泵总成:1;\",\"mileage\":33351,\"payType\":null,\"remark\":\"检查不好点火;检查点火有异响;\",\"type\":\"维修 维修\"},{\"content\":\"维修 维修;更换机油、机滤;更换防冻液;检查水温低;\",\"date\":\"2007-01-04\",\"mainTainDate\":1167840000000,\"materal\":\";加德士防冻液(4L，50%):2;密封圈（铜）:1;机滤I:1;上海机油 5W-40 (200):4;\",\"mileage\":39800,\"payType\":null,\"remark\":\"检查水温低;\",\"type\":\"维修 维修\"},{\"content\":\"维修 维修;更换三滤、机油;更换刹车油;清洗发动机内部积碳;更换驱动皮带、涨紧器;补胎(个)左后;更换正时皮带、涨紧器、介轮;清理副驾驶异物;\",\"date\":\"2007-09-16\",\"mainTainDate\":1189872000000,\"materal\":\";进气道清洗剂（跑特快）:1;组合皮带I:1;放油螺丝密封垫:1;汽滤:1;正时介轮I:2;正时涨紧器:1;正时皮带:1;机滤I:1;刹车油(巴斯夫)DOT4:1;空滤:1;上海机油 5W-30(200L):4;灯泡（飞利浦）12499:1;组合皮带涨紧器:1;\",\"mileage\":48870,\"payType\":null,\"remark\":\"清理副驾驶异物;\",\"type\":\"维修 维修\"},{\"content\":\"维修 维修;\",\"date\":\"2007-11-01\",\"mainTainDate\":1193846400000,\"materal\":\";灯泡（飞利浦）H7:2;\",\"mileage\":49000,\"payType\":null,\"remark\":null,\"type\":\"维修 维修\"},{\"content\":\"维修 维修;更换机油、机滤;更换排气管尾节及垫;排气管老化?¤;\",\"date\":\"2008-02-27\",\"mainTainDate\":1204041600000,\"materal\":\";放油螺丝密封垫:1;排气管尾节:1;机滤I:1;上海机油 5W-30(200L):4;排气管密封垫II:1;\",\"mileage\":54321,\"payType\":null,\"remark\":\"排气管老化;\",\"type\":\"维修 维修\"},{\"content\":\"维修 维修;更换三滤、机油;清洗发动机润滑系统;拆卸清洗节气门体、怠速马达;查点烟器按不下去;\",\"date\":\"2008-09-25\",\"mainTainDate\":1222272000000,\"materal\":\";放油螺丝密封垫:1;汽滤:1;前刹车盘:2;机滤I:1;空滤:1;上海机油 5W-30(200L):4;前刹车片（标准）:1;三元清洗剂:1;发动机内部清洗剂:1;化油器清洗剂(劲牌）:1;\",\"mileage\":61200,\"payType\":null,\"remark\":\"查点烟器按不下去;350;100;215;450;\",\"type\":\"维修 维修\"},{\"content\":\"维修 维修;更换机油、机滤;免拆清洗喷油嘴;查中控胎时间时有时无;查行驶CD有时跳怔;\",\"date\":\"2009-04-13\",\"mainTainDate\":1239552000000,\"materal\":\";放油螺丝密封垫:1;机滤I:1;空调滤:1;上海机油 5W-30(200L):4;喷油嘴清洗剂(劲牌):1;\",\"mileage\":67200,\"payType\":null,\"remark\":\"查中控胎时间时有时无;查行驶CD有时跳怔;170;170;300;600;\",\"type\":\"维修 维修\"},{\"content\":\"维修 维修;更换三滤、机油;查时钟无显示;\",\"date\":\"2009-08-30\",\"mainTainDate\":1251561600000,\"materal\":\";放油螺丝密封垫:1;汽滤:1;机滤I:1;空滤:1;上海机油 5W-30(200L):4;机油(5W-30);灯泡(飞利浦)12499:1;灯泡(12961):5;发动机外部护理剂:1;\",\"mileage\":72000,\"payType\":null,\"remark\":\"查时钟无显示;\",\"type\":\"维修 维修\"},{\"content\":\"维修 普通维修;更换排气管中节;检查排气管漏气;\",\"date\":\"2009-11-21\",\"mainTainDate\":1258732800000,\"materal\":\";排气管\\中:1;排气歧管螺母:1;排气管衬垫\\中前:1;排气管衬垫\\后:1;\",\"mileage\":74201,\"payType\":null,\"remark\":\"检查排气管漏气;检查排气管漏气;\",\"type\":\"维修 普通维修\"},{\"content\":\"维修 普通维修;发动机引擎系统养护;更换火花塞、高压线;更换制动液;更换气门室盖垫;更换发动机机油、机滤;\",\"date\":\"2010-04-25\",\"mainTainDate\":1272124800000,\"materal\":\";引擎深层养护剂:1;气门室盖垫:1;铜垫:1;高压线\\1 (1.8/2.0):1;高压线\\2 (1.8/2.0):1;高压线\\3 (1.8/2.0):1;高压线\\4 (1.8/2.0):1;机滤:1;火花塞 1.8/2.0:4;机油:4;密封胶:1;刹车油08.1.2后:1;\",\"mileage\":78139,\"payType\":null,\"remark\":null,\"type\":\"维修 普通维修\"},{\"content\":\"维修 普通维修;更换三滤、机油(菜单保养);拆卸清洗节气门体、怠速马达;免拆清洗三元催化器;发动机引擎深层养护;免拆清洗进气道、喷油嘴;别克关怀:冷却液玻璃水冰点检查;检查点烟器头卡着;检查左前玻璃升降器时好时坏;润滑车门;调整左前门提钮;建议观察使用刹车片;检查时间表时有时无;建议更换轮胎;有消费送礼品;更换保险片;观察使用;润滑;调整;.;建议更换时钟表;\",\"date\":\"2011-01-05\",\"mainTainDate\":1294156800000,\"materal\":\";三元清洗剂:1;引擎深层养护剂:1;铜垫:1;汽滤（凯越):1;机滤:1;空滤(凯越）:1;机油:4;燃油系统清洗套餐:1;保险丝-最小15安培:1;化油器清洗剂:1;\",\"mileage\":86539,\"payType\":null,\"remark\":\"检查点烟器头卡着;检查左前玻璃升降器时好时坏;润滑车门;调整左前门提钮;建议观察使用刹车片;检查时间表时有时无;建议更换轮胎;有消费送礼品;更换保险片;观察使用;润滑;调整;.;建议更换时钟表;\",\"type\":\"维修 普通维修\"},{\"content\":\"维修 维修;更换前车门车窗升降器_左侧;检查左前玻璃不升降;\",\"date\":\"2011-01-19\",\"mainTainDate\":1295366400000,\"materal\":\";左前门玻璃电动升降器:1;\",\"mileage\":87084,\"payType\":null,\"remark\":\"检查左前玻璃不升降;\",\"type\":\"维修 维修\"},{\"content\":\"维修 普通维修;更换或清洁空调滤芯;空调风道及蒸发箱清洗养护;菜单保养机油,机滤;灯泡是左侧的;\",\"date\":\"2011-07-15\",\"mainTainDate\":1310659200000,\"materal\":\";铜垫:1;机滤:1;前照灯灯泡总成(近光):1;空调滤芯（凯越）:1;机油:4;空调养护套装:1;开关\\制动灯 自动:1;引擎保:1;\",\"mileage\":92641,\"payType\":null,\"remark\":\"灯泡是左侧的;170;330;240;161;\",\"type\":\"维修 普通维修\"},{\"content\":\"维修 维修;购一小桶防冻液;\",\"date\":\"2011-09-04\",\"mainTainDate\":1315065600000,\"materal\":\";防冻液1升(-45'C):1;\",\"mileage\":93916,\"payType\":null,\"remark\":\"购一小桶防冻液;\",\"type\":\"维修 维修\"},{\"content\":\"维修 普通维修;菜单保养机油,三滤;2012年3月活动;发动机润滑系统清洗\\保护剂;清洗发动机燃烧室;查漏玻璃水;有时低速发动机部位有异响;查刹车;\",\"date\":\"2012-03-22\",\"mainTainDate\":1332345600000,\"materal\":\";发动机机油油路养护剂:1;铜垫:1;前刹车片:1;前制动盘:2;汽滤（凯越):1;机滤:1;刹车灯泡（双尾）:1;汽油添加剂:1;空滤(凯越）:1;机油:4;燃油系统清洗套餐;玻璃水1.5L:1;发动机机油清洗剂:1;后刹车片:1;活塞清洁剂:1;\",\"mileage\":98842,\"payType\":null,\"remark\":\"查漏玻璃水;有时低速发动机部位有异响;查刹车;317;390;396;\",\"type\":\"维修 普通维修\"},{\"content\":\"维修 事故;后杠、后围板喷漆;平俢后围板;更换后杠、后杠骨架、电眼;\",\"date\":\"2013-10-11\",\"mainTainDate\":1381420800000,\"materal\":\";后杠 WJ:1;后杠骨架 WJ:1;\",\"mileage\":115361,\"payType\":null,\"remark\":null,\"type\":\"维修 事故\"}],\"outsideAnalyzeRepairRecords\":[{\"content\":\"前杠喷漆;右后门喷漆;\",\"date\":\"2004-04-27\",\"mainTainDate\":1082995200000,\"materal\":null,\"mileage\":960,\"payType\":null,\"remark\":null,\"type\":null},{\"content\":\"前杠喷漆;右后视镜喷漆;后杠喷漆;后杠整形;免费换机油机滤送汽油添加剂;\",\"date\":\"2004-07-08\",\"mainTainDate\":1089216000000,\"materal\":\";汽油添加剂:1;机油:4;机油滤清器 96395221:1;\",\"mileage\":3826,\"payType\":null,\"remark\":null,\"type\":null},{\"content\":\"维修 一般维修;更换三滤、机油;凉车有时CD有时停顿;前杠喷漆;右前叶子板喷漆;右前叶子板钣金;后杠喷漆;后杠钣金;\",\"date\":\"2005-03-15\",\"mainTainDate\":1110816000000,\"materal\":\";机油滤清器:1;机油:4;燃油滤清器:1;空气滤清器过滤单元(总成):1;\",\"mileage\":13168,\"payType\":null,\"remark\":\"凉车有时CD有时停顿;\",\"type\":\"维修 一般维修\"},{\"content\":\"维修 保险公司;右前叶子板钣金修复;右前叶子板喷漆;左前门钣金修复;左前门喷漆;左后门钣金修复;左后门喷漆;右后门钣金修复;右后门喷漆;左后膀子钣金修复;左后膀子喷漆;前机盖钣金修复;前机盖喷漆;后箱盖钣金校正;后箱盖钣金修复;车顶钣金修复;车顶喷漆;右侧流水槽喷漆;右侧流水槽钣金;左侧流水槽钣金;左则流水槽喷漆;结帐取车;被冰雹砸;修校后保险杠;后保险杠喷漆;右后膀子钣金修复;右后膀子喷漆;\",\"date\":\"2005-06-03\",\"mainTainDate\":1117728000000,\"materal\":\";双面胶I（宽）:60;漆辅料:2;\",\"mileage\":16724,\"payType\":null,\"remark\":\"结帐取车;被冰雹砸;\",\"type\":\"维修 保险公司\"},{\"content\":\"维修 事故;后杠、后围板喷漆;平俢后围板;更换后杠、后杠骨架、电眼;\",\"date\":\"2013-10-11\",\"mainTainDate\":1381420800000,\"materal\":\";后杠 WJ:1;后杠骨架 WJ:1;\",\"mileage\":115361,\"payType\":null,\"remark\":null,\"type\":\"维修 事故\"}],\"productionArea\":\"合资\",\"reportNo\":\"20180124042972029\",\"score\":0,\"seriesName\":\"凯越\",\"transmissionType\":\"自动档\",\"vin\":\"LSGJV52P44S181440\"}";
	  Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").enableComplexMapKeySerialization().disableHtmlEscaping().create();
	  WXJL w = gson.fromJson(s, WXJL.class);
		w.translateWBJL(w);
		System.out.println(JsonUtils.toJson(w));
    }
}
