package com.hengyue.realm;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.hengyue.entity.Menu;
import com.hengyue.entity.Role;
import com.hengyue.entity.User;
import com.hengyue.respository.MenuRepository;
import com.hengyue.respository.RoleRepository;
import com.hengyue.respository.UserRepository;

/**
 * 自定义realm
 * @author 章家宝
 *
 */
public class MyRealm extends AuthorizingRealm {

	@Resource
	private UserRepository userRepository;
	@Resource
	private RoleRepository roleRepository;
	@Resource
	private MenuRepository menuRepository;
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String userName = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findByUserName(userName);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<Role> roleList = roleRepository.findByUserId(user.getId());
		Set<String> roles = new HashSet<String>();
		for(Role r : roleList) {
			roles.add(r.getName());
			List<Menu> menuList = menuRepository.findByRoleId(r.getId());
			for(Menu m : menuList) {
				info.addStringPermission(m.getName());
			}
		}
		info.setRoles(roles);
		return info;
	}

	/**
	 * 权限认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String userName = (String) token.getPrincipal();
		User user = userRepository.findByUserName(userName);
		if(user != null) {
			AuthenticationInfo authInfo = new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), "xxx");
			return authInfo;
		}else {
			return null;
		}
	}

}
