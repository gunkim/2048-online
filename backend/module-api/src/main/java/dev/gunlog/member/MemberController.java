package dev.gunlog.member;

import dev.gunlog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping(path = "check/{username}")
    public ResponseEntity<Boolean> checkUser(@PathVariable String username) {
        boolean isUser = memberService.checkUser(username);
        return ResponseEntity.ok(isUser);
    }
}