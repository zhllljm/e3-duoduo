package com.zhl.search.mapper;

import com.zhl.pojo.SearchItem;

import java.util.List;

public interface ItemMapper {

    List<SearchItem> getItemList();
    SearchItem getItemById(long itemId);

}