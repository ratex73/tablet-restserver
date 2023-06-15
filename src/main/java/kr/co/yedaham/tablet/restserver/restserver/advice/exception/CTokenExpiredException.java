package kr.co.yedaham.tablet.restserver.restserver.advice.exception;

public class CTokenExpiredException extends RuntimeException {
    public CTokenExpiredException(String msg, Throwable t) {
        super(msg, t);
    }

    public CTokenExpiredException(String msg) {
        super(msg);
    }

    public CTokenExpiredException() {
        super();
    }
}