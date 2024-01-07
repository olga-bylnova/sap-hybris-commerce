package de.hybris.training.facades.process.email.context;

import de.hybris.platform.acceleratorservices.model.cms2.pages.EmailPageModel;
import de.hybris.platform.acceleratorservices.process.email.context.AbstractEmailContext;
import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.training.core.daos.QuestionDao;
import de.hybris.training.core.model.process.QuestionsUpdateEmailProcessModel;
import org.springframework.beans.factory.annotation.Required;
import questions.data.QuestionData;
import questions.model.QuestionModel;

import java.util.ArrayList;
import java.util.List;

public class QuestionsUpdateEmailContext extends AbstractEmailContext<QuestionsUpdateEmailProcessModel> {
    private List<QuestionData> questions;
    private QuestionDao questionDao;
    private Converter<QuestionModel, QuestionData> questionConverter;

    @Override
    public void init(QuestionsUpdateEmailProcessModel businessProcessModel, EmailPageModel emailPageModel) {
        super.init(businessProcessModel, emailPageModel);

        put(EMAIL, businessProcessModel.getEmail());
        put(DISPLAY_NAME, businessProcessModel.getDisplayName());

        List<QuestionModel> createdQuestions = questionDao.getCreatedQuestions(businessProcessModel.getDate());
        if (createdQuestions != null) {
            questions = new ArrayList<>();
            for (QuestionModel questionModel : createdQuestions) {
                questions.add(questionConverter.convert(questionModel));
            }
        }
    }

    @Override
    protected BaseSiteModel getSite(QuestionsUpdateEmailProcessModel businessProcessModel) {
        return businessProcessModel.getSite();
    }

    @Override
    protected CustomerModel getCustomer(QuestionsUpdateEmailProcessModel businessProcessModel) {
        return businessProcessModel.getCustomer();
    }

    @Override
    protected LanguageModel getEmailLanguage(QuestionsUpdateEmailProcessModel businessProcessModel) {
        return businessProcessModel.getLanguage();
    }

    public Converter<QuestionModel, QuestionData> getQuestionConverter() {
        return questionConverter;
    }

    @Required
    public void setQuestionConverter(Converter<QuestionModel, QuestionData> questionConverter) {
        this.questionConverter = questionConverter;
    }

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    @Required
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public List<QuestionData> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionData> questions) {
        this.questions = questions;
    }
}
