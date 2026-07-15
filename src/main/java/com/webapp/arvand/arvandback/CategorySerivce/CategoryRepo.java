package com.webapp.arvand.arvandback.CategorySerivce;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends CrudRepository<CategoryEntity, String> {

    Page<CategoryEntity> findByDeletedFalse(Pageable pageable);

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
    Page<CategorySimple> findAllSimple(Pageable pageable);

}
