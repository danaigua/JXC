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
import com.hengyue.entity.CustomerReturnList;
import com.hengyue.entity.CustomerReturnListGoods;
import com.hengyue.entity.User;
import com.hengyue.service.LogService;
import com.hengyue.service.CustomerReturnListGoodsService;
import com.hengyue.service.CustomerReturnListService;
import com.hengyue.service.UserService;
import com.hengyue.utils.DateUtil;
import com.hengyue.utils.DateUtils;
import com.hengyue.utils.StringUtils;

/**
 * 销售管理控制层
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/customerReturnList")
public class CustomerReturnListAdminController {

	@Resource
	private LogService logService;
	
	@Resource
	private CustomerReturnListService customerReturnListService;
	
	@Resource
	private CustomerReturnListGoodsService customerReturnListGoodsService;
	
	@Resource
	private UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	/**
	 * 获取客户退货号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/genCode")
	@RequiresPermissions(value = "客户退货")
	public String genCode() throws Exception {
		StringBuffer code = new StringBuffer("XT");
		code.append(DateUtil.getCurrentDateStr());
		String customerReturnNumber = customerReturnListService.getTodayMaxCustomerReturnNumber();
		if(StringUtils.isNotEmpty(customerReturnNumber)) {
			code.append(StringUtils.formatCode(customerReturnNumber));
		}else{
			code.append("0001");
		}
		return code.toString();
	}
	
	/**
	 * 添加客户退货以及所有客户退货商品
	 * @param customerReturnList
	 * @param goodsJson
	 * @return
	 * @throws Excepiton
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "客户退货")
	public Map<String, Object> save(CustomerReturnList customerReturnList, String goodsJson) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal());			//获取操作员
		Gson gson = new Gson();
		customerReturnList.setUser(user);
		List<CustomerReturnListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<CustomerReturnListGoods>>() {}.getType());
		customerReturnListService.save(customerReturnList, plgList);
		logService.save(new Log(Log.ADD_ACTION, "添加客户退货"));
		map.put("success", true);
		return map;
	}
	/**
	 * 根据条件查询所有客户退货查询
	 * @param customerReturnList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value = {"客户退货查询","客户统计"}, logical = Logical.OR)
	public Map<String, Object> list(CustomerReturnList customerReturnList) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<CustomerReturnList> customerReturnListList = customerReturnListService.list(customerReturnList, Direction.DESC, "customerReturnDate");
		map.put("rows", customerReturnListList);
		logService.save(new Log(Log.SEARCH_ACTION,"客户退货查询"));
		return map;
	}
	
	/**
	 * 根据客户退货id查询所有客户退货商品
	 * @param customerReturnListId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listGoods")
	@RequiresPermissions(value = "客户退货查询")
	public Map<String, Object> listGoods(Integer customerReturnListId) throws Exception{
		if(customerReturnListId == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<CustomerReturnListGoods> customerReturnGoodsListList = customerReturnListGoodsService.listByCustomerReturnListId(customerReturnListId);
		map.put("rows", customerReturnGoodsListList);
		logService.save(new Log(Log.SEARCH_ACTION,"客户退货查询"));
		return map;
	}
	
	/**
	 * 根据条件查询所有客户退货查询
	 * @param customerReturnList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listReturn")
	@RequiresPermissions(value = "绩效考核")
	public Map<String, Object> listReturn() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		CustomerReturnList customerReturnList = new CustomerReturnList();
		customerReturnList.setbCustomerReturnDate(DateUtils.getThisMonthsFirstDay());
		customerReturnList.seteCustomerReturnDate(new Date());
		List<CustomerReturnList> customerReturnListList = customerReturnListService.list(customerReturnList, Direction.DESC, "customerReturnDate");
		map.put("rows", customerReturnListList);
		logService.save(new Log(Log.SEARCH_ACTION,"绩效考核查询"));
		return map;
	}
	
	/**
	 * 根据条件查询所有客户退货查询
	 * @param customerReturnList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listThisYearReturn")
	@RequiresPermissions(value = "绩效考核")
	public Map<String, Object> listThisYearReturn() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		CustomerReturnList customerReturnList = new CustomerReturnList();
		customerReturnList.setbCustomerReturnDate(DateUtils.getThisYearFirstDay());
		customerReturnList.seteCustomerReturnDate(new Date());
		List<CustomerReturnList> customerReturnListList = customerReturnListService.list(customerReturnList, Direction.DESC, "customerReturnDate");
		map.put("rows", customerReturnListList);
		logService.save(new Log(Log.SEARCH_ACTION,"绩效考核查询"));
		return map;
	}
	
	/**
	 * 根据客户退货id查询所有客户退货商品
	 * 绩效考核
	 * @param customerReturnListId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listReturnGoods")
	@RequiresPermissions(value = "绩效考核")
	public Map<String, Object> listReturnGoods(Integer customerReturnListId) throws Exception{
		if(customerReturnListId == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<CustomerReturnListGoods> customerReturnGoodsListList = customerReturnListGoodsService.listByCustomerReturnListId(customerReturnListId);
		map.put("rows", customerReturnGoodsListList);
		logService.save(new Log(Log.SEARCH_ACTION,"绩效考核查询"));
		return map;
	}
	/**
	 * 列出今年所有的月份退货金额
	 * 绩效考核
	 * @param customerReturnListId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listThisYearAllMonthReturnMoney")
	@RequiresPermissions(value = "绩效考核")
	public Map<String, Object> listThisYearAllMonthReturnMoney() throws Exception{
		List<Map<String, Object>> mapList = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 列出本月
		 * 
		 * 列出下一个月
		 * 
		 * 直达列出一月份
		 * 
		 * 递归
		 */
		int i = DateUtils.getThisMonthNumber();
		int j = 1;
		do {
			CustomerReturnList customerReturnList = new CustomerReturnList();
			customerReturnList.setbCustomerReturnDate(DateUtils.getOneMonthFitstDayFromNumber(i));
			if(j == 1) {
				customerReturnList.seteCustomerReturnDate(new Date());
				j++ ;
			}else {
				customerReturnList.seteCustomerReturnDate(DateUtils.getOneMonthLastDayFromNumber(i));
			}
			long money = 0;
			List<CustomerReturnList> customerReturnListList = customerReturnListService.list(customerReturnList, Direction.DESC, "customerReturnDate");
			for (CustomerReturnList customerReturnList2 : customerReturnListList) {
				money += customerReturnList2.getAmountPaid();
			}
			Map<String, Object> returnGoodsMap = new HashMap<String, Object>();
			returnGoodsMap.put("month", i + "月份");
			returnGoodsMap.put("amountPayable", money);
			mapList.add(returnGoodsMap);
			i--;
		}while(i != 0);
		map.put("rows", mapList);
		logService.save(new Log(Log.SEARCH_ACTION,"列出今年所有的月份退货金额"));
		return map;
	}
	/**
	 * 根据条件获取商品销售信息
	 * @param customerReturnList
	 * @param customerReturnListGoods
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listCount")
	@RequiresPermissions(value="商品销售统计")
	public Map<String,Object> listCount(CustomerReturnList customerReturnList,CustomerReturnListGoods customerReturnListGoods)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<CustomerReturnList> customerReturnListList=customerReturnListService.list(customerReturnList, Direction.DESC, "customerReturnDate");
		for(CustomerReturnList cr:customerReturnListList){
			customerReturnListGoods.setCustomerReturnList(cr);
			List<CustomerReturnListGoods> crgList=customerReturnListGoodsService.list(customerReturnListGoods);
			cr.setCustomerReturnListGoodsList(crgList);
		}
		resultMap.put("rows", customerReturnListList);
		logService.save(new Log(Log.SEARCH_ACTION,"商品销售统计查询"));
		return resultMap;
	}
	/**
	 * 删除客户退货
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value = "客户退货查询")
	public Map<String, Object> delete(Integer id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		logService.save(new Log(Log.DELETE_ACTION,"删除客户退货" + customerReturnListService.findById(id)));
		map.put("success", true);
		customerReturnListService.delete(id);
		return map;
	}
	/**
	 * 修改单支付状客户退货态
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@RequiresPermissions(value = "客户统计")
	public Map<String, Object> update(Integer id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		CustomerReturnList customerReturnList = customerReturnListService.findById(id);
		customerReturnList.setState(1);
		customerReturnListService.update(customerReturnList);
		map.put("success", true);
		logService.save(new Log(Log.UPDATE_ACTION,"修改进货单支付状态" + customerReturnList));
		return map;
	}
}
