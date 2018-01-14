package model;

/**
 * Created by rajaee on 1/14/18.
 */
public class ConfRecord {
    private Integer id;
    private String name;
    private String type;
    private String value;
    private String isActive;
    private String applicationName;

    public ConfRecord() {
    }

    public ConfRecord(Integer id, String name, String type, String value, String isActive, String applicationName) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
        this.isActive = isActive;
        this.applicationName = applicationName;
    }

    public boolean isNew() {
        return (this.id == null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }
}
