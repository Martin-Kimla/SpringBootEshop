package spring.eshop.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spring.eshop.data.entities.UserEntity;
import spring.eshop.data.repositories.UserRepository;
import spring.eshop.models.dto.UserDTO;
import spring.eshop.models.exceptions.DuplicateEmailException;
import spring.eshop.models.exceptions.PasswordsDoNotEqualException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void create(UserDTO user, boolean isAdmin) {
        if (!user.getPassword().equals(user.getConfirmPassword()))
            throw new PasswordsDoNotEqualException();

        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setAdmin(isAdmin);

        try {
            usersRepository.save(userEntity);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateEmailException();
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usersRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username, " + username + " not found"));
    }
}
