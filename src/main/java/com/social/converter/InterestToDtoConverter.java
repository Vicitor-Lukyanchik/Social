package com.social.converter;

import com.social.dto.InterestDto;
import com.social.entity.Interest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InterestToDtoConverter implements Converter<Interest, InterestDto> {

    @Override
    public InterestDto convert(Interest interest) {
        return InterestDto.builder()
                .id(interest.getId())
                .name(interest.getName())
                .message("")
                .build();
    }
}
