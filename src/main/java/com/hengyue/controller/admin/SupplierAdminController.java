package com.hengyue.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hengyue.entity.Log;
import com.hengyue.entity.Supplier;
import com.hengyue.service.LogService;
import com.hengyue.service.SupplierService;
/**
 * 供应商控制层
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/supplier")
public class SupplierAdminController {

	@Resource
	private SupplierService supplierService;
	
	@Resource
	private LogService logService;
	/**
	 * 分页查询供应商资料
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value = "供应商管理")
	public Map<String, Object> list(Supplier supplier, @RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "rows", required = false) Integer rows){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Supplier> supplierList = supplierService.list(supplier, page, rows, Direction.ASC, "id");
		Long count = supplierService.getCount(supplier);
		map.put("rows", supplierList);
		map.put("total", count);
		logService.save(new Log(Log.SEARCH_ACTION, "查询所有供应商信息"));
		return map;
	}
	/**
	 * 删除供应商信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value = "供应商管理")
	public Map<String, Object> delete(String ids){
		String[] idStr = ids.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		for(String id: idStr) {
			logService.save(new Log(Log.DELETE_ACTION, "删除供应商信息" + supplierService.findById(Integer.parseInt(id))));
			supplierService.delete(Integer.parseInt(id));
		}
		map.put("success", true);
		return map;
	}
	/**
	 * 添加或者修改供应商信息
	 * @param supplier
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "供应商管理")
	public Map<String, Object> save(Supplier supplier){
		Map<String, Object> map = new HashMap<String, Object>();
		supplierService.save(supplier);
		if(supplier.getId() == null) {
			if(supplierService.findBySupplierName(supplier.getName()) != null) {
				map.put("success", false);
				map.put("errorInfo", "该供应商已经存在");
				return map;
			}
		}
		if(supplier.getId() != null) {
			logService.save(new Log(Log.DELETE_ACTION, "修改供应商信息" + supplier.getName()));
		}else {
			logService.save(new Log(Log.DELETE_ACTION, "添加供应商信息" + supplier.getName()));
		}
		map.put("success", true);
		return map;
	}
	/**
	 * 下拉框模糊查询
	 * @param q
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/comboList")
	@RequiresPermissions(value = {"进货入库", "退货出库", "进货单据查询", "退货单据查询"}, logical = Logical.OR)
	public List<Supplier> comboList(String q) throws Exception{
		if(StringUtils.isEmpty(q)) {
			q = "";
		}
		return supplierService.findByName("%" + q + "%");
	}
}
