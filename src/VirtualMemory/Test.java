package VirtualMemory;

import java.util.Random;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws CustomException {
        usersInput();
    }

    public static int[] generate(int n) {
        Random rand = new Random();
        int[] num = new int[n];
        for (int i = 0; i < n; i++) {
            num[i] = rand.nextInt(49) + 1;
        }
        return num;
    }

    public static void usersInput() throws CustomException {
        Scanner scan = new Scanner(System.in);

        System.out.print("How long string have to be: ");
        int n = scan.nextInt();
        System.out.print("Insert amount of frames: ");
        int amountFrames = scan.nextInt();

        for (int i = 1; i <= amountFrames; i++) {
            System.out.print("Size of frames for "+i+": ");
            int numFrames = scan.nextInt();
            PageAlgo p = new PageAlgo(generate(n), numFrames);
            System.out.printf("%-5s %-5d\n", "FIFO:", p.fifo());
            System.out.printf("%-5s %-5d\n", "LRU:", p.lru());
            System.out.printf("%-5s %-5d\n", "ALRU:", p.approximateLru());
            System.out.printf("%-5s %-5d\n", "OPT:", p.optimal());
            System.out.printf("%-5s %-5d\n", "RAND:", p.random());
        }


    }

}
