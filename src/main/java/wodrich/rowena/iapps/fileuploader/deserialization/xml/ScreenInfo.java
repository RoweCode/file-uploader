package wodrich.rowena.iapps.fileuploader.deserialization.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ScreenInfo {
    @JacksonXmlProperty(isAttribute = true)
    private Integer width;
    @JacksonXmlProperty(isAttribute = true)
    private Integer height;
    @JacksonXmlProperty(isAttribute = true)
    private Integer dpi;

    public ScreenInfo() {}

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getDpi() {
        return dpi;
    }

    public void setDpi(Integer dpi) {
        this.dpi = dpi;
    }
}
