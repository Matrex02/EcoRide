package com.ECO.login_System.appuser;

import com.ECO.login_System.registration.token.ConfirmationToken;
import com.ECO.login_System.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final static String USER_NOT_FOUND_MSG =
            "user with email %s not found";

    private final AppUserRepository appUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;



    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();

        if (userExists) {
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.

            throw new IllegalStateException("email already taken");
        }

        String encodedPassword = bCryptPasswordEncoder
                .encode(appUser.getPassword());

        appUser.setPassword(encodedPassword);

        AppUser savedUser = appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                savedUser
        );

        confirmationTokenService.saveConfirmationToken(confirmationToken);


        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    public AppUser updateAppUser(Long id, AppUser appUser) {
        // Überprüfen, ob der Benutzer in der Datenbank vorhanden ist
        Optional<AppUser> optionalAppUser = appUserRepository.findById(id);
        if (optionalAppUser.isPresent()) {
            // Den vorhandenen Benutzer abrufen
            AppUser existingUser = optionalAppUser.get();

            // Die relevanten Felder des Benutzers aktualisieren
            existingUser.setUserName(appUser.getUserName());
            existingUser.setTitel(appUser.getTitel());
            existingUser.setEmail(appUser.getEmail());
            existingUser.setAdresse(appUser.getAdresse());
            existingUser.setPlz(appUser.getPlz());
            existingUser.setStadt(appUser.getStadt());
            existingUser.setTelefonnummer(appUser.getTelefonnummer());
            existingUser.setFuehrerscheinnummer(appUser.getFuehrerscheinnummer());
            existingUser.setAblaufdatum(appUser.getAblaufdatum());

            existingUser.setTarif(appUser.getTarif());


            return appUserRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("Benutzer nicht gefunden");
        }
    }
    public AppUser saveUser(AppUser appUser) {
        return appUserRepository.save(appUser);
    }

}
