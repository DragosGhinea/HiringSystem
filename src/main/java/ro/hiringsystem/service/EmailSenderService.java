package ro.hiringsystem.service;

public interface EmailSenderService {

    void sendBasicEmail(String fromName, String toEmail, String subjectEmail, String bodyEmail);
}
