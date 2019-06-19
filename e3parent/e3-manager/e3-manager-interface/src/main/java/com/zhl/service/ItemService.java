package com.zhl.service;

import com.zhl.pojo.EasyUIResult;
import com.zhl.pojo.TbItem;
import com.zhl.pojo.TbItemDesc;
import com.zhl.utils.E3Result;

public interface ItemService {

    /**
     * 根据商品ID查询商品信息
     *
     * @param itemId
     * @return
     */
    public TbItem getItemById(long itemId);

    /**
     * 查询商品列表
     *
     * @param page
     * @param rows
     * @return
     */
    public EasyUIResult getItemList(int page, int rows);

    /**
     * 添加商品
     *
     * @param item
     * @param desc
     * @return
     */
    public E3Result addItem(TbItem item, String desc);

    /**
     * 商品编辑-查看描述
     *
     * @param itemId
     * @return
     */
    public TbItemDesc findTbItemDescById(long itemId);

    /**
     * 商品编辑-查看
     *
     * @param itemId
     * @return
     */
    public TbItem findTbItemById(long itemId);

    /**
     * 编辑商品
     * @param tbItem
     * @param desc
     */
    public E3Result  updateShop(TbItem tbItem,String desc);

    /**
     * 商品删除功能
     *
     * @param ids
     * @return
     */
    public E3Result deleteItem(long[] ids);

    /**
     * 商品下架功能
     *
     * @param ids
     * @return
     */
    public E3Result updateStatusDown(long[] ids);

    /**
     * 商品上架功能
     *
     * @param ids
     * @return
     */
    public E3Result updateStatusUp(long[] ids);
}
