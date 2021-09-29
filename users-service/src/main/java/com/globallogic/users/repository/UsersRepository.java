package com.globallogic.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globallogic.users.entity.Users;


@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

}
