package by.dlstudio.jlaynor.parking.model.service.impl;

import by.dlstudio.jlaynor.parking.model.domain.entity.User;
import by.dlstudio.jlaynor.parking.model.domain.enums.Role;
import by.dlstudio.jlaynor.parking.model.repository.AuthenticationRepository;
import by.dlstudio.jlaynor.parking.model.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationRepository authenticationRepository;

    @Override
    public Boolean register(User user) {
        user.setRole(Role.USER);
        return authenticationRepository.save(user).getId() != null;
    }

    @Override
    public User login(String email, String password) {
        return authenticationRepository.getUserByEmailAndPassword(email, password);
    }
}
