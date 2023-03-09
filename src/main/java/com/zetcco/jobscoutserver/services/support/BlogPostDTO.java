package com.zetcco.jobscoutserver.services.support;

import java.util.Date;

import com.zetcco.jobscoutserver.domain.support.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogPostDTO {

    private Long id;
    private User user;
    private Date timeStamp;
    private String content;

}
