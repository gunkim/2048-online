package dev.gunlog.member;

import dev.gunlog.member.domain.Member;
import dev.gunlog.member.domain.MemberRepository;
import dev.gunlog.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MemberControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Mock
    private MemberRepository memberRepository;
    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 여부 체크 테스트")
    public void checkUserTest() throws Exception {
        //given
        final String memberId = "test";
        given(memberRepository.findByMemberId(memberId)).willReturn(Optional.of(Member.builder().build()));

        //when
        final ResultActions resultActions = this.requestCheckMember(memberId);

        //then
        resultActions.andExpect(status().isOk());
    }
    private ResultActions requestCheckMember(String username) throws Exception {
        String url = String.format("/api/v2/member/check/%s", username);
        return mockMvc.perform(get(url))
                .andDo(print());
    }
}