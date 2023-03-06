package com.loctt.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class GoogleLoginController {

    @Autowired
    private GoogleOAuth2AuthorizedClientService googleOAuth2AuthorizedClientService;

    @GetMapping("/login/google")
    public String googleLogin(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/home";
        }
        return "redirect:/oauth2/authorization/google";
    }

    @GetMapping("/oauth2/callback/google")
    public RedirectView googleCallback(@RequestParam(name = "code") String code,
                                       @RequestParam(name = "state", required = false) String state,
                                       OAuth2AuthenticationToken oauth2Token) {

        OAuth2AuthorizedClient client = getOAuth2Client(oauth2Token, code);

        return new RedirectView("/home");
    }

    private OAuth2AuthorizedClient getOAuth2Client(OAuth2AuthenticationToken oauth2Token, String code) {
        String clientRegistrationId = oauth2Token.getAuthorizedClientRegistrationId();

        OAuth2AuthorizedClient client = googleOAuth2AuthorizedClientService.loadAuthorizedClient(clientRegistrationId, oauth2Token);

        if (client == null || !StringUtils.hasText(client.getAccessToken().getTokenValue())) {
            throw new RuntimeException("Error getting Access Token");
        }

        return client;
    }
}
