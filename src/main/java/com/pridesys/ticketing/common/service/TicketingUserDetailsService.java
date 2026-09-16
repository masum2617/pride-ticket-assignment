package com.pridesys.ticketing.common.service;

import com.pridesys.ticketing.app.auth.entity.UserEntity;
import com.pridesys.ticketing.app.auth.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketingUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByEmail(username).orElseThrow(()->
                new UsernameNotFoundException("User details not found for  "+username));


        List<GrantedAuthority> authorities = user.getRoles().stream().map(thisUser -> new
                SimpleGrantedAuthority(thisUser.getRoleName())).collect(Collectors.toList());
        return new User(user.getEmail(), user.getPassword(), authorities);

    }
}
