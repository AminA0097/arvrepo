package com.webapp.arvand.arvandback.AAProductService;

import com.webapp.arvand.arvandback.Utills.ApiErrorType;
import com.webapp.arvand.arvandback.Utills.ApiException;

public enum ProductSortField {

    CREATED_AT("createdAt"),
    PRICE("price"),
    VIEWS("views"),
    RANK("rank"),
    NAME("name"),
    CATEGORY_NAME("category.name"),
    TITLE("title");

    private final String databaseFieldName;
    private ProductSortField(String databaseFieldName) {
        this.databaseFieldName = databaseFieldName;
    }
    public static ProductSortField getProductSortField(String databaseFieldName) throws ApiException {
        for (ProductSortField productSortField : ProductSortField.values()) {
            if (productSortField.databaseFieldName.equals(databaseFieldName)) {
                return productSortField;
            }
        }
        throw new ApiException(ApiErrorType.SERVER_ERROR,"Unknown product sort field: " + databaseFieldName);
    }

    public String getDatabaseFieldName() {
        return databaseFieldName;
    }
}
