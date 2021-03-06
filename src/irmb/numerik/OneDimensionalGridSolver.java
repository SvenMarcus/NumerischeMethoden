package irmb.numerik;


/**
 * Created by Sven on 09.11.2016.
 */
public class OneDimensionalGridSolver {

    private OneDimensionalGrid grid;
    private double[] lastStepValues;
    private double[] newStepValues;
    private Function function;
    private double exitCondition;
    private double gridDelta;

    public OneDimensionalGridSolver() {
    }

    public OneDimensionalGridSolver(OneDimensionalGrid grid, Function function) {
        this.grid = grid;
        this.function = function;
        initArrays();
    }

    private void initArrays() {
        int gridNodes = grid.getNumberOfNodes();
        lastStepValues = new double[gridNodes];
        newStepValues = new double[gridNodes];
        lastStepValues[0] = newStepValues[0] = grid.getLeftBoundaryCondition();
        lastStepValues[gridNodes - 1] = newStepValues[gridNodes - 1] = grid.getRightBoundaryCondition();
    }

    public void solve() {
        double delta;
        gridDelta = grid.getGridDelta();
        do {
            delta = 0.;
            for (int i = 1; i < grid.getNumberOfNodes() - 1; i++) {
                calculateNewData(i);
                delta = calculateDelta(delta, i);
            }
            swap();
        } while (delta >= exitCondition);
        grid.setNodeValues(newStepValues);
    }

    private void calculateNewData(int i) {
        double functionValue = function.evaluateAt(grid.getCoordinateOf(i));
        newStepValues[i] = 0.5 * (lastStepValues[i + 1] + lastStepValues[i - 1] - gridDelta * gridDelta * functionValue);
    }

    private double calculateDelta(double lastDelta, int i) {
        return Math.max(Math.abs(newStepValues[i] - lastStepValues[i]), lastDelta);
    }

    private void swap() {
        double[] temp = lastStepValues;
        lastStepValues = newStepValues;
        newStepValues = temp;
    }

    public void setExitCondition(double exitCondition) {
        this.exitCondition = exitCondition;
    }

    public void setGrid(OneDimensionalGrid grid) {
        this.grid = grid;
        initArrays();
    }

    public void setFunction(Function function) {
        this.function = function;
    }
}
