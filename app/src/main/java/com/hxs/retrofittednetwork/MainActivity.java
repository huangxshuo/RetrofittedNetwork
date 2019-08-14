package com.hxs.retrofittednetwork;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.hxs.retrofittednetwork.Bean.MainBean;
import com.hxs.retrofittednetwork.adapter.MainListAdapter;
import com.hxs.retrofittednetwork.manager.NetworkManager;
import com.hxs.retrofittednetwork.response.BaseResponse;
import com.hxs.retrofittednetwork.rx.BaseObserver;
import com.hxs.retrofittednetwork.rx.RxHelper;
import com.hxs.retrofittednetwork.rx.RxManager;

public class MainActivity extends AppCompatActivity {

	private RxManager rxManager = new RxManager();
	private MainListAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RecyclerView recyclerView = findViewById(R.id.rv);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		adapter = new MainListAdapter();
		recyclerView.setAdapter(adapter);
	}

	public void get(View v) {
		NetworkManager.getInstance()
				.getApiService()
				.getHomeList(0)
				.compose(RxHelper.<BaseResponse<MainBean>>applySchedulers())
				.subscribe(new BaseObserver<MainBean>(rxManager) {
					@Override
					protected void onSuccess(MainBean mainBean) {
						adapter.setNewData(mainBean.getDatas());
					}

					@Override
					protected void onFail(String errMsg) {
						Toast.makeText(MainActivity.this, errMsg, Toast.LENGTH_SHORT).show();
					}
				});
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		rxManager.unSubscribe();
	}
}
