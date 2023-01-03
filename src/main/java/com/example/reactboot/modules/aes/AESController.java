package com.example.reactboot.modules.aes;

import com.example.reactboot.common.annotation.Noheader;
import com.example.reactboot.common.response.JsonResponse;
import com.example.reactboot.common.utils.AES128Crypto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Z_(개발용)AES Encryption", description = "회원 비밀번호 전달시 암호화 방식으로 개발시 확인 용도로만 사용해주세요.")
@RestController
@RequestMapping(value = "${api.data}/encryption")
public class AESController {

    @Noheader
    @Operation(summary = "AES 암호화 데이터 출력", responses = {
            @ApiResponse(responseCode = "200", description = "암호화 성공")
    })
    @GetMapping(value = "/aes/{password}")
    public JsonResponse<String> passwordEncode(
            @Parameter(description = "AES 암호화할 문자열") @PathVariable("password") String password
    ) {
        return JsonResponse.ok("성공하였습니다.", AES128Crypto.encrypt(password));
    }
}
