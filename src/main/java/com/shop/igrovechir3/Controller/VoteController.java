package com.shop.igrovechir3.Controller;

import com.shop.igrovechir3.Repository.VoteRepository;
import com.shop.igrovechir3.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class VoteController {

  @Autowired
  private VoteRepository voteRepository;

  // 1. Показує сторінку з формою для голосування
  @GetMapping("/")
  public String showVoteForm() {
    return "vote"; // Назва HTML файлу (vote.html)
  }

  // 2. Приймає дані з форми після голосування
  @PostMapping("/vote")
  public String submitVote(@RequestParam String q1, @RequestParam String q2) {
    // Зберігаємо відповідь на перше питання
    Vote vote1 = new Vote();
    vote1.setQuestionId("q1");
    vote1.setSelectedOption(q1);
    voteRepository.save(vote1);

    // Зберігаємо відповідь на друге питання
    Vote vote2 = new Vote();
    vote2.setQuestionId("q2");
    vote2.setSelectedOption(q2);
    voteRepository.save(vote2);

    return "redirect:/results"; // Перенаправляємо на сторінку результатів
  }

  // 3. Показує сторінку зі статистикою
  @GetMapping("/results")
  public String showResults(Model model) {
    // Статистика для питання 1
    Map<String, Long> q1Stats = new HashMap<>();
    long totalQ1 = voteRepository.findByQuestionId("q1").size();
    q1Stats.put("Так", voteRepository.countByQuestionIdAndSelectedOption("q1", "yes"));
    q1Stats.put("Ні", voteRepository.countByQuestionIdAndSelectedOption("q1", "no"));

    // Статистика для питання 2
    Map<String, Long> q2Stats = new HashMap<>();
    long totalQ2 = voteRepository.findByQuestionId("q2").size();
    q2Stats.put("Java", voteRepository.countByQuestionIdAndSelectedOption("q2", "java"));
    q2Stats.put("Python", voteRepository.countByQuestionIdAndSelectedOption("q2", "python"));
    q2Stats.put("JavaScript", voteRepository.countByQuestionIdAndSelectedOption("q2", "js"));

    model.addAttribute("q1Stats", q1Stats);
    model.addAttribute("totalQ1", totalQ1);
    model.addAttribute("q2Stats", q2Stats);
    model.addAttribute("totalQ2", totalQ2);

    return "results"; // Назва HTML файлу (results.html)
  }
}