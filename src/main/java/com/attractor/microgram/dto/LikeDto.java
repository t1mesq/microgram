package com.attractor.microgram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeDto {
    private Long id;
    private Long likerId;
    private Long publicationId;
}
