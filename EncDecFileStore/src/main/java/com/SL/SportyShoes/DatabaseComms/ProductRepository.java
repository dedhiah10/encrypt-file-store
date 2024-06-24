package com.SL.SportyShoes.DatabaseComms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.SL.SportyShoes.Entities.Products;

public interface ProductRepository extends CrudRepository<Products, Integer>, JpaRepository<Products, Integer> {
	
}
