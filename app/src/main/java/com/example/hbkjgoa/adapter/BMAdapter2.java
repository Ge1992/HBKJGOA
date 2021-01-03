package com.example.hbkjgoa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hbkjgoa.R;
import com.example.hbkjgoa.model.TXL_New;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 项目联系Adapter
 *
 * @author George
 */

public class BMAdapter2 extends BaseAdapter {
	private Context context; // 运行上下文
	private List<TXL_New> listItems; // 商品信息集合
	private LayoutInflater listContainer; // 视图容器
	public List<Boolean> mChecked;
	// 存储勾选框状态的map集合
	public Map<Integer, Boolean> isCheck = new HashMap<Integer, Boolean>();
	// 用来控制CheckBox的选中状况
	public final class ListItemView { // 自定义控件集合
		public ImageView image;
		public TextView title;
		public TextView info;
		public TextView detail;
		public RelativeLayout R1;
		public CheckBox c1;
	}

	public BMAdapter2(Context context, List<TXL_New> listItems) {
		this.context = context;
		listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = listItems;
		mChecked = new ArrayList<Boolean>();
		for(int i=0;i<this.listItems.size();i++){
		mChecked.add(false);
		}
		// 默觉得不选中
		initCheck(false);
	}

	// 初始化map集合
	public void initCheck(boolean flag) {
		// map集合的数量和list的数量是一致的
		for (int i = 0; i < listItems.size(); i++) {
			// 设置默认的显示
			isCheck.put(i, flag);
		}
	}

	// 加入数据
	public void addData(TXL_New bean) {
		// 下标 数据
		listItems.add(0, bean);
	}
	public void setmes(List<TXL_New> listItems) {
			this.listItems = listItems;
			mChecked.clear();
			for(int i=0;i<this.listItems.size();i++){
				mChecked.add(false);
			}

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

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final int p=position;
		ListItemView listItemView = null;
		if (convertView == null) {
			listItemView = new ListItemView();
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.item_bm, null);
			// 获取控件对象
			listItemView.image = (ImageView) convertView.findViewById(R.id.im1);
			listItemView.title = (TextView) convertView.findViewById(R.id.tv1);
			listItemView.c1 = (CheckBox) convertView.findViewById(R.id.c1);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// 设置控件集到convertView
		listItemView.c1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox)v;
				isCheck.put(p, cb.isChecked());
			}
		});

		// 勾选框的点击事件
/*		listItemView.c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						// 用map集合保存
						isCheck.put(p, isChecked);
					}
				});*/
		// 设置状态
		if (isCheck.get(position) == null) {
			isCheck.put(position, false);
		}

		listItemView.image.setBackgroundResource(R.mipmap.bm);
		listItemView.title.setText(listItems.get(position).getMC());
		listItemView.c1.setChecked(isCheck.get(position));



		

			
		return  convertView;
	}
	// 全选button获取状态
	public Map<Integer, Boolean> getMap() {
		// 返回状态
		return isCheck;
	}
}
