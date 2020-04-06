package com.hengyue.controller.admin.vo;

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
import com.hengyue.entity.Customer;
import com.hengyue.entity.Log;
import com.hengyue.entity.User;
import com.hengyue.entity.vo.CkCustomerReturnList;
import com.hengyue.entity.vo.CkCustomerReturnListGoods;
import com.hengyue.service.CustomerService;
import com.hengyue.service.LogService;
import com.hengyue.service.UserService;
import com.hengyue.service.vo.CkCustomerReturnListGoodsService;
import com.hengyue.service.vo.CkCustomerReturnListService;
import com.hengyue.utils.DateUtil;
import com.hengyue.utils.DateUtils;
import com.hengyue.utils.StringUtils;

/**
 * 销售管理控制层
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/ckCustomerReturnList")
public class CkCustomerReturnListAdminController {

	@Resource
	private LogService logService;
	
	@Resource
	private CkCustomerReturnListService ckCustomerReturnListService;
	
	@Resource
	private CkCustomerReturnListGoodsService ckCustomerReturnListGoodsService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private CustomerService customerService;
	
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
		StringBuffer code = new StringBuffer("Chuanke");
//		code.append(DateUtil.getCurrentDateStr());
		String ckCustomerReturnNumber = ckCustomerReturnListService.getTodayMaxCkCustomerReturnNumber();
		if(StringUtils.isNotEmpty(ckCustomerReturnNumber)) {
			code.append(StringUtils.formatCode(ckCustomerReturnNumber));
		}else{
			code.append("0001");
		}
		return code.toString();
	}
	
	/**
	 * 添加客户退货以及所有客户退货商品
	 * @param ckCustomerReturnList
	 * @param goodsJson
	 * @return
	 * @throws Excepiton
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "客户退货")
	public Map<String, Object> save(CkCustomerReturnList ckCustomerReturnList, String goodsJson) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal());			//获取操作员
		Gson gson = new Gson();
		ckCustomerReturnList.setUser(user);
		List<CkCustomerReturnListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<CkCustomerReturnListGoods>>() {}.getType());
		ckCustomerReturnListService.save(ckCustomerReturnList, plgList);
		logService.save(new Log(Log.ADD_ACTION, "添加客户退货"));
		map.put("success", true);
		return map;
	}
	/**
	 * 根据条件查询所有客户退货查询
	 * @param ckCustomerReturnList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value = {"客户退货查询","客户统计", "客户退货"}, logical = Logical.OR)
	public Map<String, Object> list(CkCustomerReturnList ckCustomerReturnList) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<CkCustomerReturnList> ckCustomerReturnListList = ckCustomerReturnListService.list(ckCustomerReturnList, Direction.DESC, "customerReturnDate");
		for (CkCustomerReturnList ckCustomerReturnList2 : ckCustomerReturnListList) {
			if(ckCustomerReturnList2.getCustomer() != null) {
				try {
					Customer customer = customerService.findById(ckCustomerReturnList2.getCustomer());
					if(customer != null) {
						ckCustomerReturnList2.setCustomerName(customer.getName());
					}
				}catch(Exception e) {
					ckCustomerReturnList2.setCustomerName("");
				}
			}
		}
		map.put("rows", ckCustomerReturnListList);
		logService.save(new Log(Log.SEARCH_ACTION,"客户退货查询"));
		return map;
	}
	
	/**
	 * 根据客户退货id查询所有客户退货商品
	 * @param ckCustomerReturnListId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listGoods")
	@RequiresPermissions(value = {"客户退货查询", "客户退货"}, logical = Logical.OR)
	public Map<String, Object> listGoods(Integer ckCustomerReturnListId) throws Exception{
		if(ckCustomerReturnListId == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<CkCustomerReturnListGoods> ckCustomerReturnGoodsListList = ckCustomerReturnListGoodsService.listByCkCustomerReturnListId(ckCustomerReturnListId);
		map.put("rows", ckCustomerReturnGoodsListList);
		logService.save(new Log(Log.SEARCH_ACTION,"客户退货查询"));
		return map;
	}
	
	/**
	 * 根据条件查询所有客户退货查询
	 * @param ckCustomerReturnList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listReturn")
	@RequiresPermissions(value = "绩效考核")
	public Map<String, Object> listReturn() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		CkCustomerReturnList ckCustomerReturnList = new CkCustomerReturnList();
		ckCustomerReturnList.setbCkCustomerReturnDate(DateUtils.getThisMonthsFirstDay());
		ckCustomerReturnList.seteCkCustomerReturnDate(new Date());
		List<CkCustomerReturnList> ckCustomerReturnListList = ckCustomerReturnListService.list(ckCustomerReturnList, Direction.DESC, "customerReturnDate");
		for (CkCustomerReturnList ckCustomerReturnList2 : ckCustomerReturnListList) {
			if(ckCustomerReturnList2.getCustomer() != null) {
				try {
					Customer customer = customerService.findById(ckCustomerReturnList2.getCustomer());
					if(customer != null) {
						ckCustomerReturnList2.setCustomerName(customer.getName());
					}
				}catch(Exception e) {
					ckCustomerReturnList2.setCustomerName("");
				}
			}
		}
		map.put("rows", ckCustomerReturnListList);
		logService.save(new Log(Log.SEARCH_ACTION,"绩效考核查询"));
		return map;
	}
	
	/**
	 * 根据条件查询所有客户退货查询
	 * @param ckCustomerReturnList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listThisYearReturn")
	@RequiresPermissions(value = "绩效考核")
	public Map<String, Object> listThisYearReturn() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		CkCustomerReturnList ckCustomerReturnList = new CkCustomerReturnList();
		ckCustomerReturnList.setbCkCustomerReturnDate(DateUtils.getThisYearFirstDay());
		ckCustomerReturnList.seteCkCustomerReturnDate(new Date());
		List<CkCustomerReturnList> ckCustomerReturnListList = ckCustomerReturnListService.list(ckCustomerReturnList, Direction.DESC, "customerReturnDate");
		map.put("rows", ckCustomerReturnListList);
		logService.save(new Log(Log.SEARCH_ACTION,"绩效考核查询"));
		return map;
	}
	
	/**
	 * 根据客户退货id查询所有客户退货商品
	 * 绩效考核
	 * @param ckCustomerReturnListId
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
		List<CkCustomerReturnListGoods> ckCustomerReturnGoodsListList = ckCustomerReturnListGoodsService.listByCkCustomerReturnListId(customerReturnListId);
		map.put("rows", ckCustomerReturnGoodsListList);
		logService.save(new Log(Log.SEARCH_ACTION,"绩效考核查询"));
		return map;
	}
	/**
	 * 列出今年所有的月份退货金额
	 * 绩效考核
	 * @param ckCustomerReturnListId
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
			CkCustomerReturnList ckCustomerReturnList = new CkCustomerReturnList();
			ckCustomerReturnList.setbCkCustomerReturnDate(DateUtils.getOneMonthFitstDayFromNumber(i));
			if(j == 1) {
				ckCustomerReturnList.seteCkCustomerReturnDate(new Date());
				j++ ;
			}else {
				ckCustomerReturnList.seteCkCustomerReturnDate(DateUtils.getOneMonthLastDayFromNumber(i));
			}
			long money = 0;
			List<CkCustomerReturnList> ckCustomerReturnListList = ckCustomerReturnListService.list(ckCustomerReturnList, Direction.DESC, "customerReturnDate");
			for (CkCustomerReturnList ckCustomerReturnList2 : ckCustomerReturnListList) {
				money += ckCustomerReturnList2.getAmountPaid();
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
	 * @param ckCustomerReturnList
	 * @param ckCustomerReturnListGoods
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listCount")
	@RequiresPermissions(value= {"商品销售统计", "客户退货"}, logical = Logical.OR)
	public Map<String,Object> listCount(CkCustomerReturnList ckCustomerReturnList,CkCustomerReturnListGoods ckCustomerReturnListGoods)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<CkCustomerReturnList> ckCustomerReturnListList=ckCustomerReturnListService.list(ckCustomerReturnList, Direction.DESC, "customerReturnDate");
		for(CkCustomerReturnList cr:ckCustomerReturnListList){
			ckCustomerReturnListGoods.setCustomerReturnList(cr);
			List<CkCustomerReturnListGoods> crgList=ckCustomerReturnListGoodsService.list(ckCustomerReturnListGoods);
			cr.setCustomerReturnListGoodsList(crgList);
		}
		resultMap.put("rows", ckCustomerReturnListList);
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
	@RequiresPermissions(value = {"客户退货查询", "客户退货"}, logical = Logical.OR)
	public Map<String, Object> delete(Integer id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		logService.save(new Log(Log.DELETE_ACTION,"删除客户退货" + ckCustomerReturnListService.findById(id)));
		map.put("success", true);
		ckCustomerReturnListService.delete(id);
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
		CkCustomerReturnList ckCustomerReturnList = ckCustomerReturnListService.findById(id);
		ckCustomerReturnList.setState(1);
		ckCustomerReturnListService.update(ckCustomerReturnList);
		map.put("success", true);
		logService.save(new Log(Log.UPDATE_ACTION,"修改进货单支付状态" + ckCustomerReturnList));
		return map;
	}
	@RequestMapping("/findById")
	@RequiresPermissions(value = "退货单查询")
	public Map<String , Object> findById(Integer id){
		Map<String , Object> map = new HashMap<String, Object>();
		try {
			CkCustomerReturnList ckCustomerReturnList = ckCustomerReturnListService.findById(id);
			if(ckCustomerReturnList.getCustomer() != null) {
				Customer customer = customerService.findById(ckCustomerReturnList.getCustomer() );
				if(customer != null) {
					ckCustomerReturnList.setCustomerName(customer.getName());
					ckCustomerReturnList.setCustomerTelephone(customer.getTelephone());
					ckCustomerReturnList.setCustomerAddress(customer.getAddress());
				}
			}
			List<CkCustomerReturnListGoods> ckCustomerReturnGoodsListList = ckCustomerReturnListGoodsService.listByCkCustomerReturnListId(id);
			map.put("success", true);
			map.put("resultJson", ckCustomerReturnList);
			map.put("returnGoods", ckCustomerReturnGoodsListList);
		}catch(Exception e) {
			map.put("success", false);
			map.put("errorInfo", e.getMessage());
		}
		return map;
	}
}
