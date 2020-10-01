package com.mainsys.challenge.repo.user;

import com.mainsys.challenge.model.user.Role;
import com.mainsys.challenge.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Boolean existsByUsername(String username);

    List<User> findByRoles(Role role);

    Optional<User> findByUsername(String username);
}
