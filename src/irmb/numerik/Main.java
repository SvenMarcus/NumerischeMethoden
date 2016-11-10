package irmb.numerik;

public class Main {

    public static void main(String[] args) {
        double scaleFactor = 100000.0;
        int nMax = 9;
        int numNodes = 2;
        double length = 0.1;
        double rightBC = 1.;
        double leftBC = 0.;

//        Function fNumerical = x -> Math.sin(2. * x * Math.PI / length);
//
//        Function fAnalytical = x ->
//                -Math.sin(2. * x * Math.PI / length) * Math.pow(length, 2) / (Math.PI * Math.PI * 4.)
//                        + (rightBC - leftBC) * x / length
//                        + leftBC;

        Function fNumerical = x -> x * x * scaleFactor;
        Function fAnalytical = x -> leftBC + (rightBC - leftBC - 1. / 12. * scaleFactor * Math.pow(length, 4)) / length * x + 1. / 12. * scaleFactor * x * x * x * x;

        for (int i = 0; i < nMax; i++) {
            numNodes *= 2;
            OneDimensionalGrid grid = new OneDimensionalGrid(numNodes, length);

            grid.setLeftBoundaryCondition(leftBC);
            grid.setRightBoundaryCondition(rightBC);

            OneDimensionalGridSolver solver = new OneDimensionalGridSolver(grid, fNumerical);
            solver.setExitCondition(1e-15);
            solver.solve();

            ResultWriter resultWriter = new ResultWriter(grid, fAnalytical);
            System.out.println(resultWriter.createResults());
        }

    }
}
