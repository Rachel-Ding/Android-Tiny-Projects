package com.dingding.baiduwaimai.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dingding.baiduwaimai.Model.ShangjiaList;
import com.dingding.baiduwaimai.R;

import java.util.List;

/**
 * Created by Dingding on 2016/1/12.
 */
public class MyAdapter extends BaseAdapter {
    private List<ShangjiaList> shangjiaLists;
    private LayoutInflater mInflater;

    public MyAdapter(Context context, List<ShangjiaList> shangjiaLists) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.shangjiaLists = shangjiaLists;
    }

    @Override
    public int getCount() {

        return (shangjiaLists == null) ? 0 : shangjiaLists.size();
    }

    @Override
    public Object getItem(int position) {

        return shangjiaLists.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ShangjiaList shangjiaList = (ShangjiaList) getItem(position);

        ViewHolder holder = null;
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.list_cell_home, null);
            holder.ivShangjia = (ImageView) convertView.findViewById(R.id.ivShangjia);
            holder.ivBaiduPeisong = (ImageView) convertView.findViewById(R.id.ivBaiduPeisong);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder.tvQuan = (TextView) convertView.findViewById(R.id.tvQuan);
            holder.tvPiao = (TextView) convertView.findViewById(R.id.tvPiao);
            holder.tvFu = (TextView) convertView.findViewById(R.id.tvFu);
            holder.tvPei = (TextView) convertView.findViewById(R.id.tvPei);

            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);

            holder.tvYishou = (TextView) convertView.findViewById(R.id.tvYishou);
            holder.tvDistance = (TextView) convertView.findViewById(R.id.tvDistance);
            holder.tvQisong = (TextView) convertView.findViewById(R.id.tvQisong);
            holder.tvPeisong = (TextView) convertView.findViewById(R.id.tvPeisong);
            holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);

            holder.tvJian_1 = (TextView) convertView.findViewById(R.id.tvJian_1);
            holder.tvJian_2 = (TextView) convertView.findViewById(R.id.tvJian_2);

            holder.list_layoutHome_3 = convertView.findViewById(R.id.list_layoutHome_3);
            holder.list_itemHome_1 = convertView.findViewById(R.id.list_itemHome_1);
            holder.list_itemHome_2 = convertView.findViewById(R.id.list_itemHome_2);

            convertView.setTag(holder);
        } else {

            holder = (ViewHolder) convertView.getTag();

        }

        //设置输入内容
        holder.ivShangjia.setImageResource(shangjiaList.id_ivShangjia);
        holder.tvName.setText(shangjiaList.name);
        holder.tvQuan.setText(shangjiaList.quan);
        holder.tvPiao.setText(shangjiaList.piao);
        holder.tvFu.setText(shangjiaList.fu);
        holder.tvPei.setText(shangjiaList.pei);

        holder.ratingBar.setRating((Float) shangjiaList.ratingBarData);

        holder.tvYishou.setText("已售" + shangjiaList.yishou + "份");
        holder.tvDistance.setText(shangjiaList.distance);
        holder.tvQisong.setText("起送￥" + shangjiaList.qisong);
        holder.tvPeisong.setText("配送￥" + shangjiaList.peisong);
        holder.tvTime.setText("平均" + shangjiaList.time + "分钟");

        holder.tvJian_1.setText(shangjiaList.jian_1);
        holder.tvJian_2.setText(shangjiaList.jian_2);


        //内容排布显示设置
        if (holder.tvQuan.getText() == "")
            holder.tvQuan.setVisibility(View.GONE);
        if (holder.tvPiao.getText() == "")
            holder.tvPiao.setVisibility(View.GONE);
        if (holder.tvFu.getText() == "")
            holder.tvFu.setVisibility(View.GONE);
        if (holder.tvPei.getText() == "")
            holder.tvPei.setVisibility(View.GONE);

        if (holder.tvJian_1.getText() == "" && holder.tvJian_1.getText() == "") {
            holder.list_layoutHome_3.setVisibility(View.GONE);
        } else if (holder.tvJian_1.getText() == "") {
            holder.list_itemHome_1.setVisibility(View.GONE);
        } else if (holder.tvJian_2.getText() == "") {
            holder.list_itemHome_2.setVisibility(View.GONE);
        }

        if (shangjiaList.ivBaiduPeisong_show == 0) {
            holder.ivBaiduPeisong.setVisibility(View.GONE);
        }


        return convertView;
    }


    /*避免重复生成,提高ListView效率*/
    class ViewHolder {
        ImageView ivShangjia;
        ImageView ivBaiduPeisong;
        TextView tvName;
        TextView tvQuan, tvPiao, tvFu, tvPei;
        RatingBar ratingBar;
        TextView tvYishou, tvDistance;
        TextView tvQisong, tvPeisong, tvTime;
        TextView tvJian_1, tvJian_2;
        View list_layoutHome_3;
        View list_itemHome_1, list_itemHome_2;
    }

}
