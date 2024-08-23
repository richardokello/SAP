package co.ke.spsat.bowip.security.service;

import co.ke.spsat.bowip.entities.Department;
import co.ke.spsat.bowip.repositories.DepartmentRepository;
import co.ke.spsat.bowip.repositories.RoleRepository;
import co.ke.spsat.bowip.repositories.TokenRepository;
import co.ke.spsat.bowip.repositories.UsersRepository;
import co.ke.spsat.bowip.security.AuthenticationRequest;
import co.ke.spsat.bowip.security.AuthenticationResponse;
import co.ke.spsat.bowip.security.Token;
import co.ke.spsat.bowip.user.Roles;
import co.ke.spsat.bowip.user.Users;
import co.ke.spsat.bowip.user.dto.SignInDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final UsersRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;
    private DepartmentRepository departmentRepository;

   final private RoleRepository roleRepository;
    public AuthService(UsersRepository repository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       TokenRepository tokenRepository,
                       AuthenticationManager authenticationManager, RoleRepository roleRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.roleRepository = roleRepository;
    }
    public AuthenticationResponse register(Users request, Long departmentId) {

        // check if user already exist. if exist than authenticate the user
        if(repository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null, null,"User already exist");
        }

//        Set<Roles> roles = roleNames.stream()
//                .map(Roles::getRoleName)  // Find each role by its name
//                .collect(Collectors.toList());
        Set<Roles> roles = new HashSet<>();
        for (Roles role : request.getRoles()) {
            Roles existingRole = roleRepository.findByRoleName(role.getRoleName());
            if (existingRole != null) {
                roles.add(existingRole);
            } else {
                // Optionally handle the case where the role doesn't exist
                throw new RuntimeException("Role not found: " + role.getRoleName());
            }
        }
//        Department department = departmentRepository.findById(departmentId)
//                .orElseThrow(() -> new RuntimeException("Department not found"));
        Users user = new Users();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setDepartment(request.getDepartment());
        user.setActivated(true);
        user.setEmail(request.getEmail());
        user.setImageUrl(request.getImageUrl());
        user.setCreatedAt(new Date());
        user.setModifiedAt(Instant.now());
        user.setRoles( roles);
        user = repository.save(user);

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveUserToken(accessToken, refreshToken, user);

        return new AuthenticationResponse(accessToken, refreshToken,"User registration was successful");

    }
    public AuthenticationResponse authenticate(Users request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        Users user = repository.findByUsername(request.getUsername()).orElseThrow();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(accessToken, refreshToken, user);

        return new AuthenticationResponse(accessToken, refreshToken, "User login was successful");
    }
    private void revokeAllTokenByUser(Users user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getUserId());
        if(validTokens.isEmpty()) {
            return;
        }
        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String accessToken, String refreshToken, Users user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {
        // extract the token from authorization header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        String token = authHeader.substring(7);
        // extract username from token
        String username = jwtService.extractUsername(token);

        // check if the user exist in database
        Users user = repository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("No user found"));

        // check if the token is valid
        if(jwtService.isValidRefreshToken(token, user)) {
            // generate access token
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity(new AuthenticationResponse(accessToken, refreshToken, "New token generated"), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

}}
