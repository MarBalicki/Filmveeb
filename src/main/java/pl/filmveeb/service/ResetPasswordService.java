package pl.filmveeb.service;

import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import pl.filmveeb.model.ResetPassword;
import pl.filmveeb.model.User;
import pl.filmveeb.repository.ResetPasswordRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ResetPasswordService {

    private final static int TOKEN_LENGTH = 25;
    private final static int EXPIRY_TIME = 10;
    private final ResetPasswordRepository resetPasswordRepository;
    private final MessageService messageService;

    public ResetPasswordService(ResetPasswordRepository resetPasswordRepository, MessageService messageService) {
        this.resetPasswordRepository = resetPasswordRepository;
        this.messageService = messageService;
    }

    public void saveResetPasswordRequest(User user) {
        String token = new RandomString(TOKEN_LENGTH).nextString();
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setToken(token);
        resetPassword.setExpiryDate(LocalDateTime.now().plusMinutes(EXPIRY_TIME));
        resetPassword.setUser(user);
        resetPassword.setUsed(false);
        resetPasswordRepository.save(resetPassword);
        messageService.sendResetMail(token, user.getEmail());
    }

    public User getUserByToken(String token) {
//        Optional<ResetPassword> byToken = resetPasswordRepository.findByToken(token);
//        if (byToken.isPresent()) {
//            ResetPassword resetPassword = byToken.get();
//            if (!resetPassword.isUsed() || resetPassword.getExpiryDate().isAfter(LocalDateTime.now())) {
//                return resetPassword.getUser();
//            } else {
//                System.out.println("Token is used or expired!");
//            }
//        } else {
//            throw new RuntimeException();
//        }
        return resetPasswordRepository.findByToken(token)
                .filter(ResetPassword::isUsed)
                .filter(resetPassword -> resetPassword.getExpiryDate().isAfter(LocalDateTime.now()))
                .map(ResetPassword::getUser)
                .orElseThrow(
                        () -> new RuntimeException("Token was used or expired or not exists!"));
    }

    public void changeTokenToUsed(Long id) {
        Optional<ResetPassword> byUser = resetPasswordRepository.findByUser(id);
        if (byUser.isPresent()) {
            ResetPassword resetPassword = byUser.get();
            resetPassword.setUsed(true);
            resetPasswordRepository.save(resetPassword);
        }
    }
}
