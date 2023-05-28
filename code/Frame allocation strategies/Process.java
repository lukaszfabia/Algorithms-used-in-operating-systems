import java.util.*;

public class Process {
    ArrayList<Page> frames = new ArrayList<>();
    ArrayList<Page> requests;
    private final int PFrame;
    private int pageFaultFrequency = 0;
    private int frameSize;
    private int processId;

    public Process(ArrayList<Page> requests, int PFrame) {
        this.PFrame = PFrame;
        this.requests = requests;
    }

    public Process(Process p) {
        this.requests = p.requests;
        this.PFrame = p.PFrame;
    }

    public Process (ArrayList<Page> requests, int PFrame, int processId){
        this.PFrame = PFrame;
        this.requests = requests;
        this.processId=processId;
    }

    public int getProcessId() {
        return processId;
    }

    public void setFrame(int frameSize) {
        this.frameSize = frameSize;
    }

    public int getPFrame() {
        return PFrame;
    }

    public int getPageFaultFrequency() {
        return pageFaultFrequency;
    }

    public int getFrameSize() {
        return frameSize;
    }

    public int lru(ArrayList<Page> pageReferences) {
        int faults = 0;
        ArrayList<Page> req2 = new ArrayList<>(pageReferences);
        Page temp = req2.get(0);
        boolean done = false;

        if (frames.size() < frameSize) { // when there are free frames
            for (Page frame : frames) {
                if (frame.getNr() == temp.getNr()) { // page in frame -> no error
                    frame.setRef(frame.getRef() + 1);
                    done = true;
                    break;
                }
            }
            if (!done) { // page not in frames -> add to free frame
                faults++;
                frames.add(temp);
            }
        } else { // no free frames
            for (Page frame : frames) {
                if (frame.getNr() == temp.getNr()) { // page in frame -> no error
                    frame.setRef(frame.getRef() + 1);
                    done = true;
                    break;
                }
            }
            if (!done) { // page not in frames -> replace page
                frames.sort(Comparator.comparingInt(Page::getRef));
                frames.remove(0);
                frames.add(temp);
                faults++;
            }
        }
        pageFaultFrequency += faults;
        return faults;
    }

}
