package io.pivotal.jira;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.Iterator;

public class AdfToMarkdownDeserializer extends StdDeserializer<String> {

    public AdfToMarkdownDeserializer() {
        super(String.class);
    }

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        if (p.currentToken() == JsonToken.VALUE_STRING) {
            return p.getValueAsString();
        }
        if (p.currentToken() == JsonToken.VALUE_NULL) {
            return null;
        }
        JsonNode node = p.readValueAsTree();
        StringBuilder sb = new StringBuilder();
        convertNode(node, sb, "");
        return sb.toString().trim();
    }

    private void convertNode(JsonNode node, StringBuilder sb, String listPrefix) {
        if (node == null || node.isNull()) {
            return;
        }

        String type = node.has("type") ? node.get("type").asText() : "";

        switch (type) {
            case "doc":
                convertChildren(node, sb, listPrefix);
                break;
            case "paragraph":
                convertChildren(node, sb, listPrefix);
                sb.append("\n\n");
                break;
            case "heading":
                int level = node.has("attrs") && node.get("attrs").has("level")
                        ? node.get("attrs").get("level").asInt() : 1;
                sb.append("#".repeat(level)).append(" ");
                convertChildren(node, sb, listPrefix);
                sb.append("\n\n");
                break;
            case "text":
                String text = node.has("text") ? node.get("text").asText() : "";
                text = applyMarks(node, text);
                sb.append(text);
                break;
            case "hardBreak":
                sb.append("\n");
                break;
            case "codeBlock":
                String lang = node.has("attrs") && node.get("attrs").has("language")
                        ? node.get("attrs").get("language").asText() : "";
                sb.append("```").append(lang).append("\n");
                convertChildren(node, sb, listPrefix);
                sb.append("\n```\n\n");
                break;
            case "blockquote":
                StringBuilder bqContent = new StringBuilder();
                convertChildren(node, bqContent, listPrefix);
                for (String line : bqContent.toString().split("\n")) {
                    sb.append("> ").append(line).append("\n");
                }
                sb.append("\n");
                break;
            case "bulletList":
                convertListItems(node, sb, "- ");
                sb.append("\n");
                break;
            case "orderedList":
                convertOrderedListItems(node, sb);
                sb.append("\n");
                break;
            case "listItem":
                sb.append(listPrefix);
                convertChildren(node, sb, listPrefix);
                break;
            case "rule":
                sb.append("---\n\n");
                break;
            case "table":
                convertTable(node, sb);
                break;
            case "mediaSingle":
            case "mediaGroup":
                convertChildren(node, sb, listPrefix);
                break;
            case "media":
                String alt = node.has("attrs") && node.get("attrs").has("alt")
                        ? node.get("attrs").get("alt").asText() : "attachment";
                sb.append("[").append(alt).append("]");
                break;
            case "mention":
                String mentionText = node.has("attrs") && node.get("attrs").has("text")
                        ? node.get("attrs").get("text").asText() : "";
                sb.append(mentionText);
                break;
            case "emoji":
                String shortName = node.has("attrs") && node.get("attrs").has("shortName")
                        ? node.get("attrs").get("shortName").asText() : "";
                sb.append(shortName);
                break;
            case "inlineCard":
                String url = node.has("attrs") && node.get("attrs").has("url")
                        ? node.get("attrs").get("url").asText() : "";
                sb.append(url);
                break;
            case "panel":
                String panelType = node.has("attrs") && node.get("attrs").has("panelType")
                        ? node.get("attrs").get("panelType").asText() : "info";
                sb.append("> **").append(panelType.toUpperCase()).append(":** ");
                convertChildren(node, sb, listPrefix);
                sb.append("\n");
                break;
            default:
                convertChildren(node, sb, listPrefix);
                break;
        }
    }

    private void convertChildren(JsonNode node, StringBuilder sb, String listPrefix) {
        if (node.has("content")) {
            for (JsonNode child : node.get("content")) {
                convertNode(child, sb, listPrefix);
            }
        }
    }

    private void convertListItems(JsonNode node, StringBuilder sb, String prefix) {
        if (node.has("content")) {
            for (JsonNode child : node.get("content")) {
                convertNode(child, sb, prefix);
            }
        }
    }

    private void convertOrderedListItems(JsonNode node, StringBuilder sb) {
        if (node.has("content")) {
            int i = 1;
            for (JsonNode child : node.get("content")) {
                convertNode(child, sb, i + ". ");
                i++;
            }
        }
    }

    private void convertTable(JsonNode node, StringBuilder sb) {
        if (!node.has("content")) return;

        boolean headerDone = false;
        for (JsonNode row : node.get("content")) {
            if (!"tableRow".equals(row.has("type") ? row.get("type").asText() : "")) continue;
            sb.append("| ");
            Iterator<JsonNode> cells = row.get("content").iterator();
            while (cells.hasNext()) {
                JsonNode cell = cells.next();
                StringBuilder cellContent = new StringBuilder();
                convertChildren(cell, cellContent, "");
                sb.append(cellContent.toString().trim());
                sb.append(" | ");
            }
            sb.append("\n");
            if (!headerDone) {
                sb.append("| ");
                for (JsonNode ignored : row.get("content")) {
                    sb.append("--- | ");
                }
                sb.append("\n");
                headerDone = true;
            }
        }
        sb.append("\n");
    }

    private String applyMarks(JsonNode node, String text) {
        if (!node.has("marks")) return text;
        for (JsonNode mark : node.get("marks")) {
            String markType = mark.has("type") ? mark.get("type").asText() : "";
            switch (markType) {
                case "strong":
                    text = "**" + text + "**";
                    break;
                case "em":
                    text = "*" + text + "*";
                    break;
                case "code":
                    text = "`" + text + "`";
                    break;
                case "strike":
                    text = "~~" + text + "~~";
                    break;
                case "link":
                    String href = mark.has("attrs") && mark.get("attrs").has("href")
                            ? mark.get("attrs").get("href").asText() : "";
                    text = "[" + text + "](" + href + ")";
                    break;
                default:
                    break;
            }
        }
        return text;
    }
}
