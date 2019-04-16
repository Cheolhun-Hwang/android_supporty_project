package com.hooneys.supporty.ListPack.MySchedule;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hooneys.supporty.MyRoom.Scheduler.MySchedules;
import com.hooneys.supporty.R;

import java.util.List;

public class MyScheduleAdaptor extends RecyclerView.Adapter {
    private List<MySchedules> list;
    private Context context;

    public MyScheduleAdaptor(List<MySchedules> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_post_it, viewGroup, false);
        MyScheduleHolder holder = new MyScheduleHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MySchedules item = list.get(i);
        if(item != null){
            MyScheduleHolder hold = (MyScheduleHolder) viewHolder;
            hold.title.setText(item.title);
            hold.body.setText(item.body);
            switch (item.sheet){
                case 0: //r
                    hold.sheet.setBackgroundColor(context.getResources().getColor(R.color.post_it_pink));
                    break;
                case 1: //g
                    hold.sheet.setBackgroundColor(context.getResources().getColor(R.color.post_it_green));
                    break;
                case 2: //b
                    hold.sheet.setBackgroundColor(context.getResources().getColor(R.color.post_it_blue));
                    break;
                case 3: //y
                    hold.sheet.setBackgroundColor(context.getResources().getColor(R.color.post_it_yellow));
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyScheduleHolder extends RecyclerView.ViewHolder{
        public LinearLayout sheet;
        public TextView title, body;

        public MyScheduleHolder(@NonNull View itemView) {
            super(itemView);
            sheet = (LinearLayout) itemView.findViewById(R.id.item_post_it);
            title = (TextView) itemView.findViewById(R.id.item_post_title);
            body = (TextView) itemView.findViewById(R.id.item_post_body);
        }
    }
}
