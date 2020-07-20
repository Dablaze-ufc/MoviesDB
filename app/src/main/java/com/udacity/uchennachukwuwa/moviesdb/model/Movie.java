package com.udacity.uchennachukwuwa.moviesdb.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ChukwuwaUchenna
 */

@Entity (tableName = "movie_table")
public class Movie implements Parcelable {
    @Ignore
    @SerializedName("adult")
    private Boolean mAdult;

    @SerializedName("backdrop_path")
    private String mBackdropPath;

    @Ignore
    @SerializedName("genre_ids")
    private List<Integer> mGenreIds;

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private int mId;

    @Ignore
    @SerializedName("original_language")
    private String mOriginalLanguage;

    @Ignore
    @SerializedName("original_title")
    private String mOriginalTitle;

    @SerializedName("overview")
    private String mOverview;

    @Ignore
    @SerializedName("popularity")
    private double mPopularity;

    @SerializedName("poster_path")
    private String mPosterPath;

    @SerializedName("release_date")
    private String mReleaseDate;

    @SerializedName("title")
    private String mTitle;

    @Ignore
    @SerializedName("video")
    private Boolean mVideo;

    @SerializedName("vote_average")
    private double mVoteAverage;

    @Ignore
    @SerializedName("vote_count")
    private int mVoteCount;


    protected Movie(Parcel in) {
        byte tmpMAdult = in.readByte();
        mAdult = tmpMAdult == 0 ? null : tmpMAdult == 1;
        mBackdropPath = in.readString();
        mId = in.readInt();
        mOriginalLanguage = in.readString();
        mOriginalTitle = in.readString();
        mOverview = in.readString();
        mPopularity = in.readDouble();
        mPosterPath = in.readString();
        mReleaseDate = in.readString();
        mTitle = in.readString();
        byte tmpMVideo = in.readByte();
        mVideo = tmpMVideo == 0 ? null : tmpMVideo == 1;
        mVoteAverage = in.readDouble();
        mVoteCount = in.readInt();

    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Ignore
    public Movie() {
    }

    public Boolean getAdult() {
        return mAdult;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public List<Integer> getGenreIds() {
        return mGenreIds;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public Double getPopularity() {
        return mPopularity;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getTitle() {
        return mTitle;
    }

    public Boolean getVideo() {
        return mVideo;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public int getVoteCount() {
        return mVoteCount;
    }


    @Ignore
    public Movie(Boolean adult, String backdropPath, List<Integer> genreIds, int id,
                 String originalLanguage, String originalTitle, String overview,
                 double popularity, String posterPath, String releaseDate, String title,
                 Boolean video, double voteAverage, int voteCount) {
        mAdult = adult;
        mBackdropPath = backdropPath;
        mGenreIds = genreIds;
        mId = id;
        mOriginalLanguage = originalLanguage;
        mOriginalTitle = originalTitle;
        mOverview = overview;
        mPopularity = popularity;
        mPosterPath = posterPath;
        mReleaseDate = releaseDate;
        mTitle = title;
        mVideo = video;
        mVoteAverage = voteAverage;
        mVoteCount = voteCount;
    }

    public Movie(String backdropPath, int id, String overview, String posterPath,
                 String releaseDate, String title, double voteAverage) {
        mBackdropPath = backdropPath;
        mId = id;
        mOverview = overview;
        mPosterPath = posterPath;
        mReleaseDate = releaseDate;
        mTitle = title;
        mVoteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (mAdult == null ? 0 : mAdult ? 1 : 2));
        parcel.writeString(mBackdropPath);
        parcel.writeInt(mId);
        parcel.writeString(mOriginalLanguage);
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mOverview);
        parcel.writeDouble(mPopularity);
        parcel.writeString(mPosterPath);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mTitle);
        parcel.writeByte((byte) (mVideo == null ? 0 : mVideo ? 1 : 2));
        parcel.writeDouble(mVoteAverage);
        parcel.writeInt(mVoteCount);
    }
}
