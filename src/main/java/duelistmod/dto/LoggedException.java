package duelistmod.dto;

public class LoggedException {

    private String message;
    private String stackTrace;
    private String uuid;
    private String duelistModVersion;
    private String devMessage;

    public LoggedException(String message, String stackTrace, String uuid, String duelistModVersion, String devMessage) {
        this.message = message;
        this.stackTrace = stackTrace;
        this.uuid = uuid;
        this.duelistModVersion = duelistModVersion;
        this.devMessage = devMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getDuelistModVersion() {
        return duelistModVersion;
    }

    public void setDuelistModVersion(String duelistModVersion) {
        this.duelistModVersion = duelistModVersion;
    }

    public String getDevMessage() {
        return devMessage;
    }

    public void setDevMessage(String devMessage) {
        this.devMessage = devMessage;
    }
}
