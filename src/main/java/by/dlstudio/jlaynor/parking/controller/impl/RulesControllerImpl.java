package by.dlstudio.jlaynor.parking.controller.impl;

import by.dlstudio.jlaynor.parking.controller.RulesController;
import by.dlstudio.jlaynor.parking.controller.abstr.AbstractController;
import by.dlstudio.jlaynor.parking.model.domain.entity.Rule;
import by.dlstudio.jlaynor.parking.model.domain.enums.Role;
import by.dlstudio.jlaynor.parking.model.domain.other.RuleDTO;
import by.dlstudio.jlaynor.parking.model.service.RulesService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.InvalidParameterException;

@AllArgsConstructor
@Controller
@RequestMapping("/rules")
public class RulesControllerImpl extends AbstractController implements RulesController {
    private final RulesService rulesService;

    @Override
    @GetMapping
    public String showAllRules(Model model) throws AccessDeniedException {
        requireAuth();
        model.addAttribute("rules", rulesService.getRules());
        return currentUser.getRole().equals(Role.ADMIN) ? "modify-rules" : "rules";
    }

    @Override
    @PostMapping
    public String addRule(@RequestBody RuleDTO ruleDTO) throws AccessDeniedException {
        requireRole(Role.ADMIN);
        Rule rule = new Rule();
        rule.setTitle(ruleDTO.getTitle());
        rule.setBody(ruleDTO.getBody());
        rule.setFineValue(0.0f);
        if (rulesService.addRule(rule)) {
            return "redirect:/rules";
        }
        return "redirect:/rules?error";
    }

    @Override
    @PutMapping("/update")
    public ResponseEntity<Void> updateRule(@RequestBody RuleDTO ruleDTO) throws AccessDeniedException {
        requireRole(Role.ADMIN);
        Rule updatedRule = rulesService.getRuleById(ruleDTO.getId()).orElseThrow(() ->
                new InvalidParameterException("Wrong rule")
        );
        updatedRule.setBody(ruleDTO.getBody());
        if (rulesService.updateRule(updatedRule)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRules(@RequestBody RuleDTO ruleDTO) throws AccessDeniedException {
        requireRole(Role.ADMIN);
        for (Integer id : ruleDTO.getIds()) {
            rulesService.deleteRule(id);
        }
        return ResponseEntity.ok().build();
    }
}
