package com.udacity.uchennachukwuwa.moviesdb.model;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ChukwuwaUchenna
 */

public class TrailerResponse {
    @SerializedName("id")
    private Long mId;
    @SerializedName("results")
    private List<TrailerResult> mTrailerResults;

    public Long getId() {
        return mId;
    }

    public List<TrailerResult> getTrailerResults() {
        return mTrailerResults;
    }
}
