package com.dingding.baiduwaimai.Model;

/**
 * Created by Dingding on 2016/1/14.
 */
public class ShangjiaList {
    public int id_ivShangjia;
    public int ivBaiduPeisong_show;
    public String name;
    public String quan, piao, fu, pei;
    public float ratingBarData;
    public String yishou, distance;
    public String qisong, peisong, time;
    public String jian_1, jian_2;

    public ShangjiaList(int id_ivShangjia, int ivBaiduPeisong_show, String name, String quan, String piao,
                        String fu, String pei, double ratingBarData, String yishou, String distance, String qisong, String peisong,
                        String time, String jian_1, String jian_2) {
        super();
        this.id_ivShangjia = id_ivShangjia;
        this.ivBaiduPeisong_show = ivBaiduPeisong_show;//0为不显示，1则显示
        this.name = name;
        this.quan = quan;
        this.piao = piao;
        this.fu = fu;
        this.pei = pei;
        this.ratingBarData = (float) ratingBarData;
        this.yishou = yishou;
        this.distance = distance;
        this.qisong = qisong;
        this.peisong = peisong;
        this.time = time;
        this.jian_1 = jian_1;
        this.jian_2 = jian_2;

    }

}

