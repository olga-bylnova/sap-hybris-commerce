package de.hybris.training.core.daos;

import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import questions.model.QuestionModel;
import org.springframework.beans.factory.annotation.Required;

import java.util.List;

public class DefaultQuestionDao implements QuestionDao {
    private FlexibleSearchService flexibleSearchService;
    public List<QuestionModel> getCreatedQuestions() {
        final String queryString =
                "SELECT {p:" + QuestionModel.PK + "} "
                        + "FROM {" + QuestionModel._TYPECODE + " AS p} "
                        + "ORDER BY {creationtime}";
        final FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
        return flexibleSearchService.<QuestionModel> search(query).getResult();
    }

    @Required
    public void setFlexibleSearchService(FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }
}
