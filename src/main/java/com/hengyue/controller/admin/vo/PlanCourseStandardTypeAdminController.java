package com.hengyue.controller.admin.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hengyue.entity.vo.PlanCourseStandard;
import com.hengyue.entity.vo.PlanCourseStandardType;
import com.hengyue.entity.Log;
import com.hengyue.entity.User;
import com.hengyue.service.GoodsService;
import com.hengyue.service.vo.PlanCourseStandardTypeService;
import com.hengyue.service.vo.PlanCourseStandardService;
import com.hengyue.service.LogService;

/**
 * 文件夹类别控制层
 * 
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/planCourseStandardType")
public class PlanCourseStandardTypeAdminController {

	@Resource
	private PlanCourseStandardTypeService planCourseStandardTypeService;

	@Resource
	private PlanCourseStandardService planCourseStandardService;

	@Resource
	private LogService logService;

	@Resource
	private GoodsService goodsService;

	/**
	 * 添加文件夹类别
	 * 
	 * @param name
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "项目规划")
	public Map<String, Object> save(String name, Integer parentId, HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User)session.getAttribute("currentUser");
		if(user == null || user.getId() > 5) {
			map.put("success", false);
			map.put("errorInfo", "添加失败，原因是您没有权限删除该标准类别！");
			return map;
		}
		PlanCourseStandardType planCourseStandardType = new PlanCourseStandardType();
		planCourseStandardType.setName(name);
		planCourseStandardType.setState(0);
		planCourseStandardType.setpId(parentId);
		planCourseStandardType.setIcon("icon-forder");
		planCourseStandardTypeService.save(planCourseStandardType);
		PlanCourseStandardType parentPlanCourseStandardType = planCourseStandardTypeService.findById(parentId);
		parentPlanCourseStandardType.setState(1);// 设置为非也在节点
		planCourseStandardTypeService.save(parentPlanCourseStandardType);
		logService.save(new Log(Log.ADD_ACTION, "添加文件夹类别信息"));
		map.put("success", true);
		return map;
	}

	/**
	 * 文件夹类别删除
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value = "项目规划")
	public Map<String, Object> delete(Integer id, HttpSession session) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		//需要顺便删除所邦定的标准
		PlanCourseStandard planCourseStandard = new PlanCourseStandard();
		PlanCourseStandardType planCourseStandardType = new PlanCourseStandardType();
		planCourseStandardType.setId(id);
		planCourseStandard.setPlanCourseStandardType(planCourseStandardType);
		Long l = planCourseStandardService.getCount(planCourseStandard);
		if(l > 0) {
			map.put("success", false);
			map.put("errorInfo", "删除失败，原因是该标准类别还绑定其他的标准！");
			return map;
		}
		User user = (User)session.getAttribute("currentUser");
		if(user == null || user.getId() > 5) {
			map.put("success", false);
			map.put("errorInfo", "删除失败，原因是您没有权限删除该标准类别！");
			return map;
		}
		
		
		PlanCourseStandardType currentPlanCourseStandardType = planCourseStandardTypeService.findById(id);
		if (planCourseStandardTypeService.findByParentId(currentPlanCourseStandardType.getpId()).size() == 1) {
			PlanCourseStandardType parentPlanCourseStandardType = planCourseStandardTypeService.findById(currentPlanCourseStandardType.getpId());
			parentPlanCourseStandardType.setState(0);
			planCourseStandardTypeService.save(parentPlanCourseStandardType);
		}
		logService.save(new Log(Log.UPDATE_ACTION, "删除文件夹类别信息" + currentPlanCourseStandardType));
		planCourseStandardTypeService.delete(id);
		map.put("success", true);
		return map;

	}

	/**
	 * 根据父节点获取所有复选框权限菜单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadTreeInfo")
	@RequiresPermissions(value = "项目规划")
	public String loadTreeInfo() throws Exception {
		logService.save(new Log(Log.SEARCH_ACTION, "查询所有文件夹类别信息"));
		return getAllByParentId(-1).toString();
	}

	/**
	 * 根据父节点id和权限菜单id集合获取所有复选框菜单集合
	 * 
	 * @param parentId
	 * @return
	 */
	public JsonArray getAllByParentId(Integer parentId) {
		JsonArray jsonArray = this.getByParentId(parentId);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jsonObject = (JsonObject) jsonArray.get(i);
			if ("open".equals(jsonObject.get("state").getAsString())) {
				continue;
			} else {
				jsonObject.add("children", getAllByParentId(jsonObject.get("id").getAsInt()));
			}
		}
		return jsonArray;
	}

	/**
	 * 根据父节点查询所有子节点
	 * 
	 * @param parentId
	 * @return
	 */
	public JsonArray getByParentId(Integer parentId) {
		List<PlanCourseStandardType> planCourseStandardTypeList = planCourseStandardTypeService.findByParentId(parentId);
		JsonArray jsonArray = new JsonArray();
		for (PlanCourseStandardType planCourseStandardType : planCourseStandardTypeList) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", planCourseStandardType.getId()); // 节点Id
			jsonObject.addProperty("text", planCourseStandardType.getName()); // 节点名称
			
			if (planCourseStandardType.getState() == 1) {
				jsonObject.addProperty("state", "closed"); // 根节点
			} else {
				jsonObject.addProperty("state", "open"); // 叶子节点
			}
			jsonObject.addProperty("iconCls", planCourseStandardType.getIcon()); // 节点图标
			JsonObject attributeObject = new JsonObject(); // 扩展属性
//			attributeObject.addProperty("state", planCourseStandardType.getState()); // 节点状态
			attributeObject.addProperty("state", 0); // 节点状态
			jsonObject.add("attributes", attributeObject);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
}
