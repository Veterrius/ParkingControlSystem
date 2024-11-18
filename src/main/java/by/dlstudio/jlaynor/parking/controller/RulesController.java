package by.dlstudio.jlaynor.parking.controller;

import by.dlstudio.jlaynor.parking.model.domain.other.RuleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

public interface RulesController {
    @GetMapping
    String showAllRules(Model model) throws AccessDeniedException;

    @PostMapping
    String addRule(@RequestBody RuleDTO ruleDTO) throws AccessDeniedException;

    @PutMapping("/update")
    ResponseEntity<Void> updateRule(@RequestBody RuleDTO ruleDTO) throws AccessDeniedException;

    @DeleteMapping("/delete")
    ResponseEntity<Void> deleteRules(@RequestBody RuleDTO ruleDTO) throws AccessDeniedException;
}
