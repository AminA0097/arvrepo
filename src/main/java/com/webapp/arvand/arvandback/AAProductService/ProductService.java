package com.webapp.arvand.arvandback.AAProductService;

import com.webapp.arvand.arvandback.Dto.HotsSimple;
import com.webapp.arvand.arvandback.Utills.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class ProductService implements ProductInterface {
    private static final Set<String> WHITE_SEARCH = Set.of(
            "e.title", "e.category.id","e.views"
    );
    private static final Set<String> WHITE_SORT = Set.of(
            "e.id",
            "e.views",
            "e.rank"
    );
    private static final Map<String, String> FIELD_MAP = Map.of(
            "e.id", "e.fld_id",
            "e.name", "e.fld_name",
            "e.title", "e.fld_title",
            "e.views", "e.fld_views",
            "e.rank", "e.fld_rank",
            "v.price", "v.fld_price",
            "v.discount", "v.fld_discount",
            "v.stockQuantity", "v.fld_stock_quantity"
    );

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private EntityManager entityManager;

    @Override
    public PageResponse<ProductSimple> search(ProductSearchReq req) throws ApiException {
        try {
            String getProductsByViews = "select e.fld_id," +
                    "       e.fld_name," +
                    "       e.fld_title," +
                    "       e.fld_img_url," +
                    "       e.fld_views," +
                    "       e.fld_rank," +
                    "       v.fld_discount                         as discount," +
                    "       COALESCE(" +
                    "               MIN(CASE WHEN v.FLD_STOCK_QUANTITY > 0 THEN v.FLD_PRICE END)," +
                    "               MIN(v.FLD_PRICE)" +
                    "       )                                      AS base_price," +
                    "       SUM(COALESCE(v.FLD_STOCK_QUANTITY, 0)) AS total_stock," +
                    "       CASE" +
                    "           WHEN SUM(COALESCE(v.FLD_STOCK_QUANTITY, 0)) > 0 THEN 1" +
                    "           ELSE 0" +
                    "           END                                AS is_available" +
                    "from tbl_product e" +
                    "         LEFT JOIN TBL_PRODUCT_VARIANT v" +
                    "                   ON v.FLD_PRODUCT_ID = e.fld_id" +
                    "where e.fld_deleted = false and e.fld_views > 30" +
                    "group by e.fld_id, e.fld_name, e.fld_title, e.fld_img_url, e.fld_views, e.fld_rank, v.fld_discount";

            FilterResult result = CreateQuery.createQuery(
                    new StringBuilder(getProductsByViews),
                    req.getFilter(),
                    WHITE_SEARCH,
                    req.getSortBy(),
                    req.getSortDir(),
                    WHITE_SORT
            );

            Query query = entityManager.createNativeQuery(result.getQuery());

            for (Map.Entry<String, Object> entry : result.getParameters().entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }

            query.setFirstResult(req.getPageNo() * req.getPageSize());
            query.setMaxResults(req.getPageSize());

            List<ProductSimple> products = query.getResultList();

            FilterResult countFilterResult = CreateQuery.createQuery(
                    new StringBuilder("""
                                select count(e)
                                from ProductEntity e
                                where e.deleted = false
                            """),
                    req.getFilter(),
                    WHITE_SEARCH,
                    null,
                    null,
                    null
            );

            Query countQuery = entityManager.createNativeQuery(countFilterResult.getQuery());

            for (Map.Entry<String, Object> entry : countFilterResult.getParameters().entrySet()) {
                countQuery.setParameter(entry.getKey(), entry.getValue());
            }

            int countNumber = Integer.parseInt(countQuery.getSingleResult().toString());
            int totalPages = (int) Math.ceil((double) countNumber / req.getPageSize());

            return new PageResponse<>(
                    products,
                    req.getPageNo(),
                    totalPages,
                    countNumber,
                    req.getPageSize()
            );

        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ApiErrorType.SERVER_ERROR, "خطا در دریافت نتیجه");
        }
    }


    @Override
    public String getProductsCount() throws ApiException {
        try {
            String query = "select count(*) from ProductEntity e";
            List res = entityManager.createQuery(query).getResultList();
            if (res.size() > 0) {
                return res.get(0).toString();
            }
        } catch (Exception e) {
            return "0";
        }
        return "0";
    }

    @Override
    public List<ProductDto> getAllNew(Pageable pageable) throws Exception {
        return List.of();
    }

    @Override
    public PageResponse<HotsSimple> getProductsByType
            (int page, int size, String type) {
        Pageable pageable = PageRequest.of(page, size);
        Page<HotsSimple> entities = productRepo.findAllSimple(pageable);
        return null;
    }

    @Override
    public PageResponse<ProductSimple> getProductsByViews(int page, int size, int views) {
        List<ProductSimple> products = new ArrayList();
        int count = 0;
        String getProductsByViews = "select e.id,\n" +
                "                    e.name,\n" +
                "                    e.title,\n" +
                "                    e.imgUrl.id,\n" +
                "                    e.views,\n" +
                "                    e.rank,\n" +
                "                    v.fld_discount                         as discont,\n" +
                "                           COALESCE(\n" +
                "                                   MIN(CASE WHEN v.FLD_STOCK_QUANTITY > 0 THEN v.FLD_PRICE END),\n" +
                "                                   MIN(v.FLD_PRICE)\n" +
                "                           )                                      AS base_price,\n" +
                "                           SUM(COALESCE(v.FLD_STOCK_QUANTITY, 0)) AS total_stock,\n" +
                "                           CASE\n" +
                "                               WHEN SUM(COALESCE(v.FLD_STOCK_QUANTITY, 0)) > 0 THEN 1\n" +
                "                               ELSE 0\n" +
                "                               END                                AS is_available\n" +
                "                from ProductEntity e\n" +
                "                LEFT JOIN TBL_PRODUCT_VARIANT v\n" +
                "                                   ON v.FLD_PRODUCT_ID = p.FLD_ID\n" +
                "                where  e.deleted = false and e.views > :views";
        String getProductsByViewsCount = "select count(*) " +
                "                from ProductEntity e\n" +
                "                LEFT JOIN TBL_PRODUCT_VARIANT v\n" +
                "                                   ON v.FLD_PRODUCT_ID = p.FLD_ID\n" +
                "                where  e.deleted = false and e.views > :views";
        try {
            Query query = entityManager.createNativeQuery(getProductsByViews)
                    .setFirstResult(page)
                    .setMaxResults(size);
            List<Object[]> row = query.getResultList();
            for (Object[] r : row) {
                String id = r[0].toString();
                String name = r[0].toString();
                String title = r[0].toString();
                String imgUrl = r[0].toString();
                Long view = Long.parseLong(r[0].toString());
                Long rank = Long.parseLong(r[0].toString());
                Long price = Long.parseLong(r[0].toString());
                Long stock = Long.parseLong(r[0].toString());
                Long discount = Long.parseLong(r[0].toString());
                boolean available = r[0].toString().equals("1") ? true : false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ApiErrorType.SERVER_ERROR, "Server Not Available");
        }
        try {
            Query query = entityManager.createNativeQuery(getProductsByViewsCount);
            count = Integer.parseInt(query.getResultList().get(0).toString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ApiErrorType.SERVER_ERROR, "Server Not Available");
        }

        int totalPages = (int) Math.ceil((double) count / size);

        return new PageResponse<>(
                products,
                page,
                totalPages,
                count,
                size
        );
    }

    @Override
    public PageResponse<ProductSimple> getProductsByDis(int page, int size, int dis) {
//        try {
//            Pageable pageable = PageRequest.of(page, size);
//            Page<ProductSimple> entities = productRepo.findByDis(dis, pageable);
//            PageResponse<ProductSimple> response = new PageResponse<>(
//                    entities.getContent(),
//                    entities.getNumber(),
//                    entities.getTotalPages(),
//                    entities.getTotalElements(),
//                    entities.getSize()
//            );
//
//            return response;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }

    @Override
    public ProductDetailSimple getById(String id) throws ApiException {
//        String getProd = "" +
//                "select new com.webapp.arvand.arvandback.AAProductService.ProductDetailSimple(" +
//                "e.id,e.title,c.id,c.faName,e.views,e.rank,e.imgUrl.id,e.desc) " +
//                "from ProductEntity e " +
//                " join CategoryEntity c on c.id = e.category.id " +
//                " where e.deleted = false and e.id = :id";
//        try {
//            List<ProductDetailSimple> res = entityManager.createQuery(getProd).setParameter("id", id).getResultList();
//            return res.get(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new ApiException(ApiErrorType.SERVER_ERROR, "Product Not Found!");
//        }
        return null;
    }

    @Override
    public List<String> getProductGallery(String id) throws ApiException {
        List<String> res = new ArrayList<>();
        String query = "select e.FLD_DOC_ID from tbl_product_gallery e " +
                "where e.FLD_PRODUCT_ID = ? ";
        try {
            res = entityManager.createNativeQuery(query).setParameter(1, id).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ApiErrorType.SERVER_ERROR, "Gallery Not Found!");
        }
        return res;
    }

    @Override
    public List<Map<String, String>> getFutures(String id) throws ApiException {
        List res = new ArrayList<>();
        String getSkin = "select t.fld_id,t.fld_fa_name,t.fld_value from tbl_product e\n" +
                "join tbl_core_thing t on t.fld_id = e.fld_skin_id where e.fld_id = ?";
        try {
            List<Object[]> skinRes = entityManager.createNativeQuery(getSkin).setParameter(1, id).getResultList();
            Map skinMap = new HashMap<>();
            if (skinRes.size() > 0) {
                for (Object[] row : skinRes) {
                    skinMap.put("id", row[0]);
                    skinMap.put("type", row[1]);
                    skinMap.put("value", row[2]);
                    res.add(skinMap);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ApiErrorType.SERVER_ERROR, "Skin Not Found!");
        }
        return res;
    }
}
