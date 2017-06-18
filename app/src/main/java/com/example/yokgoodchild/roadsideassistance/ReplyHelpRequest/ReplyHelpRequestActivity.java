package com.example.yokgoodchild.roadsideassistance.ReplyHelpRequest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yokgoodchild.roadsideassistance.ClassBean.RepairShopBean;
import com.example.yokgoodchild.roadsideassistance.ClassBean.ReplyBean;
import com.example.yokgoodchild.roadsideassistance.ClassBean.RequestBean;
import com.example.yokgoodchild.roadsideassistance.ListHelpRequest.ListHelpRequestActivity;
import com.example.yokgoodchild.roadsideassistance.Map.FM_Map_Marker;
import com.example.yokgoodchild.roadsideassistance.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class ReplyHelpRequestActivity extends AppCompatActivity {

    public static final String DATA_LOGIN = "DATA_LOGIN";
    public static final String LOGIN_DATA = "LOGIN_DATA";
    private RepairShopBean repairShopBean;
    private ReplyHelpRequestManager replyHelpRequestManager;
    private RequestBean requestBean;

    private EditText detail_reply;
    private EditText price_reply;

    private String status;

    private Animator mCurrentAnimator;
    private int mShortAnimationDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_help_request);

        SharedPreferences sp = getSharedPreferences(DATA_LOGIN, Context.MODE_PRIVATE);
        String data_login = sp.getString(LOGIN_DATA, "Fail");
        if(data_login != "Fail"){
            repairShopBean = new Gson().fromJson(data_login,RepairShopBean.class);
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String requested = bundle.getString("request");
            status = bundle.getString("status");
            requestBean = new Gson().fromJson(requested,RequestBean.class);
        }

        TextView title = (TextView) findViewById(R.id.reply_help_request_txt_title);
        TextView date = (TextView) findViewById(R.id.reply_help_request_txt_date);
        TextView name = (TextView) findViewById(R.id.reply_help_request_txt_name);
        TextView detail = (TextView) findViewById(R.id.reply_help_request_txt_detail);
//        ImageView img = (ImageView) findViewById(R.id.reply_help_request_img);
        TextView title_detail = (TextView) findViewById(R.id.reply_help_request_txt_view005);
        TextView title_price = (TextView) findViewById(R.id.reply_help_request_txt_view006);
        detail_reply = (EditText) findViewById(R.id.reply_help_request_txt_detail_reply);
        price_reply = (EditText) findViewById(R.id.reply_help_request_txt_price_reply);
        final ImageButton img = (ImageButton) findViewById(R.id.reply_help_request_img);
        Button btn_map = (Button) findViewById(R.id.reply_help_request_btn_map);
        Button btn_ok = (Button) findViewById(R.id.reply_help_request_btn_ok);
        Button btn_cancel = (Button) findViewById(R.id.reply_help_request_btn_cancel);

        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        if(status.equals("0")){
            btn_map.setEnabled(true);
        }else if(status.equals("1")){
            btn_map.setEnabled(true);
            detail_reply.setVisibility(View.INVISIBLE);
            price_reply.setVisibility(View.INVISIBLE);
            title_detail.setVisibility(View.INVISIBLE);
            title_price.setVisibility(View.INVISIBLE);
            btn_ok.setVisibility(View.INVISIBLE);
            btn_cancel.setVisibility(View.INVISIBLE);
        }else {
            btn_map.setEnabled(true);
        }

        title.setText(requestBean.getTitleDamage());
        date.setText(requestBean.getDateRequest());
        name.setText(requestBean.getName());
        detail.setText(requestBean.getDamageDetail());
        Picasso.with(ReplyHelpRequestActivity.this).load(getString(R.string.urlImage)+requestBean.getPhoto()).into(img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomImageFromThumb(img,getString(R.string.urlImage)+requestBean.getPhoto());
            }
        });

        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("latitude", requestBean.getLatitude());
                bundle.putString("longitude", requestBean.getLongitude());

                Fragment one = new FM_Map_Marker();
                one.setArguments(bundle);
                FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();
                transaction1.add(R.id.show_Map, one);
                transaction1.addToBackStack(null);
                transaction1.commit();
            }
        });
    }

    public void onClickSubmitReplyHelpRequest(View v){
        ReplyBean reply = new ReplyBean();

        reply.setReplyid(0);
        reply.setTitleReply(requestBean.getTitleDamage());
        reply.getRepair().setRegistrationID(repairShopBean.getRegistrationID());
        reply.setRequestid(requestBean.getRequestID());
        reply.setStatusReply("0");
        reply.setDateReply(String.valueOf(new Date()));
        reply.setDetail(detail_reply.getText().toString());
        reply.setPrice(Integer.parseInt(price_reply.getText().toString()));

        replyHelpRequestManager = new ReplyHelpRequestManager(ReplyHelpRequestActivity.this);

        replyHelpRequestManager.setURL(getString(R.string.url));

        replyHelpRequestManager.insertRequestForHelp(reply);

        Toast.makeText(this, "ทำรายการสำเร็จ", Toast.LENGTH_SHORT).show();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = new Intent(ReplyHelpRequestActivity.this, ListHelpRequestActivity.class);
        startActivity(intent);
        finish();

    }

    private void zoomImageFromThumb(final View thumbView, String photo) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
//        final ImageView expandedImageView = (ImageView) findViewById(
//                R.id.expanded_image);
        final PhotoView expandedImageView = (PhotoView) findViewById(R.id.expanded_image001);
//        expandedImageView.setImageResource(imageResId);
        Picasso.with(ReplyHelpRequestActivity.this).load(photo).into(expandedImageView);


        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        findViewById(R.id.container)
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

}
