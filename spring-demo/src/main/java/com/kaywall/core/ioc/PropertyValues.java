package com.kaywall.core.ioc;

import java.util.ArrayList;
import java.util.List;

/**
 *  E
 * @author aikaiqiang
 * @date 2020年01月08日 16:15
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<PropertyValue>();

    public void addPropertyValue(PropertyValue pv) {
        // 在这里可以对参数值 pv 做一些处理，如果直接使用 List，则就不行了
        this.propertyValueList.add(pv);
    }

    public List<PropertyValue> getPropertyValues() {
        return this.propertyValueList;
    }

}