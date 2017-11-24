package com.jinghan.core.crash.model;

/**
 * @author F43nd1r
 * @since 13.10.2016
 */

public class NumberElement implements Element {
    private final Number content;

    public NumberElement(Number content) {
        this.content = content;
    }

    @Override
    public Object value() {
        return content;
    }

    @Override
    public String[] flatten() {
        return new String[]{toString()};
    }

    @Override
    public String toString() {
        return content.toString();
    }
}