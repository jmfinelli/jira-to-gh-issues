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

        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Blocker", "priority/blocker");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Critical", "priority/critical");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Major", "priority/major");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Minor", "priority/minor");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Optional", "priority/optional");

        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Application Server Integration", "component/app-server");
        //fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "BlackTie", "component/blacktie");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Build System", "component/build");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "CI", "component/ci");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Cloud", "component/cloud");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Common", "component/common");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Compensations", "component/compensations");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Configuration", "component/configuration");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Demonstrator", "component/demonstrator");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Documentation", "component/documentation");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "JCA", "component/jca");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "JMS", "component/jms");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "JTA", "component/jta");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "JTS", "component/jts");
        //fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "LRA", "component/lra");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "OSGi", "component/osgi");
        //fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Performance Testing", "component/perf-testing");
        //fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Quickstarts", "component/quickstarts");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Recovery", "component/recovery");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Release Process", "component/release-process");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Resource Manager", "component/resource-manager");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "REST", "component/rest");
        //fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "SPI", "component/spi");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "STM", "component/stm");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Testing", "component/testing");
        //fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Tomcat", "component/tomcat");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Tooling", "component/tooling");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Transaction Core", "component/tx-core");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Transactional Driver", "component/tx-driver");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "Transactional File I/O", "component/tx-file-io");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "TxBridge", "component/txbridge");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "vertx", "component/vertx");
        //fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "website", "component/website");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.COMPONENT, "XTS", "component/xts");

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
