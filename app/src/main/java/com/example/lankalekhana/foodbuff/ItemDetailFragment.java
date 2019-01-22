package com.example.lankalekhana.foodbuff;

import android.app.Fragment;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ItemDetailFragment extends Fragment{

    TextView textView;
    String desc,thunbUrl;
    String videoUrl;
    SimpleExoPlayer exoPlayer;
    SimpleExoPlayerView simpleExoPlayerView;
    long position;
    ImageView noVideo;
    int a=0;
    boolean currentPlay;

    public ItemDetailFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments().containsKey("videoUrl") || getArguments().containsKey("desc"))
        {
            videoUrl = getArguments().getString("videoUrl");
            desc = getArguments().getString("desc");
            thunbUrl=getArguments().getString("thumbUrl");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        textView = rootView.findViewById(R.id.descText);
        noVideo =rootView.findViewById(R.id.noVideo);
        textView.setText(desc);
        simpleExoPlayerView = rootView.findViewById(R.id.exoplayer);
        thumbUrl();
        intializeExoPlayer();

        if (savedInstanceState!=null)
        {

            videoUrl=savedInstanceState.getString("key3","");
            position = savedInstanceState.getLong("key");
            currentPlay = savedInstanceState.getBoolean("key2");
            exoPlayer.seekTo(position);
            exoPlayer.setPlayWhenReady(currentPlay);

        }


        return rootView;
    }

    private void thumbUrl() {
        if (videoUrl.isEmpty())
        {
            if (thunbUrl.isEmpty())
            {
                noVideo.setVisibility(View.VISIBLE);
                simpleExoPlayerView.setVisibility(View.INVISIBLE);

            }

            else {
                videoUrl= thunbUrl;
                noVideo.setVisibility(View.GONE);
                simpleExoPlayerView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Player();
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || exoPlayer == null)) {
            intializeExoPlayer();
        }
    }

    private void Player() {

        if(exoPlayer!=null)
        {
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer=null;
        }
    }

   @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT<=23)
        {
            Player();
        }

    }

    private void intializeExoPlayer() {

        String link = videoUrl;

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        LoadControl loadControl = new DefaultLoadControl();
        exoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
        simpleExoPlayerView.setPlayer(exoPlayer);

        String userAgent=Util.getUserAgent(getActivity(),getString(R.string.app_name));

        Uri videoURI=Uri.parse(link);
       MediaSource mediaSource=new ExtractorMediaSource(videoURI,new DefaultDataSourceFactory(getActivity(),userAgent),new DefaultExtractorsFactory(),null,null);


        exoPlayer.prepare(mediaSource);
        exoPlayer.setPlayWhenReady(true);
        exoPlayer.seekTo(position);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("key",exoPlayer.getCurrentPosition());
        outState.putBoolean("key2",exoPlayer.getPlayWhenReady());
        outState.putString("key3",videoUrl);

    }
}
