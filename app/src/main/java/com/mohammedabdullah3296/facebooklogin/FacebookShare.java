package com.mohammedabdullah3296.facebooklogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class FacebookShare extends AppCompatActivity {

    private static  final int REQEST_VIDEO_CODE = 9001 ;

    private com.squareup.picasso.Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            SharePhoto sharePhoto = new SharePhoto.Builder().setBitmap(bitmap).build();
            if (ShareDialog.canShow(ShareLinkContent.class)){
                SharePhotoContent sharePhotoContent = new SharePhotoContent.Builder()
                        .addPhoto(sharePhoto)
                        .build();
                shareDialog.show(sharePhotoContent);
            }
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }
    };
    CallbackManager callbackManager ;
    ShareDialog shareDialog ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_facebook_share);

        findViewById(R.id.link).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareDialog.registerCallback(callbackManager , new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(FacebookShare.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(FacebookShare.this, "Cancel", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(FacebookShare.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });

                ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                        .setQuote("this is usfjk jgj").
                        setContentUrl(Uri.parse("https://www.youtube.com/watch?v=2ZdzG_XObDM"))
                        .build();
                if (ShareDialog.canShow(ShareLinkContent.class)){
                    shareDialog.show(shareLinkContent);
                }
            }
        });

        findViewById(R.id.img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shareDialog.registerCallback(callbackManager , new FacebookCallback<Sharer.Result>() {
                    @Override
                    public void onSuccess(Sharer.Result result) {
                        Toast.makeText(FacebookShare.this, "Success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(FacebookShare.this, "Cancel", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onError(FacebookException error) {
                        Toast.makeText(FacebookShare.this, "Error", Toast.LENGTH_SHORT).show();

                    }
                });
                Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(target);
            }
        });


        findViewById(R.id.video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("videos/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent , "Select video") , REQEST_VIDEO_CODE);
            }
        });

        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == REQEST_VIDEO_CODE) {
            Uri uri = data.getData();
            ShareVideo shareVideo = new ShareVideo.Builder()
                    .setLocalUrl(uri)
                    .build() ;

            ShareVideoContent shareVideoContent = new ShareVideoContent.Builder()
                    .setContentTitle("This is Useful Vide")
                    .setContentDescription("cgvcvbvbvb")
                    .setVideo(shareVideo)
                    .build();


            if (ShareDialog.canShow(ShareVideoContent.class)){
                shareDialog.show(shareVideoContent);
            }
        }
    }
}
