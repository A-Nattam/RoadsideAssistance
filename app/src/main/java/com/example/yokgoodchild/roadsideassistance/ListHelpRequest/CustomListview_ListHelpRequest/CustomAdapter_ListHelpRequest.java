package com.example.yokgoodchild.roadsideassistance.ListHelpRequest.CustomListview_ListHelpRequest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yokgoodchild.roadsideassistance.ClassBean.RequestBean;
import com.example.yokgoodchild.roadsideassistance.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by YokGoodChild on 6/10/2017.
 */

public class CustomAdapter_ListHelpRequest extends ArrayAdapter<RequestBean> implements View.OnClickListener {

    private ArrayList<RequestBean> dataSet;
    private Context mContext;
    private String status;

    private  static class ViewHolder {
        TextView txtStatus;
        TextView txtTitle;
        TextView txtDetail;
        ImageView info;
    }

    public CustomAdapter_ListHelpRequest(ArrayList<RequestBean> data, Context context, String statued){
        super(context, R.layout.activity_list_help_request, data);
        this.dataSet = data;
        this.mContext = context;
        this.status =statued;
    }

    @Override
    public void onClick(View v) {

    }

    private int lastPosition = -1;

    @Override
    public View getView(int position,View convertView, ViewGroup parent) {
        // Get the data item for this position
        RequestBean dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_request_item, parent, false);
            viewHolder.txtStatus = (TextView) convertView.findViewById(R.id.status);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.titlehead);
            viewHolder.txtDetail = (TextView) convertView.findViewById(R.id.detail);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

//        viewHolder.txtStatus.setText(String.valueOf(dataModel.getAlbumId()));
        viewHolder.txtStatus.setText("รอการตอบรับ");

        viewHolder.txtTitle.setText(String.valueOf(dataModel.getName()));
        viewHolder.txtDetail.setText(dataModel.getTitleDamage());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);

        if(dataModel.getCarType().equals("0")){
            viewHolder.info.setImageResource(R.drawable.item_list02);
        }else if(dataModel.getCarType().equals("1")){
            viewHolder.info.setImageResource(R.drawable.item_list01);
        }else if(dataModel.getCarType().equals("2")){
            viewHolder.info.setImageResource(R.drawable.item_list03);
        }else {
            Picasso.with(mContext.getApplicationContext()).load("http://www.colorcombos.com/images/colors/FF9900.png").into(viewHolder.info);
        }

//        Picasso.with(mContext.getApplicationContext()).load("http://www.colorcombos.com/images/colors/FF9900.png").into(viewHolder.info);

        // Return the completed view to render on screen

        return convertView;
    }
}
