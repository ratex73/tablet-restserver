package kr.co.yedaham.tablet.restserver.restserver.service.carallocation;

import kr.co.yedaham.tablet.restserver.restserver.model.carallocation.*;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.CarallocationInfo;
import kr.co.yedaham.tablet.restserver.restserver.model.fun.CarallocationReturnInfo;

import java.util.ArrayList;
import java.util.List;

public interface CarallocationService {
    public CarallocationSaveInfo saveCarallcationAndSmsHistory(CarallocationSaveInfo carallocationInfo) throws Exception;
    public ArrayList<CarallocationInfo> getCarallocationList(String functrlno);
    public ArrayList<CarallocationReturnInfo> getCarallocationReturnList(String functrlno);
    public List<CarallocationProductInfo> getCarallocationProductInfo();
    public List<CarallocationRentalInfo> getCarallocationRentalList(String funno, String itemno);
    public List<CarallocationRental> saveCarallocationRental(List<CarallocationRental> carallocationRental);
    public long deleteCarallocationRental(long seq);
    public int getValueCarRegister(String funno);
    public CarallocationSaveInfo updateCarallocation(String funno, int line);
    public CarallocationSaveInfo updateCarallocationSignature(String funno, int line, String signature, String arriveDay, String arriveTime);
    public List<CarallocationMiddleCategory> getMiddleCategory();
    public List<CarallocationMiddleProduct> getMiddelProductList(String itemcd);
    public List<CarallocationRentalStatus> getRentalStatus(String funno);
    public List<CarallocationReturnProduct> getReturnProduct(String funno);
    public CarallocationReturnDto saveReturnList(CarallocationReturnDto carallocationReturnDto);
    public String getCompare(String funno);
}
