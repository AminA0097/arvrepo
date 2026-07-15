package com.webapp.arvand.arvandback.Service;

import com.webapp.arvand.arvandback.Dto.BannerDto;
import com.webapp.arvand.arvandback.CategorySerivce.CategoryRequest;
import com.webapp.arvand.arvandback.Dto.TheBestDto;
import com.webapp.arvand.arvandback.Entity.BannerEntity;
import com.webapp.arvand.arvandback.Repo.BannerRepo;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Utills.ApiResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LayoutService implements LayoutInterface{
    private static final Set<String> CATEGORY_ALLOWED_FILTERS = Set.of(
            "id",
            "faName",
            "enName",
            "slug"
    );
    @Autowired
    private BannerRepo bannerRepo;
    @Autowired
    private EntityManager entityManager;
    @Override
    public ResponseEntity<ApiResponse<List<BannerDto>>> getBanner() throws Exception {
        try {
            List<BannerEntity> bannerEntities =  bannerRepo.findByActiveTrue();
            if(!bannerEntities.isEmpty()){
                List<BannerDto> bannerDtos = bannerEntities.stream()
                        .map(BannerDto::new)
                        .toList();
                return ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(ApiResponse.success(bannerDtos));
            }
        }
        catch (Exception e) {
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ApiResponse.success(getFallbackBanners()));
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiResponse.success(getFallbackBanners()));
    }
    private List<BannerDto> getFallbackBanners() {
        BannerDto fallbackBanner = new BannerDto();
        fallbackBanner.setId("fallback-001");
        fallbackBanner.setTitle("به فروشگاه خوش آمدید");
        fallbackBanner.setBadge("پیشنهاد ویژه");
        fallbackBanner.setDescription("تخفیف‌های هیجان‌انگیز چرم آروند");
        fallbackBanner.setBackgroundImage("/images/default-banner.jpg"); // مسیر عکس پیش‌فرض
        fallbackBanner.setHref("/products");
        fallbackBanner.setBtnText("مشاهده محصولات");

        return Collections.singletonList(fallbackBanner);
    }

    @Override
    public ResponseEntity<ApiResponse> getCategories() throws ApiException {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse<List<TheBestDto>>> getTheBest(String type) throws ApiException {
        return null;
    }


//    public ResponseEntity<ApiResponse> getCategoriesWithItsCount(int page, int size, String sortBy, String direction) throws ApiException {
//        String q = "SELECT " +
//                "    e.fld_id AS id, " +
//                "    e.fld_fa_name AS faName, " +
//                "    e.fld_en_name AS eName, " +
//                "    e.fld_slug AS slug, " +
//                "    e.fld_pic_url AS inGrid, " +
//                "    e.fld_in_grid AS imageUrl, " +
//                "    ( " +
//                "        SELECT COUNT(*) " +
//                "        FROM tbl_product tp " +
//                "        WHERE tp.fld_category_id = e.fld_id " +
//                "    ) AS count " +
//                "FROM tbl_category e " +
//                "WHERE e.fld_deleted = false;";
//        List<Object[]> rows = entityManager.createNativeQuery(q).getResultList();
//        List<CategoryDto> result = new ArrayList<>();
//
//        for (Object[] r : rows) {
//            CategoryDto dto = new CategoryDto();
//            dto.setId(r[0].toString());
//            dto.setFaName(r[1].toString());
//            dto.setEnName(r[2].toString());
//            dto.setSlug(r[3].toString());
//            dto.setImageUrl(r[4].toString());
//            dto.setInGrid(Boolean.valueOf(r[5].toString()));
//            dto.setCount(r[6].toString());
//
//            result.add(dto);
//        }
//        return ResponseEntity.ok(ApiResponse.success(Map.of(
//                "categories", result,
//                "totalItems", result.size()
//        )));
//    }

    @Override
    public <T> List<T> searchInCategory(String nativeQuery, Class<T> outPut, Map<String, Object> params) throws ApiException {
        Query query = entityManager.createNativeQuery(nativeQuery, outPut);
        if(params != null && !params.isEmpty()) {
            params.forEach((k, v) -> query.setParameter(k, v.toString()));
        }
        return query.getResultList();
    }

    @Override
    public ResponseEntity<ApiResponse> getCategoryById(CategoryRequest categoryRequest) throws ApiException {
        String q = "select " +
                "    e.fld_id as id, " +
                "    e.fld_fa_name as faName, " +
                "    e.fld_en_name as eName, " +
                "    e.fld_slug as slug, " +
                "    e.fld_pic_url as inGrid, " +
                "    e.fld_in_grid as imageUrl, " +
                "    ( " +
                "        SELECT COUNT(*) " +
                "        FROM tbl_product tp " +
                "        WHERE tp.fld_category_id = e.fld_id " +
                "    ) AS count " +
                "from tbl_category e " +
                "where e.fld_id = :id";
        List res = entityManager.createNativeQuery(q)
                .setParameter("id",categoryRequest.getSearchId()).getResultList();

        return ResponseEntity.ok(ApiResponse.success(Map.of(
                "categories", res,
                "totalItems", res.size()
        )));
    }
}
