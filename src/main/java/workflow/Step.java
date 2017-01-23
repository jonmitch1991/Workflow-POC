package main.java.workflow;

import java.util.Map;

/**
 * Created by europa on 20/01/17.
 */
public interface Step {
    String execute(Map<String, Object> params);
}
