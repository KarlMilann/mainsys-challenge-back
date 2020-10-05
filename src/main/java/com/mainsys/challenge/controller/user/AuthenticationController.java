package com.mainsys.challenge.controller.user;

import com.mainsys.challenge.controller.user.request.LoginForm;
import com.mainsys.challenge.controller.user.request.SignUpForm;
import com.mainsys.challenge.controller.user.response.JwtResponse;
import com.mainsys.challenge.controller.user.response.ResponseMessage;
import com.mainsys.challenge.model.user.Role;
import com.mainsys.challenge.model.user.RoleName;
import com.mainsys.challenge.model.user.User;
import com.mainsys.challenge.repo.user.RoleRepository;
import com.mainsys.challenge.repo.user.UserRepository;
import com.mainsys.challenge.security.jwt.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        System.out.println("auth: "+authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        System.out.println("jwt = "+jwt+"uDetails uname = "+userDetails.getUsername()+"udetails authoritites"+userDetails.getAuthorities());
        System.out.println(ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities())).toString());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())){
            return new ResponseEntity<>(
                    new ResponseMessage("Fail -> Username is already taken ! ")
                    , HttpStatus.BAD_REQUEST);
        }
        /**
         * create user account
         */

        User user = new User(
                signUpRequest.getUsername()
                , encoder.encode(signUpRequest.getPassword())
        );

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            strRoles = new HashSet<>();
            strRoles.add("user");

        }



        setRolesToUser(strRoles, roles);
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new ResponseMessage("User registered successfully!"));
    }
    @PostMapping("/signup/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())){
            return new ResponseEntity<>(
                    new ResponseMessage("Fail -> Username is already taken ! ")
                    , HttpStatus.BAD_REQUEST);
        }
        /**
         * create user account
         */

        User user = new User(
                signUpRequest.getUsername()
                , encoder.encode(signUpRequest.getPassword())
        );

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            strRoles = new HashSet<>();
            strRoles.add("user");
        }
        strRoles.add("admin");
        setRolesToUser(strRoles, roles);
        user.setRoles(roles);
        userRepository.save(user);
        return new ResponseEntity<>(new ResponseMessage("User registerd successfully"), HttpStatus.OK);

    }

    private void setRolesToUser(Set<String> strRoles, Set<Role> roles) {
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail -> User Role not found"));
                    roles.add(adminRole);
                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail -> User Role not found"));
                    roles.add(userRole);
            }
        });
    }
}
