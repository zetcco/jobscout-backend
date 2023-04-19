package com.zetcco.jobscoutserver.domain.questionary;

// import com.zetcco.jobscoutserver.services.support.ProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionaryAttemptDTO {

    private Long id;
    private QuestionaryDTO questionary;
    // private ProfileDTO jobSeeker;
    private Integer attempts;
    private Float score;
    private Boolean isPublic;
    
}
