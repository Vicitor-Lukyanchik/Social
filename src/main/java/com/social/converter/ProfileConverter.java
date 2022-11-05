package com.social.converter;

import com.social.dto.ProfileDto;
import com.social.entity.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProfileConverter implements Converter<Profile, ProfileDto> {

    @Override
    public ProfileDto convert(Profile profile) {
        return ProfileDto.builder()
                .id(profile.getId())
                .firstname(profile.getFirstname())
                .lastname(profile.getLastname())
                .age(profile.getAge())
                .email(profile.getEmail())
                .familyStatus(profile.getFamilyStatus())
                .phone(profile.getPhone())
                .sex(profile.getSex())
                .town(profile.getTown())
                .build();
    }

    public Profile convertToProfile(ProfileDto profileDto){
        return Profile.builder()
                .id(profileDto.getId())
                .firstname(profileDto.getFirstname())
                .lastname(profileDto.getLastname())
                .age(profileDto.getAge())
                .email(profileDto.getEmail())
                .familyStatus(profileDto.getFamilyStatus())
                .phone(profileDto.getPhone())
                .sex(profileDto.getSex())
                .town(profileDto.getTown())
                .build();
    }
}
