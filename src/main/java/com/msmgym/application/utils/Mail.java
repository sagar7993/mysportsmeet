package com.msmgym.application.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class Mail {

    private JavaMailSender mailSender;
    private SimpleMailMessage simpleMailMessage;

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail(String[] to, String from, String replyTo, String subject, String htmlText, String fileUrl, String fileName) {
        @SuppressWarnings("resource")
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring-Mail.xml");
        Mail mm = (Mail) context.getBean("mail");
        mm.sendEmail(to, from, replyTo, subject, htmlText, fileUrl, fileName);
    }

    public void sendEmail(String[] to, String from, String replyTo, String subject, String htmlText, String fileUrl, String fileName) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setReplyTo(replyTo);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
//			htmlText = "<html>"
//				+ "<body>"
//					+ "<h1>Hello MSMGym User</h1>"
//					+ "<b>This is a test email</b>"
//				+ "</body>"
//			+ "</html>";
            helper.setText("", htmlText);
            if(fileUrl.length() > 0 && fileName.length() > 0) {
                FileSystemResource file = new FileSystemResource(fileUrl);
                helper.addAttachment(fileName, file);
            }
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
    }
}
