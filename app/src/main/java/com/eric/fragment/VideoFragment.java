package com.eric.fragment;

import java.util.List;

import com.eric.adapter.VideoListAdapter;
import com.eric.common.MPCommon;
import com.eric.entity.VideoInfo;
import com.eric.entity.Videofile;
import com.eric.mutiplayer.R;
import com.eric.mutiplayer.R.layout;
import com.eric.service.VideoPlayer;
import com.eric.util.VideoUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class VideoFragment extends Fragment {
    public interface OnVideoListener {
         void onChanged(VideoInfo info);
    }

    private List<Videofile> videofiles = null;
    private ListView mVideoList;
    private int listPosition = 0;
    private Button searchBtn;
    private EditText editUrl;
    VideoListAdapter listAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View videoView = inflater.inflate(R.layout.videoplayer, container, false);

        mVideoList = (ListView) videoView.findViewById(R.id.video_list);
        videofiles = VideoUtil.getVideos(getActivity());
        searchBtn = (Button)videoView.findViewById(R.id.btnSearch);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchVideo();
            }
        });

        MPCommon.setVideoList(videofiles);
        mVideoList.setOnItemClickListener(new VideoListItemClickListener());
        listAdapter = new VideoListAdapter(this.getActivity(), videofiles);
        mVideoList.setAdapter(listAdapter);

        return videoView;

    }

    private void searchVideo() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请输入URL：");
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.videodialog,null);
        builder.setView(view);
        editUrl = (EditText)view.findViewById(R.id.editUrl);
        builder.setPositiveButton("播放", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                VideoInfo info = new VideoInfo();
                if (editUrl.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "播放地址不能为空", Toast.LENGTH_SHORT).show();

                } else {
                    info.setUrl(editUrl.getText().toString());
                    ((OnVideoListener) getActivity()).onChanged(info);
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        builder.show();
    }

    private class VideoListItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // TODO Auto-generated method stub
            listPosition = position;
            playVideo(listPosition);
        }

        private void playVideo(int listPosition) {
            // TODO Auto-generated method stub
            Videofile videoInfo = videofiles.get(listPosition);
            VideoInfo vInfo = new VideoInfo();
            vInfo.setTitle(videoInfo.getTitle());
//          vInfo.setCurrentTime(currentTime);
            vInfo.setArtist(videoInfo.getArtist());
            vInfo.setListPosition(listPosition);
            vInfo.setUrl(videoInfo.getUrl());

            ((OnVideoListener) getActivity()).onChanged(vInfo);
        }

    }
}
