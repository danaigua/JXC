package com.hengyue.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hengyue.entity.Log;
import com.hengyue.entity.PurchaseList;
import com.hengyue.entity.SaleCount;
import com.hengyue.entity.SaleList;
import com.hengyue.entity.SaleListGoods;
import com.hengyue.entity.User;
import com.hengyue.service.LogService;
import com.hengyue.service.SaleListGoodsService;
import com.hengyue.service.SaleListService;
import com.hengyue.service.UserService;
import com.hengyue.utils.DateUtil;
import com.hengyue.utils.MathUtils;
import com.hengyue.utils.StringUtils;

/**
 * 销售管理控制层
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/saleList")
public class SaleListAdminController {

	@Resource
	private LogService logService;
	
	@Resource
	private SaleListService saleListService;
	
	@Resource
	private SaleListGoodsService saleListGoodsService;
	
	@Resource
	private UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	/**
	 * 获取销售单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/genCode")
	@RequiresPermissions(value = "销售出库")
	public String genCode() throws Exception {
		StringBuffer code = new StringBuffer("XS");
		code.append(DateUtil.getCurrentDateStr());
		String saleNumber = saleListService.getTodayMaxSaleNumber();
		if(StringUtils.isNotEmpty(saleNumber)) {
			code.append(StringUtils.formatCode(saleNumber));
		}else{
			code.append("0001");
		}
		return code.toString();
	}
	
	/**
	 * 添加销售单以及所有销售单商品
	 * @param saleList
	 * @param goodsJson
	 * @return
	 * @throws Excepiton
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "销售出库")
	public Map<String, Object> save(SaleList saleList, String goodsJson) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal());			//获取操作员
		Gson gson = new Gson();
		saleList.setUser(user);
		List<SaleListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<SaleListGoods>>() {}.getType());
		saleListService.save(saleList, plgList);
		logService.save(new Log(Log.ADD_ACTION, "添加销售单"));
		map.put("success", true);
		return map;
	}
	/**
	 * 根据条件查询所有销售单查询
	 * @param saleList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value = {"销售单据查询","客户统计"}, logical = Logical.OR)
	public Map<String, Object> list(SaleList saleList) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<SaleList> saleListList = saleListService.list(saleList, Direction.DESC, "saleDate");
		map.put("rows", saleListList);
		logService.save(new Log(Log.SEARCH_ACTION,"销售单查询"));
		return map;
	}
	
	/**
	 * 根据销售单id查询所有销售单商品
	 * @param saleListId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listGoods")
	@RequiresPermissions(value = "销售单据查询")
	public Map<String, Object> listGoods(Integer saleListId) throws Exception{
		if(saleListId == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<SaleListGoods> saleGoodsListList = saleListGoodsService.listBySaleListId(saleListId);
		map.put("rows", saleGoodsListList);
		logService.save(new Log(Log.SEARCH_ACTION,"销售单查询"));
		return map;
	}
	/**
	 * 根据条件获取商品销售信息
	 * @param saleList
	 * @param saleListGoods
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listCount")
	@RequiresPermissions(value="商品销售统计")
	public Map<String,Object> listCount(SaleList saleList,SaleListGoods saleListGoods)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<SaleList> saleListList=saleListService.list(saleList, Direction.DESC, "saleDate");
		for(SaleList sl:saleListList){
			saleListGoods.setSaleList(sl);
			List<SaleListGoods> slgList=saleListGoodsService.list(saleListGoods);
			sl.setSaleListGoodsList(slgList);
		}
		resultMap.put("rows", saleListList);
		logService.save(new Log(Log.SEARCH_ACTION,"商品销售统计查询"));
		return resultMap;
	}
	/**
	 * 删除销售单
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value = "销售单据查询")
	public Map<String, Object> delete(Integer id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		logService.save(new Log(Log.DELETE_ACTION,"删除销售单" + saleListService.findById(id)));
		map.put("success", true);
		saleListService.delete(id);
		return map;
	}
	/**
	 * 修改销售单支付状态
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@RequiresPermissions(value = "客户统计")
	public Map<String, Object> update(Integer id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		SaleList saleList = saleListService.findById(id);
		saleList.setState(1);
		saleListService.update(saleList);
		map.put("success", true);
		logService.save(new Log(Log.UPDATE_ACTION,"修改销售单支付状态" + saleList));
		return map;
	}
	/**
	 * 按日统计分析
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/countSaleByDay")
	@RequiresPermissions(value = "按日统计分析")
	public Map<String, Object> countSaleByDay(String begin, String end)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<SaleCount> scList = new ArrayList<SaleCount>();
		List<String> datas = DateUtil.getRangeDates(begin, end);
		List<Object> ll = saleListService.countSaleByDay(begin, end);
		
		for (String date : datas) {
			SaleCount sc = new SaleCount();
			sc.setDate(date);
			boolean flag = false;
			for (Object object : ll) {
				Object[]oo = (Object[])object;
				String dd = oo[2].toString().substring(0, 10);
				if(dd.equals(date)) {//存在
					sc.setAmountCost(MathUtils.format2Bit(Float.parseFloat(oo[0].toString())));//成本总金额
					sc.setAmountSale(MathUtils.format2Bit(Float.parseFloat(oo[1].toString())));//销售总金额
					sc.setAmountProfit(sc.getAmountSale() - sc.getAmountCost());
					flag = true;
				}
			}
			if(!flag) {
				sc.setAmountCost(0);
				sc.setAmountSale(0);
				sc.setAmountProfit(0);
			}
			scList.add(sc);
		}
		map.put("rows", scList);
		map.put("success", true);
		return map;
	}
	/**
	 * 按月统计分析
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/countSaleByMonth")
	@RequiresPermissions(value="按月统计分析")
	public Map<String,Object> countSaleByMonth(String begin,String end)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<SaleCount> scList=new ArrayList<>();
		List<String> dates=DateUtil.getRangeMonths(begin, end);
		List<Object> ll=saleListService.countSaleByMonth(begin, end);
		for(String date:dates){
			SaleCount sc=new SaleCount();
			sc.setDate(date);
			boolean flag=false;
			for(Object o:ll){
				Object []oo=(Object[]) o;
				String dd=oo[2].toString().substring(0,7);
				if(dd.equals(date)){ // 存在
					sc.setAmountCost(MathUtils.format2Bit(Float.parseFloat(oo[0].toString()))); // 成本总金额
					sc.setAmountSale(MathUtils.format2Bit(Float.parseFloat(oo[1].toString()))); // 销售总金额
					sc.setAmountProfit(MathUtils.format2Bit(sc.getAmountSale()-sc.getAmountCost())); // 销售利润
					flag=true;
				}
			}
			if(!flag){
				sc.setAmountCost(0);
				sc.setAmountSale(0);
				sc.setAmountProfit(0);
			}
			scList.add(sc);
		}
		resultMap.put("rows", scList);
		resultMap.put("success", true);
		return resultMap;
	}
}
