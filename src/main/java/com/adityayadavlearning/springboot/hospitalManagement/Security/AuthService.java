package com.adityayadavlearning.springboot.hospitalManagement.Security;

import com.adityayadavlearning.springboot.hospitalManagement.dto.LoginRequestDto;
import com.adityayadavlearning.springboot.hospitalManagement.dto.LoginResponseDto;
import com.adityayadavlearning.springboot.hospitalManagement.dto.SignUpRequestDto;
import com.adityayadavlearning.springboot.hospitalManagement.dto.SignupResponseDto;

import com.adityayadavlearning.springboot.hospitalManagement.entity.Patient;
import com.adityayadavlearning.springboot.hospitalManagement.entity.User;
import com.adityayadavlearning.springboot.hospitalManagement.entity.type.AuthProviderType;

import com.adityayadavlearning.springboot.hospitalManagement.entity.type.RoleType;
import com.adityayadavlearning.springboot.hospitalManagement.repository.PatientRepository;
import com.adityayadavlearning.springboot.hospitalManagement.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUtil authUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PatientRepository patientRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword())
        );

        User user= (User) authentication.getPrincipal();

        String token = authUtil.generateAccessToken(user);

        return new LoginResponseDto(token, user.getId());
    }

    public User signUpInternal(SignUpRequestDto signupRequestDto, AuthProviderType authProviderType, String providerId) {
        User user = userRepository.findByUsername(signupRequestDto.getUsername()).orElse(null);

        if(user != null) throw new IllegalArgumentException("User already exists");

        user = User.builder()
                .username(signupRequestDto.getUsername())
                .providerId(providerId)
                .providerType(authProviderType)
                .roles(Set.of(RoleType.PATIENT))
                .build();

        if(authProviderType == AuthProviderType.EMAIL) {
            user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        }

        User savedUser = userRepository.save(user);

        Patient patient = Patient.builder()
                .name(signupRequestDto.getName())
                .email(signupRequestDto.getUsername())
                .user(user)
                .build();


      patientRepository.save(patient);


        return savedUser;
    }



    //Controller signup
    public SignupResponseDto signup(SignUpRequestDto signupRequestDto) {
        User user = signUpInternal(signupRequestDto, AuthProviderType.EMAIL, null);
        return new SignupResponseDto(user.getId(), user.getUsername());
    }


    @Transactional
    public LoginResponseDto handleOAuth2LoginRequest(OAuth2User oAuth2User, String registrationId) {
        AuthProviderType providerType= authUtil.getProviderTypeFromRegistrationId(registrationId);
        String providerId= authUtil.determineProviderIdFromOAuth2User(oAuth2User,registrationId);

        User user= userRepository.findByProviderIdAndProviderType(providerId,providerType).orElse(null);
        String email= oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        User emailUser= userRepository.findByUsername(email).orElse(null);

        if(user==null && emailUser==null){
            //signup flow
            String username= authUtil.determineUsernameFromOAuth2User(oAuth2User,registrationId,providerId);
            User user1 = User.builder()
                    .username(username)
                    .providerId(providerId)
                    .providerType(providerType)
                    .roles(Set.of(RoleType.PATIENT))
                    .build();

            userRepository.save(user1);

            Patient patient = Patient.builder()
                    .name(name)
                    .email(username)
                    .user(user1)
                    .build();

            patientRepository.save(patient);
        } else if(user!=null){
            if(email != null && !email.isBlank() && !email.equals(user.getUsername())){
                user.setUsername(email);
                userRepository.save(user);
            }
        }else {
            throw new BadCredentialsException("This email is already registered with provider "+emailUser.getProviderType());
        }

        LoginResponseDto loginResponseDto= new LoginResponseDto(authUtil.generateAccessToken(user), user.getId());


        return loginResponseDto;


    }
}
