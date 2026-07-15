package com.webapp.arvand.arvandback.SearchService;

public interface ProductSearchProjection {

    String getProductId();
    String getFldSearchText();
    String getFldImageUrl();
    String getFldRank();
    String getFldViews();
    String getMatchedTokens();
    String getScore();
    String getDetail();
}