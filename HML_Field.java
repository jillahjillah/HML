/**
 * Created by samhollenbach on 5/19/16.
 */
public class HML_Field {

    boolean required;
    String name;
    String value = "";
    int id;
    HML_Block container;

    public HML_Field(String name, boolean required) {
        this.name = name;
        this.required = required;
    }

    public HML_Field(String name, String value, boolean required) {
        this.name = name;
        this.value = value;
        this.required = required;
    }

    public void setField(String value){
        this.value = value;
    }

    public boolean isRequiredField() {
        return required;
    }

    public String getFieldName() {
        return name;
    }

    public String getFieldValue() {
        return value;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setContainerBlock(HML_Block container) {
        this.container = container;
    }

    public HML_Block getContainerBlock() {
        return container;
    }
}
