package kr.co.yedaham.tablet.restserver.restserver.advice.exception;

public class CMaxCountOverException extends RuntimeException {

    public CMaxCountOverException(String msg, Throwable t) {
        super(msg, t);
    }
    public CMaxCountOverException(String msg) {
        super(msg);
    }
    public CMaxCountOverException() {
        super();
    }

}
