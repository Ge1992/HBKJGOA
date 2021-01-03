package com.example.hbkjgoa.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.TXL_New2;

import java.util.List;


/**
 * 项目联系Adapter
 *
 * @author George
 */

public class XMLXAdapter3 extends BaseAdapter {
	private Context context; // 运行上下文
	private List<TXL_New2> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	public final class ListItemView { // 自定义控件集合
		public ImageView image;
		public TextView title,title2;
		public TextView info;
		public TextView detail;
		public TextView ztItem;
		public RelativeLayout R1;
	}
	
	public XMLXAdapter3(Context context, List<TXL_New2> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
	}
	@Override
	public int getCount() {
		if (listItems==null) {
			return 0;
		}else {
			return listItems.size();
		}
	}

	@Override
	public Object getItem(int position) {
		
		return null;
	}
	public void setmes(List<TXL_New2> listItems) {
		this.listItems = listItems;
	}
	@Override
	public long getItemId(int position) {
		
		return 0;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int x=position;
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.item_xmlx, null);
			// 获取控件对象
			listItemView.image = (ImageView) convertView.findViewById(R.id.im1);
			listItemView.title = (TextView) convertView.findViewById(R.id.tv1);
			listItemView.title2 = (TextView) convertView.findViewById(R.id.tv2);
			listItemView.R1 =(RelativeLayout) convertView.findViewById(R.id.R1);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		
		
		listItemView.image.setBackgroundResource(R.mipmap.newtx);
	
	//	listItemView.title.setText(listItems.get(position).getChildlist().get(position).getMCString());
		
		
		
			
			listItemView.title.setText(listItems.get(position).getUserName());
			listItemView.title2.setText(listItems.get(position).getShouJi()+"\n"+listItems.get(position).getXiaoLingTong());
			
			
			
			/**
			 * 电话拨打
			 */
			convertView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					/*TXL_New2 bp = new TXL_New2();
				     bp = listItems.get(x);
					Intent intent = new Intent(Intent.ACTION_DIAL);
					Uri data = Uri.parse("tel:" + bp.getShouJi());
					intent.setData(data);
					context.startActivity(intent);*/
					 TXL_New2 bp = new TXL_New2();
				     bp = listItems.get(x);
					AlertDialog.Builder builder = new AlertDialog.Builder(context);
					builder.setTitle("拨打电话");
		            builder.setItems(new String[]{bp.getJiaTingDianHua(),bp.getZhuanYe()}, new DialogInterface.OnClickListener() {
		                 public void onClick(DialogInterface dialog, int which) {
		                	 if(which==0){
		                		 TXL_New2 bp = new TXL_New2();
		    				     bp = listItems.get(x);
		                		 Intent intent = new Intent(Intent.ACTION_DIAL);
		     					Uri data = Uri.parse("tel:" + bp.getJiaTingDianHua());
		     					intent.setData(data);
		     					context.startActivity(intent);
		                	 }else {
		                		 TXL_New2 bp = new TXL_New2();
		    				     bp = listItems.get(x);
		                		 Intent intent = new Intent(Intent.ACTION_DIAL);
		     					Uri data = Uri.parse("tel:" + bp.getZhuanYe());
		     					intent.setData(data);
		     					context.startActivity(intent);
							}
		                	 
		                 }
		            });
		            builder.create().show();
				

				/*	TXL_New2 email = new TXL_New2();
					email = listItems.get(x);
					Intent intent = new Intent();
						intent.setClass(context,InfoGRXX.class);
					Bundle bundle = new Bundle();
					bundle.putSerializable("TXL_New2", email);
					bundle.putString("show","no");
					intent.putExtras(bundle);
					context.startActivity(intent);*/
					
					
				}
			});
			
			
			
		return  convertView;
	}

}
