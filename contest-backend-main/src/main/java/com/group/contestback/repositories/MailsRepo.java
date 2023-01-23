package com.group.contestback.repositories;

import com.group.contestback.models.Mails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailsRepo extends JpaRepository<Mails, Integer> {
}
