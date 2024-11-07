package com.pets.petAfriend.features.mail;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    /**
     *
     * @param sendTo
     * @param mailMessage
     * @param subject
     * @throws MessagingException
     */
    public void send(final String sendTo, final String mailMessage, final String subject) throws MessagingException {

        var mimeMessage = mailSender.createMimeMessage();
        var message = new MimeMessageHelper(mimeMessage);
        var contentA = """
                <html>
                <p>Please do not respond to this email.</p>
                <p>
                """;
        final var contentB = """
                </p>
                </html>
                """;
        message.setFrom("noreply@example.org");
        message.setTo(sendTo);
        message.setSubject(subject);
        message.setText(contentA + mailMessage + contentB, true);
        mailSender.send(message.getMimeMessage());
    }
}
