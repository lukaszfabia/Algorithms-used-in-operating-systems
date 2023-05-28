public class Demo {
    //zalozenia symulacji
    static int range = 500; // zakres z ktorego bedziemy losowali id stron
    static int lastRefs = 20; //ilosc ostatnich odwolan
    static int defaultFrameSize = 150; // domyslny rozmiar ramki

    public static void main(String[] args) {
        //test1();
        test2();
        test3();
        test4();
    }

    public static void test1(){
        for (int i = 0; i < 5; i++) {
            FrameAllocationStrategy results = new FramesAlgo(defaultFrameSize, 10000, range, 13);
            results.showResults(40);
            System.out.println("-----------------");
        }
    }

    public static void test2(){
        int frameSize = 150;
        int pagesNr= 5000;
        int range = 250;
        int processAmount = 20;
        FrameAllocationStrategy results = new FramesAlgo(frameSize, pagesNr, range, processAmount);
        results.showResults(40);
        System.out.println("-----------------");
    }

    public static void test3(){
        int frameSize = 200;
        int pagesNr= 12000;
        int range = 500;
        int processAmount = 100;
        FrameAllocationStrategy results = new FramesAlgo(frameSize, pagesNr, range, processAmount);
        results.showResults(10);
        System.out.println("-----------------");
    }

    public static void test4(){
        FramesAlgo a = new FramesAlgo(defaultFrameSize, 10000, 250, 13);
        a.propModded();
    }
}