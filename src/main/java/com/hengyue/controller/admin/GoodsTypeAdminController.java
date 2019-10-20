package com.hengyue.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hengyue.entity.GoodsType;
import com.hengyue.entity.Log;
import com.hengyue.service.GoodsService;
import com.hengyue.service.GoodsTypeService;
import com.hengyue.service.LogService;
/**
 * 商品类别控制层
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/goodsType")
public class GoodsTypeAdminController {

	@Resource
	private GoodsTypeService goodsTypeService;
	
	@Resource
	private LogService logService;
	
	@Resource
	private GoodsService goodsService;
	
	/**
	 * 添加商品类别
	 * @param name
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value= {"商品管理", "进货入库", "销售出库", "客户退货"}, logical = Logical.OR)
	public Map<String, Object> save(String name, Integer parentId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		GoodsType goodsType = new GoodsType();
		goodsType.setName(name);
		goodsType.setState(0);
		goodsType.setPid(parentId);
		goodsType.setIcon("icon-forder");
		goodsTypeService.save(goodsType);
		GoodsType parentGoodsType = goodsTypeService.findById(parentId);
		parentGoodsType.setState(1);//设置为非也在节点
		logService.save(new Log(Log.ADD_ACTION,"添加商品类别信息"));
		map.put("success", true);
		return map;
	}
	/**
	 * 商品类别删除
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value= {"商品管理", "进货入库","销售出库", "客户退货"}, logical = Logical.OR)
	public Map<String, Object> delete(Integer id) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(goodsService.findByTypeId(id).size() == 0) {
			GoodsType currentGoodsType = goodsTypeService.findById(id);
			if(goodsTypeService.findByParentId(currentGoodsType.getPid()).size() == 1) {
				GoodsType parentGoodsType = goodsTypeService.findById(currentGoodsType.getPid());
				parentGoodsType.setState(0);
				goodsTypeService.save(parentGoodsType);
			}
			logService.save(new Log(Log.UPDATE_ACTION,"删除商品类别信息" + currentGoodsType));
			goodsTypeService.delete(id);
			map.put("success", true);
		}else {
			map.put("success", false);
			map.put("errorMsg", "该商品下含有商品， 不能删除！");
		}
		return map;
		
	}

	/**
	 * 根据父节点获取所有复选框权限菜单
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadTreeInfo")
	@RequiresPermissions(value={"商品管理","进货入库","退货出库","销售出库","客户退货","当前库存查询","商品报损","商品报溢","商品采购统计","商品销售统计"},logical=Logical.OR)
	public String loadTreeInfo()throws Exception{
		logService.save(new Log(Log.SEARCH_ACTION,"查询所有商品类别信息"));
		return getAllByParentId(-1).toString();
	}
	/**
	 * 根据父节点id和权限菜单id集合获取所有复选框菜单集合
	 * @param parentId
	 * @return
	 */
	public JsonArray getAllByParentId(Integer parentId){
		JsonArray jsonArray=this.getByParentId(parentId);
		for(int i=0;i<jsonArray.size();i++){
			JsonObject jsonObject=(JsonObject) jsonArray.get(i);
			if("open".equals(jsonObject.get("state").getAsString())){
				continue;
			}else{
				jsonObject.add("children", getAllByParentId(jsonObject.get("id").getAsInt()));
			}
		}
		return jsonArray;
	}
	
	/**
	 * 根据父节点查询所有子节点
	 * @param parentId
	 * @return
	 */
	public JsonArray getByParentId(Integer parentId){
		List<GoodsType> goodsTypeList=goodsTypeService.findByParentId(parentId);
		JsonArray jsonArray=new JsonArray();
		for(GoodsType goodsType:goodsTypeList){
			JsonObject jsonObject=new JsonObject();
			jsonObject.addProperty("id", goodsType.getId()); // 节点Id
			jsonObject.addProperty("text", goodsType.getName()); // 节点名称
			if(goodsType.getState()==1){
				jsonObject.addProperty("state", "closed"); // 根节点
			}else{
				jsonObject.addProperty("state", "open"); // 叶子节点
			}
			jsonObject.addProperty("iconCls", goodsType.getIcon()); // 节点图标
			JsonObject attributeObject=new JsonObject(); // 扩展属性
			attributeObject.addProperty("state", goodsType.getState()); // 节点状态
			jsonObject.add("attributes", attributeObject); 
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
}
