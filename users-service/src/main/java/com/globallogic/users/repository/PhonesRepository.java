package com.globallogic.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globallogic.users.entity.Phones;


@Repository
public interface PhonesRepository extends JpaRepository<Phones, Long> {

}
