package com.mainsys.challenge.repo.user;

import com.mainsys.challenge.model.user.Role;
import com.mainsys.challenge.model.user.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
