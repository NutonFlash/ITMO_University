package database;

import points.Result;

import java.util.List;

public class ResultService {
    private final ResultDAO resultDAO;

    public ResultService() {
        resultDAO = new ResultDAO();
    }

    public void addResult(Result result) {
        resultDAO.save(result);
    }

    public List<Result> getAllResults() {
        return resultDAO.findAll();
    }

    public void clearResults() {
        resultDAO.deleteAll();
    }
}