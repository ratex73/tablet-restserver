package kr.co.yedaham.tablet.restserver.restserver.service.log;

public interface LogService {
    String sendLogServer(String log);
    String sendSlackServer(String log);
}
