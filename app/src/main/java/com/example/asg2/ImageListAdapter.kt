package com.example.asg2

import android.support.v7.widget.*;
import android.view.ViewGroup;
import android.content.*
import android.graphics.BitmapFactory
import android.view.*
import android.media.*;

class ImageListAdapter: RecyclerView.Adapter<ImageViewHolder> {
    private val itemIdList: List<Int> = mutableListOf(R.drawable.image01,R.drawable.image02,R.drawable.image03,R.drawable.image04,R.drawable.image05,R.drawable.image06,R.drawable.image07,R.drawable.image08,R.drawable.image09,R.drawable.image10)
    private var layoutInflater: LayoutInflater;
    private var context: Context;
    constructor(context: Context): super() {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context)
    }

    override fun getItemCount(): Int {
        return itemIdList.count();
    }

    override fun onBindViewHolder(imageViewHolder: ImageViewHolder, position: Int) {
        var id: Int = itemIdList[position]
        //var uri: Uri = Uri.parse("android.resource://com.example.asg2/drawable/" + path)
        var itemImageView = imageViewHolder.itemImageView;
        var exifInterface = ExifInterface(itemImageView.resources.openRawResource(id))
        var latlon: FloatArray = FloatArray(2)
        exifInterface.getLatLong(latlon)
        imageViewHolder.latlon = latlon
        //android.util.Log.e("" + latlon[0] + "" + latlon[1],"?")
        itemImageView.setImageBitmap(BitmapFactory.decodeResource(itemImageView.resources,id))
        itemImageView.setOnLongClickListener {
            //android.util.Log.e("hello","hello")
            var popup = PopupMenu(context, it, Gravity.NO_GRAVITY, R.attr.actionOverflowMenuStyle, 0)
            popup.inflate(R.menu.popup_item)
            popup.setOnMenuItemClickListener {
                //if (it.itemId == R.id.item_show_location) {
                var intent = Intent(context, MapActivity::class.java)
                intent.putExtra("com.example.asg2.PICTURE_LATLON", imageViewHolder.latlon)
                intent.putExtra("com.example.asg2.SELECTED_MODE", it.itemId == R.id.item_show_location)
                context.startActivity(intent)

                return@setOnMenuItemClickListener true
            }
            popup.show()
            return@setOnLongClickListener true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): ImageViewHolder {
        var itemView = layoutInflater.inflate(R.layout.image_list_item, parent, false)
        return ImageViewHolder(itemView, this)
    }
}