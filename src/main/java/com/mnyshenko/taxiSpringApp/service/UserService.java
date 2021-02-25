package com.mnyshenko.taxiSpringApp.service;

import com.mnyshenko.taxiSpringApp.constant.Constants;
import com.mnyshenko.taxiSpringApp.constant.Role;
import com.mnyshenko.taxiSpringApp.dao.UserRepository;
import com.mnyshenko.taxiSpringApp.dto.UserDTO;
import com.mnyshenko.taxiSpringApp.exception.UserException;
import com.mnyshenko.taxiSpringApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.mnyshenko.taxiSpringApp.constant.Constants.PAGE_SIZE;
import static com.mnyshenko.taxiSpringApp.constant.Role.ROLE_USER;

@Service
public class UserService implements UserDetailsService {

    private final static String ROLE_USER_NOT_FOUND_MSG = "User with email %s not found";
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Page<User> getPaginated(int pageNo, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, PAGE_SIZE, sort);
        return userRepository.findAll(pageable);
    }

    public void updateSpentMoney(User user, Double price) {
        user.setSpentMoney(user.getSpentMoney() + price);
        userRepository.save(user);
    }

    public void save(UserDTO userDTO) throws UserException {

        if (userRepository.findUserByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserException("User already exists");
        }

        userRepository.save(new User(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                bCryptPasswordEncoder.encode(userDTO.getPassword()),
                ROLE_USER
        ));
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id).
                orElseThrow(() ->
                        new UsernameNotFoundException("No user with such id"));
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).
                orElseThrow(() ->
                        new UsernameNotFoundException("No user with such id"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                String.format(ROLE_USER_NOT_FOUND_MSG, email)));
    }
}
