package com.webapp.arvand.arvandback.AAProductService;


import com.webapp.arvand.arvandback.Utills.ApiErrorType;
import com.webapp.arvand.arvandback.Utills.ApiException;
import org.springframework.data.domain.Sort;

import static com.webapp.arvand.arvandback.AAProductService.ProductSortField.getProductSortField;

public enum Direction {
    ASC("asc"),
    DESC("desc");

    private final String value;

    Direction(String value) {
        this.value = value;
    }

    public static Direction getDirection(String directionValue) throws ApiException {
        if (directionValue == null) {
            return DESC; // پیش‌فرض
        }
        for (Direction dir : Direction.values()) {
            if (dir.value.equalsIgnoreCase(directionValue)) {
                return dir;
            }
        }
        throw new ApiException(ApiErrorType.SERVER_ERROR, "Unknown sort direction: " + directionValue);
    }
    public static Sort toSort(String sortBy, String direction) throws ApiException {
        ProductSortField field = getProductSortField(sortBy);
        Direction dir = Direction.getDirection(direction);

        return dir == Direction.ASC
                ? Sort.by(field.getDatabaseFieldName()).ascending()
                : Sort.by(field.getDatabaseFieldName()).descending();
    }

    public String getValue() {
        return value;
    }
}
