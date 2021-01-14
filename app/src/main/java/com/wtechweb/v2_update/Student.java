package com.wtechweb.v2_update;

public class Student {
    String id;
    String name;
    String cell;

    public Student() {
    }

    public Student(String id, String name, String cell) {
        this.id = id;
        this.name = name;
        this.cell = cell;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }
}
