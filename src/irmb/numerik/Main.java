package irmb.numerik;

public class Main {

    public static void main(String[] args) {
        double scaleFactor = 100000.0;
        OneDimensionalGrid grid = new OneDimensionalGrid(8, 0.1);

        grid.setLeftBoundaryCondition(0);
        grid.setRightBoundaryCondition(1);

        Function function = x -> x * x * scaleFactor;
        OneDimensionalGridSolver solver = new OneDimensionalGridSolver(grid, function);
        solver.setExitCondition(1e-15);
        solver.solve();
        for (int i = 0; i < grid.getNumberOfNodes(); i++) {
            System.out.println(grid.getCoordinateOf(i) + " \t " + grid.getGridData()[i]);
        }
    }
}
