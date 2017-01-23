package main.java.workflow;

import javax.enterprise.context.ApplicationScoped;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class WorkflowEngine {

    public String process(Map<String, Object> params) {
        Task task = (Task) params.get("task");

        // Skipped finding Steps via XML for the time being and have hardcoded them into the findSteps method
        List<String> steps = findSteps(task);
        StringBuilder sb = new StringBuilder();

        for (String stepName : steps) {
            try {
                Class<?> stepClass = Class.forName(stepName);
                Method execute = stepClass.getDeclaredMethod("execute", java.util.Map.class);
                String result = (String) execute.invoke(stepClass.newInstance(), params);
                sb.append(result + " ");
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found " + stepName);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    private List<String> findSteps(Task task) {
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
    }
}
