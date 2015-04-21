package demo.social;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import demo.data.User;
import demo.data.UserRepository;

@Service
public class RepositoryUserDetailsService implements UserDetailsService {
 
    private UserRepository repository;
 
    @Autowired
    public RepositoryUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }
 
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
 
        if (user == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }
 
        SimpleUserDetails principal = SimpleUserDetails.getBuilder()
                .firstName(user.getFirstName())
                .id(user.getId())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .role(user.getRole())
                .socialSignInProvider(user.getSignInProvider())
                .username(user.getEmail())
                .build();
 
        return principal;
    }
}