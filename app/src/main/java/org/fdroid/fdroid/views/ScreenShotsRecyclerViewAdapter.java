package org.fdroid.fdroid.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import org.fdroid.fdroid.R;
import org.fdroid.fdroid.data.App;

class ScreenShotsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String[] screenshots;
    private final DisplayImageOptions displayImageOptions;
    private final Listener listener;

    ScreenShotsRecyclerViewAdapter(Context context, App app, Listener listener) {
        super();
        this.listener = listener;

        screenshots = app.getAllScreenshots(context);
        displayImageOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .imageScaleType(ImageScaleType.NONE)
                .showImageOnLoading(R.drawable.screenshot_placeholder)
                .showImageForEmptyUri(R.drawable.screenshot_placeholder)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ScreenShotViewHolder vh = (ScreenShotViewHolder) holder;
        ImageLoader.getInstance().displayImage(screenshots[position],
                vh.image, displayImageOptions);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_details2_screenshot_item, parent, false);
        return new ScreenShotViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return screenshots.length;
    }

    public interface Listener {
        /**
         * @param position zero based position of the screenshot
         *                 that has been clicked upon
         */
        void onScreenshotClick(int position);
    }

    private class ScreenShotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView image;

        ScreenShotViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.image);
            image.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onScreenshotClick(getAdapterPosition());
        }

        @Override
        public String toString() {
            return super.toString() + " screenshot";
        }
    }
}
