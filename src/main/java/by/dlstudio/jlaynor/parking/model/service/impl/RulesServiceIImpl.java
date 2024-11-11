package by.dlstudio.jlaynor.parking.model.service.impl;

import by.dlstudio.jlaynor.parking.model.domain.entity.Rule;
import by.dlstudio.jlaynor.parking.model.repository.RulesRepository;
import by.dlstudio.jlaynor.parking.model.service.RulesService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RulesServiceIImpl implements RulesService {
    private final RulesRepository rulesRepository;

    @Override
    public List<Rule> getRules() {
        return rulesRepository.findAll();
    }

    @Override
    public Boolean addRule(Rule rule) {
        return rulesRepository.save(rule).getId() != null;
    }

    @Override
    public Boolean updateRule(Rule rule) {
        return addRule(rule);
    }

    @Override
    public Boolean deleteRule(Integer id) {
        rulesRepository.deleteById(id);
        return rulesRepository.findById(id).isEmpty();
    }
}
