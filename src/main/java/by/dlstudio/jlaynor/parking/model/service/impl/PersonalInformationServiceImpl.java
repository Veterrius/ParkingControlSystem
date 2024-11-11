package by.dlstudio.jlaynor.parking.model.service.impl;

import by.dlstudio.jlaynor.parking.model.domain.entity.User;
import by.dlstudio.jlaynor.parking.model.repository.PersonalInformationRepository;
import by.dlstudio.jlaynor.parking.model.service.PersonalInformationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PersonalInformationServiceImpl implements PersonalInformationService {
    private final PersonalInformationRepository informationRepository;

    @Override
    public User getUser(Integer id) {
        return informationRepository.getUserById(id);
    }
}
