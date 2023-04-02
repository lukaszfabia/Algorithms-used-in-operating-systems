package AlgorithmsForPages;

import java.util.Random;

public class Test {
    public static void main(String[] args) {
        Random r = new Random();
        int [] num = new int[100000];
        for (int i = 0; i < num.length; i++) {
            num[i]=r.nextInt(num.length)+1;
        }
        PageAlgo p = new PageAlgo(num, r.nextInt(99)+1);
        System.out.println(p.fifo());
        System.out.println(p.lru());

    }

}
