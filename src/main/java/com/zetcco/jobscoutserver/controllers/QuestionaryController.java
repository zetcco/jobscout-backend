package com.zetcco.jobscoutserver.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.controllers.support.QuestionaryForm;
import com.zetcco.jobscoutserver.domain.questionary.Questionary;
import com.zetcco.jobscoutserver.services.questionary.QuestionaryService;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

@Controller
@RequestMapping("/questionary")
public class QuestionaryController {

    @Autowired
    private QuestionaryService questionaryService;

    @GetMapping
    public ResponseEntity<List<Questionary>> getQuestionaries() {
        try {
            return new ResponseEntity<List<Questionary>>(questionaryService.getQuestionaries(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Questionary> createQuesionary(@RequestBody QuestionaryForm data) {
        try {
            return new ResponseEntity<Questionary>(questionaryService.createQuestionary(data), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{questionaryId}")
    public ResponseEntity<Questionary> getQuestionary(@PathVariable Long questionaryId) {
        try {
            return new ResponseEntity<Questionary>(questionaryService.getQuestionaryById(questionaryId), HttpStatus.OK);
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
            return new ResponseEntity<Float>(questionaryService.getMarks(questionaryId, answers), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
}
