package avalony.insert;

/**
 * 直接插入排序 O(n^2)
 * 将待排序的数通过反向的比较插入已排序的序列中
 */
public class DirectInsertSort {

    public static void directInertSort(int[] sort,boolean isAsc){
        int l = sort.length;
        for(int i=1;i<l;i++){
            int temp = sort[i];
            int j = 0;
            for(j = i-1;j>=0;j--){
                if(isAsc){
                    if(temp < sort[j]){
                        sort[j+1] = sort[j];
                    }else{
                        break;
                    }
                }else{
                    if(temp > sort[j]){
                        sort[j+1] = sort[j];
                    }else{
                        break;
                    }
                }
            }
            sort[j+1] = temp;
        }
    }

    public static void main(String[] args){
        int[] s = {2,35,1,6,12,6,213,6,21,123,6,1,31,123,78};
        directInertSort(s, true);
        System.out.println(s);
    }
}
