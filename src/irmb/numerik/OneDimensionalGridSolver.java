package irmb.numerik;

/**
 * Created by Sven on 09.11.2016.
 */
public class OneDimensionalGridSolver {

    private OneDimensionalGrid grid;
    private double[] lastStepValues;
    private double[] newStepValues;
    private double exitCondition;

    public OneDimensionalGridSolver(OneDimensionalGrid grid) {
        this.grid = grid;
    }

    public Result[] solve() {
        return null;
    }

    private double calculateDelta() {
        return 0.;
    }

    public void swap() {
        double[] temp = lastStepValues;
        lastStepValues = newStepValues;
        newStepValues = temp;
    }
}
