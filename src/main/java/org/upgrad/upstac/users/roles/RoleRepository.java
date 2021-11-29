package org.upgrad.upstac.users.roles;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

  Role findByName(String name);

  List<Role> findAll();


}
