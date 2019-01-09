package com.example.admin.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class listviewAdapter extends BaseAdapter {

    Context mctx;
    LayoutInflater inflater;
    List<model> modellist;
    ArrayList<model> arrayList;

    public listviewAdapter(Context mctx, List<model> modellist) {
        this.mctx = mctx;
        this.modellist = modellist;
        inflater=LayoutInflater.from(mctx);
        this.arrayList=new ArrayList<model>();
        this.arrayList.addAll(modellist);
    }

   public class ViewHolder{
        TextView mtitle,mtopic;
        ImageView micon;

   }

    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int position) {
        return modellist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if(view==null){
            holder=new ViewHolder();
            view=inflater.inflate(R.layout.row,null);

            holder.mtitle=view.findViewById(R.id.name);
            holder.mtopic=view.findViewById(R.id.topic);
            holder.micon=view.findViewById(R.id.img);

            view.setTag(holder);

        }
        else{
            holder=(ViewHolder)view.getTag();
        }
        holder.mtitle.setText(modellist.get(position).getName());
        holder.mtopic.setText(modellist.get(position).getTopic());
        holder.micon.setImageResource(modellist.get(position).getIcon());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }


    public void filter(String chartext){
        chartext=chartext.toLowerCase(Locale.getDefault());
        modellist.clear();
        if(chartext.length()==0){
            modellist.addAll(arrayList);
        }
        else{
            for(model m:arrayList){
                if (m.getName().toLowerCase(Locale.getDefault()).contains(chartext)){
                    modellist.add(m);
                }
            }
        }

        notifyDataSetChanged();
    }

}
