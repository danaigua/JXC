package com.hengyue.controller.admin;

import java.text.SimpleDateFormat;
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
import com.hengyue.entity.PurchaseListGoods;
import com.hengyue.entity.User;
import com.hengyue.service.LogService;
import com.hengyue.service.PurchaseListGoodsService;
import com.hengyue.service.PurchaseListService;
import com.hengyue.service.UserService;
import com.hengyue.utils.DateUtil;
import com.hengyue.utils.StringUtils;

/**
 * 进货管理控制层
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/purchaseList")
public class PurchaseListAdminController {

	@Resource
	private LogService logService;
	
	@Resource
	private PurchaseListService purchaseListService;
	
	@Resource
	private PurchaseListGoodsService purchaseListGoodsService;
	
	@Resource
	private UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	/**
	 * 获取进货单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/genCode")
	@RequiresPermissions(value = "进货入库")
	public String genCode() throws Exception {
		StringBuffer code = new StringBuffer("JH");
		code.append(DateUtil.getCurrentDateStr());
		String purchaseNumber = purchaseListService.getTodayMaxPurchaseNumber();
		if(StringUtils.isNotEmpty(purchaseNumber)) {
			code.append(StringUtils.formatCode(purchaseNumber));
		}else{
			code.append("0001");
		}
		return code.toString();
	}
	
	/**
	 * 添加进货单以及所有进货单商品
	 * @param purchaseList
	 * @param goodsJson
	 * @return
	 * @throws Excepiton
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "进货入库")
	public Map<String, Object> save(PurchaseList purchaseList, String goodsJson) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal());			//获取操作员
		Gson gson = new Gson();
		purchaseList.setUser(user);
		List<PurchaseListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<PurchaseListGoods>>() {}.getType());
		purchaseListService.save(purchaseList, plgList);
		logService.save(new Log(Log.ADD_ACTION, "添加进货单"));
		map.put("success", true);
		return map;
	}
	/**
	 * 根据条件查询所有进货单查询
	 * @param purchaseList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value = {"进货单据查询", "供应商统计"}, logical = Logical.OR)
	public Map<String, Object> list(PurchaseList purchaseList) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<PurchaseList> purchaseListList = purchaseListService.list(purchaseList, Direction.DESC, "purchaseDate");
		map.put("rows", purchaseListList);
		logService.save(new Log(Log.SEARCH_ACTION,"进货单查询"));
		return map;
	}
	
	/**
	 * 根据进货单id查询所有进货单商品
	 * @param purchaseListId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listGoods")
	@RequiresPermissions(value = "进货单据查询")
	public Map<String, Object> listGoods(Integer purchaseListId) throws Exception{
		if(purchaseListId == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<PurchaseListGoods> purchaseGoodsListList = purchaseListGoodsService.listByPurchaseListId(purchaseListId);
		map.put("rows", purchaseGoodsListList);
		logService.save(new Log(Log.SEARCH_ACTION,"进货单查询"));
		return map;
	}
	/**
	 * 根据条件获取商品采购信息
	 * @param purchaseList
	 * @param purchaseListGoods
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listCount")
	@RequiresPermissions(value="商品采购统计")
	public Map<String,Object> listCount(PurchaseList purchaseList,PurchaseListGoods purchaseListGoods)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<PurchaseList> purchaseListList=purchaseListService.list(purchaseList, Direction.DESC, "purchaseDate");
		for(PurchaseList pl:purchaseListList){
			purchaseListGoods.setPurchaseList(pl);
			List<PurchaseListGoods> plgList=purchaseListGoodsService.list(purchaseListGoods);
			pl.setPurchaseListGoodsList(plgList);
		}
		resultMap.put("rows", purchaseListList);
		logService.save(new Log(Log.SEARCH_ACTION,"商品采购统计查询"));
		return resultMap;
	}
	/**
	 * 删除进货单
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value = "进货单据查询")
	public Map<String, Object> delete(Integer id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		logService.save(new Log(Log.DELETE_ACTION,"删除进货单" + purchaseListService.findById(id)));
		map.put("success", true);
		purchaseListService.delete(id);
		return map;
	}
	/**
	 * 修改进货单支付状态
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@RequiresPermissions(value = "供应商统计")
	public Map<String, Object> update(Integer id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		PurchaseList purchaseList = purchaseListService.findById(id);
		purchaseList.setState(1);
		purchaseListService.update(purchaseList);
		map.put("success", true);
		logService.save(new Log(Log.UPDATE_ACTION,"修改进货单支付状态" + purchaseList));
		return map;
	}
}
