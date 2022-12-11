package com.social.converter;

import com.social.dto.ProfileDto;
import com.social.entity.Profile;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToProfileConverter implements Converter<ProfileDto, Profile> {

    @Override
    public Profile convert(ProfileDto profile){
        return Profile.builder()
                .id(profile.getId())
                .firstname(profile.getFirstname())
                .lastname(profile.getLastname())
                .town(profile.getTown())
                .email(profile.getEmail())
                .familyStatus(profile.getFamilyStatus())
                .phone(profile.getPhone())
                .sex(profile.getSex())
                .build();
    }
}
