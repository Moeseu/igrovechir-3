package com.shop.igrovechir3.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // Вказує, що цей клас є сутністю для бази даних
public class Vote {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String questionId; // Ідентифікатор питання (напр., "q1", "q2")
  private String selectedOption; // Обраний варіант відповіді

  // Getters and Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getQuestionId() {
    return questionId;
  }

  public void setQuestionId(String questionId) {
    this.questionId = questionId;
  }

  public String getSelectedOption() {
    return selectedOption;
  }

  public void setSelectedOption(String selectedOption) {
    this.selectedOption = selectedOption;
  }
}