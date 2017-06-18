package com.example.yokgoodchild.roadsideassistance.ViewReply.CustomListview_ViewReply;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yokgoodchild.roadsideassistance.AcceptReply.AcceptReplyActivity;
import com.example.yokgoodchild.roadsideassistance.ClassBean.ReplyBean;
import com.example.yokgoodchild.roadsideassistance.R;
import com.example.yokgoodchild.roadsideassistance.ViewReply.GetViewReply;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by YokGoodChild on 6/5/2017.
 */

public class CustomAdapter_ViewReply extends ArrayAdapter<ReplyBean> implements View.OnClickListener {

    private ArrayList<ReplyBean> dataSet;
    private Context mContext;
    private String status;

    private  static class ViewHolder {
        TextView txtStatus;
        TextView txtTitle;
        TextView txtDetail;
        ImageView info;
    }

    public CustomAdapter_ViewReply(ArrayList<ReplyBean> data, Context context, String statued){
        super(context, R.layout.activity_view_reply, data);
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
        String name_status = null;
        // Get the data item for this position
        ReplyBean dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        CustomAdapter_ViewReply.ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new CustomAdapter_ViewReply.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_request_item, parent, false);
            viewHolder.txtStatus = (TextView) convertView.findViewById(R.id.status);
            viewHolder.txtTitle = (TextView) convertView.findViewById(R.id.titlehead);
            viewHolder.txtDetail = (TextView) convertView.findViewById(R.id.detail);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);
//            convertView.setOnClickListener(this);
            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CustomAdapter_ViewReply.ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

//        viewHolder.txtStatus.setText(String.valueOf(dataModel.getAlbumId()));
        if(status.equals("0")){
            name_status = "รอการยืนยัน";
        }else if(status.equals("1")){
            name_status = "รอให้คะแนน";
        }else if(status.equals("2")){
            name_status = ":ซ่อมเสร็จ";
        }
        viewHolder.txtStatus.setText(name_status);

        viewHolder.txtTitle.setText(String.valueOf(dataModel.getTitleReply()));
        viewHolder.txtDetail.setText(dataModel.getRepair().getNameShop());
        viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);

        Picasso.with(mContext.getApplicationContext()).load(mContext.getString(R.string.urlImage)+dataModel.getPhoto()).into(viewHolder.info);

        // Return the completed view to render on screen
        return convertView;
    }

}
