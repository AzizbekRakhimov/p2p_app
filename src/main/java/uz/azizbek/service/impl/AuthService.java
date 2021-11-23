package uz.azizbek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.azizbek.model.Users;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    public List<Users>  users = new ArrayList<>();

    @PostConstruct
    public void init(){
        users = Arrays.asList(
                new Users(1L, "Azizbek", passwordEncoder.encode("123"), new ArrayList<>()),
                new Users(2L, "Test", passwordEncoder.encode("123"), new ArrayList<>()));
    }

    @Override
    public Users loadUserByUsername(String s) throws UsernameNotFoundException {
        return users.stream().filter(users -> s.equals(users.getUsername())).findAny().orElseThrow(() -> new UsernameNotFoundException("Bad credentials"));
    }

    public Users findUserById(Long id){
        return users.stream().filter( users -> id.equals(users.getId())).findAny().orElse(null);
    }
}
