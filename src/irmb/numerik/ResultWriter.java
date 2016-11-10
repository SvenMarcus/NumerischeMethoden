package irmb.numerik;

import java.text.DecimalFormat;

/**
 * Created by Sven on 10.11.2016.
 */
public class ResultWriter {

    private OneDimensionalGrid grid;
    private Function analyticFunction;

    public ResultWriter(OneDimensionalGrid grid, Function analyticFunction) {
        this.grid = grid;
        this.analyticFunction = analyticFunction;
    }

    public String createResults() {
        String resultString = "";
        DecimalFormat formatter = new DecimalFormat("0.00000000000000000");
        resultString += "Berechnung für " + grid.getNumberOfNodes() + " Knoten \n";
        for (int i = 0; i < grid.getNumberOfNodes(); i++) {
            resultString += "Koordinate: " + formatter.format(grid.getCoordinateOf(i))
                    + " \t Numerisch: " + formatter.format(grid.getGridData()[i])
                    + " \t Analytisch: " + formatter.format(analyticFunction.evaluateAt(grid.getCoordinateOf(i))) + "\n";
        }
        resultString += "\nFehler: " + calculateError() + "\n";
        return resultString;
    }

    private double calculateError() {
        double maxError = 0.;
        double analyticValue;
        double gridValue;
        for (int i = 1; i < grid.getNumberOfNodes() - 1; i++) {
            analyticValue = analyticFunction.evaluateAt(grid.getCoordinateOf(i));
            gridValue = grid.getGridData()[i];
            maxError = Math.max(Math.abs(analyticValue - gridValue), maxError);
        }
        return maxError;
    }
}
