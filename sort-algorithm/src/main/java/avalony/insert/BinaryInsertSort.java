package avalony.insert;

/**
 * 二分插入排序 O(n^2) 直接插入排序查找改进版
 * 直接插入排序的基础上用二分查找法寻找插入位置
 */
public class BinaryInsertSort {

    public static void binaryInsertSort(int[] sort,boolean isAsc){
        int l = sort.length;
        for(int i=1;i<l;i++) {
            int low = 0;
            int high = i - 1;
            int temp = sort[i];
            while (low <= high) {
                int mid = (low+high)/2;
                if(isAsc) {
                    if (temp > sort[mid]) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }else{
                    if (temp < sort[mid]) {
                        low = mid + 1;
                    } else {
                        high = mid - 1;
                    }
                }
            }
            for(int j = i;j>low;j--){
                sort[j] = sort[j-1];
            }
            sort[low] = temp;
        }
    }

    public static void main(String[] args){
        int[] s = {2,35,1,6,12,6,213,6,21,123,6,1,31,123,78};
            binaryInsertSort(s, true);
        System.out.println(s);
    }
}
