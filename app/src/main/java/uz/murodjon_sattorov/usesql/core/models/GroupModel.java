package uz.murodjon_sattorov.usesql.core.models;

import java.io.Serializable;

import uz.murodjon_sattorov.usesql.core.BaseData;

public class GroupModel implements Serializable, BaseData {
    private long id;
    private String name;

    public GroupModel() {
    }

    public GroupModel(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GroupModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int getType() {
        return BaseData.TYPE_GROUP;
    }
}
