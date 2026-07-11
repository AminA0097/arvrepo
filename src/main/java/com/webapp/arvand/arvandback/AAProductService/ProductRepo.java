package com.webapp.arvand.arvandback.AAProductService;

import com.webapp.arvand.arvandback.Dto.HotsSimple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, String> {
    @Query("""
    select new com.webapp.arvand.arvandback.CategorySerivce.CategorySimple(
        e.id,
        e.faName,
        e.slug,
        e.docEntity.id,
        e.inGrid,
        count(p)
    )
    from CategoryEntity e
    left join ProductEntity p on p.category.id = e.id
    where e.deleted = false and e.inGrid = true 
    group by e.id, e.faName, e.slug, e.docEntity.id, e.inGrid
""")
    Page<HotsSimple> findAllSimple(Pageable pageable);

    @Query("""
                select new com.webapp.arvand.arvandback.AAProductService.ProductSimple(
                    e.id,
                    e.name,
                    e.title,
                    e.price,
                    e.discount,
                    e.stock,
                    e.imgUrl.id,
                    e.views,
                    e.rank
                )
                from ProductEntity e
                where  e.deleted = false and e.views > :views
            """)
    Page<ProductSimple> findByMostViews(
            @Param("views") int views,
            Pageable pageable
    );
    @Query("""
                select new com.webapp.arvand.arvandback.AAProductService.ProductSimple(
                    e.id,
                    e.name,
                    e.title,
                    e.price,
                    e.discount,
                    e.stock,
                    e.imgUrl.id,
                    e.views,
                    e.rank
                )
                from ProductEntity e 
                  where e.deleted = false
                    and e.discount > :dis
            """)
    Page<ProductSimple> findByDis(
            @Param("dis") int tagId,
            Pageable pageable
    );

}
