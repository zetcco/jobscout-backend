package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.zetcco.jobscoutserver.services.support.CodeExecutor.CodeSubmission;
import com.zetcco.jobscoutserver.services.support.CodeExecutor.CodeSubmissionResult;
import com.zetcco.jobscoutserver.services.support.CodeExecutor.ProgrammingLanguage;

import reactor.core.publisher.Mono;

@Service
public class CodeExecutorService {

    private WebClient client;

    @Autowired
    public CodeExecutorService( @Value("${services.judge0_service.url}") String CLIENT_URL,
                                @Value("${services.judge0_service.headers.RAPID_API_KEY}") String API_KEY,
                                @Value("${services.judge0_service.headers.RAPID_API_HOST}") String API_HOST) {
        this.client = WebClient.builder()
                                .baseUrl(CLIENT_URL)
                                .defaultHeader("X-RapidAPI-Key", API_KEY)
                                .defaultHeader("X-RapidAPI-Host", API_HOST)
                                .build();
    } 

    public CodeSubmissionResult submit(CodeSubmission submission) {
        return this.client.post()
                    .uri("/submissions/?base64_encoded=true&wait=true")
                    .body(Mono.just(submission), CodeSubmission.class)
                    .retrieve()
                    .bodyToMono(CodeSubmissionResult.class)
                    .block();
    }

    public List<ProgrammingLanguage> getLanguages() {
        return this.client.get()
                    .uri("/languages")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<ProgrammingLanguage>>() {})
                    .block();
    }
    
}
