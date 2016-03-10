package com.eric.adapter;

import java.util.List;

import com.eric.adapter.MusicListAdapter.ViewHolder;
import com.eric.entity.Videofile;
import com.eric.mutiplayer.R;
import com.eric.util.MusicUtil;
import com.eric.util.VideoUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class VideoListAdapter extends BaseAdapter {
    private Context context;
    private List<Videofile> videofiles;
    private Videofile videofile;
    private int pos = -1;

    public VideoListAdapter(Context context, List<Videofile> videofiles) {
        this.context = context;
        this.videofiles = videofiles;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return videofiles.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.video_list_item_layout, null);
            viewHolder.albumImage = (ImageView) convertView.findViewById(R.id.albumImage);
            viewHolder.videoTitle = (TextView) convertView.findViewById(R.id.video_title);
            viewHolder.videoArtist = (TextView) convertView.findViewById(R.id.video_Artist);
            viewHolder.videoDuration = (TextView) convertView.findViewById(R.id.video_duration);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        videofile = videofiles.get(position);
        if (position == pos) {
            viewHolder.albumImage.setImageResource(R.drawable.item);
        } else {
//			Bitmap bitmap = VideoUtil.getArtwork(context, videofile.getId(),videofile.getAlbumId(), true, true);
            Bitmap bitmap = null;
            if (bitmap == null) {
                viewHolder.albumImage.setImageResource(R.drawable.video);
            } else {
                viewHolder.albumImage.setImageBitmap(bitmap);
            }
        }
        viewHolder.videoTitle.setText(videofile.getTitle());
        viewHolder.videoArtist.setText(videofile.getArtist());
        viewHolder.videoDuration.setText(VideoUtil.formatTime(videofile.getDuration()));

        return convertView;
    }

    public class ViewHolder {

        public ImageView albumImage;
        public TextView videoTitle;
        public TextView videoDuration;
        public TextView videoArtist;
    }
}
