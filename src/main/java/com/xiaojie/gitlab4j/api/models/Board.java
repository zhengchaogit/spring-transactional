package com.xiaojie.gitlab4j.api.models;

import java.util.List;

import com.xiaojie.gitlab4j.api.utils.JacksonJson;

public class Board {

    private Long id;
    private String name;
    private Project project;
    private Milestone milestone;
    private List<BoardList> lists;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public List<BoardList> getLists() {
        return lists;
    }

    public void setLists(List<BoardList> lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return (JacksonJson.toJsonString(this));
    }
}
