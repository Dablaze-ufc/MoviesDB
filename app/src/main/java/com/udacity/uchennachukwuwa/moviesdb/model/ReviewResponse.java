package com.udacity.uchennachukwuwa.moviesdb.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
/**
 * Created by ChukwuwaUchenna
 */
public class ReviewResponse {
        @SerializedName("id")
        private Integer id;
        @SerializedName("page")
        private Integer page;
        @SerializedName("results")
        private List<ReviewResult> results = null;
        @SerializedName("total_pages")
        private Integer totalPages;
        @SerializedName("total_results")
        private Integer totalResults;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public List<ReviewResult> getResults() {
            return results;
        }

        public void setResults(List<ReviewResult> results) {
            this.results = results;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Integer getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(Integer totalResults) {
            this.totalResults = totalResults;
        }
}
