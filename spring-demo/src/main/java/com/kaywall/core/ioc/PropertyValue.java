package com.kaywall.core.ioc;

/**
 *  E
 * @author aikaiqiang
 * @date 2020年01月08日 16:15
 */
public class PropertyValue {

    private final String name;

    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}