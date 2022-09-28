package com.exam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.exam.Category;
import com.exam.entity.exam.Quiz;

public interface QuizRepo extends JpaRepository<Quiz,Long> {
    
    public List<Quiz> findByCategory(Category category);
    public List<Quiz> findByActive(boolean b);
    public List<Quiz> findByCategoryAndActive(Category c, Boolean b);
}
