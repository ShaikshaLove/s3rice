package io.app.security;

import io.app.dto.User;
import io.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        try {
            User user = userRepository.findByUserName(username);
            System.out.println(user);
            if (user == null) {
                throw new UsernameNotFoundException(
                        "No user found with username: " + username);
            }


             return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword().toLowerCase(),
                    user.isEnabled(),
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    Arrays.asList(new GrantedAuthority() {
                        @Override
                        public String getAuthority() {
                            return "ADMIN_ROLE";
                        }
                    }));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
