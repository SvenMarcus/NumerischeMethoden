package irmb.numerik;

/**
 * Created by Sven on 09.11.2016.
 */
public class OneDimensionalGrid {
    private int numberOfNodes;
    private double length;
    private double gridDelta;
    private double[] coordinates;
    private double[] nodeValues;

    public OneDimensionalGrid() {
    }

    public OneDimensionalGrid(int numberOfNodes, double length) {
        this.numberOfNodes = numberOfNodes;
        this.length = length;
        this.coordinates = new double[numberOfNodes];
        this.nodeValues = new double[numberOfNodes];
        calcGridDelta();
        calcCoordinates();
    }

    private void calcGridDelta() {
        gridDelta = length / (numberOfNodes - 1);
    }

    private void calcCoordinates() {
        for (int i = 0; i < numberOfNodes; i++)
            coordinates[i] = i * gridDelta;
    }

    public void setNumberOfNodes(int numberOfNodes) {
        this.numberOfNodes = numberOfNodes;
        this.coordinates = new double[numberOfNodes];
        this.nodeValues = new double[numberOfNodes];
        calcGridDelta();
        calcCoordinates();
    }

    public void setLength(double length) {
        this.length = length;
        calcGridDelta();
        calcCoordinates();
    }

    public void setLeftBoundaryCondition(double dirichletValue) {
        nodeValues[0] = dirichletValue;
    }

    public void setRightBoundaryCondition(double dirichletValue) {
        nodeValues[nodeValues.length - 1] = dirichletValue;
    }

    public void setGridNodeValue(int index, double value) {
        nodeValues[index] = value;
    }

    public int getNumberOfNodes() {
        return numberOfNodes;
    }

    public double getLength() {
        return length;
    }

    public double getGridDelta() {
        return gridDelta;
    }

    public double getCoordinateOf(int node) {
        return coordinates[node];
    }

    public double[] getGridData() {
        return nodeValues;
    }

    public double getLeftBoundaryCondition() {
        return nodeValues[0];
    }

    public double getRightBoundaryCondition() {
        return nodeValues[nodeValues.length - 1];
    }
}
