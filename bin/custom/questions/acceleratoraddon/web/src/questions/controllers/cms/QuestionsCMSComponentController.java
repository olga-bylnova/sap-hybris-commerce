package questions.controllers.cms;

import de.hybris.platform.addonsupport.controllers.cms.AbstractCMSAddOnComponentController;
import de.hybris.platform.commercefacades.product.ProductFacade;
import de.hybris.platform.commercefacades.product.data.ProductData;
import de.hybris.platform.core.model.product.ProductModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import questions.controllers.QuestionsControllerConstants;
import questions.data.QuestionData;
import questions.model.QuestionModel;
import questions.model.QuestionsCMSComponentModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller("QuestionsCMSComponentController")
@RequestMapping(value = QuestionsControllerConstants.Actions.Cms.QuestionsCMSComponent)
public class QuestionsCMSComponentController extends AbstractCMSAddOnComponentController<QuestionsCMSComponentModel> {
    @Qualifier("productVariantFacade")
    @Autowired
    private ProductFacade productFacade;

    @Override
    protected void fillModel(HttpServletRequest request, Model model, QuestionsCMSComponentModel component) {
        ProductModel currentProduct = getRequestContextData(request).getProduct();
        if (currentProduct != null) {
            ProductData productData = productFacade.getProductForCodeAndOptions(currentProduct.getCode(), null);
            List<QuestionData> questions = productData.getQuestions()
                    .stream()
                    .limit(component.getNumberOfQuestionsToShow())
                    .toList();

            model.addAttribute("questions", questions);
            model.addAttribute("size", component.getFontSize());
        }
    }
}
