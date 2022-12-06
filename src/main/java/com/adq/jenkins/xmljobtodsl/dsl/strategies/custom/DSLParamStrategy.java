package com.adq.jenkins.xmljobtodsl.dsl.strategies.custom;

import java.util.Optional;

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
        String defaultValue = getChildrenByName("defaultValue").map(DSLStrategy::toDSL).orElse("''");
        String name = getChildrenByName("name").map(DSLStrategy::toDSL).orElse("''");
        String description = getChildrenByName("description").map(DSLStrategy::toDSL).orElse("''");
        return name + ", " + defaultValue + ", " + description;
    }

    private Optional<DSLStrategy> getChildrenByName(String name) {
        for (DSLStrategy strategy : getChildren()) {
            if (strategy.getDescriptor().getName().equals(name)) {
                return Optional.of(strategy);
            }
        }
        return Optional.empty();
    }
}
