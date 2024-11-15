package by.dlstudio.jlaynor.parking.controller.abstr;
import by.dlstudio.jlaynor.parking.model.domain.entity.User;
import by.dlstudio.jlaynor.parking.model.domain.enums.Role;
import jakarta.servlet.http.HttpSession;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.nio.file.AccessDeniedException;

@Setter
@Component
public abstract class AbstractController {
    protected User currentUser;

    protected Boolean checkRole(Role role) {
        return currentUser != null && currentUser.getRole().equals(role);
    }

    @ModelAttribute
    public void setUser(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        setCurrentUser(user);
    }

    protected void requireRole(Role role) throws AccessDeniedException {
        if (!checkRole(role)) {
            throw new AccessDeniedException("Insufficient privileges");
        }
    }

    protected void requireAuth() throws AccessDeniedException {
        if (currentUser == null) {
            throw new AccessDeniedException("Must be authorized");
        }
    }
}
