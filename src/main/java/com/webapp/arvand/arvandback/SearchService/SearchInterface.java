package com.webapp.arvand.arvandback.SearchService;

import java.util.List;

public interface SearchInterface {
    public List<SearchResult> findPro(String q)throws Exception;
}