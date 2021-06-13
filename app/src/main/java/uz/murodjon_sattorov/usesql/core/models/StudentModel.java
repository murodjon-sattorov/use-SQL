package uz.murodjon_sattorov.usesql.core.models;

import uz.murodjon_sattorov.usesql.core.BaseData;

public class StudentModel implements BaseData {

    private long id;
    private String name;
    private long groupId;

    public StudentModel() {
    }

    public StudentModel(long id, String name, long groupId) {
        this.id = id;
        this.name = name;
        this.groupId = groupId;
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

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "StudentModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", groupId=" + groupId +
                '}';
    }

    @Override
    public int getType() {
        return BaseData.TYPE_STUDENT;
    }
}
