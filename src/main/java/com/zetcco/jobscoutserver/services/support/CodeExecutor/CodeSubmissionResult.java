package com.zetcco.jobscoutserver.services.support.CodeExecutor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CodeSubmissionResult {
    
    private String stdout;
    private String time;
    private Integer memory;
    private String stderr;
    private String message;

}
