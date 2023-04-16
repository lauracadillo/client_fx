package com.example.client_fx;
import java.io.Serializable;

public class ModeleCourse implements Serializable{

    private String name;
    private String code;
    private String session;

    public ModeleCourse(String name, String code, String session) {
        this.name = name;
        this.code = code;
        this.session = session;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    @Override
    public String toString() {
        return  code + " " + name;
    }
}

