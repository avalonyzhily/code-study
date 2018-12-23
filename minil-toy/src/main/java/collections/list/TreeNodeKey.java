package collections.list;

@FunctionalInterface
public interface TreeNodeKey<T,R> {
    /**
     * 获取节点自身id的方法
     * @param t
     * @return
     */
    R get(T t);
}
