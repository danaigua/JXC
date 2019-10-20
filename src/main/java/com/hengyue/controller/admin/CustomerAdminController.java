package com.hengyue.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hengyue.entity.Log;
import com.hengyue.entity.Customer;
import com.hengyue.service.LogService;
import com.hengyue.service.CustomerService;
/**
 * 客户控制层
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/customer")
public class CustomerAdminController {

	@Resource
	private CustomerService customerService;
	
	@Resource
	private LogService logService;
	/**
	 * 分页查询客户资料
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value = "客户管理")
	public Map<String, Object> list(Customer Customer, @RequestParam(value = "page", required = false) Integer page, 
			@RequestParam(value = "rows", required = false) Integer rows){
		Map<String, Object> map = new HashMap<String, Object>();
		List<Customer> customerList = customerService.list(Customer, page, rows, Direction.ASC, "id");
		Long count = customerService.getCount(Customer);
		map.put("rows", customerList);
		map.put("total", count);
		logService.save(new Log(Log.SEARCH_ACTION, "查询所有客户信息"));
		return map;
	}
	/**
	 * 删除客户信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value = "客户管理")
	public Map<String, Object> delete(String ids){
		String[] idStr = ids.split(",");
		Map<String, Object> map = new HashMap<String, Object>();
		for(String id: idStr) {
			logService.save(new Log(Log.DELETE_ACTION, "删除客户信息" + customerService.findById(Integer.parseInt(id))));
			customerService.delete(Integer.parseInt(id));
		}
		map.put("success", true);
		return map;
	}
	/**
	 * 添加或者修改客户信息
	 * @param Customer
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "客户管理")
	public Map<String, Object> save(Customer customer){
		Map<String, Object> map = new HashMap<String, Object>();
		customerService.save(customer);
		if(customer.getId() == null) {
			if(customerService.findByCustomerName(customer.getName()) != null) {
				map.put("success", false);
				map.put("errorInfo", "该客户已经存在");
				return map;
			}
		}
		if(customer.getId() != null) {
			logService.save(new Log(Log.DELETE_ACTION, "修改客户信息" + customer.getName()));
		}else {
			logService.save(new Log(Log.DELETE_ACTION, "添加客户信息" + customer.getName()));
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
	@RequiresPermissions(value = {"销售出库", "客户退货", "销售单据查询", "客户退货查询","客户统计"}, logical = Logical.OR)
	public List<Customer> comboList(String q) throws Exception{
		if(q==null) {
			q = "";
		}
		return customerService.findByName("%" + q + "%");
	}
}
