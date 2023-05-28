public interface FrameAllocationStrategy {
    int equalAllocation(); // przydzial rowny
    int proportionalAllocation(); // proporcjonalny
    int zoneModel(int zone); // model strefowy
    int faultRateControl(); //Sterowanie częstością błędów strony
    void showResults(int zone);
}
