package wodrich.rowena.iapps.fileuploader.api.responses;

import com.fasterxml.jackson.annotation.JsonInclude;

public class FileUploadResponse {

    private boolean success;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    public FileUploadResponse() {}

    public FileUploadResponse(boolean success) {
        this.success = success;
    }

    public FileUploadResponse(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
