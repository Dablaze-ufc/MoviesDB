package com.udacity.uchennachukwuwa.moviesdb.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.uchennachukwuwa.moviesdb.R;
import com.udacity.uchennachukwuwa.moviesdb.model.TrailerResult;

import java.util.List;

/**
 * Created by ChukwuwaUchenna
 */

public class TrailersAdapter extends   RecyclerView.Adapter<TrailersAdapter.ViewHolder> {
        private List<TrailerResult> list;
        private OnItemClickListener onItemClickListener;

    public TrailersAdapter(List<TrailerResult> list, OnItemClickListener onItemClickListener) {
            this.list = list;
            this.onItemClickListener = onItemClickListener;
        }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ViewHolder(inflater.inflate(R.layout.list_item_trailer, parent, false));
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TrailerResult item = list.get(position);
        holder.bind(item, onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


        static class ViewHolder extends RecyclerView.ViewHolder {
            ViewHolder(View itemView) {
                super(itemView);
            }

            void bind(final TrailerResult trailer,
                      final OnItemClickListener listener) {
                TextView title = itemView.findViewById(R.id.tv_trailer);
                title.setText(trailer.getName());
                itemView.setOnClickListener(v -> listener.onTrailerItemClick(trailer.getKey()));
            }
        }

        public interface OnItemClickListener {
            void onTrailerItemClick(String key);
        }
    }
