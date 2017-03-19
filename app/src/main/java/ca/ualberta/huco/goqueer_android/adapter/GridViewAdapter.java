package ca.ualberta.huco.goqueer_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ualberta.huco.goqueer_android.R;
import entity.ImageItem;

/**
 * Created by Circa Lab on 3/18/2017.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context mContext;



    public GridViewAdapter(Context context)
    {
        mContext = context;
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }


    // Override this method according to your need
    public View getView(int index, View view, ViewGroup viewGroup)
    {
        // TODO Auto-generated method stub
        ImageView i = new ImageView(mContext);
        i.setImageResource(mImageIds[index]);
        i.setLayoutParams(new Gallery.LayoutParams(200, 200));
        i.setScaleType(ImageView.ScaleType.FIT_XY);
        return i;
    }

    public Integer[] mImageIds = {
            R.drawable.pin,
            R.drawable.pin2,
            R.drawable.pin3,
            R.drawable.pin4,
            R.drawable.pin5,
            R.drawable.pin6,

    };
}