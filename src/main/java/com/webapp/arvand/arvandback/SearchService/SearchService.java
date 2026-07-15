package com.webapp.arvand.arvandback.SearchService;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService implements SearchInterface{
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ProductSearchRepo productSearchRepo;

    @Override
    public List<SearchResult> findPro(String q) throws Exception {
        String normalized = q.trim();

//        String tres = productSearchRepo.threshold();
        List<String> s = productSearchRepo.checkExtension();
        List<ProductSearchProjection> result;
        result = productSearchRepo.autocompleteLong(
                normalized,
                10
        );
        return result.stream()
                .map(project -> new SearchResult(
                        project.getProductId(),
                        project.getFldSearchText(),
                        project.getFldImageUrl(),
                        project.getFldRank(),
                        project.getFldViews(),
                        project.getMatchedTokens(),
                        project.getScore(),
                        project.getDetail()
                ))
                .collect(Collectors.toList());
    }
}
