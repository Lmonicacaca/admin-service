package com.mbr.admin.common.dto;

public class BaseRequestDto<T> {

    private Object device;
    private T parameters;

    public Object getDevice() {
        return device;
    }

    public void setDevice(Object device) {
        this.device = device;
    }

    public T getParameters() {
        return parameters;
    }

    public void setParameters(T parameters) {
        this.parameters = parameters;
    }
}
