package com.webapp.arvand.arvandback.AAmazingService;

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
        List<Object[]> row = new ArrayList<>();
        try {
            String getDuration = "select e.duration,e.id from AmazingCenterEntity e" +
                    " where e.isActive = true";
            List<Object[]> resDuration = entityManager.createQuery(getDuration).getResultList();
            if (resDuration.size() > 0) {
                amazingDate = (Date) resDuration.get(0)[0];
                id = (String) resDuration.get(0)[1];
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ApiErrorType.SERVER_ERROR,"Error getting Amazing Centers");
        }
        String getAmazing = "SELECT " +
                "                    p.FLD_ID, " +
                "                    p.FLD_NAME, " +
                "                    p.FLD_TITLE, " +
                "                    p.FLD_PRICE, " +
                "                    p.FLD_DISCOUNT, " +
                "                    p.FLD_STOCK, " +
                "                    p.FLD_IMG_URL, " +
                "                    p.FLD_VIEWS, " +
                "                    p.FLD_RANK " +
                "                FROM TBL_AMAZING a " +
                "                     JOIN TBL_AMAZING_CENTER c " +
                "                        ON c.FLD_ID = a.FLD_AMAZING_CNETER " +
                "                     JOIN TBL_AMAZING_PRO ap " +
                "                        ON ap.AMAZING_ID = a.FLD_ID " +
                "                     JOIN TBL_PRODUCT p " +
                "                        ON p.FLD_ID = ap.PRO_ID " +
                "                WHERE " +
                "                    a.FLD_IS_ACTIVE = true " +
                "                    AND c.FLD_IS_ACTIVE = true " +
                "                ORDER BY p.FLD_RANK DESC";
        try {
            Query query = entityManager.createNativeQuery(getAmazing)
                    .setFirstResult(pageNo)
                    .setMaxResults(pageSize);
            row = query.getResultList();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ApiErrorType.SERVER_ERROR,"Error getting Amazing Centers");
        }
        AmazingSimple amazingSimple = new AmazingSimple();
        amazingSimple.setDuration(amazingDate);
        List<ProductSimple> productSimpleList = new ArrayList<>();
        for (Object[] r : row) {

            ProductSimple product = new ProductSimple(
                    String.valueOf(r[0]),
                    String.valueOf(r[1]),
                    String.valueOf(r[2]),
                    r[3] != null ? Long.parseLong(r[3].toString()) : 0L,
                    r[4] != null ? Integer.parseInt(r[4].toString()) : 0,
                    r[5] != null ? Integer.parseInt(r[5].toString()) : 0,
                    r[6] != null ? String.valueOf(r[6]) : null,
                    r[7] != null ? Long.parseLong(r[7].toString()) : 0L,
                    r[8] != null ? Long.parseLong(r[8].toString()) : 0L
            );

            productSimpleList.add(product);
        }
        amazingSimple.setProductList(productSimpleList);
        amazingSimple.setId(id);
        return amazingSimple;
    }
}
