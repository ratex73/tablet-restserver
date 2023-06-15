package kr.co.yedaham.tablet.restserver.restserver.service.product;

import kr.co.yedaham.tablet.restserver.restserver.model.product.ContractDetailList;
import kr.co.yedaham.tablet.restserver.restserver.model.product.ContractDusanList;
import kr.co.yedaham.tablet.restserver.restserver.model.product.ContractList;
import kr.co.yedaham.tablet.restserver.restserver.model.product.ProductList;
import kr.co.yedaham.tablet.restserver.restserver.resp.product.ProductResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductResp productResp;

    @Override
    public ArrayList<ContractList> getProductYedahamOneTwoList(String certno, String functrlno) {
        ArrayList<ContractList> productList = new ArrayList<>();
        return productList = productResp.findProductYedahamOneTwoList(certno, functrlno);
    }

    @Override
    public ArrayList<ContractDusanList> getDusanProductList(String certno) {
        ArrayList<ContractDusanList> dusanList = new ArrayList<>();
        return dusanList = productResp.findDusanProductList(certno);
    }

    @Override
    public ArrayList<ContractList> getNewProductList(String certno, String functrlno) {
        ArrayList<ContractList> newList = new ArrayList<>();
        return newList = productResp.findNewProductList(certno, functrlno);
    }

    @Override
    public ArrayList<ContractList> getOptionalProductList(String certno, String functrlno) {
      ArrayList<ContractList> optionalList = new ArrayList<>();
      return optionalList = productResp.findOptionalProductList(certno, functrlno);
    }

  @Override
    public ArrayList<ContractDetailList> getContractDetailList(String certNo) {
        ArrayList<ContractDetailList> contractDetailLis = new ArrayList<>();
        return  contractDetailLis = productResp.findContractDetailList(certNo);
    }
}
