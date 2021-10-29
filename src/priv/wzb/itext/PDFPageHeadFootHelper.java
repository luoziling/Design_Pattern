package priv.wzb.itext;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-10-25 16:48
 * @description:
 **/
public class PDFPageHeadFootHelper extends PdfPageEventHelper {
	private Document doc;

	public PDFPageHeadFootHelper(Document doc) {
		this.doc = doc;
	}

	@Override
	public void onEndPage(PdfWriter writer, Document document) {
		BaseFont bf = null;
		try {
			bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Font headFont = new Font(bf, 10, Font.NORMAL);
//		Font headFont = FontFactory.getFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 9, Font.NORMAL, BaseColor.BLACK);
		//添加标题文本
		StringBuffer underline = new StringBuffer();
		for(int i = 0;i<116;i++) {
			underline.append("_");
		}
		Phrase contentPh = new Phrase("这是是页眉",headFont);
		Phrase underlinePh = new Phrase(underline.toString(),headFont);
		Phrase pageNumberPh = new Phrase(String.valueOf(doc.getPageNumber()),headFont);
		float center = doc.getPageSize().getRight()/2;//页面的水平中点
		float top = doc.getPageSize().getTop()-36;
		float bottom = doc.getPageSize().getBottom()+36;

		/** 参数xy是指文本显示的页面上的哪个店。alignment指文本在坐标点的对齐方式 */
		ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER,contentPh,center,top,0); //页眉
		ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER,underlinePh,center,top-3,0); //页眉
		ColumnText.showTextAligned(writer.getDirectContent(),Element.ALIGN_CENTER,pageNumberPh,center,bottom,0); //页码
	}

	public static void main(String[] args) {
		java.util.List<Integer> list = new ArrayList<>();
		System.out.println("list.get(1) = " + list.get(1));
	}
}
