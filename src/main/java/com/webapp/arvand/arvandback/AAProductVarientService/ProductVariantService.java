package com.webapp.arvand.arvandback.AAProductVarientService;

import com.webapp.arvand.arvandback.Utills.ApiErrorType;
import com.webapp.arvand.arvandback.Utills.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductVariantService implements ProductVariantInterface {

    @Autowired
    private EntityManager entityManager;

    @Override
    public ProductVariantsResponseDto getVariantsById(String id) throws ApiException {
        List<Object[]> rows = new ArrayList<>();
        String sql = """
                SELECT
                    e.FLD_ID AS varId,
                    e.FLD_SKU,
                    e.FLD_STOCK_QUANTITY,
                    e.FLD_PRICE,
                    e.FLD_DISCOUNT,
                    tct.FLD_GROUP,
                    tct.FLD_VALUE
                FROM TBL_PRODUCT_VARIANT e 
                JOIN TBL_PRODUCT_VARIANT_VALUE t 
                    ON t.FLD_VARIANT_ID = e.FLD_ID 
                JOIN TBL_CORE_THING tct 
                    ON t.FLD_CORE_THING_ID = tct.FLD_ID 
                WHERE e.FLD_PRODUCT_ID = ? 
                ORDER BY e.FLD_ID, tct.FLD_GROUP
                """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter(1, id);
        try {
            rows = query.getResultList();
            System.out.println(rows.size());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ApiErrorType.SERVER_ERROR, "Server Error");
        }
        Map<String, VariantDto> variantMap = new LinkedHashMap<>();
        for (Object[] row : rows) {
            String varId = (String) row[0];
            String sku = row[1].toString();
            String value = row[6].toString();
            String group = row[5].toString();
            int discount = Integer.parseInt(row[4].toString());
            int stockQuantity = Integer.parseInt(row[2].toString());
            Long price = Long.parseLong(row[3].toString());
            VariantDto variantDto = variantMap.computeIfAbsent(varId, idd -> {
                VariantDto dto = new VariantDto();
                dto.setVarId(idd);
                dto.setSku(sku);
                dto.setStockQuantity(stockQuantity);
                dto.setPrice(price);
                dto.setDiscount(discount);
                return dto;
            });
            variantDto.getValues().put(group,value);
        }
        ProductVariantsResponseDto response = new ProductVariantsResponseDto();
        response.setProductId(id);
        response.setVariants(new ArrayList<>(variantMap.values()));
        return response;
    }
}
