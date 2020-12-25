package vnteleco.com.exeption;

public class BizException extends Exception {

    private String message;

    public BizException(String message) {
        super(message);
        this.message= message;
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
        this.message= message;
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.message= message;
    }
}
