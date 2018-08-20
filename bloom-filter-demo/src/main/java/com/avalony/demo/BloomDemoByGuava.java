package com.avalony.demo;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

public class BloomDemoByGuava {
    public static void main(String[] args){
        //构建布隆过滤器 漏斗类型 待过滤的数据集合长度/(有一个重载,第三个参数是误判率)
        BloomFilter<String>  bfs = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),1000);
        //放入数据
        bfs.put("12313");
        //判断数据是否存在
        bfs.mightContain("123123");
    }
}
