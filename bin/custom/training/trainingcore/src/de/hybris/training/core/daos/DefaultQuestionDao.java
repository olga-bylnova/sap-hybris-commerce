package de.hybris.training.core.daos;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import questions.model.QuestionModel;
import org.springframework.beans.factory.annotation.Required;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DefaultQuestionDao implements QuestionDao {
    private static final String SQL_DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
    private FlexibleSearchService flexibleSearchService;
    public List<QuestionModel> getCreatedQuestions(Date date) {
        final String dateFormatted = new SimpleDateFormat(SQL_DATE_FORMAT).format(date);

        final String queryString =
                "SELECT {p:" + QuestionModel.PK + "} "
                        + "FROM {" + QuestionModel._TYPECODE + " AS p} "
                        + "WHERE {creationtime} >= \'" + dateFormatted + "\' ";
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        return flexibleSearchService.<QuestionModel> search(query).getResult();
    }

    @Required
    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}
