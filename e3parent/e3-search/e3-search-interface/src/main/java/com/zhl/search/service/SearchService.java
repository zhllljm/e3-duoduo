package com.zhl.search.service;

import com.zhl.pojo.SearchResult;

public interface SearchService {

    SearchResult search(String keyword,int page,int rows) throws Exception;
}
