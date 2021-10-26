package dev.gunlog.api.member;

import dev.gunlog.common.ApiResponse;
import dev.gunlog.api.member.dto.PasswordMatchResponseDto;
import dev.gunlog.api.member.service.MemberService;
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