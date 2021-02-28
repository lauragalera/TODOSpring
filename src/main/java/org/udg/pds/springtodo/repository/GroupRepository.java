package org.udg.pds.springtodo.repository;

import org.springframework.data.repository.CrudRepository;
import org.udg.pds.springtodo.entity.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {
}
