package com.webapp.arvand.arvandback.AmazingService;

import com.webapp.arvand.arvandback.AAProductService.ProductSimple;
import com.webapp.arvand.arvandback.Utills.ApiErrorType;
import com.webapp.arvand.arvandback.Utills.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AmazingService implements AmazingInterface{
    @Autowired
    private EntityManager entityManager;

    @Override
    public AmazingSimple getAll(int pageNo,int pageSize)throws ApiException {
        Date amazingDate = new Date();
        String id = "";
        String name = "";
        List<Object[]> row = new ArrayList<>();
        List<ProductSimple> products = new ArrayList<>();
        try {
            String getDuration = "select e.duration,e.id,e.name from AmazingCenterEntity e" +
                    " where e.isActive = true";
            List<Object[]> resDuration = entityManager.createQuery(getDuration).getResultList();
            if (resDuration.size() > 0) {
                amazingDate = (Date) resDuration.get(0)[0];
                id = (String) resDuration.get(0)[1];
                name = (String) resDuration.get(0)[2];
            }
            if(amazingDate.before(new Date())){
               throw new ApiException(ApiErrorType.SERVER_ERROR,"AmazingService getAll() failed");
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ApiErrorType.SERVER_ERROR,"Error getting Amazing Centers");
        }
        String getAmazing = "select "+
                "    p.FLD_ID AS id,\n" +
                "    p.FLD_NAME AS name,\n" +
                "    p.FLD_TITLE AS title,\n" +
                "    p.FLD_IMG_URL AS imgUrl,\n" +
                "    p.FLD_VIEWS AS views,\n" +
                "    p.FLD_RANK AS rank,\n" +
                "    COALESCE(MAX(v.FLD_DISCOUNT), 0) AS discount,\n" +
                "    COALESCE(\n" +
                "        MIN(CASE WHEN v.FLD_STOCK_QUANTITY > 0 THEN v.FLD_PRICE END),\n" +
                "        MIN(v.FLD_PRICE),\n" +
                "        0\n" +
                "    ) AS price,\n" +
                "    COALESCE(SUM(COALESCE(v.FLD_STOCK_QUANTITY, 0)), 0) AS stock,\n" +
                "    CASE\n" +
                "        WHEN COALESCE(SUM(COALESCE(v.FLD_STOCK_QUANTITY, 0)), 0) > 0 THEN true\n" +
                "        ELSE false\n" +
                "    END AS available\n" +
                "FROM (\n" +
                "    SELECT DISTINCT ap.PRO_ID\n" +
                "    FROM TBL_AMAZING a\n" +
                "    JOIN TBL_AMAZING_CENTER c\n" +
                "        ON c.FLD_ID = a.FLD_AMAZING_CNETER\n" +
                "    JOIN TBL_AMAZING_PRO ap\n" +
                "        ON ap.AMAZING_ID = a.FLD_ID\n" +
                "    WHERE a.FLD_IS_ACTIVE = true\n" +
                "      AND c.FLD_IS_ACTIVE = true\n" +
                ") amazing_products\n" +
                "JOIN TBL_PRODUCT p\n" +
                "    ON p.FLD_ID = amazing_products.PRO_ID\n" +
                "LEFT JOIN TBL_PRODUCT_VARIANT v\n" +
                "    ON v.FLD_PRODUCT_ID = p.FLD_ID\n" +
                "GROUP BY\n" +
                "    p.FLD_ID,\n" +
                "    p.FLD_NAME,\n" +
                "    p.FLD_TITLE,\n" +
                "    p.FLD_IMG_URL,\n" +
                "    p.FLD_VIEWS,\n" +
                "    p.FLD_RANK\n" +
                "ORDER BY p.FLD_RANK DESC";
        try {
            Query query = entityManager.createNativeQuery(getAmazing)
                    .setFirstResult(pageNo)
                    .setMaxResults(pageSize);
            row = query.getResultList();
            for (Object[] r : row) {
                ProductSimple productSimple = new ProductSimple(
                        r[0].toString(),
                        r[1].toString(),
                        r[2].toString(),
                        r[3].toString(),
                        Long.parseLong(r[4].toString()),
                        Long.parseLong(r[5].toString()),
                        Long.parseLong(r[6].toString()),
                        Long.parseLong(r[7].toString()),
                        Long.parseLong(r[8].toString()),
                        Boolean.parseBoolean(r[9].toString())
                );
                products.add(productSimple);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ApiErrorType.SERVER_ERROR,"Error getting Amazing Centers");
        }
        AmazingSimple amazingSimple = new AmazingSimple();
        amazingSimple.setDuration(amazingDate);
        amazingSimple.setName(name);
        amazingSimple.setProductList(products);
        amazingSimple.setId(id);
        return amazingSimple;
    }
}
