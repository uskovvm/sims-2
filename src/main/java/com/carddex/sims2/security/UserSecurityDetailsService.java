package com.carddex.sims2.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.carddex.sims2.persistence.dao.UserRepository;
import com.carddex.sims2.persistence.model.Privilege;
import com.carddex.sims2.persistence.model.Role;
import com.carddex.sims2.persistence.model.User;

@Service("userDetailsService")
@Transactional
public class UserSecurityDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @Autowired
    private HttpServletRequest request;

    public UserSecurityDetailsService() {
        super();
    }

    // API

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }

        try {
            final User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException("No user found with username: " + email);
            }
            org.springframework.security.core.userdetails.User userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(user.getRoles()));
            return userDetails;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    public UserLoginDetails restLoadUserByUsername(final String email) throws UsernameNotFoundException {
        final String ip = getClientIP();
        if (loginAttemptService.isBlocked(ip)) {
            throw new RuntimeException("blocked");
        }

        try {
            final User user = userRepository.findByEmail(email);
            UserLoginDetails userDetails;
            if (user == null) {
            	userDetails = new UserLoginDetails("error", "Login Failure");                
            	//throw new UsernameNotFoundException("No user found with username: " + email);
            }else
            	userDetails = new UserLoginDetails("success", "Login Success", user.getId(),user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getRoles(user.getRoles()), getPrivileges(user.getRoles()));
            
            return userDetails;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
    // UTIL

    private final Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private final List getPrivileges(final Collection<Role> roles) {
        final List<Long> privileges = new ArrayList<Long>();
        final List<Privilege> collection = new ArrayList<Privilege>();
        for (final Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
            //privileges.add(item.getName());
        	privileges.add(item.getId());
        }

        return privileges;
    }

    private final List getRoles(final Collection<Role> roles) {
        final List<Long> collection = new ArrayList<Long>();
        for (final Role role : roles) {
            //collection.add(role.getName());
        	collection.add(role.getId());
        }

        return collection;
    }

    private final List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private final String getClientIP() {
        final String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

}
