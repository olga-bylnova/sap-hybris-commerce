package de.hybris.training.core.job;

import de.hybris.platform.basecommerce.model.site.BaseSiteModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobHistoryModel;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.processengine.BusinessProcessService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.CronJobHistoryService;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.site.BaseSiteService;
import de.hybris.training.core.model.process.QuestionsUpdateEmailProcessModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class QuestionsCreatedJobPerformable extends AbstractJobPerformable<CronJobModel> {
    private static final Logger LOG = Logger.getLogger(QuestionsCreatedJobPerformable.class);
    public static final String SITE = "electronics";
    public static final String EMAIL = "olga.bylnova@yandex.by";
    public static final String DISPLAY_NAME = "name";
    private BusinessProcessService businessProcessService;
    private ModelService modelService;
    private BaseSiteService baseSiteService;
    private CronJobHistoryService cronJobHistoryService;

    @Override
    public PerformResult perform(final CronJobModel cronJob) {
        final QuestionsUpdateEmailProcessModel questionsUpdateEmailProcess = getBusinessProcessService().createProcess("QuestionsUpdateEmailProcess-" + System.currentTimeMillis(), "questionsUpdateEmailProcess");
        setSiteInfo(questionsUpdateEmailProcess);

        setLastExecutionTime(cronJob, questionsUpdateEmailProcess);

        getModelService().save(questionsUpdateEmailProcess);
        getBusinessProcessService().startProcess(questionsUpdateEmailProcess);

        return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
    }

    private void setLastExecutionTime(CronJobModel cronJob, QuestionsUpdateEmailProcessModel questionsUpdateEmailProcess) {
        List<CronJobHistoryModel> cronJobHistory = cronJobHistoryService.getCronJobHistoryBy(cronJob.getCode());
        CronJobHistoryModel lastExecutionTime = cronJobHistory.stream().filter(cronJobHistoryModel -> cronJobHistoryModel.getStatus() == CronJobStatus.FINISHED)
                .max(Comparator.comparing(CronJobHistoryModel::getStartTime))
                .orElse(null);
        questionsUpdateEmailProcess.setDate(lastExecutionTime != null ? lastExecutionTime.getEndTime() : new Date());
    }

    private void setSiteInfo(QuestionsUpdateEmailProcessModel questionsUpdateEmailProcess) {
        BaseSiteModel site = baseSiteService.getBaseSiteForUID(SITE);
        questionsUpdateEmailProcess.setLanguage(site.getDefaultLanguage());
        questionsUpdateEmailProcess.setStore(site.getStores().get(0));
        questionsUpdateEmailProcess.setSite(site);
        questionsUpdateEmailProcess.setEmail(EMAIL);
        questionsUpdateEmailProcess.setDisplayName(DISPLAY_NAME);
    }

    public BusinessProcessService getBusinessProcessService() {
        return businessProcessService;
    }

    public void setBusinessProcessService(BusinessProcessService businessProcessService) {
        this.businessProcessService = businessProcessService;
    }

    public ModelService getModelService() {
        return modelService;
    }

    @Override
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    public BaseSiteService getBaseSiteService() {
        return baseSiteService;
    }

    public void setBaseSiteService(BaseSiteService baseSiteService) {
        this.baseSiteService = baseSiteService;
    }

    public CronJobHistoryService getCronJobHistoryService() {
        return cronJobHistoryService;
    }

    @Required
    public void setCronJobHistoryService(CronJobHistoryService cronJobHistoryService) {
        this.cronJobHistoryService = cronJobHistoryService;
    }
}
