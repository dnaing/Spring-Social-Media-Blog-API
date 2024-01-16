package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;



@Service
@Transactional
public class MessageService {

    private MessageRepository messageRepository;
    private AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public ResponseEntity<Message> createMessage(Message message) {
        Optional<Account> postedAccount = accountRepository.findById(message.getPosted_by());
        if (message.getMessage_text().length() == 0 || 
            message.getMessage_text().length() >= 255 || 
            postedAccount.isPresent() == false) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(messageRepository.save(message));
        }
    }

    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(messageRepository.findAll());     
    }

    public ResponseEntity<Message> getMessageById(int id) {
        Optional<Message> searchedMessage = messageRepository.findById(id);
        if (searchedMessage.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(searchedMessage.get());
        }
        return null;
    }

    public ResponseEntity<Integer> deleteMessageById(int id) {
        Optional<Message> searchedMessage = messageRepository.findById(id);
        if (!searchedMessage.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(null);
        }
        else {
            messageRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(1);
        }
    }

    public ResponseEntity<Integer> updateMessageById(int message_id, String message_text) {
        Optional<Message> searchedMessage = messageRepository.findById(message_id);
        if (searchedMessage.isPresent() == false ||
            message_text.length() == 0 ||
            message_text.length() > 255) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(null);
        }
        else {
            Message message = searchedMessage.get();
            message.setMessage_text(message_text);
            messageRepository.save(message);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(1);
        }
    }

    public ResponseEntity<List<Message>> getAllMessagesByAccountId(int account_id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(messageRepository.findAllMessagesByPostedBy(account_id));     
    }



    


}
