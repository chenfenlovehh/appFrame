package com.jinghan.core.crash.model;

/**
 * @author F43nd1r
 * @since 12.10.2016
 */
public class StringElement implements Element {
    private final String content;

    public StringElement(String content) {
        this.content = content;
    }

    @Override
    public Object value() {
        return content;
    }

    @Override
    public String[] flatten() {
        return new String[]{content};
    }

    @Override
    public String toString() {
        return content;
    }
}

