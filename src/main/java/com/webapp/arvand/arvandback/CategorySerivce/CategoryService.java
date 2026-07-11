package com.webapp.arvand.arvandback.CategorySerivce;

import com.webapp.arvand.arvandback.Dto.DocDto;
import com.webapp.arvand.arvandback.Entity.CoreThingEntity;
import com.webapp.arvand.arvandback.Entity.DocEntity;
import com.webapp.arvand.arvandback.Repo.CoreThingRepo;
import com.webapp.arvand.arvandback.Repo.DocRepo;
import com.webapp.arvand.arvandback.Service.DocInterface;
import com.webapp.arvand.arvandback.Utills.ApiErrorType;
import com.webapp.arvand.arvandback.Utills.ApiException;
import com.webapp.arvand.arvandback.Utills.PageResponse;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements CategoryInterface {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private CoreThingRepo coreThingRepo;
    @Autowired
    private DocRepo docRepo;
    @Autowired
    private DocInterface docInterface;

    @Override
    public PageResponse<CategorySimple> getCategoriesWithItsCount
            (int page, int size, String sortBy, String direction) throws ApiException {
        try {
            Sort sort = direction.equalsIgnoreCase("asc")
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();

            Pageable pageable = PageRequest.of(page, size, sort);
            Page<CategorySimple> entities = categoryRepo.findAllSimple(pageable);

            PageResponse<CategorySimple> response = new PageResponse<>(
                    entities.getContent(),
                    entities.getNumber(),
                    entities.getTotalPages(),
                    entities.getTotalElements(),
                    entities.getSize()
            );
            return response;

        } catch (Exception e) {
            throw new ApiException(ApiErrorType.SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public boolean saveCategory(CategoryForm dto) throws ApiException {
        try {
            boolean isNew = dto.getId().equals("-1") ? true : false;
            CategoryEntity categoryEntity = null;
            if (isNew) {
                if (dto.getImageFile() == null || dto.getImageFile().equals("")) {
                    throw new ApiException(ApiErrorType.SERVER_ERROR, "Image file is empty");
                }
                categoryEntity = new CategoryEntity();
            }
            if (!isNew) {
                categoryEntity = categoryRepo.findById(dto.getId())
                        .orElseThrow(() -> new ApiException(ApiErrorType.SERVER_ERROR, "Category not found"));
            }

            categoryEntity.setFaName(dto.getFaName());
            categoryEntity.setEnName(dto.getEnName());
            categoryEntity.setSlug(dto.getSlug());
            if (dto.getImageFile() != null) {
                CoreThingEntity coreThingEntity = (CoreThingEntity) coreThingRepo.findByValueAndGroup(
                        dto.getImageFile().getContentType(), "FileType");
                if(coreThingEntity == null) {
                    throw new ApiException(ApiErrorType.SERVER_ERROR, "Image file not found");
                }
                DocEntity docEntity = new DocEntity();

                docEntity.setDocData(dto.getImageFile().getBytes());
                docEntity.setDocType(coreThingEntity);
                docEntity.setDocName(dto.getImageFile().getName());

                try {
                    docEntity = docRepo.save(docEntity);
                } catch (Exception e) {
                    throw new ApiException(ApiErrorType.SERVER_ERROR, e.getMessage());
                }
                categoryEntity.setDocEntity(docEntity);
            }
            categoryEntity = categoryRepo.save(categoryEntity);

            return true;
        }
        catch (ApiException e) {
            throw  e;
        }
        catch (Exception e) {
            throw new ApiException(ApiErrorType.SERVER_ERROR, "Error while saving category");
        }
    }

    @Override
    public CategoryForm getCategory(String id) throws ApiException {
        return null;
    }

    @Override
    public DocDto getImage(String categoryId) throws ApiException {
        try {
            DocDto docEntity = docInterface.downloadById(categoryId);
            return docEntity;
        }
        catch (ApiException e){
            throw e;
        }
    }
    @Override
    public String getCategoriesCount() throws ApiException {
        try {
            String query = "select count(*) from CategoryEntity e";
            List res = entityManager.createQuery(query).getResultList();
            if(res.size() > 0){
                return res.get(0).toString();
            }
        }
        catch (Exception e) {
            return "0";
        }
        return "0";
    }
}
