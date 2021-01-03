package com.example.hbkjgoa.adapter;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.jydj.SBYJ_Bean;

import java.util.List;


public class SBJY_Adapter2 extends BaseAdapter {
	private Context context; // 运行上下文
	private List<SBYJ_Bean> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	private String spname;
	public final class ListItemView { // 自定义控件集合
		public TextView text01,text02, text03,text04,text05,text06 ,text01_1,gh,text04_1,text04_2;
		public ImageView image;
	}

	public SBJY_Adapter2(Context context, List<SBYJ_Bean> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
		Log.d("yin","listItems:"+listItems);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listItems.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setmes(List<SBYJ_Bean> listItems) {
		this.listItems = listItems;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final int w=position;
		// TODO Auto-generated method stub
		ListItemView listItemView = null;
		if (convertView == null) {



			listItemView = new ListItemView();
			convertView = listContainer.inflate(R.layout.sbjy_item, null);
			// 获取控件对象
			listItemView.text01 = (TextView) convertView.findViewById(R.id.text01);
			listItemView.text02 = (TextView) convertView.findViewById(R.id.text02);
			listItemView.text03 = (TextView) convertView.findViewById(R.id.text03);
			listItemView.text04_1 = (TextView) convertView.findViewById(R.id.text04_1);
			listItemView.text04_2 = (TextView) convertView.findViewById(R.id.text04_2);
			listItemView.gh = (TextView) convertView.findViewById(R.id.gh);
			listItemView.image =  convertView.findViewById(R.id.im1);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}


		spname =context. getSharedPreferences("sdlxLogin", Context.MODE_PRIVATE + Context.MODE_PRIVATE).getString("spname", "");

		listItemView.text01.setText(listItems.get(position).getBookName());
		listItemView.text02.setText(Html.fromHtml("借用人员: <font color='#3592D1'>"+listItems.get(position).getUserName()+"</font> "));
		listItemView.text03.setText(Html.fromHtml("借用时间: <font color='#3592D1'>"+listItems.get(position).getJieShuDate()+"</font> "));
		listItemView.text04_1.setText(Html.fromHtml("确认人: <font color='#3592D1'>"+listItems.get(position).getQRUser()+"</font> "));
		listItemView.text04_2.setText(Html.fromHtml("归还时间: <font color='#3592D1'>"+listItems.get(position).getGuiHuanDate()+"</font> "));



		listItemView.image.setBackgroundResource(R.mipmap.gdzc);
		listItemView.text04_1.setVisibility(View.VISIBLE);
		listItemView.text04_2.setVisibility(View.VISIBLE);



/*		if (spname.equals("周响洲")){
			if (listItems.get(position).getJieHuanState().contains("已归还")){
				listItemView.gh.setVisibility(View.GONE);
			}else {
				listItemView.gh.setVisibility(View.VISIBLE);
			}
		}

		listItemView.gh.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				if(JY_Activity.mm!=null){
					JY_Activity.mm.SBGH(listItems.get(w).getID());
				}



			}
		});*/

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
/*
				LanGongGao email = new LanGongGao();
				email=listItems.get(position);
				Intent intent = new Intent();
				intent.setClass(context, DZGGInfo3_1.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("info", email);
				intent.putExtras(bundle);
				context.startActivity(intent);*/
			}
		});
		return  convertView;
	}

}
