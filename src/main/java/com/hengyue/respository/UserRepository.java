package com.hengyue.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.hengyue.entity.User;

/**
 * 用户持久层接口
 * @author 章家宝
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
	/**
	 * 根据用户名查找实体
	 * @param userName
	 * @return
	 */
	@Query(value = "select * from t_user where user_name = ?1", nativeQuery = true)
	public User findByUserName(String userName);

	/**
	 * 通过查找用户真实姓名返回用户实体
	 * @param userName
	 * @return
	 */
	@Query(value = "select * from t_user where true_name = ?1", nativeQuery = true)
	public User findByUserTrueName(String userName);

}
