package com.wevioo.cantine.services;

import com.wevioo.cantine.entities.Role;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface IRoleService {
    Role findRoleById(Integer id);
    List<Role> getAllRoles();
}
