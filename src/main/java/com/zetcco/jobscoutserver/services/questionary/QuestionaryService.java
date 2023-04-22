package com.zetcco.jobscoutserver.services.questionary;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zetcco.jobscoutserver.controllers.support.QuestionaryForm;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.questionary.Question;
import com.zetcco.jobscoutserver.domain.questionary.Questionary;
import com.zetcco.jobscoutserver.domain.questionary.QuestionaryAttempt;
import com.zetcco.jobscoutserver.domain.questionary.QuestionaryAttemptDTO;
import com.zetcco.jobscoutserver.domain.questionary.QuestionaryDTO;
import com.zetcco.jobscoutserver.repositories.questionary.QuestionaryAttemptRepository;
import com.zetcco.jobscoutserver.repositories.questionary.QuestionaryRepository;
import com.zetcco.jobscoutserver.services.JobSeekerService;
import com.zetcco.jobscoutserver.services.UserService;
import com.zetcco.jobscoutserver.services.mappers.questionary.QuestionaryMapper;
import com.zetcco.jobscoutserver.services.support.NotFoundException;
import com.zetcco.jobscoutserver.services.support.StorageService;

import jakarta.transaction.Transactional;

@Service
public class QuestionaryService {
    
    @Autowired
    private QuestionaryRepository questionaryRepository;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuestionaryAttemptRepository questionaryAttemptRepository;

    @Autowired
    private JobSeekerService jobSeekerService;

    @Autowired
    private UserService userService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private QuestionaryMapper questionaryMapper;

    public QuestionaryDTO createQuestionary(QuestionaryForm data, MultipartFile file) {
        if (file != null) {
            String fileName = storageService.store(file);
            data.setBadge(fileName);
        }
        return questionaryMapper.mapQuestionaryToDto(this.createQuestionary(
            data.getName(),
            data.getBadge(),
            data.getDescription(),
            data.getTimePerQuestion(),
            data.getAttemptCount(),
            data.getQuestions()
        ));
    }

    private Questionary createQuestionary(String name, String badge, String description, Integer timePerQuestion, Integer attemptCount, List<Question> questions) {
        questions = questionService.saveAll(questions);
        Questionary questionary = new Questionary(null, name, badge, description, timePerQuestion, attemptCount, questions);
        return questionaryRepository.save(questionary);
    }

    public float submitAnswers(Long questionaryId, List<Integer> answers) throws NotFoundException, AccessDeniedException {
        Long jobSeekerId = userService.getUser().getId();
        return this.submitAnswers(jobSeekerId, questionaryId, answers);
    }

    @Transactional
    public float submitAnswers(Long jobSeekerId, Long questionaryId, List<Integer> answers) throws NotFoundException, AccessDeniedException {
        JobSeeker jobSeeker = jobSeekerService.getJobSeeker(userService.getUser().getId());
        Questionary questionary = this.getQuestionaryById(questionaryId);

        Float score = questionary.getMarks(answers);

        QuestionaryAttempt questionaryAttempt = questionaryAttemptRepository.findByJobSeekerIdAndQuestionaryId(jobSeekerId, questionaryId);
        if (questionaryAttempt == null) 
            questionaryAttempt = new QuestionaryAttempt(null, questionary, jobSeeker, 1, score, false);
        else if (questionaryAttempt.getAttempts() < questionary.getAttemptCount() ) {
            questionaryAttempt.setScore(score);
            questionaryAttempt.setAttempts( questionaryAttempt.getAttempts() + 1 );
            questionaryAttempt.setIsPublic(false);
        } else 
            throw new AccessDeniedException("You have no attempts left to take this questionary.");

        questionaryAttemptRepository.save(questionaryAttempt);

        return score;
    }

    public QuestionaryDTO getQuestionaryDTOById(Long id) throws NotFoundException {
        return questionaryMapper.mapQuestionaryToDto(this.getQuestionaryById(id));
    }

    private Questionary getQuestionaryById(Long id) throws NotFoundException {
        return questionaryRepository.findById(id).orElseThrow(() -> new NotFoundException("Questionary not found"));
    }

    public List<QuestionaryDTO> getQuestionaries() {
        return questionaryMapper.mapQuestionariesToDTOs(questionaryRepository.getAll());
    }

    public Integer getRemainingAttempts(Long questionaryId) {
        return this.getRemainingAttempts(userService.getUser().getId(), questionaryId);
    }

    public Integer getRemainingAttempts(Long jobSeekerId, Long questionaryId) {
        Questionary questionary = this.getQuestionaryById(questionaryId);
        QuestionaryAttempt attempt = questionaryAttemptRepository.findByJobSeekerIdAndQuestionaryId(jobSeekerId, questionaryId);
        return questionary.getAttemptCount() - (attempt != null ? attempt.getAttempts() : 0);
    }

    @Transactional
    public void deleteQuestionary(Long questionaryId) throws NotFoundException {
        Questionary questionary = this.getQuestionaryById(questionaryId);
        List<Long> questions = questionary.getQuestions().stream().map( question -> question.getId() ).toList();

        questionaryRepository.delete(questionary);
        questionService.deleteAllById(questions);
    }

    public Boolean setResultsPublic(Long jobSeekerId, Long questionaryId, Boolean value) {
        QuestionaryAttempt attempt = questionaryAttemptRepository.findByJobSeekerIdAndQuestionaryId(jobSeekerId, questionaryId);
        attempt.setIsPublic(value);
        attempt = questionaryAttemptRepository.save(attempt);
        return attempt.getIsPublic();
    }

    public Boolean setResultsPublic(Long questionaryId, Boolean value) {
        Long jobSeekerId = userService.getUser().getId();
        return this.setResultsPublic(jobSeekerId, questionaryId, value);
    }

    public List<QuestionaryAttemptDTO> getPublicAttemptsByJobSeekerId(Long jobSeekerId) throws AccessDeniedException {
        List<QuestionaryAttempt> questionaryAttempts;

        if (userService.getUser().getId() != jobSeekerId )
            questionaryAttempts = questionaryAttemptRepository.findByJobSeekerIdAndIsPublic(jobSeekerId, true);
        else 
            questionaryAttempts = questionaryAttemptRepository.findByJobSeekerId(jobSeekerId);

        return questionaryMapper.mapAttemptsToDTOs(questionaryAttempts);
    }

}