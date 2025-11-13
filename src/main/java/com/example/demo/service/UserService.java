package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User updateUserByBokadaka(User user) {
        Optional<User> retriveduser = userRepository.findByBokadaka(user.getBokadaka());
        if (retriveduser.isPresent()) {
            User existingUser = retriveduser.get();
            existingUser.setName(user.getName());
            // Update other fields as necessary
            return userRepository.save(existingUser);
        } else {
            return userRepository.save(user);
        }
    }

    @Transactional
    public User updateUserByEmail(User user) {
/*        return userRepository.findByEmail(user.getEmail())
                .map(existingUser -> {
                    existingUser.setName(user.getName());
                    // Update other fields as necessary
                    return userRepository.save(existingUser);
                })
                .orElseGet(() -> userRepository.save(user));*/

        Optional<User> retriveduser = userRepository.findByEmail(user.getEmail());
        if (retriveduser.isPresent()) {
            User existingUser = retriveduser.get();
            existingUser.setName(user.getName());
            existingUser.setShippingOrders(user.getShippingOrders());
            // Update other fields as necessary
            return userRepository.save(existingUser);
        } else {
            return userRepository.save(user);
        }
    }

    @Transactional
    public User createUser(User user) {
/*        System.out.println("Creating user: " + user.getName() + ", Email: " + user.getEmail());
        if (user.getId() != null) {
            System.out.println("User ID is not null - Updating existing user with ID: " + user.getId());
            // Use merge for detached entities
                 if (user.getVersion() == null) {
                    user.setVersion(0); // Initialize version to 0 for new users
                }
            user = entityManager.merge(user);
            System.out.println("User merged with ID: " + user.getId());
        } else {
            System.out.println("User ID is null - Creating a new user");
            entityManager.persist(user); // Persist new user
            System.out.println("New user persisted with ID: " + user.getId());
        }
        return user;*/
/*        System.out.println("Creating user: " + user.getName() + ", Email: " + user.getEmail());
        if (user.getId() != null) {
            System.out.println("usergetId is not null - Hence Updating existing user with ID: " + user.getId());
            // Fetch the managed entity from the database
            User managedUser = entityManager.find(User.class, user.getId());
            System.out.println("Managed User fetched with ID: " + (managedUser != null ? managedUser.getId() : "null"));
            if (managedUser != null) {
                System.out.println("Updating existing user with ID: " + user.getId());
                managedUser.setName(user.getName());
                managedUser.setEmail(user.getEmail());
                return managedUser;
            }
        }
        // Persist new user
        System.out.println("Creating/Persisting new user");
        user.setVersion(0); // Initialize version for new entities
        entityManager.persist(user);
        System.out.println("New user persisted with ID: " + user.getId());
        return user;*/
/*        if (user.getVersion() == null) {
            user.setVersion(0); // Initialize version to 0 for new users
        }
        System.out.println("User Version "+ user.getVersion());
        if (!entityManager.contains(user)) {
            System.out.println("merging user");
            user = entityManager.merge(user);
        } else {
            System.out.println("persisting user");
             entityManager.persist(user);
        }
        return user;*/
        return userRepository.save(user);
    }

}
