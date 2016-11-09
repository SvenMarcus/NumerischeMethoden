package irmb.numerik;

/**
 * Created by Sven on 09.11.2016.
 */
public class OneDimensionalGrid {
    private int numberOfNodes;
    private double length;
    private double gridDelta;

    private double[] nodeValues;

    public OneDimensionalGrid(int numberOfNodes, double length) {
        this.numberOfNodes = numberOfNodes;
        this.length = length;
        this.nodeValues = new double[numberOfNodes];
        calcGridDelta();
    }

    private void calcGridDelta() {
        gridDelta = length / (numberOfNodes - 1);
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
        if (node < numberOfNodes)
            return node * gridDelta;
        throw new IndexOutOfBoundsException("Max index: " + numberOfNodes + ", got: " + node);
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
