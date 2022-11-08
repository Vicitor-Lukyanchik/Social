package com.social.converter;

import com.social.dto.ProfileDto;
import com.social.entity.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileToDtoConverter implements Converter<Profile, ProfileDto> {

    @Override
    public ProfileDto convert(Profile profile) {
        return ProfileDto.builder()
                .id(profile.getId())
                .firstname(profile.getFirstname())
                .lastname(profile.getLastname())
                .town(profile.getTown())
                .age(profile.getAge())
                .email(profile.getEmail())
                .familyStatus(profile.getFamilyStatus())
                .phone(profile.getPhone())
                .sex(profile.getSex())
                .message("")
                .build();
    }
}
