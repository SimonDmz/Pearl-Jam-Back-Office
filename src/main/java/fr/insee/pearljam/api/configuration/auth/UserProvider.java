package fr.insee.pearljam.api.configuration.auth;

import fr.insee.pearljam.api.configuration.auth.user.User;
import org.springframework.security.core.Authentication;

@FunctionalInterface
public interface UserProvider {

    User getUser(Authentication authentication);

}