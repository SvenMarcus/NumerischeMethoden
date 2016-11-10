package irmb.numerik;

public class Main {

    public static void main(String[] args) {
        double scaleFactor = 100000.0;

        OneDimensionalGrid grid = new OneDimensionalGrid(16, 0.1);

        grid.setLeftBoundaryCondition(0);
        grid.setRightBoundaryCondition(1);

        Function fNumerical = x -> Math.sin(2. * x * Math.PI / grid.getLength());
        OneDimensionalGridSolver solver = new OneDimensionalGridSolver(grid, fNumerical);
        solver.setExitCondition(1e-15);
        solver.solve();

        Function fAnalytical = x -> {
            return -Math.sin(2. * x * Math.PI / grid.getLength()) * Math.pow(grid.getLength(), 2) / (Math.PI * Math.PI * 4.)
                    + (grid.getRightBoundaryCondition() - grid.getLeftBoundaryCondition()) * x / grid.getLength()
                    + grid.getLeftBoundaryCondition();
        };


        ResultWriter resultWriter = new ResultWriter(grid, fAnalytical);
        System.out.println(resultWriter.createResults());


    }
}
