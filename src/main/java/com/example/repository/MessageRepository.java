package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    // List<Message> findMessagesByPostedBy(int account_id);
    @Query("FROM Message WHERE posted_by = ?1")
    List<Message> findAllMessagesByPostedBy(int account_id);
}
