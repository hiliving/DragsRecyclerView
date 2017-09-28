package dev.yong.com.mrecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DragRecyclerView recyclerView;
    private List<MainInfo> list;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (DragRecyclerView) findViewById(R.id.recycleview);
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MainInfo info = new MainInfo();
            info.setContent("这是内容"+i);
            list.add(info);
        }
        adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
