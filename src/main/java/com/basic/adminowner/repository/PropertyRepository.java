package com.basic.adminowner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basic.adminowner.entity.Property;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByOwnerId(Long ownerId);


}
