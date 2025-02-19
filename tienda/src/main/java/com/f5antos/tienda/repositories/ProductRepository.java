package com.f5antos.tienda.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.f5antos.tienda.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
