package ro.hiringsystem.service.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String EMAIL;

    public void sendBasicEmail(String fromName, String toEmail, String subjectEmail, String bodyEmail){
        /* A MORE SIMPLE FORMAT, might be useful
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("HiringSystem");
        msg.setTo(toEmail);
        msg.setSubject(subjectEmail);
        msg.setText(bodyEmail);
        */

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(bodyEmail);
            helper.setTo(toEmail);
            helper.setSubject(subjectEmail);
            helper.setFrom(EMAIL, fromName);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
