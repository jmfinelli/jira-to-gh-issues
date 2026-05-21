package io.pivotal.migration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "jira.projectId", havingValue = "JBTM")
public class JBTMMigrationConfig {

    @Bean
    public MilestoneFilter milestoneFilter() {
        return fixVersion -> !fixVersion.isReleased() && !fixVersion.isArchived();
    }

    @Bean
    public LabelHandler labelHandler() {
        FieldValueLabelHandler fieldValueHandler = new FieldValueLabelHandler();

        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Bug", "type/bug");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Enhancement", "type/enhancement");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Feature Request", "type/enhancement");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Task", "type/task");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Sub-task", "type/task");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Patch", "type/patch");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Support Patch", "type/support-patch");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Component Upgrade", "type/component-upgrade");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Component Upgrade Subtask", "type/component-upgrade");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Release", "type/release");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Quality Risk", "type/quality-risk");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "CTS Challenge", "type/cts-challenge");

        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Blocker", "priority/blocker", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Critical", "priority/critical", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Major", "priority/major", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Minor", "priority/minor", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Optional", "priority/optional", LabelFactories.TYPE_LABEL);

        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Application Server Integration", "component/app-server", LabelFactories.TYPE_LABEL);
        //fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "BlackTie", "component/blacktie", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Build System", "component/build", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "CI", "component/ci", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Cloud", "component/cloud", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Common", "component/common", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Compensations", "component/compensations", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Configuration", "component/configuration", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Demonstrator", "component/demonstrator", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Documentation", "component/documentation", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "JCA", "component/jca", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "JMS", "component/jms", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "JTA", "component/jta", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "JTS", "component/jts", LabelFactories.TYPE_LABEL);
        //fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "LRA", "component/lra", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "OSGi", "component/osgi", LabelFactories.TYPE_LABEL);
        //fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Performance Testing", "component/perf-testing", LabelFactories.TYPE_LABEL);
        //fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Quickstarts", "component/quickstarts", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Recovery", "component/recovery", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Release Process", "component/release-process", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Resource Manager", "component/resource-manager", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "REST", "component/rest", LabelFactories.TYPE_LABEL);
        //fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "SPI", "component/spi", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "STM", "component/stm", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Testing", "component/testing", LabelFactories.TYPE_LABEL);
        //fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Tomcat", "component/tomcat", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Tooling", "component/tooling", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Transaction Core", "component/tx-core", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Transactional Driver", "component/tx-driver", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Transactional File I/O", "component/tx-file-io", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "TxBridge", "component/txbridge", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "vertx", "component/vertx", LabelFactories.TYPE_LABEL);
        //fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "website", "component/website", LabelFactories.TYPE_LABEL);
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "XTS", "component/xts", LabelFactories.TYPE_LABEL);

        CompositeLabelHandler handler = new CompositeLabelHandler();
        handler.addLabelHandler(fieldValueHandler);
        return handler;
    }

    @Bean
    public IssueProcessor issueProcessor() {
        return new CompositeIssueProcessor();
    }

    @Bean
    public JiraIssueFilter jiraIssueFilter() {
        return new CompositeJiraIssueFilter();
    }
}
