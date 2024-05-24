package com.onlinemadrasa.features.video;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoContentDetails;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatistics;
import com.onlinemadrasa.R;
import com.onlinemadrasa.model.OnVideoSelect;
import com.onlinemadrasa.model.PlaylistVideos;

import java.text.DecimalFormat;

public class PlaylistCardAdapterNew extends RecyclerView.Adapter<PlaylistCardAdapterNew.ViewHolder> {
    private static final DecimalFormat sFormatter = new DecimalFormat("#,###,###");
    private final PlaylistVideos mPlaylistVideos;
    private final VideoListingActivity.LastItemReachedListener mListener;
    private OnVideoSelect onVideoSelect;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final Context mContext;
        private CardView mainCard;
        private LinearLayout videoDuration;
        private TextView videoDutationText;
        private ImageView videoThumbnail;
        private TextView videoTitle;
        private TextView descTxv;
        private TextView viewsText;
        private TextView addedTime;
        private ImageButton moreButton;
        private RelativeLayout titleRlay;

        public ViewHolder(View view) {
            super(view);
            mContext = view.getContext();
            titleRlay = view.findViewById(R.id.titleRlay);
            mainCard = (CardView) view.findViewById(R.id.mainCard);
            videoDuration = (LinearLayout) view.findViewById(R.id.video_duration);
            videoDutationText = (TextView) view.findViewById(R.id.video_dutation_text);
            videoThumbnail = (ImageView) view.findViewById(R.id.video_thumbnail);
            videoTitle = (TextView) view.findViewById(R.id.video_title);
            descTxv = (TextView) view.findViewById(R.id.descTxv);
            viewsText = (TextView) view.findViewById(R.id.views_text);
            addedTime = (TextView) view.findViewById(R.id.added_time);
            moreButton = (ImageButton) view.findViewById(R.id.moreButton);
        }

    }

    public PlaylistCardAdapterNew(PlaylistVideos playlistVideos, VideoListingActivity.LastItemReachedListener lastItemReachedListener, OnVideoSelect onVideoSelect) {
        mPlaylistVideos = playlistVideos;
        mListener = lastItemReachedListener;
        this.onVideoSelect = onVideoSelect;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflate a card layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_records, parent, false);
        // populate the viewholder
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        try {
            if (mPlaylistVideos.size() == 0) {
                return;
            }

            final Video video = mPlaylistVideos.get(position);
            final VideoSnippet videoSnippet = video.getSnippet();
            final VideoContentDetails videoContentDetails = video.getContentDetails();
            final VideoStatistics videoStatistics = video.getStatistics();

            holder.videoTitle.setText(videoSnippet.getTitle());
            //holder.descTxv.setText(videoSnippet.getDescription());
            //holder.mDescriptionText.setText(videoSnippet.getDescription());

            holder.titleRlay.setOnClickListener(v -> {
                onVideoSelect.onVideoSelect(video.getId(), videoSnippet.getTitle(), videoSnippet.getDescription());
            });

            // load the video thumbnail image
            Glide.with(holder.mContext)
                    .load(videoSnippet.getThumbnails().getHigh().getUrl())
                    .placeholder(R.drawable.video_placeholder)
                    .into(holder.videoThumbnail);

            // set the click listener to play the video
            holder.videoThumbnail.setOnClickListener(view -> {
                //holder.mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + video.getId())));
                String url = "https://www.youtube.com/watch?v=" + video.getId();
                onVideoSelect.onVideoSelect(video.getId(), videoSnippet.getTitle(), videoSnippet.getDescription());
            });

            // create and set the click listener for both the share icon and share text
            View.OnClickListener shareClickListener = view -> {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Watch \"" + videoSnippet.getTitle() + "\" on YouTube");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://www.youtube.com/watch?v=" + video.getId());
                sendIntent.setType("text/plain");
                holder.mContext.startActivity(sendIntent);
            };

            holder.moreButton.setOnClickListener(shareClickListener);
            //holder.mShareText.setOnClickListener(shareClickListener);

            // set the video duration text
            holder.videoDutationText.setText(parseDuration(videoContentDetails.getDuration()));
            // set the video statistics
            holder.viewsText.setText(sFormatter.format(videoStatistics.getViewCount()) + " Views");
            holder.addedTime.setText(videoStatistics.getLikeCount() + " Likes");
            //holder.mLikeCountText.setText(sFormatter.format(videoStatistics.getLikeCount()));
            //holder.mDislikeCountText.setText(sFormatter.format(videoStatistics.getDislikeCount()));

            if (mListener != null) {
                // get the next playlist page if we're at the end of the current page and we have another page to get
                final String nextPageToken = mPlaylistVideos.getNextPageToken();
                if (!isEmpty(nextPageToken) && position == mPlaylistVideos.size() - 1) {
                    holder.itemView.post(new Runnable() {
                        @Override
                        public void run() {
                            mListener.onLastItem(position, nextPageToken);
                        }
                    });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("PLAYLIS", e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return mPlaylistVideos.size();
    }

    private boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    private String parseDuration(String in) {
        boolean hasSeconds = in.indexOf('S') > 0;
        boolean hasMinutes = in.indexOf('M') > 0;

        String s;
        if (hasSeconds) {
            s = in.substring(2, in.length() - 1);
        } else {
            s = in.substring(2);
        }

        String minutes = "0";
        String seconds = "00";

        if (hasMinutes && hasSeconds) {
            String[] split = s.split("M");
            minutes = split[0];
            seconds = split[1];
        } else if (hasMinutes) {
            minutes = s.substring(0, s.indexOf('M'));
        } else if (hasSeconds) {
            seconds = s;
        }

        // pad seconds with a 0 if less than 2 digits
        if (seconds.length() == 1) {
            seconds = "0" + seconds;
        }

        return minutes + ":" + seconds;
    }
}
