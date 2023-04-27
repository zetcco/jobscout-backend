package com.zetcco.jobscoutserver.controllers;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.controllers.support.QuestionaryForm;
import com.zetcco.jobscoutserver.domain.questionary.QuestionaryAttemptDTO;
import com.zetcco.jobscoutserver.domain.questionary.QuestionaryDTO;
import com.zetcco.jobscoutserver.services.questionary.QuestionaryService;
import com.zetcco.jobscoutserver.services.support.exceptions.NotFoundException;

@Controller
@RequestMapping("/questionary")
public class QuestionaryController {

    @Autowired
    private QuestionaryService questionaryService;

    @GetMapping
    public ResponseEntity<List<QuestionaryDTO>> getQuestionaries() {
        try {
            return new ResponseEntity<List<QuestionaryDTO>>(questionaryService.getQuestionaries(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping(path = "/create", consumes = { MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<QuestionaryDTO> createQuesionary(@RequestPart QuestionaryForm data, @RequestPart(required = true) MultipartFile file) {
        try {
            return new ResponseEntity<QuestionaryDTO>(questionaryService.createQuestionary(data, file), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping(path = "/{questionaryId}/update", consumes = { MediaType.APPLICATION_OCTET_STREAM_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<QuestionaryDTO> updateQuesionary(@RequestPart QuestionaryForm data, @RequestPart(required = false) MultipartFile file, @PathVariable Long questionaryId) {
        try {
            return new ResponseEntity<QuestionaryDTO>(questionaryService.updateQuestionary(questionaryId, data, file), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{questionaryId}")
    public ResponseEntity<QuestionaryDTO> getQuestionary(@PathVariable Long questionaryId) {
        try {
            return new ResponseEntity<QuestionaryDTO>(questionaryService.getQuestionaryDTOById(questionaryId), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/{questionaryId}/check")
    public ResponseEntity<Float> checkMarks(@PathVariable Long questionaryId, @RequestBody Map<String, List<Integer>> data) {
        try {
            List<Integer> answers = data.get("answers");
            return new ResponseEntity<Float>(questionaryService.submitAnswers(questionaryId, answers), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{questionaryId}/remaining-attempts")
    public ResponseEntity<Integer> isEligible(@PathVariable Long questionaryId) {
        try {
            return new ResponseEntity<Integer>(questionaryService.getRemainingAttempts(questionaryId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{questionaryId}")
    public ResponseEntity<?> deleteQuestionary(@PathVariable Long questionaryId) {
        try {
            questionaryService.deleteQuestionary(questionaryId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/attempts")
    public ResponseEntity<List<QuestionaryAttemptDTO>>  getPublicQuestionaryAttempts(@RequestParam Long jobSeekerId) {
        try {
            return new ResponseEntity<List<QuestionaryAttemptDTO>>(questionaryService.getPublicAttemptsByJobSeekerId(jobSeekerId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('JOB_SEEKER')")
    @PutMapping("/attempts/{questionaryId}/set-privacy")
    public ResponseEntity<Boolean> setPrivacy(@PathVariable Long questionaryId, @RequestBody Map<String, Boolean> reqMap) {
        try {
            Boolean value = reqMap.get("privacy");
            return new ResponseEntity<Boolean>(questionaryService.setResultsPublic(questionaryId, value), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
}
