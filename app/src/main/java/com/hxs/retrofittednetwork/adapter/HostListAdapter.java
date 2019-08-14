package com.hxs.retrofittednetwork.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hxs.retrofittednetwork.Bean.HostBean;
import com.hxs.retrofittednetwork.R;

import java.util.List;

public class HostListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private List<HostBean> mDataList;

	public void setDataList(List<HostBean> mDataList) {
		this.mDataList = mDataList;
		notifyDataSetChanged();
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_host, viewGroup, false);
		return new HostViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
		HostViewHolder hvh = (HostViewHolder) viewHolder;
		HostBean item = mDataList.get(i);
		hvh.hostName.setText(item.getServiceName());
		hvh.hostAddress.setText(item.getServiceAddress());
		hvh.rootView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(mDataList.get(viewHolder.getAdapterPosition()));
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return mDataList == null ? 0 : mDataList.size();
	}


	static class HostViewHolder extends RecyclerView.ViewHolder {

		TextView hostName;
		TextView hostAddress;
		View rootView;

		public HostViewHolder(@NonNull View itemView) {
			super(itemView);
			rootView = itemView;
			hostName = itemView.findViewById(R.id.tv_host_name);
			hostAddress = itemView.findViewById(R.id.tv_host_address);
		}
	}

	private OnItemClickListener mOnItemClickListener;

	public interface OnItemClickListener {
		void onItemClick(HostBean bean);
	}

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mOnItemClickListener = listener;
	}

}
