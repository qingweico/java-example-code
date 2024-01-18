package object.entity;

import lombok.Setter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author zqw
 * @date 2023/12/12
 */
@Setter
@XmlRootElement(name = "EntityToXml")
public class EntityToXml {

    private String var1;

    private String var2;

    @XmlElement(name = "var1")
    public String getVar1() {
        return var1;
    }

    @XmlElement(name = "var2")
    public String getVar2() {
        return var2;
    }

    public EntityToXml() {
    }

    public EntityToXml(String var1, String var2) {
        this.var1 = var1;
        this.var2 = var2;
    }
}
