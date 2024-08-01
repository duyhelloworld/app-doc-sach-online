package huce.edu.vn.appdocsach.services.impl.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import huce.edu.vn.appdocsach.repositories.database.UserRepo;
import huce.edu.vn.appdocsach.services.impl.auth.users.AuthenticatedUser;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return AuthenticatedUser.of(userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Not found " + username)));
    }

}
