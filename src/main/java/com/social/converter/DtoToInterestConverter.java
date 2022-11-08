package com.social.converter;

import com.social.dto.InterestDto;
import com.social.entity.Interest;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToInterestConverter implements Converter<InterestDto, Interest> {

    @Override
    public Interest convert(InterestDto interestDto){
        return Interest.builder()
                .id(interestDto.getId())
                .name(interestDto.getName())
                .build();
    }
}
