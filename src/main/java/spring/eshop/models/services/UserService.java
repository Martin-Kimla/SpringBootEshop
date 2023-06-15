package spring.eshop.models.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import spring.eshop.models.dto.UserDTO;

public interface UserService extends UserDetailsService {

    void create(UserDTO user, boolean isAdmin);
}
