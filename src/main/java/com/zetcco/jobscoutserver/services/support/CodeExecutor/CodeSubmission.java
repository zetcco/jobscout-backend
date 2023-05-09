package com.zetcco.jobscoutserver.services.support.CodeExecutor;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CodeSubmission {
    
    private String source_code;
    private Integer language_id;
    private String stdin;

}
