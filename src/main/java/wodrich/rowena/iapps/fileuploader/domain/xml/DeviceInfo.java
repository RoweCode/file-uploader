package wodrich.rowena.iapps.fileuploader.domain.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class DeviceInfo {
    @JacksonXmlProperty(isAttribute = true)
    private String name;
    @JacksonXmlProperty(isAttribute = true)
    private String id;

    private ScreenInfo screenInfo;
    private OsInfo osInfo;

    private AppInfo appInfo;

    public DeviceInfo() {}

    public ScreenInfo getScreenInfo() {
        return screenInfo;
    }

    public void setScreenInfo(ScreenInfo screenInfo) {
        this.screenInfo = screenInfo;
    }

    public OsInfo getOsInfo() {
        return osInfo;
    }

    public void setOsInfo(OsInfo osInfo) {
        this.osInfo = osInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AppInfo getAppInfo() {
        return appInfo;
    }

    public void setAppInfo(AppInfo appInfo) {
        this.appInfo = appInfo;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", screenInfo=" + screenInfo +
                ", osInfo=" + osInfo +
                '}';
    }
}
