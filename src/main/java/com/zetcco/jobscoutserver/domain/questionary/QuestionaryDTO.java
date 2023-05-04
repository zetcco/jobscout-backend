package com.zetcco.jobscoutserver.domain.questionary;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionaryDTO {
    private Long id;

    private String name;
    private String badge;
    private String description;
    private Integer timePerQuestion;
    private Integer attemptCount;
    private List<QuestionDTO> questions;
}
