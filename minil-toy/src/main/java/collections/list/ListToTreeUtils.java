package collections.list;

import java.util.*;

public class ListToTreeUtils {
    public static List<Map<String,Object>> listToTree(String rootId,String pKeyField,String keyField,String childrenKeyField,List<Map<String,Object>> needToTreeList){
        return listToTree(rootId,pKeyField,keyField,childrenKeyField,null,needToTreeList);
    }
    /**
     * 树形数据组装Map泛型版
     * @param rootId 起始父节点id
     * @param pKeyField 父节点id字段名
     * @param keyField 本节点id字段名
     * @param needToTreeList 待转换的数据集合
     * @return
     */
    public static List<Map<String,Object>> listToTree(String rootId,String pKeyField,String keyField,String childrenKeyField,String orderKey,List<Map<String,Object>> needToTreeList){
        //以父节点id为key来把数据分类
        Map<String,List<Map<String,Object>>> treeNodeMapSupSub = classifyDataToSupSub(needToTreeList,pKeyField);
        //根据起始父节点id来组装树
        if(treeNodeMapSupSub == null){
            return null;
        }
        // 首先找到sysParentId为-1的项作为根目录
        List<Map<String,Object>> rootTreeNodeList = treeNodeMapSupSub.get(rootId);
        if(rootTreeNodeList!=null&&treeNodeMapSupSub.size()>0){
            return createTree(rootTreeNodeList,treeNodeMapSupSub,keyField,childrenKeyField,orderKey);
        }
        return null;
    }

    private static Map<String,List<Map<String,Object>>> classifyDataToSupSub(List<Map<String,Object>> needToTreeList, String pKeyField) {
        if (needToTreeList != null && needToTreeList.size() > 0) {
            // sysParentId,value的为同一父菜单的子菜单的集合
            Map<String, List<Map<String,Object>>> treeNodeMapSupSub = new HashMap<>();
            for (Map<String,Object> map : needToTreeList) {
                String sysParentId = (String)map.get(pKeyField);
                List<Map<String,Object>> tempSubList = treeNodeMapSupSub.get(sysParentId);
                if (tempSubList != null) {
                    tempSubList.add(map);
                } else {
                    tempSubList = new ArrayList<>();
                    tempSubList.add(map);
                }
                treeNodeMapSupSub.put(sysParentId, tempSubList);
            }
            return treeNodeMapSupSub;
        }
        return null;
    }
    private static List<Map<String,Object>> createTree(List<Map<String,Object>> rootTreeNodeList, Map<String, List<Map<String,Object>>> treeNodeMapSupSub,String keyField,String childrenKeyField,String orderKey) {
        for(Map<String,Object> treeNode : rootTreeNodeList){
            String sysId = (String)treeNode.get(keyField);
            List<Map<String,Object>> subTreeNodeList = treeNodeMapSupSub.get(sysId);
            if(subTreeNodeList!=null&&subTreeNodeList.size()>0){
                subTreeNodeList = createTree(subTreeNodeList,treeNodeMapSupSub,keyField,childrenKeyField,orderKey);
                treeNode.put(childrenKeyField,subTreeNodeList);
            }
        }
        if(orderKey!=null){
            Collections.sort(rootTreeNodeList,(o1,o2)->{
                Object obj1 =  o1.get(orderKey);
                Object obj2 =  o2.get(orderKey);
                if(obj1!=null&&obj2!=null) {
                    if (obj1 instanceof Comparable && obj2 instanceof Comparable) {
                        return ((Comparable) obj1).compareTo(obj2);
                    }
                    return 0;
                }else if(obj1==null&&obj2==null){
                    return 0;
                }else if(obj1==null){
                    return 1;
                }else {
                    return -1;
                }
            });
        }
        return rootTreeNodeList;
    }

