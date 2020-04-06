package com.hengyue.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hengyue.entity.Log;
import com.hengyue.entity.vo.CkGoods;
import com.hengyue.entity.vo.CkGoodsType;
import com.hengyue.entity.Customer;
import com.hengyue.service.LogService;
import com.hengyue.utils.DateUtils;
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
	
	/**
	 * 批量导入ck商品 把库存设置为0
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/upload")
	@RequiresPermissions(value = "客户管理")
	public Map<String, Object> uploadCkGoods(@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		InputStream is = null;
		Workbook workBook = null;
		try {
			String type = null;
			is = uploadFile.getInputStream();
			int len = uploadFile.getOriginalFilename().split("\\.").length;
			if( len >= 2) {
				type = uploadFile.getOriginalFilename().split("\\.")[1];
			}else {
				type = uploadFile.getOriginalFilename().split("\\.")[0];
			}
//			if(!"xls".equals(type) || !"XLS".equals(type) || !"xlsx".equals(type) || !"XLSX".equals(type)) {
//				map.put("success", false);
//				map.put("errorInfo", "需要您导入xls或者xlsx类型的文件");
//				return map;
//			}
			if ("xlsx".equals(type) || "XLSX".equals(type)) {
				workBook = new XSSFWorkbook(is); // 使用XSSFWorkbook
			} else if ("xls".equals(type) || "XLS".equals(type)) {
				workBook = new HSSFWorkbook(is, true); // 使用HSSFWorkbook 构造函数略有不同 true表示转化成为Nodes
			}
			// 清理开始
			Sheet sheet = workBook.getSheetAt(0); // 获取第一个sheet
			List<String> firstRow = new ArrayList<String>();
			int i = 0;
			for (Row row : sheet) {
				String code = "";
				int j = 0;
				if (i == 0) {
					for (Cell cell : row) { // 遍历当前行的所有cell
						cell.setCellType(CellType.STRING);
						firstRow.add(cell.getRichStringCellValue().getString());
					}
				}
				if (i != 0) {
					Customer customer = null;
					for (int index = 0; index < firstRow.size(); index++) {
						Cell cell = row.getCell(index);
						if (cell == null) {
							j++;
						} else {
							switch (firstRow.get(j)) {
							case "代码":
								customer = customerService.findCode(cell.getRichStringCellValue().getString());
								if(customer == null) {
									customer = new Customer();
								}
								code = cell.getRichStringCellValue().getString();
								customer.setCode(code);
								break;
							case "名称":
								customer.setName(cell.getRichStringCellValue().getString());
								break;
							case "地址":
								customer.setAddress(cell.getRichStringCellValue().getString());
								break;
							case "联系人":
								customer.setContact(cell.getRichStringCellValue().getString());
								break;
							case "电话":
								customer.setNumber(cell.getRichStringCellValue().getString());
								break;
							case "移动电话":
								customer.setTelephone(cell.getRichStringCellValue().getString());
								break;
							case "全球唯一标识内码":
								customer.setUuid(cell.getRichStringCellValue().getString());
								break;
							}
							j++;
						}
					}
						customerService.save(customer);
					}
				i++;
				}
			// 清理结束
			map.put("success", true);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			map.put("errorInfo", e.getMessage());
			map.put("success", false);
			return map;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (workBook != null) {
				try {
					workBook.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
}
