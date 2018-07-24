package avalony.swap;

/**
 * 选择排序 O(n^2)
 * 从第一个下标开始,逐个跟后面的数比较交换,直到把合适的数放置在该下标
 */
public class SelectedSort {

    public static void selectedSort(int[] sort,boolean isAsc){
        int l = sort.length;
        for(int i = 0;i<l;i++){
            for(int j = i+1;j<l;j++){
                if(isAsc){
                    if(sort[i]>sort[j]){
                        int temp = sort[i];
                        sort[i] = sort[j];
                        sort[j] = temp;
                    }
                }else{
                    if(sort[i]<sort[j]){
                        int temp = sort[i];
                        sort[i] = sort[j];
                        sort[j] = temp;
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        int[] s = {2,35,1,6,12,6,213,6,21,123,6,1,31,123,78};
        selectedSort(s, false);
        System.out.println(s);
    }
}
