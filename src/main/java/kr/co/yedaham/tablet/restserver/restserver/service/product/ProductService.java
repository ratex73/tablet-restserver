package kr.co.yedaham.tablet.restserver.restserver.service.product;

import kr.co.yedaham.tablet.restserver.restserver.model.product.ContractDetailList;
import kr.co.yedaham.tablet.restserver.restserver.model.product.ContractDusanList;
import kr.co.yedaham.tablet.restserver.restserver.model.product.ContractList;

import java.util.ArrayList;

public interface ProductService {
    public ArrayList<ContractList> getProductYedahamOneTwoList(String certno, String functrlno);
    public ArrayList<ContractDusanList> getDusanProductList(String certno, String functrlno);
    public ArrayList<ContractList> getNewProductList(String certno, String functrlno);
    public ArrayList<ContractList> getOptionalProductList(String certno, String functrlno);
    public ArrayList<ContractDetailList> getContractDetailList(String certNo);
}
