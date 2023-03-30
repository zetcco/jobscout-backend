package com.zetcco.jobscoutserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.zetcco.jobscoutserver.services.support.CodeExecutor.CodeSubmission;
import com.zetcco.jobscoutserver.services.support.CodeExecutor.CodeSubmissionResult;

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

    public void getTest() {
        this.client.get().uri("/about").exchangeToMono(response -> {
            System.out.println("------------------------------");
            System.out.println(response.statusCode());
            System.out.println("------------------------------");
            return response.bodyToMono(Void.class);
        }).block();
    }

    public void postTest() {
        CodeSubmission submission = new CodeSubmission(
            "#include <stdio.h>\n\nint main(void) {\n  char name[10];\n  scanf(\"%s\", name);\n  printf(\"hello, %s\n\", name);\n  return 0;\n}",
            4,
            "motherfucker");

        CodeSubmissionResult result = this.client.post()
                    .uri("/submissions/?base64_encoded=true&wait=true")
                    .body(Mono.just(submission), CodeSubmission.class)
                    .exchangeToMono(response -> {
                        System.out.println("--------------------------");
                        System.out.println(response.statusCode());
                        System.out.println("--------------------------");
                        return response.bodyToMono(CodeSubmissionResult.class);
                    })
                    .block();

        System.out.println(result);
    }

    public CodeSubmissionResult submit(CodeSubmission submission) {
        return this.client.post()
                    .uri("/submissions/?base64_encoded=true&wait=true")
                    .body(Mono.just(submission), CodeSubmission.class)
                    .exchangeToMono(response -> {
                        System.out.println("--------------------------");
                        System.out.println(response.statusCode());
                        System.out.println("--------------------------");
                        return response.bodyToMono(CodeSubmissionResult.class);
                    })
                    .block();
    }
    
}
