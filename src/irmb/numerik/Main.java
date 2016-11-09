package irmb.numerik;

public class Main {

    public static void main(String[] args) {
        OneDimensionalGrid grid = new OneDimensionalGrid(4, 0.1);
        grid.setLeftBoundaryCondition(0);
        grid.setRightBoundaryCondition(1);
    }
}
