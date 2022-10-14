package com.social.service.impl;

import com.social.entity.Group;
import com.social.entity.Interest;
import com.social.entity.Profile;
import com.social.repository.GroupRepository;
import com.social.service.GroupService;
import com.social.service.InterestService;
import com.social.service.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final InterestService interestService;

    @Override
    @Transactional
    public Group save(@Valid Group group, Profile profile, Interest interest) {
        if (!interestService.isExist(interest.getName())){
            throw new ServiceException("Interest haven't been founded by name " + interest.getName());
        }
        return groupRepository.save(buildGroup(group, profile, interest));
    }

    private Group buildGroup(Group group, Profile profile, Interest interest) {
        group.setProfile(profile);
        group.setInterest(interest);
        group.setJoinProfiles(new ArrayList<>());
        return group;
    }

    @Override
    @Transactional
    public boolean isExist(Group group) {
        try {
            findById(group.getId());
            return true;
        } catch (ServiceException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public Group findById(Long id) {
        Optional<Group> group = groupRepository.findById(id);

        if (group.isEmpty()) {
            throw new ServiceException("Group haven't been founded by id : " + id);
        }
        return group.get();
    }
}
