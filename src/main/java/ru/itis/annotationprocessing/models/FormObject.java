package ru.itis.annotationprocessing.models;

import java.util.List;

public class FormObject {

    private String action;
    private String method;
    private List<InputObject> inputs;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<InputObject> getInputs() {
        return inputs;
    }

    public void setInputs(List<InputObject> inputs) {
        this.inputs = inputs;
    }
}
