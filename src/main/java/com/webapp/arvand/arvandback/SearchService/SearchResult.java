package com.webapp.arvand.arvandback.SearchService;


public class SearchResult {

    // Constructor matching the SQL result mapping
    public SearchResult(String productId, String fldSearchText,
                        String fldImageUrl, String fldRank, String fldViews,
                        String matchedTokens, String score,String detail) {
        this.productId = productId;
        this.fldSearchText = fldSearchText;
        this.fldImageUrl = fldImageUrl;
        this.fldRank = fldRank;
        this.fldViews = fldViews;
        this.matchedTokens = matchedTokens;
        this.score = score;
        this.detail = detail;

    }

    public SearchResult() {
    }

    private String productId;
    private String fldSearchText;
    private String fldImageUrl;
    private String fldRank;
    private String fldViews;
    private String matchedTokens;    // Changed from String to Long
    private String score;           // Changed from String to Double
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    // Changed from String to Double
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getFldSearchText() {
        return fldSearchText;
    }

    public void setFldSearchText(String fldSearchText) {
        this.fldSearchText = fldSearchText;
    }

    public String getFldImageUrl() {
        return fldImageUrl;
    }

    public void setFldImageUrl(String fldImageUrl) {
        this.fldImageUrl = fldImageUrl;
    }

    public String getFldRank() {
        return fldRank;
    }

    public void setFldRank(String fldRank) {
        this.fldRank = fldRank;
    }

    public String getFldViews() {
        return fldViews;
    }

    public void setFldViews(String fldViews) {
        this.fldViews = fldViews;
    }

    public String getMatchedTokens() {
        return matchedTokens;
    }

    public void setMatchedTokens(String matchedTokens) {
        this.matchedTokens = matchedTokens;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "productId='" + productId + '\'' +
                ", title='" + fldSearchText + '\'' +
                ", matchedTokens=" + matchedTokens +
                ", score=" + score +
                ", rank='" + fldRank + '\'' +
                ", views='" + fldViews + '\'' +
                '}';
    }
}