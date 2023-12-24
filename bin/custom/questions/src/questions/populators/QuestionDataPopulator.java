package questions.populators;

import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import questions.data.QuestionData;
import questions.model.QuestionModel;

public class QuestionDataPopulator implements Populator<QuestionModel, QuestionData> {
    @Override
    public void populate(QuestionModel question, QuestionData questionData) throws ConversionException {
        questionData.setQuestion(question.getQuestion());
        questionData.setQuestionCustomer(question.getQuestionCustomer().getName());
        questionData.setAnswer(question.getAnswer());
        questionData.setAnswerCustomer(question.getAnswerCustomer() == null ? "" : question.getAnswerCustomer().getName());
    }
}
