package com.zhl.cart.service;

import com.zhl.pojo.TbItem;
import com.zhl.utils.E3Result;

import java.util.List;

public interface CartService {
    /**
     * 登录状态
     * 添加购物车
     * @param userId
     * @param itemId
     * @param num
     * @return
     */
    E3Result addCart(long userId, long itemId,int num);

    /**
     * 登录状态
     * 合并购物车
     * @param userId
     * @param itemList
     * @return
     */
    E3Result mergeCart(long userId, List<TbItem> itemList);

    /**
     * 取购物车列表
     * @param userId
     * @return
     */
    List<TbItem> getCartList(long userId);

    /**
     * 更新购物车数量
     * @param userId
     * @param itemId
     * @param num
     * @return
     */
    E3Result updateCartNum(long userId,long itemId,int num);

    /**
     * 删除购物车商品
     * @param userId
     * @param itemId
     * @return
     */
    E3Result deleteCartItem(long userId,long itemId);

    /**
     * 删除购物车信息
     * @param userId
     * @return
     */
    E3Result clearCartItem(long userId);
}
