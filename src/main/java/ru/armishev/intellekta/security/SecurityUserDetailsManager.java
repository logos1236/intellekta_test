package ru.armishev.intellekta.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SecurityUserDetailsManager implements UserDetailsManager {
    @Override
    public void createUser(UserDetails userDetails) {

    }

    @Override
    public void updateUser(UserDetails userDetails) {

    }

    @Override
    public void deleteUser(String s) {

    }

    @Override
    public void changePassword(String s, String s1) {

    }

    @Override
    public boolean userExists(String s) {
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        /* TODO
          Должна быть реализована логика по вычитке пользователя из хранилища данных
         */
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        if (!userName.equals("user")) {
            throw new UsernameNotFoundException("Нет такого пользователя");
        }

        List<SecurityPermission> permissions = new ArrayList<>();

        permissions.add(new SecurityPermission("product.read"));
        permissions.add(new SecurityPermission("product.readById"));
        permissions.add(new SecurityPermission("product.write"));
        permissions.add(new SecurityPermission("product.delete"));

        permissions.add(new SecurityPermission("salesPeriod.read"));
        permissions.add(new SecurityPermission("salesPeriod.readById"));
        permissions.add(new SecurityPermission("salesPeriod.write"));
        permissions.add(new SecurityPermission("salesPeriod.delete"));

        return new SecurityUser(userName, encoder.encode("123"), permissions);
    }
}
