package doctorBookingApp.service.userService;


import doctorBookingApp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class MailService {
    private final JavaMailSender mailSender;

    public void sendConfirmationEmail(User user, String confirmationCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("doctorbooking4@gmail.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Registration Confirmation Code");
        mailMessage.setText("Bitte bestätigen Sie Ihre Registrierung mit dem Code: " + confirmationCode);
        mailSender.send(mailMessage);
    }

}


//формирует и отправляет письмо с кодом подтверждения