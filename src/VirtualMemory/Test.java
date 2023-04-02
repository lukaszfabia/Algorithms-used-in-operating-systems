package VirtualMemory;

import java.util.Random;

public class Test {
    public static void main(String[] args) throws CustomException {
        int [] t = {1, 2, 3, 4, 1, 2, 5, 1, 2, 3, 4, 5};
        //PageAlgo p = new PageAlgo(generate(20),3);
        PageAlgo p = new PageAlgo(t,3);
        System.out.println(p.fifo());
        System.out.println(p.lru());
        System.out.println(p.optimal());
        System.out.println(p.random());
        System.out.println(p.approximateLru());
    }

    public static int [] generate(int n){
        Random rand = new Random();
        int [] num = new int[n];
        for (int i = 0; i <n ; i++) {
            num[i]=rand.nextInt(10)+1;
        }
        return num;
    }

}
