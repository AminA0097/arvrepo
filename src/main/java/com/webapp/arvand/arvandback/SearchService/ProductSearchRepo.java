package com.webapp.arvand.arvandback.SearchService;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductSearchRepo
        extends JpaRepository<ProductSearchEntity, String> {

    @Query(value = """
            WITH q AS (
              SELECT plainto_tsquery('simple', :query) AS query
            )
            SELECT
                p.product_id as productId,
                p.fld_search_text as fldSearchText,
                p.fld_image_url as fldImageUrl,
                p.fld_rank as fldRank,
                p.fld_views as fldViews,
                '' as matchedTokens,
                (ts_rank(p.search_vector, q.query) * 0.7 +
                 similarity(p.fld_search_text, :query) * 0.3) as score,
                p.fld_detail as detail
            FROM tbl_product_search p, q
            WHERE
                  p.search_vector @@ q.query
               OR p.fld_search_text % :query
            ORDER BY score DESC
            LIMIT :limit    
            """, nativeQuery = true)
    List<ProductSearchProjection> autocompleteLong(
            @Param("query") String query,
            @Param("limit") int limit
    );
    @Query(value = "SHOW pg_trgm.similarity_threshold", nativeQuery = true)
    String threshold();
    @Query(value = """
        SELECT extname
        FROM pg_extension
        WHERE extname = 'pg_trgm'
        """, nativeQuery = true)
    List<String> checkExtension();
    @Query(value = """

        SELECT
            product_id    AS productId,
            fld_title     AS title,
            fld_image_url AS imageUrl,
            fld_rank      AS rank,
            fld_views     AS views,
            1.0           AS score

        FROM tbl_product_search

        WHERE
            fld_search_text ILIKE
            '%' || fa_normalize(:query) || '%'

        ORDER BY
            fld_rank DESC,
            fld_views DESC

        LIMIT :limit

        """, nativeQuery = true)
    List<ProductSearchProjection> autocompleteShort(
            @Param("query") String query,
            @Param("limit") int limit
    );
}