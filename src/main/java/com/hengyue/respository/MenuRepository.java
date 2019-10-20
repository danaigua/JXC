package com.hengyue.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.Menu;

/**
 * 菜单持久层接口
 * @author 章家宝
 *
 */
public interface MenuRepository extends JpaRepository<Menu, Integer> {
	/**
	 * 根据父节点以及角色id查询用户子节点
	 * @param parentId
	 * @param roleId
	 * @return
	 */
	@Query(value = "SELECT * FROM t_menu WHERE p_id = ?1 AND id IN ( SELECT menu_id FROM t_role_menu WHERE role_id = ?2 )", nativeQuery = true)
	public List<Menu> findByParentIdAndRoleId(Integer parentId, Integer roleId);

	/**
	 * 通过角色id查找菜单
	 * @param roleId
	 * @return
	 */
	@Query(value = "SELECT m.* FROM t_role_menu rm ,t_role r, t_menu m WHERE rm.`menu_id` = m.`id` AND rm.`role_id` = r.`id`AND r.`id` = ?1 ", nativeQuery = true)
	public List<Menu> findByRoleId(Integer roleId);
	/**
	 * 根据父节点查找所有子节点
	 * @param parentId
	 * @return
	 */
	@Query(value = "select * from t_menu where p_id = ?1", nativeQuery = true)
	public List<Menu> findByParentId(int parentId);
}
