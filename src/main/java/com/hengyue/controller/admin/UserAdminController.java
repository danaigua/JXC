package com.hengyue.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hengyue.entity.Log;
import com.hengyue.entity.Role;
import com.hengyue.entity.User;
import com.hengyue.entity.UserRole;
import com.hengyue.service.LogService;
import com.hengyue.service.RoleService;
import com.hengyue.service.UserRoleService;
import com.hengyue.service.UserService;
import com.hengyue.utils.StringUtils;
/**
 * 用户管理controller
 * @author 章家宝
 *
 */
@Controller
@RequestMapping("/admin/user")
public class UserAdminController {

	@Resource
	private UserService userService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private UserRoleService userRoleService;
	
	@Resource
	private LogService logService;
	/**
	 * 分页查询用户信息
	 * @param user
	 * @param page
	 * @param rows
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions(value = "用户管理")
	public Map<String, Object> list(User user, @RequestParam(value = "page", required = false)Integer page,
			@RequestParam(value = "rows", required = false)Integer rows) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> userList = userService.list(user, page, rows, Direction.ASC, "id");
		for(User u:userList) {
			List<Role> roleList = roleService.findByUserId(u.getId());
			StringBuffer sb = new StringBuffer();
			for (Role role : roleList) {
				sb.append("," + role.getName());
			}
			u.setRoles(sb.toString().replaceFirst(",", ""));
		}
		Long total = userService.getCount(user);
		map.put("rows", userList);
		map.put("total", total);
		logService.save(new Log(Log.SEARCH_ACTION, "查询用户信息"));
		return map;
	}
	/**
	 * 添加或者修改用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/save")
	@RequiresPermissions(value = "用户管理")
	public Map<String, Object> save(User user) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(user.getId() == null) {
			if(userService.findByUserName(user.getUserName()) != null) {
				map.put("success", false);
				map.put("errorInfo", "用户名已经存在");
				return map;
			}
			logService.save(new Log(Log.ADD_ACTION, "添加用户信息" + user));
		}else {
			logService.save(new Log(Log.UPDATE_ACTION, "更新用户信息" + user));
		}
		userService.save(user);
		map.put("success", true);
		return map;
	}
	/**
	 * 删除用户id
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions(value = "用户管理")
	public Map<String, Object> delete(Integer id) throws Exception{
		logService.save(new Log(Log.DELETE_ACTION, "删除用户信息" + userService.findById(id)));
		Map<String, Object> map = new HashMap<String, Object>();
		userRoleService.deleteByUserId(id);
		userService.delete(id);
		map.put("success", true);
		return map;
	}
	/**
	 * 保存用户角色设置
	 * @param roleIds
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/saveRoleSet")
	@RequiresPermissions(value = "用户管理")
	public Map<String, Object> saveRoleSet(String roleIds, Integer userId) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		userRoleService.deleteByUserId(userId);
		if(StringUtils.isNotEmpty(roleIds)) {
			String roleIdStr[] = roleIds.split(",");
			for(int i = 0; i < roleIdStr.length; i++) {
				UserRole userRole = new UserRole();
				userRole.setUser(userService.findById(userId));
				userRole.setRole(roleService.findById(Integer.parseInt(roleIdStr[i])));
				userRoleService.save(userRole);
			}
		}
		map.put("success", true);
		logService.save(new Log(Log.UPDATE_ACTION, "保存用户角色信息" ));
		return map;
	}
	/**
	 * 修改密码
	 * @param newPassword
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/modifyPassword")
	@RequiresPermissions(value = "修改密码")
	public Map<String, Object> modifyPassword(String newPassword, HttpSession session) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		User user = (User)session.getAttribute("currentUser");
		User u = userService.findById(user.getId());
		u.setPassword(newPassword);
		userService.save(u);
		map.put("success", true);
		logService.save(new Log(Log.UPDATE_ACTION, "修改密码" ));
		return map;
	}
	/**
	 * 安全退出
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/logout")
	@RequiresPermissions(value = "安全退出")
	public String logout(HttpSession session)throws Exception{
		logService.save(new Log(Log.LOGOUT_ACTION, "用户注销" ));
		SecurityUtils.getSubject().logout();//在这一步的时候已经有帮忙清除session了，所以不用下面的操作
//		session.invalidate();//把当前session全部清掉
		return "redirect:/login.html";
	}
	/**
	 * 
	 * 用户的combobox
	 * @param q
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/combobox")
	@RequiresPermissions(value = "项目规划")
	public List<Map<String, Object>> combobox(@RequestParam(value = "q", required= false)String q) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<User> userList = userService.findAll();
		for (User user : userList) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", user.getId());
			map.put("text", user.getTrueName());
			list.add(map);
		}
		return list;
	}
}
