public class Result {
    public Result(int totalStepCount, boolean isStringDerived) {
        this.totalStepCount = totalStepCount;
        this.isStringDerived = isStringDerived;
    }

    public Result(){

    }

    public int getTotalStepCount() {
        return this.totalStepCount;
    }

    public boolean isStringDerived() {
        return this.isStringDerived;
    }

    private int totalStepCount;
    private boolean isStringDerived;

}
