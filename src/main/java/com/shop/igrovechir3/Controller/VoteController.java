// VoteController.java
package com.shop.igrovechir3.Controller;

import com.shop.igrovechir3.Repository.VoteRepository;
import com.shop.igrovechir3.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody; // Import for @ResponseBody

import java.util.HashMap;
import java.util.LinkedHashMap; // For ordered map in JSON output
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
  public String submitVote(@RequestParam String q1, @RequestParam String q2,
                           @RequestParam String q3, @RequestParam String q4) { // Додано параметри q3 та q4
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

    // Зберігаємо відповідь на третє питання
    Vote vote3 = new Vote();
    vote3.setQuestionId("q3");
    vote3.setSelectedOption(q3);
    voteRepository.save(vote3);

    // Зберігаємо відповідь на четверте питання
    Vote vote4 = new Vote();
    vote4.setQuestionId("q4");
    vote4.setSelectedOption(q4);
    voteRepository.save(vote4);

    return "redirect:/results"; // Перенаправляємо на сторінку результатів
  }

  // 3. Показує сторінку зі статистикою (тепер просто повертає HTML, дані отримуються через API)
  @GetMapping("/results")
  public String showResults() { // Видалено Model model, оскільки дані не передаються напряму
    return "results"; // Назва HTML файлу (results.html)
  }

  // Новий ендпоінт для отримання результатів голосування у форматі JSON
  @GetMapping("/api/voting-results")
  @ResponseBody // Вказує Spring, що повернений об'єкт має бути серіалізований у JSON
  public Map<String, Object> getVotingResults() {
    Map<String, Object> results = new LinkedHashMap<>(); // Використовуємо LinkedHashMap для збереження порядку в JSON

    // Загальна кількість голосів (кожне питання = 1 голос)
    long totalVotes = voteRepository.count(); // Загальна кількість записів у таблиці Vote
    // Припускаємо, що кожен учасник відповідає на всі 4 питання.
    // Тому загальна кількість учасників = загальна кількість голосів / 4
    long totalParticipants = totalVotes / 4; //

    results.put("totalVotes", totalVotes);
    results.put("totalParticipants", totalParticipants);

    Map<String, Map<String, Long>> questions = new LinkedHashMap<>(); // Мапа для зберігання статистики по кожному питанню

    // Q1: Яка настільна гра є переоціненою?
    Map<String, Long> q1Stats = new LinkedHashMap<>();
    q1Stats.put("monopoly", voteRepository.countByQuestionIdAndSelectedOption("q1", "monopoly")); //
    q1Stats.put("fool", voteRepository.countByQuestionIdAndSelectedOption("q1", "fool")); //
    q1Stats.put("js", voteRepository.countByQuestionIdAndSelectedOption("q1", "js")); //
    q1Stats.put("uno", voteRepository.countByQuestionIdAndSelectedOption("q1", "uno")); //
    questions.put("q1", q1Stats); //

    // Q2: Ваш стиль гри?
    Map<String, Long> q2Stats = new LinkedHashMap<>();
    q2Stats.put("competitive", voteRepository.countByQuestionIdAndSelectedOption("q2", "competitive")); //
    q2Stats.put("fun", voteRepository.countByQuestionIdAndSelectedOption("q2", "fun")); //
    q2Stats.put("chaos", voteRepository.countByQuestionIdAndSelectedOption("q2", "chaos")); //
    q2Stats.put("strategic", voteRepository.countByQuestionIdAndSelectedOption("q2", "strategic")); //
    questions.put("q2", q2Stats); //

    // Q3: Що робите, коли програєте?
    Map<String, Long> q3Stats = new LinkedHashMap<>();
    q3Stats.put("analyze", voteRepository.countByQuestionIdAndSelectedOption("q3", "analyze")); //
    q3Stats.put("blame", voteRepository.countByQuestionIdAndSelectedOption("q3", "blame")); //
    q3Stats.put("laugh", voteRepository.countByQuestionIdAndSelectedOption("q3", "laugh")); //
    q3Stats.put("revenge", voteRepository.countByQuestionIdAndSelectedOption("q3", "revenge")); //
    questions.put("q3", q3Stats); //

    // Q4: Найгірший тип гравця?
    Map<String, Long> q4Stats = new LinkedHashMap<>();
    q4Stats.put("cheater", voteRepository.countByQuestionIdAndSelectedOption("q4", "cheater")); //
    q4Stats.put("slow", voteRepository.countByQuestionIdAndSelectedOption("q4", "slow")); //
    q4Stats.put("rules", voteRepository.countByQuestionIdAndSelectedOption("q4", "rules")); //
    q4Stats.put("phone", voteRepository.countByQuestionIdAndSelectedOption("q4", "phone")); //
    questions.put("q4", q4Stats); //

    results.put("questions", questions); //

    return results; // Повертаємо об'єкт, який буде серіалізований у JSON
  }
}