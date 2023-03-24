package discPlanning;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        start();
    }
    public static void start(){
        Results r = new Results(4, 500, 5);

        Scanner scan = new Scanner(System.in);
        System.out.print("Insert a amount of simulations: ");
        int n= scan.nextInt();

        for (int i = 1; i <= n; i++) {
            System.out.println(i+". Simulation");
            r.showData();
            System.out.println();
        }
    }
}
