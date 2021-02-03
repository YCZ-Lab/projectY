package com.ysh.projectY.dao;

import com.ysh.projectY.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(String roleName);

    Page<Role> findAllByRoleNameContainingAndRoleDescriptContaining(String roleName, String roleDescript, Pageable pageable);
}
