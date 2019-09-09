package priv.wzb.design_pattern.structural_pattern.proxypattern.dynamicproxy;

/**
 * @author Satsuki
 * @time 2019/5/19 15:09
 * @description:
 */
public class DocumentDAO implements AbstractDocumentDAO {
    @Override
    public Boolean deleteDocumentById(String documentId) {
        if(documentId.equalsIgnoreCase("D001")){
            System.out.println("delete document with ID" + documentId + "success");
            return true;
        }else {
            System.out.println("delete document with ID" + documentId + "failure");
            return false;
        }
    }
}
