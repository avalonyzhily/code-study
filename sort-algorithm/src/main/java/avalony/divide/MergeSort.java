package avalony.divide;

/**
 * 归并排序 分治法
 */
public class MergeSort {

    public static void mergeSort(int[] sort,int low,int high,boolean isAsc){
        if(low >= high){
            return;
        }
        int mid =  low + (high - low)/2;//防止大数溢出
        mergeSort(sort,low,mid,isAsc);
        mergeSort(sort,mid+1,high,isAsc);
        merge(sort,low,mid,high,isAsc);
    }

    private static void merge(int[] sort, int low, int mid, int high, boolean isAsc) {
        int[] temp = new int[high-low+1];
        int i = low;
        int j = mid + 1;
        int k = 0;
        while(i<=mid && j<=high){
            if(isAsc) {
                //此处加入等于号,可以保证稳定性
                if (sort[i] <= sort[j]) {
                    temp[k++] = sort[i++];
                } else {
                    temp[k++] = sort[j++];
                }
            }else{
                if (sort[i] >= sort[j]) {
                    temp[k++] = sort[i++];
                } else {
                    temp[k++] = sort[j++];
                }
            }
        }
        while(i<=mid){
            temp[k++] = sort[i++];
        }
        while(j<=high){
            temp[k++] = sort[j++];
        }

        for(int k1 = 0;k1<temp.length;k1++){
            sort[k1+low] = temp[k1];
        }
    }

    public static void main(String[] args){
        int[] s = {2,35,1,6,12,6,213,6,21,123,6,1,31,123,78};
        mergeSort(s,0,s.length-1,false);
        System.out.println(s);
    }
}
