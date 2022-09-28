package com.exam.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.entity.exam.Question;
import com.exam.entity.exam.Quiz;
import com.exam.repo.QuesRepo;
import com.exam.service.QuesService;

@Service
public class QuesServiceImpl implements QuesService {
    @Autowired
    private QuesRepo questionRepository;

    @Override
    public Question addQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Set<Question> getQuestions() {
        return new HashSet<>(this.questionRepository.findAll());
    }

    @Override
    public Question getQuestion(Long questionId) {
        return this.questionRepository.findById(questionId).get();
    }

    @Override
    public Set<Question> getQuestionsOfQuiz(Quiz quiz) {
        return this.questionRepository.findByQuiz(quiz);
    }

    @Override
    public void deleteQuestion(Long questionId) {
        Question question = new Question();
        question.setQuesId(questionId);
        this.questionRepository.delete(question);
    }

    
}
