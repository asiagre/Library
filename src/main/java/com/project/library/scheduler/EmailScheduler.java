package com.project.library.scheduler;

import com.project.library.config.AdminConfig;
import com.project.library.domain.Mail;
import com.project.library.repository.ReaderDao;
import com.project.library.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class EmailScheduler {

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    private ReaderDao readerDao;

    @Scheduled(cron = "0 0 8 * * MON")
    public void sendMailToReaders() {
        readerDao.findAll()
                .forEach(reader -> simpleEmailService.send(new Mail(
                        reader.getMail(),
                        "Weekly update from library",
                        "In this week we added: " )));
    }
}
