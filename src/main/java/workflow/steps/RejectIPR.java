package main.java.workflow.steps;

import main.java.workflow.Step;

import java.util.Map;

/**
 * Created by europa on 20/01/17.
 */
public class RejectIPR implements Step {

    @Override
    public String execute(Map<String, Object> params) {
        return "IPR has been rejected.";
    }
}