    public static <T,R> List<T> listToTreeGeneral(String rootId, TreeNodeParentKey<T,R> treeNodeParentKey, TreeNodeKey<T,R> treeNodeKey,TreeNodeChildren<T,List> treeNodeChildren, List<T> needToTreeList){
        return listToTreeGeneral(rootId,treeNodeParentKey,treeNodeKey,treeNodeChildren,null, needToTreeList);
    }
    /**
     * 树形数据组装通用
     * @param rootId 起始父节点id
     * @param treeNodeParentKey 获取父节点id的方法
     * @param treeNodeKey 获取本节点id的方法
     * @Param treeNodeChildren 添加子节点集合的方法
     * @param needToTreeList 待转换的数据集合
     * @return
     */
    public static <T,R> List<T> listToTreeGeneral(String rootId, TreeNodeParentKey<T,R> treeNodeParentKey, TreeNodeKey<T,R> treeNodeKey, TreeNodeChildren<T,List> treeNodeChildren, TreeNodeSort<T,? extends Comparable> treeNodeSort,List<T> needToTreeList){
        //以父节点id为key来把数据分类
        Map<R,List<T>> treeNodeMapSupSub = classifyDataToSupSub(needToTreeList,treeNodeParentKey);
        //根据起始父节点id来组装树
        if(treeNodeMapSupSub == null){
            return null;
        }
        // 首先找到sysParentId为-1的项作为根目录
        List<T> rootTreeNodeList = treeNodeMapSupSub.get(rootId);
        if(rootTreeNodeList!=null&&treeNodeMapSupSub.size()>0){

            return createTree(rootTreeNodeList,treeNodeMapSupSub,treeNodeKey,treeNodeChildren,treeNodeSort);
        }
        return null;
    }

    private static <T,R> Map<R,List<T>> classifyDataToSupSub(List<T> needToTreeList, TreeNodeParentKey<T,R> treeNodeParentKey) {
        if (needToTreeList != null && needToTreeList.size() > 0) {
            // sysParentId,value的为同一父菜单的子菜单的集合
            Map<R, List<T>> treeNodeMapSupSub = new HashMap<>();
            for (T t : needToTreeList) {
                R sysParentId = treeNodeParentKey.get(t);
                List<T> tempSubList = treeNodeMapSupSub.get(sysParentId);
                if (tempSubList != null) {
                    tempSubList.add(t);
                } else {
                    tempSubList = new ArrayList<>();
                    tempSubList.add(t);
                }
                treeNodeMapSupSub.put(sysParentId, tempSubList);
            }
            return treeNodeMapSupSub;
        }
        return null;
    }
    private static <T,R> List<T> createTree(List<T> rootTreeNodeList, Map<R, List<T>> treeNodeMapSupSub,TreeNodeKey<T,R> treeNodeKey,TreeNodeChildren<T,List> treeNodeChildren,TreeNodeSort<T,? extends Comparable> treeNodeSort) {
        for(T treeNode : rootTreeNodeList){
            R sysId = treeNodeKey.get(treeNode);
            List<T> subTreeNodeList = treeNodeMapSupSub.get(sysId);
            if(subTreeNodeList!=null&&subTreeNodeList.size()>0){
                subTreeNodeList = createTree(subTreeNodeList,treeNodeMapSupSub,treeNodeKey,treeNodeChildren,treeNodeSort);
                treeNodeChildren.set(treeNode,subTreeNodeList);
            }
        }
        if(treeNodeSort!=null) {
            Collections.sort(rootTreeNodeList, (o1, o2)->{
                Comparable obj1 = treeNodeSort.get(o1);
                Comparable obj2 = treeNodeSort.get(o2);
                if(obj1!=null&&obj2!=null){
                    return  obj1.compareTo(obj2);
                }else if(obj1==null&&obj2==null){
                    return 0;
                }else if(obj1==null){
                    return 1;
                }else {
                    return -1;
                }
            });
        }
        return rootTreeNodeList;
    }
}
