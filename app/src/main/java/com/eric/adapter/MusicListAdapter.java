package com.eric.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.eric.entity.Music;
import com.eric.mutiplayer.R;
import com.eric.util.MusicUtil;



public class MusicListAdapter extends BaseAdapter{
	private Context context;
	private List<Music> musics;
	private Music music;
	private int pos=-1;
	
	public MusicListAdapter(Context context, List<Music> musics){
		this.context=context;
		this.musics=musics;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return musics.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.music_list_item_layout, null);
			viewHolder.albumImage = (ImageView) convertView.findViewById(R.id.albumImage);
			viewHolder.musicTitle = (TextView) convertView.findViewById(R.id.music_title);
			viewHolder.musicArtist = (TextView) convertView.findViewById(R.id.music_Artist);
			viewHolder.musicDuration = (TextView) convertView.findViewById(R.id.music_duration);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder)convertView.getTag();
		}
		music = musics.get(position);
		if(position == pos) {
			viewHolder.albumImage.setImageResource(R.drawable.item);
		} else {
			Bitmap bitmap = MusicUtil.getArtwork(context, music.getId(),music.getAlbumId(), true, true);
			if(bitmap == null) {
				viewHolder.albumImage.setImageResource(R.drawable.music5);
			} else {
				viewHolder.albumImage.setImageBitmap(bitmap);
			}
		}
			viewHolder.musicTitle.setText(music.getTitle());			//��ʾ����
			viewHolder.musicArtist.setText(music.getArtist());		//��ʾ������
			viewHolder.musicDuration.setText(MusicUtil.formatTime(music.getDuration()));//��ʾʱ��
			
			return convertView;
		}
	public class ViewHolder {
		//���пؼ���������
		public ImageView albumImage;	//ר��ͼƬ
		public TextView musicTitle;		//���ֱ���
		public TextView musicDuration;	//����ʱ��
		public TextView musicArtist;	//����������
	}
}
