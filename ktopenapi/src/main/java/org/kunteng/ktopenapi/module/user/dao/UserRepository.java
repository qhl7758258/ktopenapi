package org.kunteng.ktopenapi.module.user.dao;

import org.kunteng.ktopenapi.module.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, String> {

	@Query("select u from User u where u.account=?1 and u.password=?2")
	User login(String account,String password);
	
}
