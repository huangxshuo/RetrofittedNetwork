package com.hxs.retrofittednetwork.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hxs.retrofittednetwork.Bean.MainListBean;
import com.hxs.retrofittednetwork.R;

import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


	private List<MainListBean> mDataList;

	public void setNewData(List<MainListBean> list) {
		this.mDataList = list;
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main, viewGroup, false);
		return new MainViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
		MainViewHolder mvh = (MainViewHolder) viewHolder;
		MainListBean data = mDataList.get(i);
		mvh.title.setText(data.getTitle());
	}

	@Override
	public int getItemCount() {
		return mDataList == null ? 0 : mDataList.size();
	}


	class MainViewHolder extends RecyclerView.ViewHolder {
		TextView title;

		MainViewHolder(@NonNull View itemView) {
			super(itemView);
			title = itemView.findViewById(R.id.tv_title);
		}
	}
}
