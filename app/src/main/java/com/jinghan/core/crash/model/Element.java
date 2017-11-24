package com.jinghan.core.crash.model;

/**
 * @author F43nd1r
 * @since 12.10.2016
 */
public interface Element {
    /**
     * @return this elements json value.
     * This must be one of the valid json types: bull, boolean, number, String, JSONObject or JSONArray
     */
    Object value();

    String[] flatten();
}
