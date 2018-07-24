package avalony.swap;

import java.util.Random;

/**
 * 快速排序 冒泡改进版 O(nlogn) 分治思想
 * 核心在于基准关键值的选取,可以固定切分,可以取中值,可以随机
 * 前后下标移动的起始跟基准关键值的下标有关系
 */
public class QuicklySort {
    /**
     * 这个思路是首先移动尾部下标,所以把选取的基准值放在头部
     * @param sort
     * @param low
     * @param high
     * @param isAsc
     */
    public static void quicklySort(int[] sort,int low,int high,boolean isAsc){
        if(low >= high){
            return;
        }
        int i = low;
        int j = high;
        int mid = selectedOptimized(low,high,3);
        int key = sort[mid];//固定基准值取第一个元素
        while(i<j){
            if(isAsc) {
                while (sort[i] < key) {
                    i++;
                }
                while (sort[j] > key) {
                    j--;
                }
            }else{
                while (sort[i] > key) {
                    i++;
                }
                while (sort[j] < key) {
                    j--;
                }
            }

            if(i<=j){
                int temp = sort[j];
                sort[j] = sort[i];
                sort[i] = temp;
                i++;
                j--;
            }
        }
//        sort[i] = key;
        quicklySort(sort,low,j,isAsc);
        quicklySort(sort,i,high,isAsc);
    }

    private static int selectedOptimized(int low, int high, int strategy) {
        switch(strategy){
            case 1:
                //基准数选尾部
                return high;
            case 2:
                //中枢轴
                int mid = low+(high-low)/2;
                return mid;
            case 3:
                //随机切分
                Random r = new Random();
                int k = r.nextInt(high)%(high-low+1)+low;
                return k;
            default:
                //默认固定选首部元素切分
                return low;

        }
    }

    public static void main(String[] args){
        int[] s = {2,35,1,6,12,6,213,6,21,123,6,1,31,123,78};
        quicklySort(s,0,s.length-1,false);
        System.out.println(s);
    }
}
