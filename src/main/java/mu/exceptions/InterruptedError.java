package mu.exceptions;


public class InterruptedError extends Error{
    public InterruptedError() {
    }

    public InterruptedError(String message) {
        super(message);
    }

    public InterruptedError(String message, InterruptedException cause) {
        super(message, cause);
    }

    public InterruptedError(InterruptedException cause) {
        super(cause);
    }

    public InterruptedError(String message, InterruptedException cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
