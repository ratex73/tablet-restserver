package kr.co.yedaham.tablet.restserver.restserver.advice.exception;

public class CFileNotExistException extends RuntimeException {
    public CFileNotExistException(String msg, Throwable t) {
        super(msg, t);
    }
    public CFileNotExistException(String msg) {
        super(msg);
    }
    public CFileNotExistException() {
        super();
    }
}
