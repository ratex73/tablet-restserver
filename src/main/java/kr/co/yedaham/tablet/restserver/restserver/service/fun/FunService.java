package kr.co.yedaham.tablet.restserver.restserver.service.fun;

import kr.co.yedaham.tablet.restserver.restserver.model.file.FileSiginRequest;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.*;
import kr.co.yedaham.tablet.restserver.restserver.service.ResponseService;

import java.util.ArrayList;
import java.util.List;

public interface FunService {
    public List<FunList> getFunList(FunPostRequest request);
    public ArrayList<Subcontractor> getSubcontractPhoneList(String userid);
    public ArrayList<SubcontractorFunList> getSubcontractFunList(String userid);

    public FunCarInfo findGetFunCarInfo(String functrlno);
    public FunProgressInfo findFunProgressInfo(String functrlno);
    public FunCustomerInfo findFunCustomerInfo(String functrlno);
    public FunFuneralInfo1 findFuneralInfo1(String functrlno);
    public FunFuneralInfo2 findFuneralInfo2(String functrlno);
    public FunWorkerInfo findFunWorkerInfo(String functrlno);
    public ResponseService sendFunSms(FunSmsInfo funSmsInfo);
    public ArrayList<FunSmsEmpList> getFunSmsEmpList(String functrlno, String smsType);
    public ResponseService updateDeparted(DepartedInfo departedInfo);
    public ResponseService updateAmbulanceInfo(AmbulanceInfo ambulanceInfo);
    public ResponseService updateBurialInfo(BurialInfo burialInfo);
    public ResponseService updateFunCarlInfo(FunCarUpdateInfo funCarUpdateInfo);

    boolean fileSignin(FileSiginRequest fileSiginRequest);
}
