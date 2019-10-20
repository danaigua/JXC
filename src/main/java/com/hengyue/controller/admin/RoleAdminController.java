package com.hengyue.controller.admin;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hengyue.entity.Log;
import com.hengyue.entity.Menu;
import com.hengyue.entity.Role;
import com.hengyue.entity.RoleMenu;
import com.hengyue.service.LogService;
import com.hengyue.service.MenuService;
import com.hengyue.service.RoleMenuService;
import com.hengyue.service.RoleService;
import com.hengyue.service.UserRoleService;
import com.hengyue.utils.StringUtils;

/**
 * 后台管理角色controller
 * 
 * @author 章家宝
 *
 */
@RestController
@RequestMapping("/admin/role")
public class RoleAdminController {

	@Resource
	private RoleService roleService;

	@Resource
	private UserRoleService userRoleService;

	@Resource
	private RoleMenuService roleMenuService;

	@Resource
	private MenuService menuService;
	
	@Resource
	private LogService logService;
	/**
	 * 查询所有角色
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listAll")
	@RequiresPermissions(value = { "用户管理", "角色管理" }, logical = Logical.OR)
	public Map<String, Object> listAll() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", roleService.listAll());
		logService.save(new Log(Log.SEARCH_ACTION, "查询所有用户角色信息" ));
		return map;
	}

	/**
	 * 分页查询角色信息
	 * 
	 * @param user
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value = "角色管理")
	public Map<String, Object> list(Role role, @RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "rows", required = false) Integer rows) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Role> roleList = roleService.list(role, page, rows, Direction.ASC, "id");
		Long total = roleService.getCount(role);
		map.put("rows", roleList);
		map.put("total", total);
		logService.save(new Log(Log.UPDATE_ACTION, "查询用户角色信息" ));
		return map;
	}

	/**
	 * 添加或者修改角色信息
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value = "角色管理")
	public Map<String, Object> save(Role role) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		roleService.save(role);
		if (role.getId() == null) {
			if (roleService.findByRoleName(role.getName()) != null) {
				map.put("success", false);
				map.put("errorInfo", "用户名已经存在");
				return map;
			}
			logService.save(new Log(Log.ADD_ACTION, "添加用户信息" + role));
		}else {
			logService.save(new Log(Log.ADD_ACTION, "修改用户信息" + role));
		}
		map.put("success", true);
		return map;
	}

	/**
	 * 删除角色id
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value = "角色管理")
	public Map<String, Object> delete(Integer id) throws Exception {
		logService.save(new Log(Log.DELETE_ACTION, "删除角色信息" + roleService.findById(id)));
		Map<String, Object> map = new HashMap<String, Object>();
		userRoleService.deleteByRoleId(id);
		roleMenuService.deleteByRoleId(id);
		roleService.delete(id);
		map.put("success", true);
		return map;
	}

	/**
	 * 根据父节点获取所有复选框权限菜单
	 * 
	 * @param parentId
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/loadCheckMenuInfo")
	@RequiresPermissions(value = "角色管理")
	public String loadCheckMenuInfo(Integer parentId, Integer roleId) throws Exception {
		// 根据角色id获取实体
		List<Menu> menuList = menuService.findByRoleId(roleId);
		List<Integer> menuIdList = new LinkedList<Integer>();
		for (Menu m : menuList) {
			menuIdList.add(m.getId());
		}
		return getAllCheckMenuByParentId(parentId, menuIdList).toString();
	}

	/**
	 * 根据父节点id和权限菜单id集合获取复选框菜单集合 获取所有
	 * 
	 * @param parentId
	 * @param menuIdList
	 * @return
	 */
	public JsonArray getAllCheckMenuByParentId(Integer parentId, List<Integer> menuIdList) {
		JsonArray jsonArray = this.getCheckMenuByParentId(parentId, menuIdList);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jsonObject = (JsonObject) jsonArray.get(i);
			if ("open".equals(jsonObject.get("state").getAsString())) {
				continue;
			} else {
				jsonObject.add("children", getAllCheckMenuByParentId(jsonObject.get("id").getAsInt(), menuIdList));
			}
		}
		return jsonArray;
	}

	/**
	 * 获取一层
	 * 
	 * @param parentId
	 * @param menuIdList
	 * @return
	 */
	public JsonArray getCheckMenuByParentId(Integer parentId, List<Integer> menuIdList) {
		List<Menu> menuList = menuService.findByParentId(parentId);
		JsonArray jsonArray = new JsonArray();
		for (Menu menu : menuList) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", menu.getId());
			jsonObject.addProperty("text", menu.getName());
			if (menu.getState() == 1) {
				jsonObject.addProperty("state", "closed");
			} else {
				jsonObject.addProperty("state", "open");
			}
			jsonObject.addProperty("iconCls", menu.getIcon());
			if (menuIdList.contains(menu.getId())) {
				jsonObject.addProperty("checked", true);
			}
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}

	/**
	 * 保存角色权限设置
	 * 
	 * @param menuIds
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveMenuSet")
	@RequiresPermissions(value = "角色管理")
	public Map<String, Object> saveMenuSet(String menuIds, Integer roleId) throws Exception {
		logService.save(new Log(Log.UPDATE_ACTION, "保存角色权限设置"));
		Map<String, Object> resultMap = new HashMap<>();
		roleMenuService.deleteByRoleId(roleId);//根据角色id删除所有关联角色实体
		if(StringUtils.isNotEmpty(menuIds)) {
			String idsStr[] = menuIds.split(",");
			for(int i =0; i < idsStr.length; i++) {
				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setRole(roleService.findById(roleId));
				roleMenu.setMenu(menuService.findById(Integer.parseInt(idsStr[i])));
				roleMenuService.save(roleMenu);
			}
		}
		resultMap.put("success", true);
		return resultMap;
	}
}
