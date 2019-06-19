package com.zhl.controller;

import com.zhl.pojo.EasyUIResult;
import com.zhl.pojo.TbItem;
import com.zhl.pojo.TbItemDesc;
import com.zhl.service.ItemService;
import com.zhl.utils.E3Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品管理Controller
 * <p>Title: ItemController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p>
 *
 * @version 1.0
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;


    /**
     * 根据ID查询商品信息，返回json数据
     *
     * @param itemId
     * @return
     */
    @RequestMapping("/item/{itemId}")
    @ResponseBody
    private TbItem getItemById(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    /**
     * 查询商品列表
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/item/list")
    @ResponseBody
    public EasyUIResult getItemList(Integer page, Integer rows) {
        EasyUIResult result = itemService.getItemList(page, rows);
        return result;
    }

    /**
     * 添加商品列表
     * @param item
     * @param desc
     * @return
     */
    @RequestMapping(value = "/item/save",method = RequestMethod.POST)
    @ResponseBody
    public E3Result addItem(TbItem item, String desc) {
        E3Result result = itemService.addItem(item,desc);
        return result;
    }

    /*
     * 商品编辑-查看描述
     * @param id
     * @return
     */
    @RequestMapping(value="/rest/item/query/item/desc/{id}")
    @ResponseBody
    public TbItemDesc findTbItemDescById(@PathVariable long id){
        System.out.println(id);
        TbItemDesc result = itemService.findTbItemDescById(id);
        System.out.println(result);
        return result;
    }

    /*
     * 商品编辑-查看
     * @param itemId
     * @return
     */
    @RequestMapping(value="/rest/item/param/item/query/{itemId}")
    @ResponseBody
    public TbItem findTbItemById(@PathVariable long itemId) {
        System.out.println(itemId);
        TbItem result = itemService.findTbItemById(itemId);
        return result;
    }

    @RequestMapping("/rest/item/update")
    @ResponseBody
    public  E3Result updateStore(TbItem tbItem,String desc){
        E3Result result = itemService.updateShop(tbItem,desc);
        return result;

    }

    /**
     * 商品删除功能
     * @param ids
     * @return
     */
    @RequestMapping(value="/rest/item/delete", method=RequestMethod.POST)
    @ResponseBody
    public E3Result delete(long[] ids){
        E3Result result = itemService.deleteItem(ids);
        return result;
    }

    /**
     * 商品下架功能
     * @param ids
     * @return
     */
    @RequestMapping(value= "/rest/item/instock", method=RequestMethod.POST )
    @ResponseBody
    public E3Result updateStatusDown(long[] ids){
        E3Result result = itemService.updateStatusDown(ids);
        return result;
    }

    /**
     * 商品上架功能
     * @param ids
     * @return
     */
    @RequestMapping(value="/rest/item/reshelf", method=RequestMethod.POST )
    @ResponseBody
    public E3Result updateStatusUp(long[] ids){
        E3Result result = itemService.updateStatusUp(ids);
        return result;
    }

}