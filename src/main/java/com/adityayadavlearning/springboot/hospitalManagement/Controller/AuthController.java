package com.adityayadavlearning.springboot.hospitalManagement.Controller;

import com.adityayadavlearning.springboot.hospitalManagement.Security.AuthService;
import com.adityayadavlearning.springboot.hospitalManagement.dto.LoginRequestDto;
import com.adityayadavlearning.springboot.hospitalManagement.dto.LoginResponseDto;
import com.adityayadavlearning.springboot.hospitalManagement.dto.SignUpRequestDto;
import com.adityayadavlearning.springboot.hospitalManagement.dto.SignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.servlet.function.ServerResponse.ok;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        return ResponseEntity.ok(authService.login(loginRequestDto));
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignUpRequestDto signupRequestDto){
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }
}
