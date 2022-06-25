package io.github.gunkim.member;

import io.github.gunkim.common.ApiResponse;
import io.github.gunkim.member.dto.PasswordMatchResponseDto;
import io.github.gunkim.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping(path = "check/{memberId}")
    public ApiResponse<PasswordMatchResponseDto> checkMember(@PathVariable String memberId) {
        return ApiResponse.success(memberService.checkMember(memberId));
    }
}