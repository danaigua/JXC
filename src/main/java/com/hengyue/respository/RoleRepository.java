package com.hengyue.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.Role;

/**
 * 角色持久层接口
 * @author 章家宝
 *
 */
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
	/**
	 * 根据用户id查找角色实体
	 * @param id
	 * @return
	 */
	@Query(value = "select r.* from t_user u, t_role r, t_user_role ur WHERE ur.`user_id`=u.`id` AND ur.`role_id`=r.`id` AND u.`id`=?1", nativeQuery = true)
	public List<Role> findByUserId(Integer id);
	/**
	 * 根据id查询实体
	 */
	public Optional<Role> findById(Integer id);
	/**
	 * 通过角色名查找角色实体
	 * @param roleName
	 * @return
	 */
	@Query(value = "select * from t_role where name = ?1", nativeQuery = true)
	public Role findByRoleName(String roleName);

}
