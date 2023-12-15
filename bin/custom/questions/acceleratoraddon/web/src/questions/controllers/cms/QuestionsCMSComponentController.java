package questions.controllers.cms;

import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;
import de.hybris.platform.core.model.product.ProductModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import questions.controllers.QuestionsControllerConstants;
import questions.model.QuestionModel;
import questions.model.QuestionsCMSComponentModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller("QuestionsCMSComponentController")
@RequestMapping(value = QuestionsControllerConstants.Actions.Cms.QuestionsCMSComponent)
public class QuestionsCMSComponentController extends AbstractCMSAddOnComponentController<QuestionsCMSComponentModel> {
    @Override
    protected void fillModel(HttpServletRequest request, Model model, QuestionsCMSComponentModel component) {
        ProductModel currentProduct = getRequestContextData(request).getProduct();
        List<QuestionModel> questions = currentProduct.getQuestions()
                .stream()
                .limit(component.getNumberOfQuestionsToShow())
                .toList();

        model.addAttribute("questions", questions);
        model.addAttribute("size", component.getFontSize());
    }
}
