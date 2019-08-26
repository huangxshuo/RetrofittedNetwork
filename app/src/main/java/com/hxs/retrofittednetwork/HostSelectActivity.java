package com.hxs.retrofittednetwork;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxs.retrofittednetwork.Bean.HostBean;
import com.hxs.retrofittednetwork.adapter.HostListAdapter;
import com.hxs.retrofittednetwork.api.Host;
import com.hxs.retrofittednetwork.manager.NetworkManager;

import java.util.List;

public class HostSelectActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_host_select);
		RecyclerView recyclerView = findViewById(R.id.rv);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		HostListAdapter adapter = new HostListAdapter();
		recyclerView.setAdapter(adapter);
		List<HostBean> list = new Gson().fromJson(Host.HostList, new TypeToken<List<HostBean>>() {
		}.getType());
		adapter.setDataList(list);
		adapter.setOnItemClickListener(new HostListAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(HostBean bean) {
				NetworkManager.getInstance().init(bean.getServiceAddress());
				startActivity(new Intent(HostSelectActivity.this, MainActivity.class));
			}
		});
	}
}
