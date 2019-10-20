package com.hengyue.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.UserRole;

/**
 * 用户角色关联持久层接口
 * @author 章家宝
 *
 */
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>, JpaSpecificationExecutor<UserRole> {

	/**
	 * 通过用户id删除关联
	 * @param userId
	 */
	@Query(value = "delete from t_user_role where user_id = ?1", nativeQuery = true)
	@Modifying//加事务
	public void deleteByUserId(Integer userId);
	/**
	 * 通过roleid删除关联
	 * @param userId
	 */
	@Query(value = "delete from t_user_role where role_id = ?1", nativeQuery = true)
	@Modifying//加事务
	public void deleteByRoleId(Integer roleId);
	
}
