package com.haiyangrpdev.apptmasterdetail.ui;

import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;
import com.haiyangrpdev.apptmasterdetail.R;
import com.haiyangrpdev.apptmasterdetail.apiservice.ITunesService;
import java.util.List;
import com.haiyangrpdev.apptmasterdetail.ui.base.BaseActivity;
import com.haiyangrpdev.apptmasterdetail.model.AppITunes;

public class ItemListActivity extends BaseActivity<ItemListActivityViewModel> {

    private boolean mTwoPane;
    private List<AppITunes> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //main layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        try {
            //prepare data via VM
            viewModel.getData();
            viewModel.getSongs().observe(this, new SongsObserver());

            //toolbar stuff
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitle(getTitle());

            if (findViewById(R.id.item_detail_container) != null) {
                mTwoPane = true;
            }
        }
        catch (Exception e) {
           String errorMessage = e.getMessage();
        }
    }

    @NonNull
    @Override
    protected ItemListActivityViewModel createViewModel() {
        return new ItemListActivityViewModel(ITunesService.getInstance());
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, mData, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<AppITunes> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /*DummyContent.DummyItem item = (DummyContent.DummyItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, item.id);
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, item.id);
                    context.startActivity(intent);
                }*/
            }
        };

        SimpleItemRecyclerViewAdapter(ItemListActivity parent,
                                      List<AppITunes> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            //display fields
            holder.mTrackNameView.setText(mValues.get(position).getTrackName());
            holder.mArtworkView.setText(mValues.get(position).getArtwork());
            holder.mGenreView.setText(mValues.get(position).getGenre());
            holder.mPriceView.setText(String.valueOf(mValues.get(position).getPrice()));

            //set listener
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mTrackNameView;
            final TextView mArtworkView;
            final TextView mGenreView;
            final TextView mPriceView;

            ViewHolder(View view) {
                super(view);
                mTrackNameView = (TextView) view.findViewById(R.id.tvTrackName);
                mArtworkView = (TextView) view.findViewById(R.id.tvArtwork);
                mGenreView = (TextView) view.findViewById(R.id.tvGenre);
                mPriceView = (TextView) view.findViewById(R.id.tvPrice);
            }
        }
    }

    private class SongsObserver implements Observer<List<AppITunes>> {

        @Override
        public void onChanged(@Nullable List<AppITunes> songs) {
            //draw view
            if (songs == null) return;
            mData = songs;
            View recyclerView = findViewById(R.id.item_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView);
        }
    }
}
