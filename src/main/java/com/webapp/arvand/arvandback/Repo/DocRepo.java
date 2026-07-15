package com.webapp.arvand.arvandback.Repo;

import com.webapp.arvand.arvandback.Entity.DocEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocRepo extends CrudRepository<DocEntity, String> {

}
