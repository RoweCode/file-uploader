package wodrich.rowena.iapps.fileuploader.deserialization.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class OsInfo {
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private Double version;

    public OsInfo() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }
}
