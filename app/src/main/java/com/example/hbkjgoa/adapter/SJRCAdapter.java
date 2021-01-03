package com.example.hbkjgoa.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.SJ_Bean;
import com.example.hbkjgoa.rczyk.InfoRCXX2;

import java.util.List;


public class SJRCAdapter extends BaseAdapter {
	private Context context; // 运行上下文
	private List<SJ_Bean> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器

	public final class ListItemView { // 自定义控件集合
		public TextView text01,text02, text03,text04,text05,text06, text01_1;
		public ImageView image;
	}

	public SJRCAdapter(Context context, List<SJ_Bean> listItems) {
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

	public void setmes(List<SJ_Bean> listItems) {
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
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			convertView = listContainer.inflate(R.layout.listitem, null);
			// 获取控件对象
			listItemView.text01 = (TextView) convertView.findViewById(R.id.text01);
			listItemView.text02 = (TextView) convertView.findViewById(R.id.text02);
			listItemView.text03 = (TextView) convertView.findViewById(R.id.text03);
			listItemView.text04 = (TextView) convertView.findViewById(R.id.text04);
			listItemView.text05 = (TextView) convertView.findViewById(R.id.text05);
			listItemView.text06 = (TextView) convertView.findViewById(R.id.text06);
			listItemView.text01_1 = (TextView) convertView.findViewById(R.id.text01_1);
			listItemView.image =  convertView.findViewById(R.id.im1);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}




		if (listItems.get(position).getZiXin().equals("1")){
			listItemView.image.setBackgroundResource(R.mipmap.nsrc);
			listItemView.text01.setText(Html.fromHtml("<font color='#000000'>"+listItems.get(position).getNameStr()+"</font> "));
			listItemView.text02.setText(Html.fromHtml("<font color='#3592D1'>"+listItems.get(position).getAiHao()+"</font> "));
			listItemView.text03.setText(Html.fromHtml("联系方式: <font color='#3592D1'>"+listItems.get(position).getMobTel()+"</font> "));
			listItemView.text06.setText(Html.fromHtml("学历专业: <font color='#3592D1'>"+listItems.get(position).getXueLi()+" - "+listItems.get(position).getZiLi()+"</font> "));
		}else if (listItems.get(position).getZiXin().equals("2")){
			listItemView.image.setBackgroundResource(R.mipmap.zjjg);
			listItemView.text01.setText(Html.fromHtml("<font color='#000000'>"+listItems.get(position).getNameStr()+"</font> "));
			listItemView.text02.setText(Html.fromHtml("<font color='#3592D1'>"+listItems.get(position).getAiHao()+"</font> "));
			listItemView.text03.setText(Html.fromHtml("联系方式: <font color='#3592D1'>"+listItems.get(position).getMobTel()+"</font> "));
			listItemView.text06.setText(Html.fromHtml("学历专业: <font color='#3592D1'>"+listItems.get(position).getXueLi()+" - "+listItems.get(position).getZiLi()+"</font> "));
		}else if (listItems.get(position).getZiXin().equals("3")){
			listItemView.image.setBackgroundResource(R.mipmap.bsdx);
			listItemView.text01.setText(Html.fromHtml("<font color='#000000'>"+listItems.get(position).getNameStr()+"</font> "));
			listItemView.text02.setText(Html.fromHtml("<font color='#3592D1'>"+listItems.get(position).getAiHao()+"</font> "));
			listItemView.text03.setText(Html.fromHtml("职务: <font color='#3592D1'>"+listItems.get(position).getGeXing()+"</font> "));
			listItemView.text06.setText(Html.fromHtml("学历专业: <font color='#3592D1'>"+listItems.get(position).getXueLi()+" - "+listItems.get(position).getZiLi()+"</font> "));
		}else if (listItems.get(position).getZiXin().equals("4")){
			listItemView.image.setBackgroundResource(R.mipmap.qt);
			listItemView.text01.setText(Html.fromHtml("<font color='#000000'>"+listItems.get(position).getNameStr()+"</font> "));
			listItemView.text02.setText(Html.fromHtml("<font color='#3592D1'>"+listItems.get(position).getAiHao()+"</font> "));
			listItemView.text03.setText(Html.fromHtml("职务: <font color='#3592D1'>"+listItems.get(position).getGeXing()+"</font> "));
			listItemView.text06.setText(Html.fromHtml("毕业院校及专业: <font color='#3592D1'>"+listItems.get(position).getZiLi()+"</font> "));
		}else if (listItems.get(position).getZiXin().equals("5")){
			listItemView.image.setBackgroundResource(R.mipmap.xmjd);
			listItemView.text01.setText(Html.fromHtml("<font color='#000000'>"+listItems.get(position).getNameStr()+"</font> "));
			listItemView.text02.setText(Html.fromHtml("<font color='#3592D1'>"+listItems.get(position).getJiGuan()+"</font> "));
			listItemView.text03.setText(Html.fromHtml("期次: <font color='#3592D1'>"+listItems.get(position).getZiLi()+"</font> "));
			listItemView.text06.setText(Html.fromHtml("班级性质: <font color='#3592D1'>"+listItems.get(position).getGeXing()+"</font> "));

		}








		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				new SJ_Bean();
				SJ_Bean localGZRZ = listItems.get(w);
				Intent intent = new Intent();
				intent.setClass(context, InfoRCXX2.class);
				Bundle localBundle = new Bundle();
				localBundle.putSerializable("HD", localGZRZ);
				intent.putExtras(localBundle);
				context.startActivity(intent);

			}
		});
		return  convertView;
	}

}
