package com.systech.mss.callback;

public abstract class ProcessCallBack {
    public abstract void start(Object o);

    public Object process(Object o) {
        return o;
    }

    public Object end(Object o) {
        return o;
    }
}
