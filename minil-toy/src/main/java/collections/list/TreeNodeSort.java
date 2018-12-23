package collections.list;

@FunctionalInterface
public interface TreeNodeSort<T,R extends Comparable> {
    /**
     * 获取节点排序字段值
     * @param t
     * @return
     */
    R get(T t);
}
