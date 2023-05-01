package uz.devops.web.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @PreAuthorize("hasPermission('hasAccess', 'ROLE_')")
    @GetMapping("/getAllNames")
    public List<String> getAllBookNames() {
        return List.of("Hello java", "Islom");
    }
}
