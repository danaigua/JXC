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
import com.hengyue.entity.ReturnList;
import com.hengyue.entity.ReturnListGoods;
import com.hengyue.entity.User;
import com.hengyue.service.LogService;
import com.hengyue.service.ReturnListGoodsService;
import com.hengyue.service.ReturnListService;
import com.hengyue.service.UserService;
import com.hengyue.utils.DateUtil;
import com.hengyue.utils.StringUtils;

/**
 * 退货管理控制层
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/returnList")
public class ReturnListAdminController {

	@Resource
	private LogService logService;
	
	@Resource
	private ReturnListService returnListService;
	
	@Resource
	private ReturnListGoodsService returnListGoodsService;
	
	@Resource
	private UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	/**
	 * 获取退货单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/genCode")
	@RequiresPermissions(value = "退货出库")
	public String genCode() throws Exception {
		StringBuffer code = new StringBuffer("TH");
		code.append(DateUtil.getCurrentDateStr());
		String returnNumber = returnListService.getTodayMaxReturnNumber();
		if(StringUtils.isNotEmpty(returnNumber)) {
			code.append(StringUtils.formatCode(returnNumber));
		}else{
			code.append("0001");
		}
		return code.toString();
	}
	
	/**
	 * 添加退货单以及所有退货单商品
	 * @param returnList
	 * @param goodsJson
	 * @return
	 * @throws Excepiton
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "退货出库")
	public Map<String, Object> save(ReturnList returnList, String goodsJson) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal());			//获取操作员
		Gson gson = new Gson();
		returnList.setUser(user);
		List<ReturnListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<ReturnListGoods>>() {}.getType());
		returnListService.save(returnList, plgList);
		logService.save(new Log(Log.ADD_ACTION, "添加退货单"));
		map.put("success", true);
		return map;
	}
	
	@RequestMapping("/list")
	@RequiresPermissions(value={"退货单据查询","供应商统计"}, logical=Logical.OR)
	public Map<String, Object> list(ReturnList returnList){
		Map<String, Object> map = new HashMap<String, Object>();
		List<ReturnList> returnListList=returnListService.list(returnList, Direction.DESC, "returnDate");
		map.put("rows", returnListList);
		return map;
	}
	/**
	 * 根据退货单id查询所有退货单商品
	 * @param purchaseListId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listGoods")
	@RequiresPermissions(value = "退货单据查询")
	public Map<String, Object> listGoods(Integer returnListId) throws Exception{
		if(returnListId == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<ReturnListGoods> purchaseGoodsListList = returnListGoodsService.listByReturnListId(returnListId);
		map.put("rows", purchaseGoodsListList);
		logService.save(new Log(Log.SEARCH_ACTION,"退货单查询"));
		return map;
	}
	/**
	 * 删除退货单
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value = "退货单据查询")
	public Map<String, Object> delete(Integer id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		logService.save(new Log(Log.DELETE_ACTION,"删除退货单" + returnListService.findById(id)));
		map.put("success", true);
		returnListService.delete(id);
		return map;
	}
}
