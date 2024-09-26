package com.attractor.microgram.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublicationDto {
    private Long id;
    private Long authorId;
    private String image;
    private String description;
    private LocalDateTime timeOfPublication;
    private boolean isLike;
    private Integer likeCount;
}
