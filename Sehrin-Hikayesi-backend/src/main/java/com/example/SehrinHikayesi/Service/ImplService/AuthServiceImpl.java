package com.example.SehrinHikayesi.Service.ImplService;

import com.example.SehrinHikayesi.DTO.Request.UserRequest;
import com.example.SehrinHikayesi.DTO.Response.UserResponse;
import com.example.SehrinHikayesi.Entity.Role;
import com.example.SehrinHikayesi.Entity.User;
import com.example.SehrinHikayesi.Mapper.UserMapper;
import com.example.SehrinHikayesi.Repository.RoleRepository;
import com.example.SehrinHikayesi.Repository.UserRepository;
import com.example.SehrinHikayesi.Security.JwtUtil;
import com.example.SehrinHikayesi.Service.AbstactService.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository; //  Rol repository eklendi
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Şifre yanlış");
        }
        System.out.println("Girilen şifre: " + password);
        System.out.println("Veritabanındaki hash: " + user.getPassword());
        System.out.println("Şifre eşleşiyor mu? " + passwordEncoder.matches(password, user.getPassword()));

        String role = user.getRoles().stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Kullanıcının rolü yok"))
                .getRoleName();

        System.out.println("-------------------------------------"+role);

        return jwtUtil.generateToken(username, role);

    }

    @Override
    public UserResponse register(UserRequest request) {
        String email = request.getEmail().trim();

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")) {
            throw new RuntimeException("Geçerli bir e-posta adresi giriniz.");
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role defaultRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Rol bulunamadı"));

        user.setRoles(Set.of(defaultRole));

        return userMapper.toResponse(userRepository.save(user));
    }

}
