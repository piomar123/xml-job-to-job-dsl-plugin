package com.adq.jenkins.xmljobtodsl.dsl.strategies;

import com.adq.jenkins.xmljobtodsl.JobDescriptor;
import com.adq.jenkins.xmljobtodsl.PropertyDescriptor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DSLJobStrategy extends AbstractDSLStrategy {

    public DSLJobStrategy(JobDescriptor jobDescriptor) {
        super(0, jobDescriptor, false);

        checkNeedsConfigureBlockAndCreateIt(jobDescriptor);

        initChildren(jobDescriptor);
    }

    private void checkNeedsConfigureBlockAndCreateIt(JobDescriptor jobDescriptor) {
        List<PropertyDescriptor> configureBlocks = findConfigureBlockStrategyProperty(jobDescriptor.getProperties());
        if (configureBlocks.size() > 0) {
            PropertyDescriptor configureBlockProperty = new PropertyDescriptor("configure", null, configureBlocks);
            jobDescriptor.getProperties().add(configureBlockProperty);
        }
    }

    private List<PropertyDescriptor> findConfigureBlockStrategyProperty(List<PropertyDescriptor> properties) {
        List<PropertyDescriptor> configureBlocksList = new ArrayList<>();
        Iterator<PropertyDescriptor> iterator = properties.iterator();
        while (iterator.hasNext()) {
            PropertyDescriptor descriptor = iterator.next();
            String type = getType(descriptor);
            if (DSLStrategyFactory.TYPE_CONFIGURE.equals(type)) {
                configureBlocksList.add(descriptor);
                iterator.remove();
            }
            if (descriptor.getProperties() != null && descriptor.getProperties().size() > 0) {
                configureBlocksList.addAll(findConfigureBlockStrategyProperty(descriptor.getProperties()));
            }
        }

        return configureBlocksList;
    }

    @Override
    public String toDSL() {
        return String.format(getSyntax("syntax.job"), getProperty(getDescriptor().getProperties().get(0)).getValue()
                , getDescriptor().getName(), getChildrenDSL());
    }
}
