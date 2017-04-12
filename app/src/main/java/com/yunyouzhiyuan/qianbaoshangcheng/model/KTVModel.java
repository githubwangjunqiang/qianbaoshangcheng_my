package com.yunyouzhiyuan.qianbaoshangcheng.model;

import com.yunyouzhiyuan.qianbaoshangcheng.entity.AddFangXing;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.KTVList;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.KTVPrice;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.KTVTimeCount;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.MyOkHttpClent;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by ${王俊强} on 2017/3/24.
 */

public class KTVModel extends IModel {
    /**
     * 45、获取ktv K歌时间属性
     * gets_goods_attribute
     */
    public void getTimeCount(final AsyncCallBack callBack) {
        MyOkHttpClent.newBuilder().url(HttpUrl.gets_goods_attribute).get().build().
                enqueue(new BaseCallback.ComonCallback<KTVTimeCount>() {
                    @Override
                    protected void onSuccess(KTVTimeCount s) {
                        callBack.onSucceed(s.getData());
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onFailure("网络是不是出错了亲");
                    }
                });

    }

    /**
     * 25、商家发布商品时属性列表
     * getSpecList
     * 传入：cat_id3   store_id
     */
    public void getSpecList(String cat_id3, String store_id, final AsyncCallBack callBack) {
        MyOkHttpClent.newBuilder().url(HttpUrl.getSpecList).post().addParam("cat_id3", cat_id3)
                .addParam("store_id", store_id).build().enqueue(new BaseCallback.ComonCallback<KTVList>() {
            @Override
            protected void onSuccess(KTVList ktvList) {
                callBack.onSucceed(ktvList.getData());
            }

            @Override
            protected void onError(int code, String msg) {
                callBack.onError(msg);
            }

            @Override
            protected void onFailure(Call call, IOException e) {
                callBack.onFailure(e.getLocalizedMessage());
            }
        });
    }

    /**
     * 29、商家发布商品时添加的规格
     * addSpecItem
     * 传入：store_id  （店铺名称） spec_id （规格id）   spec_item （属性值）
     * 返回：spec_item_id  属性值id
     */
    public Call addSpecItem(String store_id, String spec_id, String spec_item, final AsyncCallBack callBack) {
        LogUtils.d("商家发布商品时添加的规格参数=" + HttpUrl.addSpecItem + "?store_id=" + store_id
                + "&spec_id=" + spec_id + "&spec_item=" + spec_item);
        return MyOkHttpClent.newBuilder().url(HttpUrl.addSpecItem).post().addParam("store_id", store_id)
                .addParam("spec_id", spec_id).addParam("spec_item", spec_item).build()
                .enqueue(new BaseCallback.ComonCallback<AddFangXing>() {
                    @Override
                    protected void onSuccess(AddFangXing s) {
                        callBack.onSucceed(s.getData().getSpec_item_id());
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onFailure("网络错误");
                    }
                });
    }

    /**
     * 30、商家发布商品时删除的规格
     * delSpecItem
     * 传入:store_id   spec_id    spec_item_id
     */
    public void delSpecItem(String store_id, String spec_id, String spec_item_id, final AsyncCallBack callBack) {
        MyOkHttpClent.newBuilder().url(HttpUrl.delSpecItem).post().addParam("store_id", store_id)
                .addParam("spec_id", spec_id).addParam("spec_item_id", spec_item_id).build().enqueue(
                new BaseCallback.ComonCallback<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if (GetJsonRetcode.getRetcode(s) == 2000) {
                            callBack.onSucceed(GetJsonRetcode.getmsg(s));
                        } else {
                            callBack.onError(GetJsonRetcode.getmsg(s));
                        }
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onFailure("亲，断网络了");
                    }
                }
        );
    }

    /**
     * 34、商家段发布KTV
     * addSpecialGoods
     * 传入：store_id (商铺id)  goods_name   goods_content   cat_id1     cat_id2   cat_id3  （商品分类 ）
     * keywords （请填写商品标签） shop_price  （商品价格 ）
     * market_price  （商品市场价格） distribut（商品分销金额） store_count（请填写商品库存）
     * original_img （请上传商品图片） week （请选择周几） tim （请选时间段）
     * wrap （请选择包）  attr_value(K歌时间  来自45接口)
     */
    public Call addSpecialGoods(String store_id, String goods_name, String goods_content, String cat_id1
            , String cat_id2, String cat_id3, String keywords, String shop_price, String market_price,
                                String distribut, String store_count, String original_img,
                                String week, String tim, String wrap, String attr_value,final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HttpUrl.addSpecialGoods).post().addParam("store_id", store_id)
                .addParam("goods_name", goods_name).addParam("goods_content", goods_content).addParam("cat_id1", cat_id1)
                .addParam("cat_id2", cat_id2).addParam("cat_id3", cat_id3).addParam("keywords", keywords)
                .addParam("shop_price", shop_price).addParam("market_price", market_price).
                        addParam("attr_value",attr_value).addParam("distribut", distribut).addParam("store_count", store_count).addParam("original_img", original_img)
                .addParam("week", week).addParam("tim", tim).addParam("wrap", wrap).build()
                .enqueue(new BaseCallback.ComonCallback<KTVPrice>() {
                    @Override
                    protected void onSuccess(KTVPrice s) {
                        callBack.onSucceed(s);
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onFailure(e.getLocalizedMessage());
                    }
                });
    }

    /**
     * 39、编辑商品库存和价格
     * eidt_addgoods_info
     */

    public Call eidt_addgoods_info(String string, final AsyncCallBack callBack) {
        LogUtils.d("编辑商品库存和价格参数" + string);
        return MyOkHttpClent.newBuilder().url(HttpUrl.eidt_addgoods_info).json(string).build()
                .enqueue(new BaseCallback.ComonCallback<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if (GetJsonRetcode.getRetcode(s) == 2000) {
                            callBack.onSucceed(GetJsonRetcode.getmsg(s));
                        } else {
                            callBack.onError(GetJsonRetcode.getmsg(s));
                        }
                    }

                    @Override
                    protected void onError(int code, String msg) {
                        callBack.onError(msg);
                    }

                    @Override
                    protected void onFailure(Call call, IOException e) {
                        callBack.onFailure("网络错误");
                    }
                });
    }

}