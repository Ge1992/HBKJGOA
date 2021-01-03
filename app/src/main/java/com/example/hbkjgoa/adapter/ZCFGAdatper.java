package com.example.hbkjgoa.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.DZGG;
import com.example.hbkjgoa.sjyw.MyGridAdapter_XXFB;
import com.example.hbkjgoa.util.NoScrollGridView;
import com.example.hbkjgoa.util.photo.ImagePagerActivity;
import com.example.hbkjgoa.zcfg.YWFB4;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ZCFGAdatper extends BaseAdapter {
	private Context context; // 运行上下文
	private List<DZGG> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	private String json,uxm;
	public final class ListItemView { // 自定义控件集合
		public ImageView image;
		public TextView text01;
		public TextView text02;
		public TextView text03;
		public TextView text04;
		public LinearLayout dzgg_R1;
		public NoScrollGridView detail;
	}

	public ZCFGAdatper(Context context, List<DZGG> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
	}

	@Override
	public int getCount() {
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		return listItems.get(position);
	}

	public void setmes(List<DZGG> listItems) {
		this.listItems = listItems;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@SuppressLint("SetTextI18n")
	@SuppressWarnings("unused")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final int w=position;
		ListItemView listItemView = null;
		Set<String> pic = new HashSet<>();
		String[] strArray = new String[20];
		int x = 0;
		pic = getImgStr(listItems.get(position).getContentStr().replace("http://218.92.191.44:4888/", "http://218.92.191.44:8016/").replace("http://218.92.191.44:8016http://218.92.191.44:8016", "http://218.92.191.44:8016"));
		if (!pic.isEmpty()) {	
			
			for (String str : pic) {
				//System.out.println(str);
				strArray[x++] = str;
			}
		}
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = listContainer.inflate(R.layout.dzgg_list3, null);
			// 获取控件对象
			listItemView.image = (ImageView) convertView.findViewById(R.id.imageItem);
			listItemView.text01 = (TextView) convertView.findViewById(R.id.text01);
			listItemView.text04 = (TextView) convertView.findViewById(R.id.text04);
			listItemView.dzgg_R1 = (LinearLayout) convertView.findViewById(R.id.dzgg_R1);
			listItemView.detail = (NoScrollGridView) convertView.findViewById(R.id.gridView);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		if (position % 2 == 0) {

			listItemView.dzgg_R1.setBackgroundColor(Color.WHITE);
		} else {
			listItemView.dzgg_R1.setBackgroundColor(Color.rgb(235, 246, 252));
		}

		listItemView.text01.setText(listItems.get(position).getTitleStr());
		listItemView.text04.setText("发布人：" + listItems.get(position).getUserName()+"    发布时间：" + listItems.get(position).getTimeStr().substring(0,
				listItems.get(position).getTimeStr().length() - 5));

		if (strArray != null ) {		
			final String TP_urls[] = new String[3];
			final String TP_urls2[] = new String[20];
			for (int i = 0; i < 3; i++) {
				TP_urls[i] = strArray[i];
				TP_urls2[i] = strArray[i];
				//System.out.println(TP_urls[i]);
				
				
			}

			if (TP_urls.length > 0 && TP_urls[0]!=null) {
				
				for (int i = 0; i < 3; i++)
					System.out.println(TP_urls[i]);
				listItemView.detail.setVisibility(View.VISIBLE);
				listItemView.detail.setAdapter(new MyGridAdapter_XXFB(TP_urls, context));
				listItemView.detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					//	imageBrower(position, TP_urls2);

					}
				});
			} else {
				listItemView.detail.setVisibility(View.GONE);
			}

		} else {
			listItemView.detail.setVisibility(View.GONE);
		}
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new DZGG();
				DZGG localGZRZ = listItems.get(w);
				Intent intent = new Intent();
				intent.setClass(context, YWFB4.class);
				Bundle localBundle = new Bundle();
				localBundle.putSerializable("DZGG", localGZRZ);
				intent.putExtras(localBundle);
				context.startActivity(intent);
				
			}
		});
		
		

		return convertView;
	}

	public static Set<String> getImgStr(String htmlStr) {
//      Set<String> pics = new HashSet<>();      无序 
		Set pics = new LinkedHashSet();
		String img = "";
		Pattern p_image;
		Matcher m_image;
		// String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
		String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		
		while (m_image.find()) {
			// 得到<img />数据
			img = m_image.group();
			// 匹配<img>中的src数据
			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
			System.out.println("sssssssssss:"+m);
			while (m.find()) {

				pics.add(m.group(1));
			}
		}
		return pics;
	}

	private void imageBrower(int position, String[] urls) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		context.startActivity(intent);
	}

}
