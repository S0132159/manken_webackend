package com.example.manken.repository;

import com.example.manken.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    // 檢查學號是否已存在
    boolean existsByStudentId(String studentId);
}
