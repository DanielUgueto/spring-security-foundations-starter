package ek.osnb.demo.user;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MeController {

    record MeResponse(String username, List<String> roles) {
    }

    @GetMapping("/api/me")
    MeResponse me(Authentication authentication) {
        List<String> roles = authentication
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .filter(a -> a.startsWith("ROLE_"))
                .toList();

        return new MeResponse(authentication.getName(), roles);
    }

    @GetMapping("/api/me/id")
    Long myId(@AuthenticationPrincipal AppUserDetails principal) {
        return principal.getId();
    }

}
