package main.java.workflow.steps;

import main.java.workflow.Step;

import java.util.Map;

/**
 * Created by europa on 20/01/17.
 */
public class NotifyUsers implements Step {

    @Override
    public String execute(Map<String, Object> params) {
        return "Users have been notified of the change.";
    }
}
