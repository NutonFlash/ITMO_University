package points;

import database.ResultService;

import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import java.io.Serializable;;
import java.util.ArrayList;
import java.util.Collections;


public class PointBean implements Serializable {
    private final AreaChecker areaChecker;
    private final ResultService resultService;
    private String strX;
    private String strY;
    private String strR;
    private float x, y, r;
    private ArrayList<Result> results;
    private AreaManager areaManager;

    public PointBean() {
        areaChecker = new AreaChecker();
        areaManager = new AreaManager(areaChecker);
        resultService = new ResultService();
        init();
        initArea();
    }

    public void init() {
        results = (ArrayList<Result>) resultService.getAllResults();
        Collections.reverse(results);
    }

    public void initArea() {
        r = 1;
        strR = "1";
        areaManager.setR(r);
        for (Result result : results) {
            Point point = new Point(result.getX(), result.getY(), result.isHitValue());
            areaManager.addPoint(point);
        }
    }

    public void valuesToFloat(String strX, String strY, String strR) {
        r = Float.parseFloat(strR);
        x = Float.parseFloat(strX);
        y = Float.parseFloat(strY);
    }

    public void toggle(ActionEvent event) {
        UIComponent component = event.getComponent();
        strX = (String) component.getAttributes().get("name");
    }

    public void submitButton() {
        if (strX != null && strY != null && strR != null) {
            valuesToFloat(strX, strY, strR);
            Result result;
            result = areaChecker.handleNumbers(x, y, r);
            System.out.println("Добавляем результат в БД");
            resultService.addResult(result);
            results.add(0, result);
            System.out.println("Добавляем точку на график");
            Point point = new Point(x, y, result.isHitValue());
            areaManager.setR(r);
            areaManager.addPoint(point);
        }
    }

    public void clearButton() {
        System.out.println("Очищаем график и таблицу...");
        results.clear();
        areaManager.clearPoints();
        resultService.clearResults();
        strX = null;
        strY = null;
        strR = "1";
    }


    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public String getStrX() {
        return strX;
    }

    public void setStrX(String strX) {
        this.strX = strX;
    }

    public String getStrY() {
        return strY;
    }

    public void setStrY(String strY) {
        this.strY = strY;
    }

    public String getStrR() {
        return strR;
    }

    public void setStrR(String strR) {
        this.strR = strR;
        this.r = Float.parseFloat(strR);
        areaManager.setR(r);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getR() {
        return r;
    }

    public void setR(float r) {
        this.r = r;
    }

    public AreaManager getAreaManager() {
        return areaManager;
    }

    public void setAreaManager(AreaManager areaManager) {
        this.areaManager = areaManager;
    }

}
