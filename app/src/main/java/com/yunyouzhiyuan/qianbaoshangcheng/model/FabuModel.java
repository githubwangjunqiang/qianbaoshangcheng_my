package com.yunyouzhiyuan.qianbaoshangcheng.model;

import android.text.TextUtils;

import com.yunyouzhiyuan.qianbaoshangcheng.entity.FoodCuisine;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.HttpUrl;
import com.yunyouzhiyuan.qianbaoshangcheng.entity.ShopLeixing;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.BaseCallback;
import com.yunyouzhiyuan.qianbaoshangcheng.okhttp.MyOkHttpClent;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.GetJsonRetcode;
import com.yunyouzhiyuan.qianbaoshangcheng.uitl.LogUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangjunqiang on 2016/11/25.
 */
public class FabuModel extends IModel {

    /**
     * 发布跑腿
     * 传入：sid  acname  acmobile  gprice   acaddress   remark  gname  gnum
     */
    public void fabupaotui(String sid, String acname, String acmobile, String gprice,
                           String acaddress, String remark, String gname, String gnum, final AsyncCallBack callBack) {
        RequestParams params = new RequestParams(HttpUrl.PAOTUI);
        params.addParameter("sid", sid);
        params.addParameter("acname", acname);
        params.addParameter("acmobile", acmobile);
        params.addParameter("gprice", gprice);
        params.addParameter("acaddress", acaddress);
        params.addParameter("remark", remark);
        params.addParameter("gname", gname);
        params.addParameter("gnum", gnum);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (GetJsonRetcode.getRetcode(result) == 2000) {
                    callBack.onSucceed(GetJsonRetcode.getmsg(result));
                } else {
                    callBack.onError(GetJsonRetcode.getmsg(result));
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                callBack.onError("网络是不是有问题呢？");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                callBack.onError("网络是不是有问题呢？");
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 24、获取商家     商品分类
     * get_scategory
     * 传入：parent_id （默认0）   store_id（店铺id）
     */
    public void getScategory(String parent_id, String store_id, final AsyncCallBack callBack) {
        MyOkHttpClent.newBuilder().url(HttpUrl.get_scategory).post().addParam("parent_id", parent_id)
                .addParam("store_id", store_id).build().enqueue(new BaseCallback.ComonCallback<ShopLeixing>() {
            @Override
            protected void onSuccess(ShopLeixing shopLeixing) {
                List<ShopLeixing.DataBean> data = shopLeixing.getData();
                callBack.onSucceed(data);
            }

            @Override
            protected void onError(int code, String msg) {
                callBack.onError(msg);
            }

            @Override
            protected void onFailure(Call call, IOException e) {
                call.cancel();
                callBack.onFailure(e.getLocalizedMessage());
            }
        });
    }

    /**
     * 21、添加普通商品
     * addCommenGoods
     * 传入：
     * //商品参数
     * store_id  (店铺id)
     * goods_name （商品名称）
     * goods_content （商品详情）
     * cat_id1 （一级分类）
     * cat_id2  （二级分类）
     * cat_id3    （三级分类）
     * store_cat_id1 (店铺一级分类id)
     * store_cat_id2 (店铺二级分类id)
     * keywords   （商品标签）
     * shop_price   （店铺价）
     * market_price  （市场价）
     * cost_price  （成本价）
     * distribut  （分销金额）
     * store_count   （库存）
     * original_img   （原始图片）
     */
    public void toFabuShop(String store_id, String goods_name, String goods_content, String cat_id1, String cat_id2,
                           String cat_id3, String keywords, String shop_price,
                           String market_price, String distribut, String store_count,
                           String original_img, String store_cat_id1, final AsyncCallBack callBack) {
        FormBody.Builder builder = new FormBody.Builder().add("store_id", store_id).add("goods_name", goods_name)
                .add("cat_id1", cat_id1).add("cat_id2", cat_id2).add("cat_id3", cat_id3)
                .add("keywords", keywords).add("shop_price", shop_price).add("market_price", market_price)
                .add("distribut", distribut).add("store_count", store_count)
                .add("original_img", original_img).add("goods_content", goods_content);

        if (!TextUtils.isEmpty(store_cat_id1)) {
            builder.add("store_cat_id1", store_cat_id1);
        }
        Request request = new Request.Builder().url(HttpUrl.addCommenGoods).post(builder.build()).build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiError(null, callBack);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtils.d("添加普通商品" + string);
                if (GetJsonRetcode.getRetcode(string) == 2000) {
                    runOnUiSuccess(GetJsonRetcode.getmsg(string), callBack);
                } else {
                    runOnUiError(GetJsonRetcode.getmsg(string), callBack);
                }
            }
        });
    }

    /**
     * 50、商家端 外卖发布 添加、修改或删除店内分类
     * goods_class_save
     * 添加传入：store_id   cat_name（分类名称）              返回：（cat_id值）
     */
    public Call goods_class_save(String store_id, String cat_name, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HttpUrl.goods_class_save).post()
                .addParam("store_id", store_id).addParam("cat_name", cat_name).build()
                .enqueue(new BaseCallback.ComonCallback<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if (GetJsonRetcode.getRetcodeCode(s)) {
                            callBack.onSucceed(GetJsonRetcode.getname("data", s));
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

    /**
     * 删除传入：store_id   act（值为：del）   cat_id（分类id）
     */
    public void goods_class_delete(String store_id, String act, String cat_id, final AsyncCallBack callBack) {
        LogUtils.d("删除传入参数store_id=" + store_id + "/act=" + act + "/");
        MyOkHttpClent.newBuilder().url(HttpUrl.goods_class_save).post()
                .addParam("store_id", store_id).addParam("act", "del").addParam("cat_id", cat_id).build()
                .enqueue(new BaseCallback.ComonCallback<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if (GetJsonRetcode.getRetcodeCode(s)) {
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

    /**
     * 编辑传入：store_id   cat_name    cat_id
     */
    public Call goods_class_editing(String store_id, String cat_name, String cat_id, final AsyncCallBack callBack) {
        return MyOkHttpClent.newBuilder().url(HttpUrl.goods_class_save).post()
                .addParam("store_id", store_id).addParam("cat_name", cat_name).addParam("cat_id", cat_id).build()
                .enqueue(new BaseCallback.ComonCallback<String>() {
                    @Override
                    protected void onSuccess(String s) {
                        if (GetJsonRetcode.getRetcodeCode(s)) {
                            callBack.onSucceed(GetJsonRetcode.getname("data", s));
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

    /**
     * 51、商家端   发布外卖获取店铺内分类
     * storeGoodsClass
     * 传入：store_id
     */
    public void storeGoodsClass(String store_id, final AsyncCallBack callBack) {
        MyOkHttpClent.newBuilder().url(HttpUrl.storeGoodsClass).post().addParam("store_id", store_id)
                .build().enqueue(new BaseCallback.ComonCallback<FoodCuisine>() {
            @Override
            protected void onSuccess(FoodCuisine s) {
                callBack.onSucceed(s.getData());
            }

            @Override
            protected void onError(int code, String msg) {
                callBack.onError(msg);
            }

            @Override
            protected void onFailure(Call call, IOException e) {
                callBack.onFailure("网络时不时不稳定亲");
            }
        });
    }
}
