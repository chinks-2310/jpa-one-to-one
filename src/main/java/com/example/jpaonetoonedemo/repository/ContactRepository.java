package com.example.jpaonetoonedemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository {
    List findByStudentId(Long studentId);
}
