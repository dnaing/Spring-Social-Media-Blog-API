package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;

import javax.websocket.server.PathParam;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    @Autowired
    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("register")
    public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
        return accountService.registerAccount(account);
    }

    @PostMapping("login")
    public ResponseEntity<Account> loginAccount(@RequestBody Account account) {
        return accountService.loginAccount(account);
    }

    @PostMapping("messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        return messageService.createMessage(message);
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int message_id) {
        return messageService.getMessageById(message_id);
    }

    @DeleteMapping("messages/{message_id}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable int message_id) {
        return messageService.deleteMessageById(message_id);
    }

    @PatchMapping("messages/{message_id}")
    public ResponseEntity<Integer> updateMessageById(@PathVariable int message_id, @RequestBody Message message) {
        return messageService.updateMessageById(message_id, message.getMessage_text());
    }


}
