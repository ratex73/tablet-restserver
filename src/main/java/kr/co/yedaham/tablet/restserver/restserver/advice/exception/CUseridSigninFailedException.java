package kr.co.yedaham.tablet.restserver.restserver.advice.exception;

public class CUseridSigninFailedException extends RuntimeException {
    public CUseridSigninFailedException(String msg, Throwable t) {
        super(msg, t);
    }

    public CUseridSigninFailedException(String msg) {
        super(msg);
    }

    public CUseridSigninFailedException() {
        super();
    }
}
