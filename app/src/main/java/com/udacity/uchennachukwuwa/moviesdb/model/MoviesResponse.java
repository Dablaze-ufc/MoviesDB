package com.udacity.uchennachukwuwa.moviesdb.model;

import com.google.gson.annotations.SerializedName;
import com.udacity.uchennachukwuwa.moviesdb.model.Movie;

import java.util.List;
@SuppressWarnings("unused")

public class MoviesResponse {

        @SerializedName("page")
        private int mPage;
        @SerializedName("results")
        private List<Movie> mResults;
        @SerializedName("total_pages")
        private int mTotalPages;
        @SerializedName("total_results")
        private int mTotalResults;

        public int getPage() {
            return mPage;
        }

        public List<Movie> getResults() {
            return mResults;
        }

        public int getTotalPages() {
            return mTotalPages;
        }

        public int getTotalResults() {
            return mTotalResults;
        }
}
