package com.eric.fragment;

import java.util.List;

import com.eric.activity.AudioActivity;
import com.eric.activity.MainActivity;
import com.eric.adapter.MusicListAdapter;
import com.eric.common.MPCommon;
import com.eric.entity.Music;
import com.eric.entity.MusicInfo;
import com.eric.entity.VideoInfo;
import com.eric.mutiplayer.R;
import com.eric.service.Download;
import com.eric.util.MusicUtil;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MusicFragment extends Fragment {
    public interface OnMusicListener {
        void onChanged(MusicInfo info);
    }

    private ListView mMusiclist;
    MusicListAdapter listAdapter;
    private List<Music> musics = null;
    private int listPosition = 0;

    //    private ImageView musicAlbum;
//    private TextView musicTitle;
    private int currentTime;
    //    private int duration;
    private Button btnMusicSearch;
    private EditText editUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View musicView = inflater.inflate(R.layout.musicplayer, container,
                false);

        mMusiclist = (ListView) musicView.findViewById(R.id.music_list);
        btnMusicSearch = (Button) musicView.findViewById(R.id.btnMusicSearch);
        mMusiclist.setOnItemClickListener(new MusicListItemClickListener());
        btnMusicSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchMusic();
            }
        });

        musics = MusicUtil.getMusics(getActivity());//获取音乐list

        MPCommon.setMusicList(musics);//list设为全局变量

        listAdapter = new MusicListAdapter(this.getActivity(), musics);
        mMusiclist.setAdapter(listAdapter);

        return musicView;
    }

    private void searchMusic() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("请输入URL：");
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.videodialog, null);
        builder.setView(view);
        editUrl = (EditText) view.findViewById(R.id.editUrl);
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (editUrl.getText().toString().length() == 0) {
                    Toast.makeText(getActivity(), "下载地址不能为空", Toast.LENGTH_SHORT).show();

                } else {
                    Download download = new Download();
                    download.download(editUrl.getText().toString());
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

    private class MusicListItemClickListener implements OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            listPosition = position;
            playMusic(listPosition);
        }

        private void playMusic(int listPosition) {
            // TODO Auto-generated method stub
            Music mp3Info = musics.get(listPosition);//获取目标的信息
            //musicTitle.setText(mp3Info.getTitle());
            //Bitmap bitmap = MusicUtil.getArtwork(getActivity(),
            //      mp3Info.getId(), mp3Info.getAlbumId(), true, true);
            //musicAlbum.setImageBitmap(bitmap);
            MusicInfo mInfo = new MusicInfo();
            mInfo.setTitle(mp3Info.getTitle());
            mInfo.setCurrentTime(currentTime);
            mInfo.setArtist(mp3Info.getArtist());
            mInfo.setListPosition(listPosition);
            mInfo.setUrl(mp3Info.getUrl());

            Log.i("TAG", getActivity().toString());

            ((OnMusicListener) getActivity()).onChanged(mInfo);
        }
    }
}
