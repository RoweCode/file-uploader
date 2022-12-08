package wodrich.rowena.iapps.fileuploader.deserialization.xml;

public class AppInfo {

    private String newspaperName;
    private Double version;

    public AppInfo() {}

    public String getNewspaperName() {
        return newspaperName;
    }

    public void setNewspaperName(String newspaperName) {
        this.newspaperName = newspaperName;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }
}
