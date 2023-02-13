/**
 * @author YUChangcan
 * @date 2023/1/27 - 19:46
 */
import java.util.*;
import java.io.*;
public class test {

    /**
     * @param args
     */
    public static void main(String[] args) {
// TODO Auto-generated method stub
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int temp[] = new int[n];
        for (int i = 0; i < n; i++) {
            temp[i] = scanner.nextInt();
        }
        String[] array = new String[n];
        for (int i = 0; i < n; i++) {
            array[i] = temp[i] + "";
        }
//        String[] array = new String[]{
//                "1", "2", "3", "4","1"
//        };
        listAll(Arrays.asList(array));

    }

    public static void listAll(List<String> candidate){

        for(int i=0; i<candidate.size(); i++){
            List<String> temp = new LinkedList<String>(candidate);//new LinkedList<String>(candidate)---copy candidate
            listAll(temp);
        }
    }

}
