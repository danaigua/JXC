package com.hengyue.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
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
import com.hengyue.entity.DamageList;
import com.hengyue.entity.DamageListGoods;
import com.hengyue.entity.User;
import com.hengyue.service.LogService;
import com.hengyue.service.DamageListGoodsService;
import com.hengyue.service.DamageListService;
import com.hengyue.service.UserService;
import com.hengyue.utils.DateUtil;
import com.hengyue.utils.StringUtils;

/**
 * 商品报损管理控制层
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/damageList")
public class DamageListAdminController {

	@Resource
	private LogService logService;
	
	@Resource
	private DamageListService damageListService;
	
	@Resource
	private DamageListGoodsService damageListGoodsService;
	
	@Resource
	private UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	/**
	 * 获取商品报损单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/genCode")
	@RequiresPermissions(value = "商品报损")
	public String genCode() throws Exception {
		StringBuffer code = new StringBuffer("BS");
		code.append(DateUtil.getCurrentDateStr());
		String damageNumber = damageListService.getTodayMaxDamageNumber();
		if(StringUtils.isNotEmpty(damageNumber)) {
			code.append(StringUtils.formatCode(damageNumber));
		}else{
			code.append("0001");
		}
		return code.toString();
	}
	
	/**
	 * 添加商品报损单以及所有商品报损单商品
	 * @param damageList
	 * @param goodsJson
	 * @return
	 * @throws Excepiton
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "商品报损")
	public Map<String, Object> save(DamageList damageList, String goodsJson) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal());			//获取操作员
		Gson gson = new Gson();
		damageList.setUser(user);
		List<DamageListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<DamageListGoods>>() {}.getType());
		damageListService.save(damageList, plgList);
		logService.save(new Log(Log.ADD_ACTION, "添加商品报损单"));
		map.put("success", true);
		return map;
	}
	/**
	 * 根据条件查询所有商品报损单查询
	 * @param damageList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="报损报溢查询")
	public Map<String, Object> list(DamageList damageList) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<DamageList> damageListList = damageListService.list(damageList, Direction.DESC, "damageDate");
		map.put("rows", damageListList);
		logService.save(new Log(Log.SEARCH_ACTION,"商品报损单查询"));
		return map;
	}
	
	/**
	 * 根据商品报损单id查询所有商品报损单商品
	 * @param damageListId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listGoods")
	@RequiresPermissions(value="报损报溢查询")
	public Map<String, Object> listGoods(Integer damageListId) throws Exception{
		if(damageListId == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<DamageListGoods> damageGoodsListList = damageListGoodsService.listByDamageListId(damageListId);
		map.put("rows", damageGoodsListList);
		logService.save(new Log(Log.SEARCH_ACTION,"商品报损单查询"));
		return map;
	}
	
}
