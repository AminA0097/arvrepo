package com.webapp.arvand.arvandback.Service;

import com.webapp.arvand.arvandback.Dto.DocDto;
import com.webapp.arvand.arvandback.Entity.DocEntity;
import com.webapp.arvand.arvandback.Repo.DocRepo;
import com.webapp.arvand.arvandback.Utills.ApiErrorType;
import com.webapp.arvand.arvandback.Utills.ApiException;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocService implements DocInterface{

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private DocRepo docRepo;
    @Override
    public DocDto downloadById(String id) throws ApiException {
        try {
            Optional<DocEntity> doc = docRepo.findById(id);
            if (doc.isPresent()) {
                DocDto docDto = new DocDto();
                docDto.setDocData(doc.get().getDocData());
                docDto.setDocName(doc.get().getDocName());
                docDto.setDocType(doc.get().getDocType().getValue());
                return docDto;
            }
        }
        catch (Exception e) {
            throw new ApiException(ApiErrorType.SERVER_ERROR,e.getMessage());
        }
        return null;
    }
}
