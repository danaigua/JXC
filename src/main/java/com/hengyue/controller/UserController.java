package com.hengyue.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.hengyue.entity.Log;
import com.hengyue.entity.Menu;
import com.hengyue.entity.Role;
import com.hengyue.entity.User;
import com.hengyue.service.LogService;
import com.hengyue.service.MenuService;
import com.hengyue.service.RoleService;
import com.hengyue.service.UserService;
import com.hengyue.utils.StringUtils;

/**
 * 用户controller
 * @author 章家宝
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Resource
	private UserService userService;
	@Resource
	private RoleService roleService;
	
	@Resource
	private MenuService menuService;
	
	@Resource
	private LogService logService;
	/**
	 * 用户登录
	 * @param imageCode
	 * @param user
	 * @param bindingResult
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/login")
	public Map<String, Object> login(String imageCode, @Valid User user, BindingResult bindingResult, HttpSession session){
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.isEmpty(imageCode)) {
			map.put("success", false);
			map.put("errorInfo", "请输入验证码");
			return map;
		}
		if(!session.getAttribute("checkcode").equals(imageCode)) {
			map.put("success", false);
			map.put("errorInfo", "验证码错误");
			return map;
		}
		if(bindingResult.hasErrors()) {
			map.put("success", false);
			map.put("errorInfo", bindingResult.getFieldError().getDefaultMessage());
			return map;
		}
		//判断用户名密码是否正确
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(), user.getPassword());
		try {
			subject.login(token);
			String userName = (String) SecurityUtils.getSubject().getPrincipal();
			User currentUser = userService.findByUserName(userName);
			session.setAttribute("currentUser", currentUser);
			List<Role> roleList = roleService.findByUserId(currentUser.getId());
			map.put("roleList", roleList);
			map.put("roleSize", roleList.size());
			map.put("success", true);
			logService.save(new Log(Log.LOGIN_ACTION, "用户登录"));
			return map;
		}catch(Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("errorInfo", "用户名或者密码错误");
			return map;
		}
	}
	/**
	 * 保存用户角色
	 * @param roleId
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/saveRole")
	public Map<String, Object> saveRole(Integer roleId, HttpSession session) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Role currentRole = roleService.findById(roleId);
		session.setAttribute("currentRole", currentRole);
		map.put("success", true);
		return map;
	}
	/**
	 * 加载当前用户信息
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@GetMapping("/loadUserInfo")
	public String loadUserInfo(HttpSession session) throws Exception{
		User user = (User)session.getAttribute("currentUser");
		Role role = (Role)session.getAttribute("currentRole");
		return "欢迎您：" + user.getTrueName() + "&nbsp;[&nbsp;" + role.getName() + "&nbsp;]";
	}
	/**
	 * 加载权限菜单
	 * @param session
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@PostMapping("/loadMenuInfo")
	public String loadMenuInfo(HttpSession session, Integer parentId) throws Exception{
		Role role = (Role)session.getAttribute("currentRole");
		return getAllMenuByParentId(parentId, role.getId()).toString();
	}
	/**
	 * 获得所有菜单信息
	 * 递归
	 * @param parentId
	 * @return
	 * @throws Exception
	 */
	public JsonArray getAllMenuByParentId(Integer parentId, Integer roleId) throws Exception{
		JsonArray jsonArray = this.getMenuByParentId(parentId, roleId);
		for(int i = 0; i < jsonArray.size(); i++) {
			JsonObject jsonObject = (JsonObject) jsonArray.get(i);
			if("open".equals(jsonObject.get("state").getAsString())) {
				continue;
			}else {
				jsonObject.add("children", getAllMenuByParentId(jsonObject.get("id").getAsInt(), roleId));
			}
		}
		return jsonArray;
	}
	/**
	 * 根据父节点以及用户角色id
	 * @param parentId
	 * @param roleId
	 * @return
	 */
	public JsonArray getMenuByParentId(Integer parentId, Integer roleId) {
		JsonArray jsonArray = new JsonArray();
		List<Menu> menuList = menuService.findByParentIdAndRoleId(parentId, roleId);
		for(Menu menu : menuList) {
			JsonObject jsonObject = new JsonObject();
			jsonObject.addProperty("id", menu.getId());
			jsonObject.addProperty("text", menu.getName());	//节点名称
			if(menu.getState() == 1) {
				jsonObject.addProperty("state", "closed");		//根节点
			}else {
				jsonObject.addProperty("state", "open");		//叶子节点
			}
			jsonObject.addProperty("iconCls", menu.getIcon());		//叶子节点
			JsonObject attributeObject = new JsonObject();			//扩展属性
			attributeObject.addProperty("url", menu.getUrl());
			jsonObject.add("attributes", attributeObject);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
}
