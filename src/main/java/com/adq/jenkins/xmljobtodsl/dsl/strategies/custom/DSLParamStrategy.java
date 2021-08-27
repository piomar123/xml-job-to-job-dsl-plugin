package com.adq.jenkins.xmljobtodsl.dsl.strategies.custom;

import com.adq.jenkins.xmljobtodsl.dsl.strategies.DSLMethodStrategy;
import com.adq.jenkins.xmljobtodsl.dsl.strategies.DSLStrategy;
import com.adq.jenkins.xmljobtodsl.parsers.PropertyDescriptor;

public class DSLParamStrategy extends DSLMethodStrategy {

    private final String methodName;

    public DSLParamStrategy(int tabs, PropertyDescriptor propertyDescriptor, String methodName) {
        super(tabs, propertyDescriptor, methodName);
        this.methodName = methodName;
    }

    @Override
    public String toDSL() {
        return replaceTabs(String.format(getSyntax("syntax.method_call"),
                                         methodName, getOrderedChildrenDSL()), getTabs());
    }

    public String getOrderedChildrenDSL() {
        final DSLStrategy defaultValue1 = getChildrenByName("defaultValue");
        String defaultValue = defaultValue1 == null ? "\"\"" : defaultValue1.toDSL();
        String name = getChildrenByName("name").toDSL();
        final DSLStrategy description1 = getChildrenByName("description");
        String description = description1 == null ? "\"\"" : description1.toDSL();
        return name + ", " + defaultValue + ", " + description;
    }

    private DSLStrategy getChildrenByName(String name) {
        for (DSLStrategy strategy : getChildren()) {
            if (strategy.getDescriptor().getName().equals(name)) {
                return strategy;
            }
        }
        return null;
    }
}
