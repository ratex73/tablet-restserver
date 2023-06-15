package kr.co.yedaham.tablet.restserver.restserver.advice.exception;

public class CExistingNewCarallocationException extends  RuntimeException{

    public CExistingNewCarallocationException(String msg, Throwable t) {
        super(msg, t);
    }
    public CExistingNewCarallocationException(String msg) {
        super(msg);
    }
    public CExistingNewCarallocationException() {
        super();
    }
}
