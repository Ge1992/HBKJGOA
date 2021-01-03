package com.example.hbkjgoa.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.hbkjgoa.KS.SJ_List;
import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.SJ_LB_Bean;

import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB) @SuppressLint({ "InflateParams", "HandlerLeak" })
public class SJ_List_ListAdapter extends BaseAdapter {
	
	private LayoutInflater listContainer;
	private List<SJ_LB_Bean> listItems;
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
	}



	public SJ_List_ListAdapter(Context context, List<SJ_LB_Bean> listItems) {
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
	
	public void setmes(List<SJ_LB_Bean> listItems) {
		this.listItems = listItems;
	}

	@Override
	public long getItemId(int position) {
		return 1;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = listContainer.inflate(R.layout.sj_list_item, null);
			listItemView.text01 = (TextView) convertView.findViewById(R.id.text01);
			listItemView.text02 = (TextView) convertView.findViewById(R.id.text02);
			listItemView.text03 = (TextView) convertView.findViewById(R.id.text03);
			listItemView.text04 = (TextView) convertView.findViewById(R.id.text04);
			listItemView.text04_1 = (TextView) convertView.findViewById(R.id.text04_1);
			listItemView.text05 = (TextView) convertView.findViewById(R.id.text05);
			listItemView.text06 = (TextView) convertView.findViewById(R.id.text06);
			listItemView.text06_1 = (TextView) convertView.findViewById(R.id.text06_1);
			
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		listItemView.text01.setText(listItems.get(position).getShiJuanTitle());
		listItemView.text02.setText(listItems.get(position).getFenLeiShunXu());
		listItemView.text03.setText(listItems.get(position).getKaoShiXianShi()+"分钟");
		listItemView.text04.setText(listItems.get(position).getPanDuanFenShu()+"分/题");
		listItemView.text04_1.setText(listItems.get(position).getDanXuanFenShu()+"分/题");
		listItemView.text05.setText(listItems.get(position).getDuoXuanFenShu()+"分/题");
		listItemView.text06.setText(listItems.get(position).getTianKongFenShu()+"分/题");
		listItemView.text06_1.setText(listItems.get(position).getIFSuiJiChuTi());
		
		convertView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
		/*		if(listItems.get(position).getJianDaFenShu()==1){

					Toast.makeText(context,"您今天已经参加过这门考试，不能再进行考试。", Toast.LENGTH_SHORT).show();
					return;
				}*/

				if(SJ_List.listact!=null){
					SJ_List.listact.takephoto(listItems.get(position));
				}
			}
		});
		

		
		
		return  convertView;
	}
	

}
