package dev.gunlog.member;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.gunlog.RestDocControllerTest;
import dev.gunlog.member.dto.PasswordMatchResponseDto;
import dev.gunlog.member.service.MemberService;
import dev.gunlog.common.ApiResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(MemberController.class)
public class MemberControllerTests extends RestDocControllerTest {
    @MockBean
    private MemberService memberService;

    @Test
    @DisplayName("회원가입 여부 체크 테스트")
    public void checkMemberTest() throws Exception {
        //given
        final String memberId = "test";
        given(memberService.checkMember(anyString())).willReturn(new PasswordMatchResponseDto(true));

        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v2/member/check/{memberId}", memberId)).andDo(print());

        //then
        MvcResult result = resultActions.andExpect(status().isOk())
                .andDo(document("member/checkMember",
                        pathParameters(
                                parameterWithName("memberId").description("회원 ID")
                        ),
                        responseFields(
                                beneathPath("data").withSubsectionId("data"),
                                fieldWithPath("matchPassword").type(JsonFieldType.BOOLEAN).description("비밀번호 일치 여부")
                        )
                ))
                .andReturn();
        ApiResponse<PasswordMatchResponseDto> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<ApiResponse<PasswordMatchResponseDto>>() {});
        assertThat(response).isNotNull();
        PasswordMatchResponseDto dto = response.getData();
        assertThat(dto).isNotNull();
        assertThat(dto.isMatchPassword()).isTrue();
    }
}