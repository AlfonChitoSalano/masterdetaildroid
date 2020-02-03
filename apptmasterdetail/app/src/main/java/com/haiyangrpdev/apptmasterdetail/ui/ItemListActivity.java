package com.haiyangrpdev.apptmasterdetail.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.lifecycle.Observer;
import androidx.annotation.Nullable;
import com.haiyangrpdev.apptmasterdetail.R;
import com.haiyangrpdev.apptmasterdetail.apiservice.ITunesService;
import java.util.List;
import com.haiyangrpdev.apptmasterdetail.ui.base.BaseActivity;
import com.haiyangrpdev.apptmasterdetail.model.AppITunes;
import com.bumptech.glide.Glide;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import com.haiyangrpdev.apptmasterdetail.utility.ExtStorageHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ItemListActivity extends BaseActivity<ItemListActivityViewModel> {

    private boolean mWritePermitted;
    private boolean mReadPermitted;
    private boolean mTwoPane;
    private List<AppITunes> mData;
    private static final int STORAGE_REQUEST = 1;
    private static int WRITE_PERMISSION = 1;
    private static int READ_PERMISSION = 2;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mWritePermitted = (requestCode == WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED);
        mReadPermitted = (requestCode == READ_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //main layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        try {
            //prepare data via VM
            if (isNetworkAvailable()) {
                viewModel.getData();
                viewModel.getSongs().observe(this, new SongsObserver());
            }
            else {
                showMessageAlert("Internet", "Please connect to the internet!");
            }

            //toolbar stuff
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            toolbar.setTitle(getTitle());

            if (findViewById(R.id.item_detail_container) != null) {
                mTwoPane = true;
            }

            //ask permission
            if (isReadStoragePermissionGranted() && isWriteStoragePermissionGranted()) {
                //do nothing
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

    private boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, READ_PERMISSION);
                return false;
            }
        } else {
            return true;
        }
    }

    private boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_PERMISSION);
                return false;
            }
        } else {
            return true;
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showMessageAlert(String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<AppITunes> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AppITunes item = (AppITunes) view.getTag();

                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(ItemDetailFragment.ARG_ITEM_ID, String.valueOf(item.getTrackNumber()));
                    ItemDetailFragment fragment = new ItemDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.item_detail_container, fragment)
                            .commit();
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ItemDetailActivity.class);
                    intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, String.valueOf(item.getTrackNumber()));
                    context.startActivity(intent);
                }
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
            Glide.with(holder.mArtworkView.getContext()).load(mValues.get(position).getArtwork()).into(holder.mArtworkView);

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
            final ImageView mArtworkView;

            ViewHolder(View view) {
                super(view);
                mTrackNameView = (TextView) view.findViewById(R.id.tvTrackName);
                mArtworkView = (ImageView) view.findViewById(R.id.tvArtwork);
            }
        }
    }

    private class SongsObserver implements Observer<List<AppITunes>> {

        @Override
        public void onChanged(@Nullable List<AppITunes> songs) {
            //draw view
            if (songs == null) return;
            mData = songs;
            ExtStorageHelper.saveData("songsFolder", "songs.txt", "Data", ItemListActivity.this);
            View recyclerView = findViewById(R.id.item_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView);
        }
    }
}
