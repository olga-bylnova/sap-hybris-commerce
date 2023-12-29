package de.hybris.training.core.daos;

import questions.model.QuestionModel;

import java.util.List;

public interface QuestionDao {
    List<QuestionModel> getCreatedQuestions();
}
