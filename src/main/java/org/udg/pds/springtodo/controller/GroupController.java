package org.udg.pds.springtodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.udg.pds.springtodo.entity.Group;
import org.udg.pds.springtodo.entity.IdObject;
import org.udg.pds.springtodo.service.GroupService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RequestMapping(path="/groups")
@RestController
public class GroupController extends BaseController {

    @Autowired
    GroupService groupService;

    @PostMapping(consumes = "application/json")
    public IdObject addGroup(HttpSession session, @Valid @RequestBody GroupController.R_Group group) {

        Long userId = getLoggedUser(session);
        return groupService.addGroup(group.name, userId, group.description);
    }

    @GetMapping(path="/{id}")
    public Group getGroup(HttpSession session,
                         @PathVariable("id") Long id) {
        Long userId = getLoggedUser(session);

        return groupService.getGroup(userId, id);
    }

    @PostMapping(path="/{grid}/members/{id}")
    public String addMember(HttpSession session,
                          @PathVariable("grid") Long groupId, @PathVariable("id") Long memberId) {

        Long userId = getLoggedUser(session);
        groupService.addMemberToGroup(userId, groupId, memberId);
        return BaseController.OK_MESSAGE;
    }

    static class R_Group {

        @NotNull
        public String name;

        @NotNull
        public String description;
    }
}
