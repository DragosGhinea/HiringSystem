package ro.hiringsystem.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ro.hiringsystem.service.EmailSenderService;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private final JavaMailSender javaMailSender;
    private final ResourceLoader resourceLoader;

    @Value("${spring.mail.username}")
    private String EMAIL;

    public void sendBasicEmail(String fromName, String toEmail, String subjectEmail, String bodyEmail){
        /* A SIMPLER FORMAT, might be useful
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("HiringSystem");
        msg.setTo(toEmail);
        msg.setSubject(subjectEmail);
        msg.setText(bodyEmail);
        */

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,"utf-8");
            helper.setText(bodyEmail, true);
            helper.setTo(toEmail);
            helper.setSubject(subjectEmail);
            helper.setFrom(EMAIL, fromName);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendAccountConfirmEmail(String toEmail, String token){
        try {
            String subject = "Confirm your account";
            Resource htmlResource = resourceLoader.getResource("classpath:" + "email_templates/confirm.html");
            String htmlContent = readResourceContent(htmlResource);

            // Load CSS file
            Resource cssResource = resourceLoader.getResource("classpath:" + "email_templates/confirm.css");
            String cssContent = readResourceContent(cssResource);

            // Combine HTML and CSS
            String body = htmlContent.replace("</head>", "<style>" + cssContent + "</style></head>");

            sendBasicEmail("HiringSystem", toEmail, subject, body.replace("%token-placeholder%", token));
        }catch(Exception x){
            x.printStackTrace();
        }
    }

    @Override
    public void sendInterviewCreationEmail(String toEmail, String interviewId, String interviewDate) {
        try {
            String subject = "Interview Scheduled";
            Resource htmlResource = resourceLoader.getResource("classpath:" + "email_templates/interview.html");
            String htmlContent = readResourceContent(htmlResource);

            // Load CSS file
            Resource cssResource = resourceLoader.getResource("classpath:" + "email_templates/confirm.css");
            String cssContent = readResourceContent(cssResource);

            // Combine HTML and CSS
            String body = htmlContent.replace("</head>", "<style>" + cssContent + "</style></head>");

            sendBasicEmail("HiringSystem", toEmail, subject, body.replace("%room-id%", interviewId).replace("%interview-date%", interviewDate));
        }catch(Exception x){
            x.printStackTrace();
        }
    }

    @Override
    public void sendApplicationAcceptedEmail(String toEmail) {
        try {
            String subject = "Application Accepted";
            Resource htmlResource = resourceLoader.getResource("classpath:" + "email_templates/accepted.html");
            String htmlContent = readResourceContent(htmlResource);

            // Load CSS file
            Resource cssResource = resourceLoader.getResource("classpath:" + "email_templates/confirm.css");
            String cssContent = readResourceContent(cssResource);

            // Combine HTML and CSS
            String body = htmlContent.replace("</head>", "<style>" + cssContent + "</style></head>");

            sendBasicEmail("HiringSystem", toEmail, subject, body);
        }catch(Exception x){
            x.printStackTrace();
        }
    }

    @Override
    public void sendDenyApplicationEmail(String toEmail) {
        try {
            String subject = "Application Denied";
            Resource htmlResource = resourceLoader.getResource("classpath:" + "email_templates/denied.html");
            String htmlContent = readResourceContent(htmlResource);

            // Load CSS file
            Resource cssResource = resourceLoader.getResource("classpath:" + "email_templates/confirm.css");
            String cssContent = readResourceContent(cssResource);

            // Combine HTML and CSS
            String body = htmlContent.replace("</head>", "<style>" + cssContent + "</style></head>");

            sendBasicEmail("HiringSystem", toEmail, subject, body);
        }catch(Exception x){
            x.printStackTrace();
        }
    }

    private String readResourceContent(Resource resource) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            StringBuilder contentBuilder = new StringBuilder();
            char[] buffer = new char[4096];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                contentBuilder.append(buffer, 0, read);
            }
            return contentBuilder.toString();
        }
    }
}
