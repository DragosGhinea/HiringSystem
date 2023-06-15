package ro.hiringsystem.service;

public interface EmailSenderService {

    void sendAccountConfirmEmail(String toEmail, String token);

    void sendInterviewCreationEmail(String toEmail, String interviewId, String interviewDate);

    void sendApplicationAcceptedEmail(String toEmail);

    void sendDenyApplicationEmail(String toEmail);

    void sendBasicEmail(String fromName, String toEmail, String subjectEmail, String bodyEmail);
}
