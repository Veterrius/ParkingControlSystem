package by.dlstudio.jlaynor.parking.model.service;

import by.dlstudio.jlaynor.parking.model.domain.entity.User;

public interface AuthenticationService {

    Boolean register(User user);

    User login(String email, String password);
}
