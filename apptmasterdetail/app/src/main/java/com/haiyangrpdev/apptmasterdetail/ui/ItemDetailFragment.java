package com.haiyangrpdev.apptmasterdetail.ui;

import android.app.Activity;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.haiyangrpdev.apptmasterdetail.R;
import com.haiyangrpdev.apptmasterdetail.model.AppITunes;
import com.haiyangrpdev.apptmasterdetail.utility.ExtStorageHelper;

import java.util.List;

public class ItemDetailFragment extends Fragment {

    private AppITunes mItem;
    public static final String ARG_ITEM_ID = "item_id";

    private long mTrackId = 0;

    public ItemDetailFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            //ct0.temp rem dummy  mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);

            mTrackId = Long.parseLong(getArguments().getString(ARG_ITEM_ID));
            String jsonData = ExtStorageHelper.readData("songsFolder","songs.txt", ItemDetailFragment.this.getContext());
            Gson gson = new Gson();
            List<AppITunes> songs = gson.fromJson(jsonData, new TypeToken<List<AppITunes>>(){}.getType());

            for (AppITunes song: songs) {
                if(song.getTrackId() == mTrackId){
                    mItem = song;
                    break;
                }
            }

            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getTrackName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        if (mItem != null) {
            ImageView imageView = rootView.findViewById(R.id.ivArtwork);
            Glide.with(this).load(mItem.getArtwork()).into(imageView);
            ((TextView) rootView.findViewById(R.id.tvName)).setText("Name: " + mItem.getTrackName());
            ((TextView) rootView.findViewById(R.id.tvGenre)).setText("Genre: "+ mItem.getGenre());
            ((TextView) rootView.findViewById(R.id.tvPrice)).setText("Price: $" + mItem.getPrice());
        }

        return rootView;
    }
}
