package com.example.manken.controller;

import com.example.manken.entity.Member;
import com.example.manken.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody Map<String, String> payload) {
        String studentId = payload.get("studentId");
        String name = payload.get("name");
        String contact = payload.get("contact");
        String reason = payload.get("reason");

        // 後端欄位驗證
        if (studentId == null || studentId.isBlank() ||
            name == null || name.isBlank() ||
            contact == null || contact.isBlank() ||
            reason == null || reason.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "所有欄位皆為必填！"));
        }

        // 避免重複學號申請
        if (memberRepository.existsByStudentId(studentId.trim())) {
            return ResponseEntity.badRequest().body(Map.of("error", "此學號已遞交過申請！"));
        }

        try {
            Member member = new Member();
            member.setStudentId(studentId.trim());
            member.setName(name.trim());
            member.setContact(contact.trim());
            member.setReason(reason.trim());

            memberRepository.save(member);
            return ResponseEntity.ok(Map.of("success", true, "message", "報名成功！"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "寫入資料庫時出錯：" + e.getMessage()));
        }
    }
}
