package com.example.fastsoccer.service;

import com.example.fastsoccer.entity.UserEntity;
import com.example.fastsoccer.repository.MyUserDetails;
import com.example.fastsoccer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl  implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findAllByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }

 /*   @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserEntity appUser = this.appUserDAO.findAllByUsername(userName);

        if (appUser == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        System.out.println("Found User: " + appUser);
        UserDetails userDetails = (UserDetails) new UserEntity(appUser.getUsername(), appUser.getPassword(), appUser.getRole());
        return userDetails;
    }*/
}
