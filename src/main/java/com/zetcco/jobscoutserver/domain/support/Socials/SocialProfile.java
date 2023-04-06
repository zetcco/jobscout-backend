package com.zetcco.jobscoutserver.domain.support.Socials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SocialProfile {
    private SocialPlatform platform;
    private String link;
}
