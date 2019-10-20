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

import com.hengyue.entity.Goods;
import com.hengyue.entity.Log;
import com.hengyue.service.CustomerReturnListGoodsService;
import com.hengyue.service.GoodsService;
import com.hengyue.service.LogService;
import com.hengyue.service.SaleListGoodsService;
import com.hengyue.utils.StringUtils;

/**
 * 商品控制层
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/goods")
public class GoodsAdminController {

	@Resource
	private LogService logService;
	
	@Resource
	private GoodsService goodsService;
	
	@Resource
	private SaleListGoodsService saleListGoodsService;
	
	@Resource
	private CustomerReturnListGoodsService customerReturnListGoodsService;
	/**
	 * 分页查询商品信息
	 * @param goods
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value= {"商品管理", "进货入库"}, logical = Logical.OR)
	public Map<String, Object> list(Goods goods, @RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Goods> goodsList = goodsService.list(goods, page, rows, Direction.ASC, "id");
		Long total = goodsService.getCount(goods);
		map.put("rows", goodsList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息"));
		return map;
	}
	/**
	 * 分页查询商品库存信息
	 * @param goods
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listInventory")
	@RequiresPermissions(value="当前库存查询")
	public Map<String, Object> listInventory(Goods goods, @RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Goods> goodsList = goodsService.list(goods, page, rows, Direction.ASC, "id");
		for(Goods g : goodsList) {
			g.setSaleTotal(saleListGoodsService.getTotalByGoodsId(g.getId() - customerReturnListGoodsService.getTotalByGoodsId(g.getId())));
		}
		Long total = goodsService.getCount(goods);
		map.put("rows", goodsList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询商品信息"));
		return map;
	}
	/**
	 * 删除商品信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value = "商品管理")
	public Map<String, Object> delete(Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		Goods goods = goodsService.findById(id);
		if(goods.getState() == 1) {
			map.put("errorInfo", "该商品已经期初入库，不能删除");
			map.put("success", false);
		}else if(goods.getState() == 2) {
			map.put("errorInfo", "该商品已经发生单据，不能删除");
			map.put("success", false);
		}else {
			logService.save(new Log(Log.DELETE_ACTION, "删除商品信息" + goodsService.findById(id)));
			goodsService.delete(id);
			map.put("success", true);
		}
		return map;
	}
	/**
	 * 修改或者删除商品信息
	 * @param goods
	 * @return
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "商品管理")
	public Map<String, Object> delete(Goods goods){
		Map<String, Object> map = new HashMap<String, Object>();
		if(goods.getId() != null) {
			logService.save(new Log(Log.UPDATE_ACTION, "修改商品信息" + goods));
		}else {
			logService.save(new Log(Log.ADD_ACTION, "添加商品信息" + goods));
			goods.setLastPurchasingPrice(goods.getPurchasingPrice());//设置上次进价为当前进价
		}
		goodsService.save(goods);
		map.put("success", true);
		return map;
	}
	/**
	 * 生产商品编码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/genGoodsCode")
	@RequiresPermissions(value = "商品管理")
	public String genGoodsCode() throws Exception{
		String maxGoodsCode = goodsService.getMaxGoodsCode();
		if(StringUtils.isNotEmpty(maxGoodsCode)) {
			Integer code = Integer.parseInt(maxGoodsCode) + 1;
			String codes = code.toString();
			int length = codes.length();
			for(int i = 4; i > length; i--) {
				
				codes = "0" + codes;
			}
			return codes;
		}else {
			return "0001";
		}
	}
	
	/**
	 * 分页查询没有库存商品信息
	 * @param codeOrName
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listNoInventoryQuantity")
	@RequiresPermissions(value = "期初库存")
	public Map<String, Object> listNoInventoryQuantity(@RequestParam(value = "codeOrName", required = false)String codeOrName, 
			@RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Goods> goodsList = goodsService.listNoInventoryQuantityByCodeOrName(codeOrName, page, rows, Direction.ASC, "id");
		Long total = goodsService.getCountNoInventoryQuantityByCodeOrName(codeOrName);
		map.put("rows", goodsList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询没有库存商品信息"));
		return map;
	}
	/**
	 * 分页查询有库存商品信息
	 * @param codeOrName
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listHasInventoryQuantity")
	@RequiresPermissions(value = "期初库存")
	public Map<String, Object> listHasInventoryQuantity( @RequestParam(value = "codeOrName", required = false)String codeOrName, @RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<Goods> goodsList = goodsService.listHasInventoryQuantityByCodeOrName(codeOrName, page, rows, Direction.ASC, "id");
		Long total = goodsService.getCountHasInventoryQuantityByCodeOrName(codeOrName);
		map.put("rows", goodsList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询有库存商品信息"));
		return map;
	}
	
	/**
	 * 期初库存
	 * 添加商品到仓库
	 * 添加库存和价格到库存信息
	 * @param id
	 * @param num
	 * @param price
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveStore")
	@RequiresPermissions(value = "期初库存")
	public Map<String, Object> saveStore(Integer id, Integer num, Float price) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Goods goods = goodsService.findById(id);
		goods.setInventoryQuantity(num);
		goods.setPurchasingPrice(price);
		goods.setLastPurchasingPrice(price);
		goodsService.save(goods);
		logService.save(new Log(Log.UPDATE_ACTION, "修改商品信息：" + goods + ", 价格=" + price + ", 库存=" + num));
		map.put("success", true);
		return map;
	}
	/**
	 * 删除商品库存信息
	 * 把库存设置为0
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteStock")
	@RequiresPermissions(value = "期初库存")
	public Map<String, Object> deleteStock(Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		Goods goods = goodsService.findById(id);
		if(goods.getState() == 2) {
			map.put("errorInfo", "该商品已经发生单据，不能删除");
			map.put("success", false);
		}else {
			goods.setInventoryQuantity(0);
			logService.save(new Log(Log.UPDATE_ACTION, "修改商品信息" + goods));
			goodsService.save(goods);
			map.put("success", true);
		}
		return map;
	}
}
