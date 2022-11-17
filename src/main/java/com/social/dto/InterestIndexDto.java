package com.social.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterestIndexDto {

    private boolean isSort;

    private int offset;

    private int pageSize;
}
