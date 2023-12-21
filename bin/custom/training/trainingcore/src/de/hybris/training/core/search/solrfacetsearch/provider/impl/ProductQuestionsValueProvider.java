package de.hybris.training.core.search.solrfacetsearch.provider.impl;

import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.solrfacetsearch.config.IndexConfig;
import de.hybris.platform.solrfacetsearch.config.IndexedProperty;
import de.hybris.platform.solrfacetsearch.config.exceptions.FieldValueProviderException;
import de.hybris.platform.solrfacetsearch.provider.FieldNameProvider;
import de.hybris.platform.solrfacetsearch.provider.FieldValue;
import de.hybris.platform.solrfacetsearch.provider.FieldValueProvider;
import org.springframework.beans.factory.annotation.Required;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductQuestionsValueProvider implements FieldValueProvider, Serializable {
    private FieldNameProvider fieldNameProvider;

    @Override
    public Collection<FieldValue> getFieldValues(final IndexConfig indexConfig, final IndexedProperty indexedProperty, final Object model) throws FieldValueProviderException {
        if (model instanceof ProductModel) {
            final ProductModel product = (ProductModel) model;
            final Collection<FieldValue> fieldValues = new ArrayList<>();

            fieldValues.addAll(createFieldValue(product, indexedProperty));
            return fieldValues;
        }

        throw new FieldValueProviderException("Error: item is not a Product type !");
    }

    protected List<FieldValue> createFieldValue(final ProductModel product, final IndexedProperty indexedProperty) {
        final List<FieldValue> fieldValues = new ArrayList<>();

        int questionsCount = product.getQuestions().size();
        addFieldValues(fieldValues, indexedProperty, questionsCount);

        return fieldValues;
    }

    protected void addFieldValues(final List<FieldValue> fieldValues, final IndexedProperty indexedProperty, final Object value) {
        final Collection<String> fieldNames = fieldNameProvider.getFieldNames(indexedProperty, null);
        for (final String fieldName : fieldNames) {
            fieldValues.add(new FieldValue(fieldName, value));
        }
    }

    @Required
    public void setFieldNameProvider(final FieldNameProvider fieldNameProvider) {
        this.fieldNameProvider = fieldNameProvider;
    }
}
