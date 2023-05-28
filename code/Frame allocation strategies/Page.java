public class Page {
    private final int nr;
    private final int process;
    private int ref;

    public Page(int nr, int ref, int process) {
        this.nr = nr;
        this.process = process;
        this.ref = ref;
    }

    public void setRef(int ref) {
        this.ref = ref;
    }

    public int getNr() {
        return nr;
    }

    public int getProcess() {
        return process;
    }

    public int getRef() {
        return ref;
    }

    @Override
    public String toString() {
        return "process: "+getProcess()+" id : "+getNr()+" ref: "+getRef();
    }
}