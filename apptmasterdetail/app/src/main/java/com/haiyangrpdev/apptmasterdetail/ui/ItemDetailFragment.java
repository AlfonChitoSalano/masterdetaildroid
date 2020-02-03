package com.haiyangrpdev.apptmasterdetail.ui;

import android.app.Activity;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public ItemDetailFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            //ct0.temp rem dummy  mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);

            if (appBarLayout != null) {
            //ct0.temp rem dummy  appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        //ct0.temp rem dummy
        // Show the dummy content as text in a TextView.
        String jsonData = ExtStorageHelper.readData("songsFolder","songs.txt", ItemDetailFragment.this.getContext());
        Gson gson = new Gson();
        List<AppITunes> songs = gson.fromJson(jsonData, new TypeToken<List<AppITunes>>(){}.getType());

        if (mItem != null) {
            String trackName = mItem.getTrackName();
            String trackGenre = mItem.getGenre();
            String trackArtwork = mItem.getArtwork();
            double trackPrice = mItem.getPrice();

             ((TextView) rootView.findViewById(R.id.item_detail)).setText(trackName);
        }

        return rootView;
    }
}
