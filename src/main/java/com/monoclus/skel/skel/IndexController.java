package com.monoclus.skel.skel;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController {

    @RequestMapping(path = "/")
    Object index() {
        return Map.of("path", "index");
    }

    @RequestMapping(path = {"/whoami", "/app/whoami"})
    Object whoami(HttpServletRequest request, HttpServletResponse response) {
        KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) request.getUserPrincipal();
        KeycloakPrincipal principal = token != null ? (KeycloakPrincipal)token.getPrincipal() : null;
        //principal.getKeycloakSecurityContext().getToken().getRealmAccess().getRoles();
        boolean isLoggedIn = principal != null;
        return Map.of(
                "name", isLoggedIn ? principal.getName() : "anonymous",
                "resourceRoles", isLoggedIn ? principal.getKeycloakSecurityContext().getToken().getResourceAccess() : List.of(),
                "realmRoles", isLoggedIn ? principal.getKeycloakSecurityContext().getToken().getRealmAccess().getRoles() : List.of(),
                "authorities", isLoggedIn ? token.getAuthorities() : List.of()
        );
    }

}
