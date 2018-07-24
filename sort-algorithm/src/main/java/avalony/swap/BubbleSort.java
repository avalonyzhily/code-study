package avalony.swap;

/**
 * 冒泡排序 O(n^2)
 * 相邻数据的比较交换
 */
public class BubbleSort {

    public static void bubbleSort(int[] sort,boolean isAsc){
        int l = sort.length;
        for(int i = 0 ;i < l ;i++){
            for(int j = 0;j<l-i-1;j++){
                if(isAsc){
                    if(sort[j]>sort[j+1]){
                        int temp = sort[j];
                        sort[j] = sort[j+1];
                        sort[j+1] = temp;
                    }
                }else{
                    if(sort[j]<sort[j+1]){
                        int temp = sort[j];
                        sort[j] = sort[j+1];
                        sort[j+1] = temp;
                    }
                }
            }
        }
    }
    public static void main(String[] args){
        int[] s = {2,35,1,6,12,6,213,6,21,123,6,1,31,123,78};
        bubbleSort(s, true);
        System.out.println(s);
    }
}
