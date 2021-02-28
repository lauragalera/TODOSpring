package org.udg.pds.springtodo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.udg.pds.springtodo.controller.exceptions.ServiceException;
import org.udg.pds.springtodo.entity.*;
import org.udg.pds.springtodo.repository.GroupRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserService userService;

    public GroupRepository crud() {
        return groupRepository;
    }

    public Group getGroup(Long userId, Long id) {
        Optional<Group> g = groupRepository.findById(id);
        if (!g.isPresent()) throw new ServiceException("Task does not exists");
        if (g.get().getUser().getId() != userId)
            throw new ServiceException("User does not own this task");
        return g.get();
    }

    @Transactional
    public IdObject addGroup(String name, Long userId,
                            String desc) {
        try {
            User user = userService.getUser(userId);

            Group group = new Group(name, desc);

            group.setUser(user);

            user.addGroupOwner(group);

            groupRepository.save(group);
            return new IdObject(group.getId());
        } catch (Exception ex) {
            // Very important: if you want that an exception reaches the EJB caller, you have to throw an ServiceException
            // We catch the normal exception and then transform it in a ServiceException
            throw new ServiceException(ex.getMessage());
        }
    }

    @Transactional
    public void addMemberToGroup(Long userId, Long groupId, Long memberId) {
        Group g = this.getGroup(userId, groupId);

        if (g.getUser().getId() != userId)
            throw new ServiceException("This user is not the owner of the group");

        try {
                User u = userService.getUser(memberId);
                g.addMember(u);
        } catch (Exception ex) {
            // Very important: if you want that an exception reaches the EJB caller, you have to throw an ServiceException
            // We catch the normal exception and then transform it in a ServiceException
            throw new ServiceException(ex.getMessage());
        }
    }
}
