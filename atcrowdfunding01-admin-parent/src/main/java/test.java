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
        String[] array = new String[]{
                "1", "2", "3", "4","1"
        };
        listAll(Arrays.asList(array)," ");

    }

    public static void listAll(List<String> candidate, String prefix){
        System.out.println(prefix);

        for(int i=0; i<candidate.size(); i++){
            List<String> temp = new LinkedList<String>(candidate);//new LinkedList<String>(candidate)---copy candidate
            listAll(temp, prefix+temp.remove(i));
        }
    }

}
