package com.hooneys.supporty.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hooneys.supporty.ListPack.MySchedule.MyScheduleAdaptor;
import com.hooneys.supporty.MyApp.MyApplication;
import com.hooneys.supporty.MyRoom.Scheduler.MyScheduleDB;
import com.hooneys.supporty.MyRoom.Scheduler.MySchedules;
import com.hooneys.supporty.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SchedulerFragment extends Fragment {
    private final String TAG = SchedulerFragment.class.getSimpleName();
    private CalendarView calendarView;
    private TextView notifyNonePost;
    private Button addPostBTN;
    private ProgressBar progressBar;
    private RecyclerView postRecyclerView;

    private List<MySchedules> scList;
    private Thread loadScheduler;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 701:
                    //not null
                    postRecyclerView.getAdapter().notifyDataSetChanged();
                    visibleRecycler();
                    break;
                case 702:
                    //null
                    visibleNotify();
                    break;
                case 799:
                    //error
                    break;
                case 801:
                    //add
                    break;
                case 802:
                    //failed
                    break;
            }
            return true;
        }
    });

    public SchedulerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_scheduler, container, false);
        init(view);
        return view;
    }

    @Override
    public void onDetach() {
        stopThread();
        super.onDetach();
    }

    private void init(View view) {
        //variable
        scList = Arrays.asList();

        //views
        notifyNonePost = (TextView) view.findViewById(R.id.calendar_notify_none_post);
        progressBar = (ProgressBar) view.findViewById(R.id.calendar_notify_progress_bar);
        addPostBTN = (Button) view.findViewById(R.id.calendar_add_post_btn);
        addPostBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        postRecyclerView = (RecyclerView) view.findViewById(R.id.daily_post_recycler_view);
        postRecyclerView.setHasFixedSize(false);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        postRecyclerView.setAdapter(new MyScheduleAdaptor(scList, getContext()));

        calendarView = (CalendarView) view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String date = year+"-"+(month+1)+"-"+dayOfMonth;
                Log.d(TAG, "Selected Date : " + date);
                visibleProgress();
                if(scList != null){
                    scList.clear();
                }
                loadScheduler = initThread(date);
                loadScheduler.start();
            }
        });

    }

    private Thread initThread(final String date) {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                scList = MyScheduleDB
                        .getInstance(getContext())
                        .getMyScheduleDAO()
                        .getScheduleByDates(date);
                if(scList == null){
                    message.what = 702;
                }else{
                    if(scList.size() < 1){
                        message.what = 702;
                    }else{
                        message.what = 701;
                    }
                }
                handler.sendMessage(message);
            }
        });
    }

    private void stopThread(){
        if(loadScheduler != null){
            if(loadScheduler.isAlive()){
                loadScheduler.interrupt();
            }
            loadScheduler = null;
        }
    }

    private void visibleProgress(){
        progressBar.setVisibility(View.VISIBLE);
        notifyNonePost.setVisibility(View.GONE);
        postRecyclerView.setVisibility(View.GONE);
    }

    private void visibleNotify(){
        progressBar.setVisibility(View.GONE);
        notifyNonePost.setVisibility(View.VISIBLE);
        postRecyclerView.setVisibility(View.GONE);
    }

    private void visibleRecycler(){
        progressBar.setVisibility(View.GONE);
        notifyNonePost.setVisibility(View.GONE);
        postRecyclerView.setVisibility(View.VISIBLE);
    }

}
