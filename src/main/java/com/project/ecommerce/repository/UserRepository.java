package com.project.ecommerce.repository;


import com.project.ecommerce.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    Users findByUserEmailId(String userEmailId);
    Users findByUserName(String userName);
}
