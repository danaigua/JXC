package com.hengyue.controller.admin.vo;

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

import com.hengyue.entity.vo.CkGoods;
import com.hengyue.entity.vo.CkGoodsType;
import com.hengyue.entity.Log;
import com.hengyue.service.CustomerReturnListGoodsService;
import com.hengyue.service.vo.CkGoodsService;
import com.hengyue.service.vo.CkGoodsTypeService;
import com.hengyue.service.LogService;
import com.hengyue.service.SaleListGoodsService;
import com.hengyue.utils.DateUtils;
import com.hengyue.utils.StringUtils;

/**
 * 商品控制层
 * 
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/ckGoods")
public class CkGoodsAdminController {

	@Resource
	private LogService logService;

	@Resource
	private CkGoodsService ckGoodsService;
	
	@Resource
	private CkGoodsTypeService ckGoodsTypeService;

	@Resource
	private SaleListGoodsService saleListGoodsService;

	@Resource
	private CustomerReturnListGoodsService customerReturnListGoodsService;

	/**
	 * 分页查询商品信息
	 * 
	 * @param ckGoods
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value = { "商品管理", "进货入库", "销售出库", "客户退货", "商品报损", "商品报溢" }, logical = Logical.OR)
	public Map<String, Object> list(CkGoods ckGoods, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<CkGoods> ckGoodsList = ckGoodsService.list(ckGoods, page, rows, Direction.ASC, "id");
		Long total = ckGoodsService.getCount(ckGoods);
		map.put("rows", ckGoodsList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息"));
		return map;
	}

	/**
	 * 修改或者删除商品信息
	 * 
	 * @param ckGoods
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "商品管理")
	public Map<String, Object> delete(CkGoods ckGoods) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (ckGoods.getId() != null) {
			logService.save(new Log(Log.UPDATE_ACTION, "修改商品信息" + ckGoods));
		} else {
			logService.save(new Log(Log.ADD_ACTION, "添加商品信息" + ckGoods));
			ckGoods.setLastPurchasingPrice(ckGoods.getPurchasingPrice());// 设置上次进价为当前进价
		}
		ckGoodsService.save(ckGoods);
		map.put("success", true);
		return map;
	}

	/**
	 * 生产商品编码
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/genCkGoodsCode")
	@RequiresPermissions(value = "商品管理")
	public String genCkGoodsCode() throws Exception {
		String maxCkGoodsCode = ckGoodsService.getMaxCkGoodsCode();
		if (StringUtils.isNotEmpty(maxCkGoodsCode)) {
			Integer code = Integer.parseInt(maxCkGoodsCode) + 1;
			String codes = code.toString();
			int length = codes.length();
			for (int i = 4; i > length; i--) {

				codes = "0" + codes;
			}
			return codes;
		} else {
			return "0001";
		}
	}

	/**
	 * 删除商品库存信息 把库存设置为0
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteStock")
	@RequiresPermissions(value = "期初库存")
	public Map<String, Object> deleteStock(Integer id) {
		Map<String, Object> map = new HashMap<String, Object>();
		CkGoods ckGoods = ckGoodsService.findById(id);
		if (ckGoods.getState() == 2) {
			map.put("errorInfo", "该商品已经发生单据，不能删除");
			map.put("success", false);
		} else {
			ckGoods.setInventoryQuantity(0);
			logService.save(new Log(Log.UPDATE_ACTION, "修改商品信息" + ckGoods));
			ckGoodsService.save(ckGoods);
			map.put("success", true);
		}
		return map;
	}

	/**
	 * 批量导入ck商品 把库存设置为0
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/uploadCkGoods")
	@RequiresPermissions(value = "商品管理")
	public Map<String, Object> uploadCkGoods(@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		InputStream is = null;
		Workbook workBook = null;
		try {
			String formatname = null;
			String type = null;
			is = uploadFile.getInputStream();
			int len = uploadFile.getOriginalFilename().split("\\.").length;
			if( len >= 2) {
				formatname = DateUtils.getCurrentTime()  + "." +  uploadFile.getOriginalFilename().split("\\.")[1];
				type = uploadFile.getOriginalFilename().split("\\.")[1];
			}else {
				formatname = DateUtils.getCurrentTime()  + "." +  uploadFile.getOriginalFilename().split("\\.")[0];
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
				boolean flag = false;
				int j = 0;
				if (i == 0) {
					for (Cell cell : row) { // 遍历当前行的所有cell
						cell.setCellType(CellType.STRING);
						firstRow.add(cell.getRichStringCellValue().getString());
					}
				}
				if (i != 0) {
					CkGoodsType ckGoodsType = null;
					CkGoods ckGoods = null;
					for (int index = 0; index < firstRow.size(); index++) {
						Cell cell = row.getCell(index);
						if (cell == null) {
							j++;
						} else {
							switch (firstRow.get(j)) {
							case "代码":
								ckGoods = ckGoodsService.findBymodelName(cell.getRichStringCellValue().getString());
								if(ckGoods == null) {
									ckGoods = new CkGoods();
								}
								ckGoods.setModel(cell.getRichStringCellValue().getString());
								code = cell.getRichStringCellValue().getString();
								break;
							case "名称":
								ckGoods.setName(cell.getRichStringCellValue().getString());
								//查看代码是否存在
								if(code.length() < 9) {
									ckGoodsType = ckGoodsTypeService.findByModelName(code);
									//查看
									if(ckGoodsType == null) {
										//查看类型是否存在，不存在那么就改为存储类型
										ckGoodsType = new CkGoodsType();
										if(code.length() == 4) {
											CkGoodsType newCkGoodsType = ckGoodsTypeService.findByModelName(code.substring(0, 1));
											newCkGoodsType.setState(1);
											newCkGoodsType.setIcon("icon-folderOpen");
											ckGoodsTypeService.save(newCkGoodsType);
										}else if(code.length() == 8) {
											CkGoodsType newCkGoodsType = ckGoodsTypeService.findByModelName(code.substring(0, 4));
											newCkGoodsType.setState(1);
											newCkGoodsType.setIcon("icon-folderOpen");
											ckGoodsTypeService.save(newCkGoodsType);
										}
										ckGoodsType.setName(cell.getRichStringCellValue().getString());
										ckGoodsType.setState(0);
										ckGoodsType.setIcon("icon-folder");
										if(code.length() == 1) {
											//建立pid为1的根目录
											ckGoodsType.setpId(1);
										}else if(code.length() == 4) {
											ckGoodsType.setpId(ckGoodsTypeService.findByModel(code.subSequence(0, 1)));
										}else if(code.length() == 8) {
											ckGoodsType.setpId(ckGoodsTypeService.findByModel(code.subSequence(0, 4)));
										}
										ckGoodsType.setModel(code);
										ckGoodsTypeService.save(ckGoodsType);
									}
								}else {
									flag = true;
								}
								break;
							case "物料全名":
								ckGoods.setAllName(cell.getRichStringCellValue().getString());
								break;
							case "销售单价":
								try {
									float f = Float.parseFloat(cell.getRichStringCellValue().getString());
									ckGoods.setSellingPrice(f);
								}catch( Exception e){
									ckGoods.setSellingPrice(0f);
								}
								break;
							case "规格型号":
								ckGoods.setCode(cell.getRichStringCellValue().getString());
								break;
							case "基本计量单位_FName":
								ckGoods.setUnit(cell.getRichStringCellValue().getString());
								break;
							case "全球唯一标识内码":
								ckGoods.setUuid(cell.getRichStringCellValue().getString());
								break;
							}
							j++;
						}
					}
					if(flag) {
						if(code.length() > 9) {
							CkGoodsType ckGoodsType1 = ckGoodsTypeService.findByModelName(code.substring(0, 8));
							ckGoods.setType(ckGoodsType1);
						}
						ckGoodsService.save(ckGoods);
					}
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
		}
	}
}
