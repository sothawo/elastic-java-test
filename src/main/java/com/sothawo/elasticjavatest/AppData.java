/*
 * (c) Copyright 2021 sothawo
 */
package com.sothawo.elasticjavatest;

/**
 * @author P.J. Meisch (pj.meisch@sothawo.com)
 */
public class AppData {
    private String foo;
    private String bar;

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    @Override
    public String toString() {
        return "AppData{" +
            "foo='" + foo + '\'' +
            ", bar='" + bar + '\'' +
            '}';
    }
}
