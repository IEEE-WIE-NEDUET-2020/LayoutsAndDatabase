package com.example.reminderapp;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyObject implements Serializable {
    private List<TestofSub1> listname;
    private Integer[] imagenames;

    public MyObject(ArrayList<TestofSub1> listname, Integer[] imagenames) {
        super();
        this.listname = listname;
        this.imagenames= imagenames;
    }

    public ArrayList<TestofSub1> getList() {
        if (!(listname == null))
            return (ArrayList<TestofSub1>) listname;
        else
            return new ArrayList<TestofSub1>();
    }

    public Integer[] getImages() {
        return imagenames;
    }


}

