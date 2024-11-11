package by.dlstudio.jlaynor.parking.model.service;

import by.dlstudio.jlaynor.parking.model.domain.entity.Rule;

import java.util.List;

public interface RulesService {

    List<Rule> getRules();

    Boolean addRule(Rule rule);

    Boolean updateRule(Rule rule);

    Boolean deleteRule(Integer id);
}
