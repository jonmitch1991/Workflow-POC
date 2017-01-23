package main.java.workflow;

import main.java.workflow.steps.RejectIPR;
import main.java.workflow.steps.SubmitIPR;

import javax.enterprise.context.ApplicationScoped;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class WorkflowEngine {

    private Map<Task, List<String>> stepsByTask;

    public WorkflowEngine() {
        // This map would be populated from an XML file which detailed the Step's for each Task.
        stepsByTask = new HashMap<>();
        stepsByTask.put(Task.CREATE_IPR, new ArrayList<>());
        stepsByTask.get(Task.CREATE_IPR).add("main.java.workflow.steps.ValidateIPR");
        stepsByTask.get(Task.CREATE_IPR).add("main.java.workflow.steps.SaveIPR");
        stepsByTask.get(Task.CREATE_IPR).add("main.java.workflow.steps.NotifyUsers");

        stepsByTask.put(Task.SUBMIT_IPR, new ArrayList<>());
        stepsByTask.get(Task.SUBMIT_IPR).add("main.java.workflow.steps.ValidateIPR");
        stepsByTask.get(Task.SUBMIT_IPR).add("main.java.workflow.steps.SubmitIPR");
        stepsByTask.get(Task.SUBMIT_IPR).add("main.java.workflow.steps.NotifyUsers");

        stepsByTask.put(Task.APPROVE_IPR, new ArrayList<>());
        stepsByTask.get(Task.APPROVE_IPR).add("main.java.workflow.steps.ApproveIPR");
        stepsByTask.get(Task.APPROVE_IPR).add("main.java.workflow.steps.NotifyUsers");

        stepsByTask.put(Task.REJECT_IPR, new ArrayList<>());
        stepsByTask.get(Task.REJECT_IPR).add("main.java.workflow.steps.RejectIPR");
        stepsByTask.get(Task.REJECT_IPR).add("main.java.workflow.steps.NotifyUsers");
    }

    public String process(Map<String, Object> params) {
        Task task = (Task) params.get("task");

        /*// Skipped finding Steps via XML for the time being and have hardcoded them into the findSteps method
        List<String> steps = findSteps(task);*/
        List<String> steps = stepsByTask.get(task);
        StringBuilder sb = new StringBuilder();

        for (String stepName : steps) {
            try {
                Class<?> stepClass = Class.forName(stepName);
                Step step = (Step) stepClass.newInstance();
                String result = step.execute(params);
                sb.append(result + " ");
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found " + stepName);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    /*private List<String> findSteps(Task task) {
        List<String> results = new ArrayList<>();
        switch (task) {
            case CREATE_IPR:
                results.add("main.java.workflow.steps.ValidateIPR");
                results.add("main.java.workflow.steps.SaveIPR");
                results.add("main.java.workflow.steps.NotifyUsers");
                break;
            case SUBMIT_IPR:
                results.add("main.java.workflow.steps.ValidateIPR");
                results.add("main.java.workflow.steps.SubmitIPR");
                results.add("main.java.workflow.steps.NotifyUsers");
                break;
            case APPROVE_IPR:
                results.add("main.java.workflow.steps.ApproveIPR");
                results.add("main.java.workflow.steps.NotifyUsers");
                break;
            case REJECT_IPR:
                results.add("main.java.workflow.steps.RejectIPR");
                results.add("main.java.workflow.steps.NotifyUsers");
                break;
            default:
                break;
        }
        return results;
    }*/
}
