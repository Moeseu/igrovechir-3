package com.shop.igrovechir3.Repository;

import com.shop.igrovechir3.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
  // Метод для підрахунку голосів за конкретне питання та варіант
  long countByQuestionIdAndSelectedOption(String questionId, String selectedOption);

  // Метод для пошуку всіх голосів за певне питання
  List<Vote> findByQuestionId(String questionId);
}