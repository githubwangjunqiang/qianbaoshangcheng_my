package com.yunyouzhiyuan.qianbaoshangcheng.entity;

/**
 * Created by wangjunqiang on 2016/11/21.
 */
public class HttpUrl {
    /**
     * 后台域名
     */
    public static final String URL = "http://120.26.225.230:813/index.php/Api/api/";
    /**
     * 图片显示
     */
    public static final String IMAGE = "http://120.26.225.230:813";
    /**
     * 获取店铺申请信息  修改资料
     */
    public static final String STORSHENQINGINFO = URL + "getstoreinfo";
    /**
     * 修改店铺信息
     */
    public static final String SETSTOR = URL + "editdianpu";
    /**
     * 获取店铺信息
     */
    public static final String STORINFO = URL + "showshopinfo";
    /**
     * 关键字搜索商品
     */
    public static final String STORSOUSUO = URL + "searchByKeyword";
    /**
     * 商品上下架
     */
    public static final String STORSHANGXIAJIA = URL + "son_offsale";
    /**
     * 商家端获取店铺商品信息列表
     * gets_store_goods_lists
     */
    public static final String gets_store_goods_lists = URL + "gets_store_goods_lists";
    /**
     * 发布跑腿
     */
    public static final String PAOTUI = URL + "fexpress";
    /**
     * 申请步骤
     */
    public static final String SHENQINGBUZHOU = URL + "getApplyProcess";
    /**
     * 发布商品
     */
    public static final String FABUSTOR = URL + "fgoods";
    /**
     * 显示店铺申请记录
     */
    public static final String STORJILU = URL + "getStoreApplyStatus";
    /**
     * 注册
     */
    public static final String ZHUCE = URL + "dianpurenzheng";
    /**
     * 获取银行卡名称
     */
    public static final String BANKNAME = URL + "getBankname";
    /**
     * 上传图片
     */
    public static final String ADDIMAGE = URL + "fileUpload";
    /**
     * 获取店铺类型http://120.26.225.230:813/index.php/Api/api/gets_category
     */
    public static final String STOR_LEIXING = URL + "gets_category";
    /**
     * 城市三级联动
     */
    public static final String CITY = URL + "get_area";
    /**
     * 忘记密码 重置密码
     */
    public static final String UNPASSWORD = URL + "schangePassword";
    /**
     * 用户登录
     */
    public static final String LOGIN = URL + "slogin";
    /**
     * 新用户注册
     */
    public static final String REGISTER = URL + "sregister";
    /**
     * 获取验证码
     */
    public static final String CODE = URL + "ssendMsgRegist";
    /**
     * 获取验证码（忘记密码界面）
     */
    public static final String ADDCODE = URL + "ssendVerCode_login";
    /**
     * 11、显示店铺信息
     * showshopinfo
     */
    public static final String showshopinfo = URL + "showshopinfo";
    /**
     * 24、获取商家     商品分类
     * get_scategory
     */
    public static final String get_scategory = URL + "get_scategory";
    /**
     * 26、获取商家申请分类下的子分类   用于绑定经营项目
     * get_bind_scategory
     */
    public static final String get_bind_scategory = URL + "get_bind_scategory";
    /**
     * 27、商家申请经营项目类型
     * apply_business
     */
    public static final String apply_business = URL + "apply_business";
    /**
     * 32、查看店铺是否绑定经营项目
     * get_sis_bind
     */
    public static final String get_sis_bind = URL + "get_sis_bind";
    /**
     * 28、获取商家商品   本店分类
     * get_store_scategory
     */
    public static final String get_store_scategory = URL + "get_store_scategory";
    /**
     * 21、添加普通商品
     * addCommenGoods
     */
    public static final String addCommenGoods = URL + "addCommenGoods";
    /**
     * 25、商家发布商品时属性列表
     * getSpecList
     */
    public static final String getSpecList = URL + "getSpecList";
    /**
     * 29、商家发布商品时添加的规格
     * addSpecItem
     */
    public static final String addSpecItem = URL + "addSpecItem";
    /**
     * 30、商家发布商品时删除的规格
     * delSpecItem
     */
    public static final String delSpecItem = URL + "delSpecItem";
    /**
     * 34、商家段发布KTV
     * addSpecialGoods
     */
    public static final String addSpecialGoods = URL + "addSpecialGoods";
    /**
     * 8、优惠券订单
     * get_coupon_order
     */
    public static final String get_coupon_order = URL + "get_coupon_order";
    /**
     * 39、编辑商品库存和价格
     * eidt_addgoods_info
     */
    public static final String eidt_addgoods_info = URL + "eidt_addgoods_info";
    /**
     * 40、发布活动获取店铺商品列表
     * get_store_goodslist
     */
    public static final String get_store_goodslist = URL + "get_store_goodslist";
    /**
     * 36、商家端发布团购
     * add_group_buying_Goods
     */
    public static final String add_group_buying_Goods = URL + "add_group_buying_Goods";
    /**
     * 37、商家端发布促销
     * publish_discount_Goods
     */
    public static final String publish_discount_Goods = URL + "publish_discount_Goods";
    /**
     * 42、商家端通过订单id查看订单评论
     * get_comment_by_orderid
     */
    public static final String get_comment_by_orderid = URL + "get_comment_by_orderid";
    /**
     * 45、获取ktv K歌时间属性
     * gets_goods_attribute
     */
    public static final String gets_goods_attribute = URL + "gets_goods_attribute";
    /**
     * 47、外卖退款订单
     * gets_return_order
     */
    public static final String gets_return_order = URL + "gets_return_order";
    /**
     *  48、外卖历史订单
     gets_history_order
     */
    public static final String gets_history_order = URL + "gets_history_order";
    /**
     *  44、 获取外卖最新订单
     gets_takeaway_order
     */
    public static final String gets_takeaway_order = URL + "gets_takeaway_order";
    /**
     *  49、商家端 商家接单
     store_order_taking
     */
    public static final String store_order_taking = URL + "store_order_taking";
    /**
     *  50、商家端 外卖发布 添加、修改或删除店内分类
     * goods_class_save
     */
    public static final String goods_class_save = URL + "goods_class_save";
    /**
     *  51、商家端   发布外卖获取店铺内分类
     * storeGoodsClass
     */
    public static final String storeGoodsClass = URL + "storeGoodsClass";
    /**
     *  52、商家端 跑腿发布
     * send_express_order
     */
    public static final String send_express_order = URL + "send_express_order";
    /**
     * 54、获取商家起送价和运费
     get_send_price
     */
    public static final String get_send_price = URL + "get_send_price";
    /**
     * 53、编辑商家起送价和运费
     * edit_send_price
     */
    public static final String edit_send_price = URL + "edit_send_price";
    /**
     * 55、获取店铺待验证订单详情
     verify_consum_list
     */
    public static final String verify_consum_list = URL + "verify_consum_list";
    /**
     * 56、搜索店铺待验证订单详情
     search_verify_consum
     */
    public static final String search_verify_consum = URL + "search_verify_consum";
    /**
     *  57、验证店铺订单
     verify_consum_order
     */
    public static final String verify_consum_order = URL + "verify_consum_order";
    /**
     *  59、商家端  获取店铺余额
     gets_store_money
     */
    public static final String gets_store_money = URL + "gets_store_money";
    /**
     * 60、商家端  获取提现明显
     * store_withdrawals_list
     传入：store_id
     */
    public static final String store_withdrawals_list = URL + "store_withdrawals_list";
    /**
     * 58、店铺申请提现
     store_withdrawals
     */
    public static final String store_withdrawals = URL + "store_withdrawals";
    /**
     * 61、商家端  消息列表
     store_message_list
     */
    public static final String store_message_list = URL + "store_message_list";
    /**
     * 62、商家端  关于我们（H5）
     storeaboutus
     */
    public static final String storeaboutus = URL + "storeaboutus";
    /**
     * 63、商家端   反馈
     feed_back
     */
    public static final String feed_back = URL + "feed_back";
}
