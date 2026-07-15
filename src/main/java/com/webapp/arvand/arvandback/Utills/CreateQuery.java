package com.webapp.arvand.arvandback.Utills;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateQuery {
    private static final Pattern FILTER_PATTERN = Pattern.compile("@@([^@]+)@@");

    public static FilterResult createQuery(StringBuilder query,
                                           String filter,
                                           Set<String> whiteList,
                                           String sortBy,
                                           String sortDir,
                                           Set<String> sortWhitelist) {

        String originalQuery = query.toString().trim();
        Map<String, Object> queryParams = new HashMap<>();

        // Validate sortBy
        if (sortBy != null && !sortBy.isBlank()) {
            if (sortWhitelist == null || !sortWhitelist.contains(sortBy)) {
                throw new ApiException(ApiErrorType.SERVER_ERROR, "مرتب‌سازی روی این فیلد مجاز نیست: " + sortBy);
            }
        }

        // Normalize sortDir
        if (sortDir == null || sortDir.isBlank()) {
            sortDir = "ASC";
        } else {
            sortDir = sortDir.trim().toUpperCase();
            if (!sortDir.equals("ASC") && !sortDir.equals("DESC")) {
                throw new ApiException(ApiErrorType.SERVER_ERROR, "sortDir باید ASC یا DESC باشد");
            }
        }

        // Remove existing ORDER BY
        String queryWithoutOrder = removeOrderBy(originalQuery);

        StringBuilder filterClause = new StringBuilder();

        if (filter != null && !filter.isBlank()) {
            Matcher matcher = FILTER_PATTERN.matcher(filter);
            int paramIndex = 0;

            while (matcher.find()) {
                String innerFilter = matcher.group(1);
                String[] parts = innerFilter.split(";");

                if (parts.length != 3) {
                    throw new ApiException(ApiErrorType.SERVER_ERROR, "Incorrect filter format");
                }

                String field = parts[0].trim();
                String operator = parts[1].trim();
                String value = parts[2].trim();

                if (!whiteList.contains(field)) {
                    throw new ApiException(ApiErrorType.SERVER_ERROR, "دسترسی به این فیلد مجاز نیست: " + field);
                }

                String paramName = "filterParam" + paramIndex++;

                if (filterClause.length() > 0) {
                    filterClause.append(" AND ");
                }

                switch (operator) {
                    case "eq":
                        filterClause.append(field).append(" = :").append(paramName);
                        queryParams.put(paramName, value);
                        break;

                    case "like":
                        filterClause.append(field).append(" LIKE :").append(paramName);
                        queryParams.put(paramName, "%" + value + "%");
                        break;

                    case "gt":
                        filterClause.append(field).append(" > :").append(paramName);
                        queryParams.put(paramName, value);
                        break;

                    case "lt":
                        filterClause.append(field).append(" < :").append(paramName);
                        queryParams.put(paramName, value);
                        break;

                    default:
                        throw new ApiException(ApiErrorType.SERVER_ERROR, "اپراتور نامعتبر: " + operator);
                }
            }
        }

        String finalQuery = appendFilterBeforeGroupOrEnd(queryWithoutOrder, filterClause.toString());

        if (sortBy != null && !sortBy.isBlank()) {
            finalQuery += " ORDER BY " + sortBy + " " + sortDir;
        }

        return new FilterResult(finalQuery, queryParams);
    }
    private static String appendFilterBeforeGroupOrEnd(String query, String filterClause) {
        if (filterClause == null || filterClause.isBlank()) {
            return query;
        }

        String lower = query.toLowerCase();

        int groupByIndex = lower.indexOf(" group by ");
        int insertPos = groupByIndex >= 0 ? groupByIndex : query.length();

        String beforeGroup = query.substring(0, insertPos).trim();
        String afterGroup = query.substring(insertPos);

        boolean hasWhere = beforeGroup.toLowerCase().contains(" where ");

        StringBuilder result = new StringBuilder();
        result.append(beforeGroup);

        if (hasWhere) {
            result.append(" AND ").append(filterClause);
        } else {
            result.append(" WHERE ").append(filterClause);
        }

        result.append(afterGroup);

        return result.toString().trim();
    }
    private static String removeOrderBy(String query) {
        String lower = query.toLowerCase();

        int orderByIndex = lower.lastIndexOf(" order by ");
        if (orderByIndex == -1) {
            return query;
        }

        return query.substring(0, orderByIndex).trim();
    }

}
