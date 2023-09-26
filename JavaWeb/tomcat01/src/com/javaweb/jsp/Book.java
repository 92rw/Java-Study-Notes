package com.javaweb.jsp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Book {

    private String name;//书名
    private String[] writer;//作者
    private List<String> reader;//读者
    private Map<String, String> topics;//评价

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getWriter() {
        return writer;
    }

    public void setWriter(String[] writer) {
        this.writer = writer;
    }

    public List<String> getReader() {
        return reader;
    }

    public void setReader(List<String> reader) {
        this.reader = reader;
    }

    public Map<String, String> getTopics() {
        return topics;
    }

    public void setTopics(Map<String, String> topics) {
        this.topics = topics;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", writer=" + Arrays.toString(writer) +
                ", reader=" + reader +
                ", topics=" + topics +
                '}';
    }
}
