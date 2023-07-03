package com.shakya.userinfochangeapp.repository;

import com.shakya.userinfochangeapp.model.Payload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<Payload, Integer> {
}
