package priv.wzb.design_pattern.structural_pattern.proxypattern.dynamicproxy;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * @author Satsuki
 * @time 2019/5/19 15:04
 * @description:
 */
public interface AbstractDocumentDAO {
    public Boolean deleteDocumentById(String documentId);
}
