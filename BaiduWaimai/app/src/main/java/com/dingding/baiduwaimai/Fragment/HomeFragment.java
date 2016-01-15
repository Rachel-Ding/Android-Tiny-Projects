package com.dingding.baiduwaimai.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.dingding.baiduwaimai.Adapter.MyAdapter;
import com.dingding.baiduwaimai.Model.ShangjiaList;
import com.dingding.baiduwaimai.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

;

/**
 * 首页界面
 */
public class HomeFragment extends Fragment {
    private GridView gridView;
    private ListView listView;
    private List<ShangjiaList> shangjiaLists;
    private LayoutInflater layoutInflater;
    private MyAdapter myAdapter;
    private SimpleAdapter simpleAdapter;
    private View viewGrid;
    private List<Map<String,Object>> grid_data;
    private int[] grid_icon = new int[] {R.drawable.item1,R.drawable.item2,R.drawable.item3,R.drawable.item4,
            R.drawable.item5,R.drawable.item6,R.drawable.item7,R.drawable.item8};
    private String[] grid_text= new String[]{"餐饮","超市购","水果生鲜","下午茶","土豪特供","新店","百度配送","火锅"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        layoutInflater = LayoutInflater.from(getActivity());


        initGridView();
        //GridView适配器
        simpleAdapter = new SimpleAdapter(getActivity(),grid_data,R.layout.grid_cell_home,new String[]{"image","text"},
                new int[]{R.id.ivGrid,R.id.tvGrid});
        gridView.setAdapter(simpleAdapter);

        listView = (ListView) rootView.findViewById(R.id.lvHome);
//        initAddHeader();
        //其他视图与listView绑定滑动
        listView.addHeaderView(viewGrid);
        initListView();

        //适配器设置
        myAdapter = new MyAdapter(getActivity(), shangjiaLists);
        listView.setAdapter(myAdapter);

        return rootView;
    }

    private void initGridView() {
        //GridView初始化
        viewGrid = layoutInflater.inflate(R.layout.home_heah_view,null);
        gridView = (GridView) viewGrid.findViewById(R.id.gridView);
        grid_data = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < grid_icon.length;++i) {
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("image",grid_icon[i]);
            map.put("text",grid_text[i]);
            grid_data.add(map);
        }
    }



//    /*其他视图与listView绑定滑动*/
//    private void initAddHeader() {
//
//        View view = layoutInflater.inflate(R.layout.home_heah_view, null);
//        listView.addHeaderView(view);
//        listView.addHeaderView(gridView);
//
//    }

    /*listView中添加的项*/
    private void initListView() {
        shangjiaLists = new ArrayList<ShangjiaList>();
        shangjiaLists.add(new ShangjiaList(R.drawable.home_image_1, 1, "汉堡王（新街口一店）", "券", "票", "付", "赔", 4.7, "1378", "990m", "30", "5", "35", "", ""));
        shangjiaLists.add(new ShangjiaList(R.drawable.home_image_2, 1, "必胜客欢乐餐厅（金...", "", "", "付", "赔", 4.2, "221", "1.0km", "20", "12", "41", "", ""));
        shangjiaLists.add(new ShangjiaList(R.drawable.home_image_3, 0, "七德好美食城", "券", "票", "", "", 4.5, "846", "500m", "30", "5", "35", "", ""));
        shangjiaLists.add(new ShangjiaList(R.drawable.home_image_4, 1, "黄焖鸡米饭（青岛路店）", "券", "票", "", "赔", 4.6, "1418", "1.2m", "20", "0", "50", "满30减15元，满50减23元（在线支付专享）", "使用百度钱包多减2元"));
        shangjiaLists.add(new ShangjiaList(R.drawable.home_image_5, 1, "老徽州咸肉菜饭骨头汤（中...", "券", "票", "付", "赔", 4.1, "2842", "1.8km", "20", "1", "60", "满30减15元，满50减23元（在线支付专享）", ""));
        shangjiaLists.add(new ShangjiaList(R.drawable.home_image_6, 1, "德克士（百老汇店）", "券", "票", "付", "赔", 4.7, "611", "900m", "20", "5", "35", "", "使用百度钱包多减2元"));
        shangjiaLists.add(new ShangjiaList(R.drawable.home_image_7, 0, "大牌小厨（新街口店）", "券", "票", "付", "赔", 4.5, "944", "620m", "20", "8", "35", "满30减15元，满50减23元（在线支付专享）", ""));
        shangjiaLists.add(new ShangjiaList(R.drawable.home_image_8, 0, "津津咖喱（金轮店）", "券", "票", "付", "赔", 4.2, "855", "1.8km", "25", "5", "35", "", ""));
        shangjiaLists.add(new ShangjiaList(R.drawable.home_image_9, 1, "和风便当（珠江路）", "券", "票", "付", "赔", 4.8, "695", "800m", "30", "5", "35", "满30减15元，满50减23元（在线支付专享）", "使用百度钱包多减2元"));
    }



}
