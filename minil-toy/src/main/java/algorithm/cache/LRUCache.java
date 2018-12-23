package algorithm.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author HeMiaolin
 * @Description
 * @Date 2018/4/23 8:57
 */
public class LRUCache<E> {
    private static class LRUNode<E> {
        private String cacheKey;
        private E cacheValue;
        private LRUNode prev;
        private LRUNode next;

        public LRUNode(String cacheKey, E cacheValue) {
            this.cacheKey = cacheKey;
            this.cacheValue = cacheValue;
        }
    }
    private String leastUserKey;
    private String lastestUserKey;
    private Map<String,LRUNode> cacheMap;
    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cacheMap = new HashMap<>(capacity);
    }
    public void put(String key,E cacheValue){
        if(cacheMap.size()==0){
            //全新的缓存,直接添加节点
            this.leastUserKey = key;
            this.lastestUserKey = key;
            LRUNode<E> lruNode = new LRUNode<>(key,cacheValue);
            cacheMap.put(key,lruNode);
            return;
        }
        if(!cacheMap.containsKey(key)){
            if(capacity==1){
                //就只有一个缓存直接替换
                cacheMap.remove(this.leastUserKey);
                this.leastUserKey = key;
                this.lastestUserKey = key;
                LRUNode lruNode = new LRUNode<>(key,cacheValue);
                cacheMap.put(key,lruNode);
                return;
            }
            //判断缓存容量
            if(cacheMap.size()==capacity){
                //需要删除最近最少使用的节点
                LRUNode<E> leastLruNode = cacheMap.remove(this.leastUserKey);
                LRUNode<E> lessLruNode = leastLruNode.next;
                this.leastUserKey = lessLruNode.cacheKey;
                lessLruNode.prev = null;
                LRUNode<E> lruNode = new LRUNode<>(key,cacheValue);
                LRUNode<E> lastLruNode = cacheMap.get(lastestUserKey);
                lastLruNode.next = lruNode;
                lruNode.prev = lastLruNode;
                this.lastestUserKey = lruNode.cacheKey;
            }
            //添加新的缓存,直接添加到最后
            LRUNode<E> lastLruNode = cacheMap.get(lastestUserKey);
            LRUNode<E> lruNode = new LRUNode<>(key,cacheValue);
            lruNode.prev = lastLruNode;
            lastLruNode.next = lruNode;
            this.lastestUserKey = key;
            cacheMap.put(key,lruNode);
            return;
        }
        if(cacheMap.containsKey(key)){
            //更新已有的缓存
            if(cacheMap.size()==1 || key.equals(lastestUserKey)){
                //就是同一个节点或是直接更新最后一个节点,直接更新
                LRUNode<E> lruNode = cacheMap.get(key);
                lruNode.cacheValue = cacheValue;
                return;
            }
            LRUNode<E> addLruNode = cacheMap.get(key);
            addLruNode.cacheValue = cacheValue;
            if(key.equals(leastUserKey)){
                //修改的是最近最少使用的节点为新的节点
                this.modifyLeastLRU(addLruNode);
                return;
            }else{
                //中间的节点
                this.modifyAnyLRU(addLruNode);
                return;
            }

        }


    }
    public E get(String key){
        if(cacheMap.containsKey(key)){
            if(cacheMap.size()==1 || key.equals(lastestUserKey)){
                //就是同一个节点或是直接更新最后一个节点,直接返回
                LRUNode<E> lruNode = cacheMap.get(key);
                return lruNode.cacheValue;
            }
            LRUNode<E> queryLruNode = cacheMap.get(key);
            if(key.equals(leastUserKey)){
                //修改的是最近最少使用的节点为新的节点
                this.modifyLeastLRU(queryLruNode);
            }else {
                //中间的节点
                //首先要把中间节点的前后节点连接
                this.modifyAnyLRU(queryLruNode);
            }
            return queryLruNode.cacheValue;
        }
        return null;
    }
    private void modifyLeastLRU(LRUNode<E> lruNode) {
        LRUNode<E> lessLruNode = lruNode.next;
        this.leastUserKey = lessLruNode.cacheKey;
        lessLruNode.prev = null;
        //原最近最少使用节点作为最近使用节点追加在最后
        LRUNode<E> lastLruNode = cacheMap.get(lastestUserKey);
        lastLruNode.next = lruNode;
        lruNode.prev = lastLruNode;
        lruNode.next = null;
        this.lastestUserKey = lruNode.cacheKey;
    }

    private void modifyAnyLRU(LRUNode<E> lruNode) {
        //首先要把中间节点的前后节点连接
        LRUNode<E> prevAnyLruNode = lruNode.prev;
        LRUNode<E> nextAnyLruNode = lruNode.next;
        prevAnyLruNode.next = nextAnyLruNode;
        nextAnyLruNode.prev = prevAnyLruNode;
        LRUNode<E> lastLruNode = cacheMap.get(lastestUserKey);
        lastLruNode.next = lruNode;
        lruNode.prev = lastLruNode;
        lruNode.next = null;
        this.lastestUserKey = lruNode.cacheKey;
    }



}
