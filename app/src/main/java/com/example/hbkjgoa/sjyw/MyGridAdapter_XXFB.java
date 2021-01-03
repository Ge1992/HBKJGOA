package com.example.hbkjgoa.sjyw;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.example.hbkjgoa.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.HashMap;

public class MyGridAdapter_XXFB extends BaseAdapter {
	private String[] files;
	private Context context;
	private LayoutInflater mLayoutInflater;

	public MyGridAdapter_XXFB(String[] files, Context context) {
		this.files = files;
		mLayoutInflater = LayoutInflater.from(context);
		this.context=context;
	}

	@Override
	public int getCount() {
		int cou=files.length;
		if(cou>6){cou=6;}
		return files == null ? 0 : cou;
	}

	@Override
	public String getItem(int position) {
		return files[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		MyGridViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new MyGridViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.gridview_item,
					parent, false);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.album_image);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (MyGridViewHolder) convertView.getTag();
		}
		String url = getItem(position);

		
			ImageLoader.getInstance().displayImage(url, viewHolder.imageView);
		

		return convertView;
	}
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)

	private Bitmap createVideoThumbnail(String url, int width, int height) {
	  Bitmap bitmap = null;
	  MediaMetadataRetriever retriever = new MediaMetadataRetriever();
	  int kind = MediaStore.Video.Thumbnails.MINI_KIND;
	  try {
	   if (Build.VERSION.SDK_INT >= 14) {
	    retriever.setDataSource(url, new HashMap<String, String>());
	   } else {
	    retriever.setDataSource(url);
	   }
	   bitmap = retriever.getFrameAtTime();
	  } catch (IllegalArgumentException ex) {
	   // Assume this is a corrupt video file
	  } catch (RuntimeException ex) {
	   // Assume this is a corrupt video file.
	  } finally {
	   try {
	    retriever.release();
	   } catch (RuntimeException ex) {
	    // Ignore failures while cleaning up.
	   }
	  }
	  if (kind == Images.Thumbnails.MICRO_KIND && bitmap != null) {
	   bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
	     ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
	  }
	  return bitmap;
	 }
	private Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
       	Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;  
	}
	private static class MyGridViewHolder {            
		ImageView imageView;
	}
}
