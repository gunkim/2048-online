package dev.gunlog.member;

import dev.gunlog.common.ApiResponse;
import dev.gunlog.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping(path = "check/{memberId}")
    public ApiResponse<Boolean> checkUser(@PathVariable String memberId) {
        boolean isUser = memberService.checkMember(memberId);
        return ApiResponse.success(isUser);
    }
}