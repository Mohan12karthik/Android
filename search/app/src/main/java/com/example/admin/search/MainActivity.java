package com.example.admin.search;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    listviewAdapter adapter;
    String[] title;
    String[] topic;
    int[] icon;
    ArrayList<model> arrayList=new ArrayList<model>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();

        listView=(ListView)findViewById(R.id.listview);

        title=new String[]{"MI","MI2","MI3"};
        topic=new String[]{"JC","Tom","Rajini"};
        icon=new int[]{R.drawable.mi,R.drawable.mi,R.drawable.mi};

        for(int i=0;i<title.length;i++){
            model mod=new model(title[i],topic[i],icon[i]);
            arrayList.add(mod);
        }

        adapter=new listviewAdapter(this,arrayList);
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.drop_down,menu);

        MenuItem myActionMenuItem=menu.findItem(R.id.action_search);
        SearchView searchView=(SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    adapter.filter("");
                    listView.clearTextFilter();
                }
                else {
                    adapter.filter(newText);
                }
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
           int id=item.getItemId();

           if(id==R.id.settings){
               return true;
           }

        return super.onOptionsItemSelected(item);
    }
}
