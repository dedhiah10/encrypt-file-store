package com.SL.SportyShoes.DatabaseComms;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.SL.SportyShoes.Entities.User;

public interface UserRepository extends CrudRepository<User, Integer>, JpaRepository<User, Integer> {
	List<User> findByNumber(Long number);
	List<User> findByName(String name);
}
