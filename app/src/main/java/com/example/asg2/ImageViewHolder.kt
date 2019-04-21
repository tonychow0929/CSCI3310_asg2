package com.example.asg2

import android.view.*;
import android.widget.*
import android.support.v7.app.*;
import android.support.v7.widget.*;


class ImageViewHolder: RecyclerView.ViewHolder {
    var adapter: ImageListAdapter;
    var itemImageView: ImageView;
    lateinit var latlon: FloatArray;
    constructor(itemView: View, adapter: ImageListAdapter): super(itemView) {
        this.adapter = adapter;
        this.itemImageView = itemView.findViewById(R.id.itemImageView);
    }
}
