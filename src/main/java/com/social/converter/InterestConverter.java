package com.social.converter;

import com.social.dto.InterestDto;
import com.social.entity.Interest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InterestConverter implements Converter<Interest, InterestDto> {

    @Override
    public InterestDto convert(Interest interest) {
        return InterestDto.builder()
                .id(interest.getId())
                .name(interest.getName())
                .build();
    }

    public Interest convertToInterest(InterestDto interestDto){
        return Interest.builder()
                .id(interestDto.getId())
                .name(interestDto.getName())
                .build();
    }
}
