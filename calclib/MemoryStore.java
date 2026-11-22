package calclib;

public class MemoryStore {
    private double memory = 0.0;
    private boolean hasValue = false;

    public void memoryStore(double val) {
        this.memory = val;
        this.hasValue = true;
    }

    public void memoryAdd(double val) {
        this.memory += val;
        this.hasValue = true;
    }

    public void memorySubtract(double val) {
        this.memory -= val;
        this.hasValue = true;
    }

    public double memoryRead() {
        return memory;
    }

    public void memoryClear() {
        this.memory = 0.0;
        this.hasValue = false;
    }

    public boolean hasStoredValue() {
        return hasValue;
    }
}