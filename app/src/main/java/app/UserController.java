package app;

import dao.OrderDao;
import dao.UserDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import model.User;

import java.security.Principal;
import java.util.List;

@RestController
public class UserController {

    private UserDao dao;

    public UserController(UserDao dao) {
        this.dao = dao;
    }

    @GetMapping("/")
    public String frontPage() {
        return "Front page!";
    }

    @GetMapping("/api/home")
    public String home() {
        return "Api home url";
    }

    @GetMapping("/api/version")
    public String version() {
        return "1.0";
    }

    @GetMapping("/api/info")
    public String info(Principal principal) {
        String user = principal != null ? principal.getName() : "";

        return "Current user: " + user;
    }

    @GetMapping("/api/admin/info")
    public String adminInfo(Principal principal) {
        return "Admin user info: " + principal.getName();
    }

    @GetMapping("/api/users")
    @PreAuthorize("authentication.name == 'admin'")
    public List<User> getUsers() {
        return dao.findAll();
    }

    @GetMapping("/api/users/{userName}")
    @PreAuthorize("#userName == authentication.name || authentication.name == 'admin'")
    public User getUserByName(@PathVariable String userName) {
        return dao.getUserByUserName(userName);
    }

}