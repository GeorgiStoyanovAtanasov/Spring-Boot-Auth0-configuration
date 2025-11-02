package com.example.helloworld.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import com.example.helloworld.models.Message;
import com.example.helloworld.services.MessageService;

import lombok.RequiredArgsConstructor;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/public")
    public Message getPublic() {
        return messageService.getPublicMessage();
    }

    @GetMapping("/protected")
    public Message getProtected() {
        return messageService.getProtectedMessage();
    }

    @PreAuthorize("hasRole('Admin')")
    @GetMapping("/admin")
    public Message getAdmin(@AuthenticationPrincipal Jwt jwt) {
        log.info("User sub: {}", jwt.getSubject());
        log.info("Email: {}", jwt.getClaimAsString("email"));
        log.info("Name: {}", jwt.getClaimAsString("name"));

        return messageService.getAdminMessage();
    }

}
