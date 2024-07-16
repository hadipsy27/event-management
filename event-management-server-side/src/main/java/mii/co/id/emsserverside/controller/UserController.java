package mii.co.id.emsserverside.controller;

//<editor-fold defaultstate="collapsed" desc="Import">
import java.util.List;
import mii.co.id.emsserverside.model.User;
import mii.co.id.emsserverside.model.data.UserDto;
import mii.co.id.emsserverside.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//</editor-fold>

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countUser() {
        return new ResponseEntity(userService.countUser(), HttpStatus.OK);
    }

    @GetMapping("/getby-email/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable("email") String email) {
        return new ResponseEntity(userService.getProfile(email), HttpStatus.OK);
    }

    @GetMapping("/get-byrole/{role}")
    public ResponseEntity<List<User>> getByRole(@PathVariable("role") String role) {
        return new ResponseEntity(userService.getByRole(role), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
        return new ResponseEntity(userService.getById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return new ResponseEntity(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/activation/{token}")
    public String activation(@PathVariable("token") String token) {
        return userService.activation(token);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDto userDto) {
        return new ResponseEntity(userService.create(userDto), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        return new ResponseEntity(userService.update(id, userDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> delete(@PathVariable("id") Long id) {
        return new ResponseEntity(userService.delete(id), HttpStatus.OK);
    }
}
