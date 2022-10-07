package com.example.quiz.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/* Form */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizForm {
    /* 식별 ID */
    private Integer id;

    /* 퀴즈 내용 */
    @NotBlank
    private String question;

    /* 퀴즈 해답 */
    private Boolean answer;

    /* 작성자 */
    @NotBlank
    private String author;

    /* 등록 또는 변경 판단용 */
    private Boolean newQuiz;
}
