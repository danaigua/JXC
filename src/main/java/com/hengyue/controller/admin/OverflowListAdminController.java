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
import com.hengyue.entity.OverflowList;
import com.hengyue.entity.OverflowListGoods;
import com.hengyue.entity.User;
import com.hengyue.service.LogService;
import com.hengyue.service.OverflowListGoodsService;
import com.hengyue.service.OverflowListService;
import com.hengyue.service.UserService;
import com.hengyue.utils.DateUtil;
import com.hengyue.utils.StringUtils;

/**
 * 商品报溢管理控制层
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/overflowList")
public class OverflowListAdminController {

	@Resource
	private LogService logService;
	
	@Resource
	private OverflowListService overflowListService;
	
	@Resource
	private OverflowListGoodsService overflowListGoodsService;
	
	@Resource
	private UserService userService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	/**
	 * 获取商品报溢单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/genCode")
	@RequiresPermissions(value = "商品报溢")
	public String genCode() throws Exception {
		StringBuffer code = new StringBuffer("BS");
		code.append(DateUtil.getCurrentDateStr());
		String overflowNumber = overflowListService.getTodayMaxOverflowNumber();
		if(StringUtils.isNotEmpty(overflowNumber)) {
			code.append(StringUtils.formatCode(overflowNumber));
		}else{
			code.append("0001");
		}
		return code.toString();
	}
	
	/**
	 * 添加商品报溢单以及所有商品报溢单商品
	 * @param overflowList
	 * @param goodsJson
	 * @return
	 * @throws Excepiton
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "商品报溢")
	public Map<String, Object> save(OverflowList overflowList, String goodsJson) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal());			//获取操作员
		Gson gson = new Gson();
		overflowList.setUser(user);
		List<OverflowListGoods> plgList = gson.fromJson(goodsJson, new TypeToken<List<OverflowListGoods>>() {}.getType());
		overflowListService.save(overflowList, plgList);
		logService.save(new Log(Log.ADD_ACTION, "添加商品报溢单"));
		map.put("success", true);
		return map;
	}
	/**
	 * 根据条件查询所有商品报溢单查询
	 * @param overflowList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="报损报溢查询")
	public Map<String, Object> list(OverflowList overflowList) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<OverflowList> overflowListList = overflowListService.list(overflowList, Direction.DESC, "overflowDate");
		map.put("rows", overflowListList);
		logService.save(new Log(Log.SEARCH_ACTION,"商品报溢单查询"));
		return map;
	}
	
	/**
	 * 根据商品报溢单id查询所有商品报溢单商品
	 * @param overflowListId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listGoods")
	@RequiresPermissions(value="报损报溢查询")
	public Map<String, Object> listGoods(Integer overflowListId) throws Exception{
		if(overflowListId == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<OverflowListGoods> overflowGoodsListList = overflowListGoodsService.listByOverflowListId(overflowListId);
		map.put("rows", overflowGoodsListList);
		logService.save(new Log(Log.SEARCH_ACTION,"商品报溢单查询"));
		return map;
	}
	
}
