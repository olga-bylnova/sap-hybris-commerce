package de.hybris.training.core.job;

import de.hybris.platform.commerceservices.enums.QuoteNotificationType;
import de.hybris.platform.commerceservices.event.QuoteExpiredEvent;
import de.hybris.platform.commerceservices.order.dao.CommerceQuoteDao;
import de.hybris.platform.core.enums.QuoteState;
import de.hybris.platform.core.model.order.AbstractOrderModel;
import de.hybris.platform.core.model.order.QuoteModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.servicelayer.time.TimeService;
import de.hybris.training.core.daos.QuestionDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import questions.model.QuestionModel;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QuestionsCreatedJobPerformable extends AbstractJobPerformable<CronJobModel> {
    private static final Logger LOG = Logger.getLogger(QuestionsCreatedJobPerformable.class);

    private QuestionDao questionDao;

    private EventService eventService;

    @Override
    public PerformResult perform(final CronJobModel cronJob) {

        List<QuestionModel> createdQuestions = getQuestionDao().getCreatedQuestions();
        LOG.info("new questions created: " + createdQuestions.stream().map(QuestionModel::getCode).collect(Collectors.joining(", ")));

        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    protected void publishQuoteExpiredEvent(final QuoteModel quoteModel) {
        final QuoteExpiredEvent quoteExpiredEvent = new QuoteExpiredEvent(quoteModel);

        getEventService().publishEvent(quoteExpiredEvent);
    }

    protected EventService getEventService() {
        return eventService;
    }

    @Required
    public void setEventService(final EventService eventService) {
        this.eventService = eventService;
    }

    protected QuestionDao getQuestionDao() {
        return questionDao;
    }

    @Required
    public void setQuestionDao(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }
}
