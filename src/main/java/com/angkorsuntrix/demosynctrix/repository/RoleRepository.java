package com.angkorsuntrix.demosynctrix.repository;

import com.angkorsuntrix.demosynctrix.entity.Role;
import com.angkorsuntrix.demosynctrix.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
