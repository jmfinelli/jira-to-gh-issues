package io.pivotal.migration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "jira.projectId", havingValue = "JBTM")
public class JBTMMigrationConfig {

    @Bean
    public MilestoneFilter milestoneFilter() {
        return fixVersion -> true;
    }

    @Bean
    public LabelHandler labelHandler() {
        FieldValueLabelHandler fieldValueHandler = new FieldValueLabelHandler();
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Bug", "bug");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Enhancement", "enhancement");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Improvement", "enhancement");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "New Feature", "enhancement");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Feature Request", "enhancement");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Task", "task");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Sub-task", "task");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Epic", "epic");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.ISSUE_TYPE, "Story", "enhancement");

        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Blocker", "blocker");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Critical", "critical");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Major", "major");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Minor", "minor");
        fieldValueHandler.addMapping(FieldValueLabelHandler.FieldType.PRIORITY, "Trivial", "trivial");

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
