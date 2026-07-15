package com.webapp.arvand.arvandback.SearchService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    private SearchInterface searchInterface;
    @GetMapping
    public List<SearchResult> autocomplete(@RequestParam String q) throws Exception {
        return searchInterface.findPro(q);
    }
    @GetMapping("/count")
    public List<SearchResult> count(@RequestParam String q) throws Exception {
        return searchInterface.findPro(q);
    }
}
