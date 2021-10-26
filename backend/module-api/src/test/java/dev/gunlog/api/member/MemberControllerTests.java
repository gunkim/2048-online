package dev.gunlog.api.member;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.gunlog.common.ApiResponse;
import dev.gunlog.api.member.dto.PasswordMatchResponseDto;
import dev.gunlog.api.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;

@ExtendWith({SpringExtension.class, RestDocumentationExtension.class})
@SpringBootTest
@ActiveProfiles("dev")
public class MemberControllerTests {
    private MockMvc mockMvc;
    @InjectMocks
    private MemberController memberController;
    @MockBean
    private MemberService memberService;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp(
            WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider restDocumentationContextProvider
    ) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentationContextProvider)
                        .uris()
                        .withScheme("http")
                        .withHost("192.168.0.1")
                        .withPort(8080)
                        .and()
                        .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint())
                )
                .apply(springSecurity())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }


    @Test
    @DisplayName("회원가입 여부 체크 테스트")
    public void checkMemberTest() throws Exception {
        //given
        final String memberId = "test";
        given(memberService.checkMember(anyString())).willReturn(new PasswordMatchResponseDto(true));

        //when
        final ResultActions resultActions = this.mockMvc.perform(get("/api/v2/member/check/{memberId}", memberId)).andDo(print());

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