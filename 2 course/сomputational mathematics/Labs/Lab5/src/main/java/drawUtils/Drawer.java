package drawUtils;

import comulation.Coordinate;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYDotRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Drawer {
    private DrawData drawData;

    public Drawer(DrawData drawData) {
        this.drawData = drawData;
    }

    public void createGraphic() {
        XYSeries approximationLine = getSeriesDataFromList(drawData.getApproximationCoordinates(), "численное решение");
        XYSeries analyticLine = getSeriesDataFromList(drawData.getAnalyticCoordinates(), "аналитическое решение");
        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        xySeriesCollection.addSeries(approximationLine);
        xySeriesCollection.addSeries(analyticLine);

        JFreeChart chart = ChartFactory.createXYLineChart(drawData.getGraphHeader(), "x", "y",
                xySeriesCollection,
                PlotOrientation.VERTICAL, true, true, true);
        XYPlot plot = chart.getXYPlot();

        XYSeries points = new XYSeries("Начальная точка");
        Coordinate initialPoint = drawData.getInitialPoint();
        points.add(initialPoint.getX(), initialPoint.getY());
        XYSeriesCollection pointSeriesCollection = new XYSeriesCollection();
        pointSeriesCollection.addSeries(points);
        plot.setDataset(1, pointSeriesCollection);
        XYDotRenderer dotRenderer = new XYDotRenderer();
        dotRenderer.setDotHeight(6);
        dotRenderer.setDotWidth(6);
        dotRenderer.setSeriesPaint(0, Color.green);
        plot.setRenderer(1, dotRenderer);

        XYSeries aproPoints = new XYSeries("Посчитанные точки");
        drawData.getApproximationCoordinates().forEach(point -> aproPoints.add(point.getX(), point.getY()));
        XYSeriesCollection pointSeriesCollection2 = new XYSeriesCollection();
        pointSeriesCollection2.addSeries(aproPoints);
        XYDotRenderer dotRenderer2 = new XYDotRenderer();
        dotRenderer2.setDotHeight(6);
        dotRenderer2.setDotWidth(6);
        dotRenderer2.setSeriesPaint(0, Color.red);
        plot.setRenderer(2, dotRenderer2);
        plot.setDataset(2, pointSeriesCollection2);
        JFrame frame = new JFrame("Лаба 4");
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(1350, 670);
        frame.setVisible(true);
    }

    private XYSeries getSeriesDataFromList(ArrayList<Coordinate> coordinates, String funcName) {
        XYSeries xySeries = new XYSeries(funcName);
        coordinates.forEach( point -> xySeries.add(point.getX(), point.getY()));
        return xySeries;
    }
}
