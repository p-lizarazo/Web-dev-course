package co.edu.javeriana.pw.back.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserService {

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD', 'ROLE_USER')")
    public Authentication retrieveUser(Authentication auth)
    {
        return auth;
    }
    
}
