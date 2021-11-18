package priv.wzb.itext;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-10-29 16:05
 * @description: 修改表格属性处理，自定义表格处理时定义的表格属性
 **/

public class TableTagProcessor extends com.itextpdf.tool.xml.html.table.Table {

	@Override
	protected PdfPTable intPdfPTable(int numberOfColumn) {
		PdfPTable table = new PdfPTable(numberOfColumn);

		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		// 避免分页导致一行上下分割
		table.setSplitLate(true);
//		table.setSplitLate(false);
//		table.setSplitRows(false);

		return table;
	}
}
