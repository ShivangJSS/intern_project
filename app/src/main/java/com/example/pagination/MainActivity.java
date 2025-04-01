package com.example.pagination;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    NestedScrollView nestedScrollView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ArrayList<MainData> dataArrayList = new ArrayList<>();
    MainAdapter adapter;

    int page = 1, limit = 10;
    boolean isLoading = false;  // Prevents duplicate requests

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nestedScrollView = findViewById(R.id.scroll_view);
        recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.pgb);

        adapter = new MainAdapter(MainActivity.this, dataArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Load first set of data
        getData(page, limit);

        // Scroll Listener for Pagination
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (!isLoading && scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    isLoading = true;
                    page++;
                    progressBar.setVisibility(View.VISIBLE);
                    getData(page, limit);
                }
            }
        });
    }

    private void getData(int page, int limit) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MainInterface mainInterface = retrofit.create(MainInterface.class);
        int start = (page - 1) * limit;

        Call<List<MainData>> call = mainInterface.getPosts(start, limit);
        call.enqueue(new Callback<List<MainData>>() {
            @Override
            public void onResponse(Call<List<MainData>> call, Response<List<MainData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    progressBar.setVisibility(View.GONE);
                    isLoading = false;

                    dataArrayList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<MainData>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                isLoading = false;
            }
        });
    }
}
