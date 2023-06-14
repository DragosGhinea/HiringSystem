package ro.hiringsystem.service;

public interface EmailSenderService {

    void sendAccountConfirmEmail(String toEmail, String token);
    void sendBasicEmail(String fromName, String toEmail, String subjectEmail, String bodyEmail);
}
