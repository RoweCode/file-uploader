package wodrich.rowena.iapps.fileuploader.deserialization.xml;

public class EpaperRequest {

    private DeviceInfo deviceInfo;
    private GetPages getPages;

    private String fileName;

    public EpaperRequest() {}

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public GetPages getGetPages() {
        return getPages;
    }

    public void setGetPages(GetPages getPages) {
        this.getPages = getPages;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
