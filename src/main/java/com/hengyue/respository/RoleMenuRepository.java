package com.hengyue.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.RoleMenu;

/**
 * 角色菜单关联持久层接口
 * @author 章家宝
 *
 */
public interface RoleMenuRepository extends JpaRepository<RoleMenu, Integer>, JpaSpecificationExecutor<RoleMenu> {

	/**
	 * 通过roleid删除关联
	 * @param userId
	 */
	@Query(value = "delete from t_role_menu where role_id = ?1", nativeQuery = true)
	@Modifying//加事务
	public void deleteByRoleId(Integer roleId);
	
}
