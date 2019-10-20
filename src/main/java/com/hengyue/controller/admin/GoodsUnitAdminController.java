package com.hengyue.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hengyue.entity.GoodsUnit;
import com.hengyue.entity.Log;
import com.hengyue.service.GoodsUnitService;
import com.hengyue.service.LogService;

/**
 * 商品单位控制层
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/goodsUnit")
public class GoodsUnitAdminController {

	@Resource
	private GoodsUnitService goodsUnitService;
	
	@Resource
	private LogService logService;
	
	/**
	 * 添加商品单位信息
	 * @param goodsUnit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "商品管理")
	public Map<String, Object> save(GoodsUnit goodsUnit) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		goodsUnitService.save(goodsUnit);
		logService.save(new Log(Log.ADD_ACTION, "添加商品单位"));
		map.put("success", true);
		return map;
	}
	
	/**
	 * 删除商品单位信息
	 * @param goodsUnit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value = "商品管理")
	public Map<String, Object> save(Integer id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		goodsUnitService.delete(id);
		logService.save(new Log(Log.DELETE_ACTION, "删除商品单位"));
		map.put("success", true);
		return map;
	}
	
	/**
	 * 列出所有商品单位信息
	 * 下拉框用到
	 * @param goodsUnit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/comboList")
	@RequiresPermissions(value = "商品管理")
	public List<GoodsUnit> save() throws Exception{
		logService.save(new Log(Log.SEARCH_ACTION, "查询商品单位"));
		return goodsUnitService.listAll();
	}
	/**
	 * 列出所有商品单位信息
	 * @param goodsUnit
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listAll")
	@RequiresPermissions(value = "商品管理")
	public Map<String, Object> listAll() throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		logService.save(new Log(Log.SEARCH_ACTION, "查询商品单位"));
		map.put("rows", goodsUnitService.listAll());
		return map;
	}
}
