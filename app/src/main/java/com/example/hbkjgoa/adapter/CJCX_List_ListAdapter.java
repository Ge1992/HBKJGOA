package com.example.hbkjgoa.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.hbkjgoa.KS.CJCX_Check;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.CJCX;
import com.example.hbkjgoa.util.WebServiceUtil;
import com.example.hbkjgoa.util.photo.ImagePagerActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint({ "InflateParams", "HandlerLeak" })
public class CJCX_List_ListAdapter extends BaseAdapter {
	
	private LayoutInflater listContainer;
	private List<CJCX> listItems;
	private Context context;
	private String json,spname;
	
	private Handler handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if(msg.what==1){
				if(json!=null&&!json.equals("")&&!json.equals("null")){
					
					
					
					
				}
			}
		}
	};

	public final class ListItemView {
		public TextView text01,text02,text03,text04,text04_1,text05,text06,text06_1;
		public ImageView Im1;
	}



	public CJCX_List_ListAdapter(Context context, List<CJCX> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
	}

	@Override
	public int getCount() {
		if(listItems!=null){
			return listItems.size();
		}else{
			return 0;
		}
		
	}

	@Override
	public Object getItem(int position) {
		return null;
	}
	
	public void setmes(List<CJCX> listItems) {
		this.listItems = listItems;
	}

	@Override
	public long getItemId(int position) {
		return 1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		final int w=position;
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = listContainer.inflate(R.layout.cjcx_list_item, null);
			listItemView.text01 = (TextView) convertView.findViewById(R.id.text01);
			listItemView.text02 = (TextView) convertView.findViewById(R.id.text02);
			listItemView.text03 = (TextView) convertView.findViewById(R.id.text03);
			listItemView.text04 = (TextView) convertView.findViewById(R.id.text04);
			listItemView.text04_1 = (TextView) convertView.findViewById(R.id.text04_1);
			listItemView.text05 = (TextView) convertView.findViewById(R.id.text05);
			listItemView.Im1 = (ImageView) convertView.findViewById(R.id.Im1);
			
			
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		
		
		
		listItemView.text01.setText(listItems.get(position).getShiJuanName());
		listItemView.text02.setText(listItems.get(position).getTimeStr());
		
		String aa=listItems.get(position).getZf().substring(0, listItems.get(position).getZf().length()-2);
		listItemView.text03.setText("得分:"+aa+"分");
		listItemView.text04.setText(listItems.get(position).getUserName()+"    "+listItems.get(position).getSJHM());
		listItemView.text04_1.setText(listItems.get(position).getSFZ());
		listItemView.text05.setText(listItems.get(position).getDWMC());

		listItemView.Im1.setImageBitmap(null);
		if(listItems.get(position).getTX()!=null&&!listItems.get(position).getTX().equals("")){
			ImageLoader.getInstance().displayImage(WebServiceUtil.SERVICE_URL2+listItems.get(position).getTX(),listItemView.Im1);
			listItemView.Im1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					imageBrower(0,new String[]{WebServiceUtil.SERVICE_URL2+listItems.get(position).getTX()});
				}
			});
		}


		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {



		/*		new CJCX();
				CJCX localGZRZ = listItems.get(w);
				Intent intent=new Intent(context, SJ_Check2.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("YZM",localGZRZ);
				intent.putExtras(bundle);
				context.startActivity(intent);
*/
				Intent intent=new Intent(context, CJCX_Check.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("YZM",listItems.get(position));
				intent.putExtras(bundle);
				context.startActivity(intent);

			}
		});
		
		return  convertView;
	}



	private void imageBrower(int position, String[] urls) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		context.startActivity(intent);
	}

}
