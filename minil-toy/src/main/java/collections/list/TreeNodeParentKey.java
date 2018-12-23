package collections.list;

@FunctionalInterface
public interface TreeNodeParentKey<T,R> {
    /**
     * 获取节点父id的方法
     * @param t
     * @return
     */
    R get(T t);
}
