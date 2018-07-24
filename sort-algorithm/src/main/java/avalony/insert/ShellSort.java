package avalony.insert;

/**
 * 希尔排序 O(nlogn) 直接插入排序(直接步长为1)改进版
 * 按照不同的步长将待排序的序列分割成子序列,然后使用直接插入排序排序,整个序列差不多有序后,在进行一次直接插入排序
 * 步长也有序列,基本原则是当前序列长度二分向下取整,最小步长为1
 */
public class ShellSort {

    public static void shellSort(int[] sort,boolean isAsc){
        int step = sort.length/2;
        while(step >= 1){
            for(int i=step;i<sort.length;i++){
                int temp = sort[i];
                int j = 0;
                for(j = i-step;j >= 0; j -= step){
                    if(isAsc){
                        if(temp < sort[j]){
                            sort[j+step]=sort[j];
                        }else{
                            break;
                        }
                    }else{
                        if(temp > sort[j]){
                            sort[j+step]=sort[j];
                        }else{
                            break;
                        }
                    }
                }
                sort[j+step] = temp;
            }
            step /= 2;
        }
    }

    public static void main(String[] args){
        int[] s = {2,35,1,6,12,6,213,6,21,123,6,1,31,123,78};
        shellSort(s, false);
        System.out.println(s);
    }
}
