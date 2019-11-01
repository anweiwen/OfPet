package com.cn.flylo.ofpet.utils.date;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cn.flylo.ofpet.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */

public class AdapterDate extends BaseAdapter {

    private Context context;
    private List<Integer> days = new ArrayList<>();
    //日历数据
    private List<Boolean> status = new ArrayList<>();
    //签到状态，实际应用中初始化签到状态可通过该字段传递
    private OnSignedSuccess onSignedSuccess;
    //签到成功的回调方法，相应的可自行添加签到失败时的回调方法

    private List<Integer> listDay = new ArrayList<>();
    public void setListDay(List<Integer> listDay) {
        this.listDay = listDay;
        notifyDataSetChanged();
    }

    public AdapterDate(Context context) {
        this.context = context;
        int maxDay = DateUtil.getCurrentMonthLastDay();//获取当月天数
        for (int i = 0; i < DateUtil.getFirstDayOfMonth() - 1; i++) {
            //DateUtil.getFirstDayOfMonth()获取当月第一天是星期几，星期日是第一天，依次类推
            days.add(0);
            //0代表需要隐藏的item
            status.add(false);
            //false代表为签到状态
        }
        for (int i = 0; i < maxDay; i++) {
            days.add(i+1);
            //初始化日历数据
            status.add(false);
            //初始化日历签到状态
        }
    }

    public void setData(int maxDay, int firstDay){
        days.clear();
        for (int i = 0; i < firstDay - 1; i++) {
            days.add(0);
            status.add(false);
        }
        for (int i = 0; i < maxDay; i++) {
            days.add(i+1);
            status.add(false);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int i) {
        return days.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            view = LayoutInflater.from(context).inflate(R.layout.item_calendar_date,null);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv = view.findViewById(R.id.tvWeek);
        viewHolder.rlItem = view.findViewById(R.id.rlItem);
        viewHolder.text_this_day = view.findViewById(R.id.text_this_day);

        final int day = days.get(i);
        viewHolder.tv.setText(day+"");
        if(day==0){
            viewHolder.rlItem.setVisibility(View.GONE);
        }else{
            viewHolder.rlItem.setVisibility(View.VISIBLE);
        }

        int thisDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        if(day == thisDay || listDay.contains(day)){
            viewHolder.tv.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.tv.setBackgroundResource(R.drawable.shape_circle);
            viewHolder.tv.setSelected(true);
        }else{
            viewHolder.tv.setTextColor(Color.parseColor("#000000"));
            viewHolder.tv.setBackgroundResource(0);
            viewHolder.tv.setSelected(false);
        }
        if (thisDay == day){
            viewHolder.text_this_day.setVisibility(View.VISIBLE);
        }else{
            viewHolder.text_this_day.setVisibility(View.INVISIBLE);
        }

        viewHolder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listDay.clear();
                listDay.add(day);

                notifyDataSetChanged();

                if (onSignedSuccess != null){
                    onSignedSuccess.onClickDay(day);
                }
            }
        });

        return view;
    }

    class ViewHolder{
        RelativeLayout rlItem;
        TextView tv;
        ImageView ivStatus;
        View text_this_day;
    }

    public void setOnSignedSuccess(OnSignedSuccess onSignedSuccess){
        this.onSignedSuccess = onSignedSuccess;
    }
}
