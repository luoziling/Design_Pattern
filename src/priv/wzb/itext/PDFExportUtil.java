package com.qianxin.command.util.tool;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.*;

/**
 * @program: command
 * @author: wangzibai01
 * @create: 2021-10-11 16:47
 * @description: yuzuki
 **/

public class PDFExportUtil {
	private static Integer onepagerow = 15;

	private static Integer towpagerow = 22;
	public static final String HTML = "E:\\模板\\test.html";
	public static void exportPDF(Map<String,Object> o) throws Exception {
		String templatePath = "D:\\Learning\\export\\test3.pdf";
		// 生成的新文件路径
		String newPDFPath = "D:\\Learning\\export\\test32.pdf";
		FileOutputStream out = new FileOutputStream(newPDFPath);;
//		TemplateExportParams
//		PdfExportUtil.exportPdf(templatePath,o,out);
	}

	// 利用模板生成pdf
	public static void pdfout(Map<String,Object> o) {

		// 模板路径
		String templatePath = "D:\\Learning\\export\\test4.pdf";
		// 生成的新文件路径
		String newPDFPath = "D:\\Learning\\export\\test44.pdf";

		PdfReader reader;
		FileOutputStream out;
		// 尝试改为单个bos
//		ByteArrayOutputStream bos;
		ByteArrayOutputStream[] bos;
		PdfStamper stamper;
		// 计算表格总页数
		int totalPages = 0;
		try {
//			BaseFont bf = BaseFont.createFont();
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
//			BaseFont bf = BaseFont.createFont("c://windows//fonts//simsun.ttc,1" , BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font FontChinese = new Font(bf, 5, Font.NORMAL);
			// 输出流
			out = new FileOutputStream(newPDFPath);
			// 读取pdf模板
			reader = new PdfReader(templatePath);
			int numberOfPages = reader.getNumberOfPages();
//			reader.getPageSize()
			// bos变更
			bos = new ByteArrayOutputStream[numberOfPages];
//			bos = new ByteArrayOutputStream();
			for (int page = 0; page < numberOfPages; page++) {
				bos[page] = new ByteArrayOutputStream();
				// 读取pdf模板
				reader = new PdfReader(templatePath);
				//生成输出流
				stamper = new PdfStamper(reader, bos[page]);
//				stamper = new PdfStamper(reader, bos);

//				stamper.insertPage(2,);
				//获取文本域
				AcroFields form = stamper.getAcroFields();
				//文字类的内容处理
				Map<String,String> datemap = (Map<String,String>)o.get("datemap");
				form.addSubstitutionFont(bf);
				for(String key : datemap.keySet()){
					Object value = datemap.get(key);
					if (value instanceof String){
						form.setField(key, (String) value);
					}
					if (value instanceof List){
						// 处理列表
						List<String> stringList = (List<String>) value;
//					form.setListSelection(key,stringList.toArray(new String[0]));
						String[] values = stringList.toArray(new String[0]);
//						if (Objects.nonNull(form.getListOptionExport(key))){
//							form.setListSelection(key,values);
//						}
						form.setListOption(key,values,values);
						form.setField(key,key);
						form.setFieldProperty(key,"textcolor",BaseColor.BLUE,null);
//						form.setFieldProperty(key,"flags",1,null);
//					form.
//					form.setListSelection()
					}

				}
				//图片类的内容处理
				Map<String,String> imgmap = (Map<String,String>)o.get("imgmap");
				for(String key : imgmap.keySet()) {
					String value = imgmap.get(key);
					String imgpath = value;
//				String imgpath = "https://img-blog.csdnimg.cn/07483d771bd14239bd41e1656e61636d.png?x-oss-process=image/watermark,type_ZHJvaWRzYW5zZmFsbGJhY2s,shadow_50,text_Q1NETiBA6aOe5buJ54Gs5bCR5bCG,size_20,color_FFFFFF,t_70,g_se,x_16";
					int pageNo = form.getFieldPositions(key).get(0).page;
					Rectangle signRect = form.getFieldPositions(key).get(0).position;
					float x = signRect.getLeft();
					float y = signRect.getBottom();
					//根据路径读取图片
					Image image = Image.getInstance(imgpath);
					//获取图片页面
					PdfContentByte under = stamper.getOverContent(pageNo);
//					PdfContentByte under = stamper.getUnderContent(pageNo);

					//图片大小自适应
					image.scaleToFit(signRect.getWidth(), signRect.getHeight());
					//添加图片
					image.setAbsolutePosition(x, y);
					under.addImage(image);
				}
				// 表格类处理
				// 表格类
				Map<String, List<List<String>>> listMap = (Map<String, List<List<String>>>) o.get("tableList");
				for (String key : listMap.keySet()) {
					List<List<String>> lists = listMap.get(key);
					// 模板只有一个表格
					if (page>0){
						break;
					}
					int pageNo = form.getFieldPositions(key).get(page).page;
					// 通过表格覆盖原有元素的方式写入，现在尝试追加而不是覆盖呢
//					PdfContentByte pcb = stamper.getOverContent(pageNo);
					// 尝试获取页面下方写入呢
					PdfContentByte pcb = stamper.getUnderContent(pageNo);

					// 设置是否覆盖其他元素，true代表不覆盖，其他元素在固定位置 否则被过量数据压缩导致变为相对位置
					stamper.setFreeTextFlattening(true);
					Rectangle signRect = form.getFieldPositions(key).get(page).position;
					// 设置变化的边界
					signRect.setUseVariableBorders(true);
//					signRect.
//					signRect.setTop(1000.0f);
					//表格位置
					int column = lists.get(0).size();
					int row = lists.size();
					// 计算总页数
					totalPages = calculatePageable(row);
					Document document = new Document();

					PdfPTable table = new PdfPTable(column);
					table.setLockedWidth(false);
					// 是否让表格强制保持在一页内
					table.setKeepTogether(true);
					table.setSplitLate(false);
//				table.set
					table.setSplitRows(true);
					table.setExtendLastRow(true);

//				PdfPTable table = new PdfPTable();
					// 表格宽度依赖rectangle
					float tatalWidth = signRect.getRight() - signRect.getLeft() - 1;
					// 获取总共有多少行 size
					int size = lists.get(0).size();
					// 设定每行宽度 第一行较宽 其他的类似
					float width[] = new float[size];
					for (int j = 0; j < size; j++) {
						if (j == 0) {
							width[j] = 60f;
						} else {
							width[j] = (tatalWidth - 60) / (size - 1);
						}
					}
					table.setTotalWidth(width);

//				table.set(true);
					// 表内元素的字体
					Font FontProve = new Font(bf, 10, 0);
					// 双重循环 填写数据 表格数据填写
					for (int i = 0; i < row; i++) {
						List<String> list = lists.get(i);
						for (int j = 0; j < column; j++) {
							Paragraph paragraph = new Paragraph(String.valueOf(list.get(j)), FontProve);
							PdfPCell cell = new PdfPCell(paragraph);
							// 设置表格宽度
							cell.setBorderWidth(1);
							// 设置表格垂直/水平居中
							cell.setVerticalAlignment(Element.ALIGN_CENTER);
							cell.setHorizontalAlignment(Element.ALIGN_CENTER);
							cell.setLeading(0, (float) 1.4);
							table.addCell(cell);
						}
					}
					// 表格写入到某个地方 推测 关键在这里 canvas可否转为某个画布的位置
//					table.writeSelectedRows(0, -1, signRect.getLeft(), signRect.getTop(), pcb);
//					PdfContentByte mypcf = new PdfContentByte(null);
					// 表格写入，signRect仅仅标记位置
					table.writeSelectedRows(0, -1, signRect.getLeft(), signRect.getTop(), pcb);
					// 判断表格一页还是多页
					if (totalPages == 1) {
						//获table页面
						PdfContentByte under = stamper.getOverContent(1);
						//添加table
						table.writeSelectedRows(0, -1, signRect.getLeft(), signRect.getTop(), under);

					} else {
						//目前模板中暂时解决分页方案 （动态增加空白模板填充值初始化给9个空白模板不包含收尾两个）
						for (int i = 1; i <= totalPages; i++) {
//							document.newPage();
							stamper.insertPage(i+1,PageSize.A4);
							// stamperNPE
							PdfContentByte under = stamper.getOverContent(i);
							if (i == 1) {
								//第一页显示9+头尾4条
								table.writeSelectedRows(0, onepagerow, signRect.getLeft(), signRect.getTop(), under);
							}
							//空白模板每页显示22条
							else {
								table.writeSelectedRows(onepagerow + towpagerow * (i - 2), onepagerow + towpagerow * (i - 1), 60, 800, under);
							}
						}
					}
				}
				stamper.setFormFlattening(true);// 如果为false，生成的PDF文件可以编辑，如果为true，生成的PDF文件不可以编辑
				stamper.close();
			}




			// 新建文档
			Document doc = new Document(PageSize.A4);
//			doc.setPageCount(2);
//			Font font = new Font(bf, 32);
//			// 添加有序列表
//			com.itextpdf.text.List orderedList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
//			orderedList.add(new ListItem("Item 1"));
//			orderedList.add(new ListItem("Item 2"));
//			orderedList.add(new ListItem("Item 3"));
//			orderedList.
//			doc.add(orderedList);

			//用于保存原页面内容,然后输出
			PdfCopy copy = new PdfCopy(doc, out);
//			PdfObject
//			copy.addToBody()
			doc.open();
			// -1用于首页重复计算问题
			// 拷贝所有页到导出文件
//			for (int i = 0; i < numberOfPages + totalPages-1; i++) {
//				PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos[i].toByteArray()), i+1);
//				copy.addPage(importPage);
//			}
			if (totalPages>1){
				for (int i = 0; i < totalPages; i++) {
					PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos[0].toByteArray()), i+1);
					copy.addPage(importPage);
				}
			}
			for (int i = 0; i < numberOfPages; i++) {
				PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos[i].toByteArray()), i+1);
				copy.addPage(importPage);
			}


			doc.close();
			out.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e);
		} catch (DocumentException e) {
			e.printStackTrace();
			System.out.println(e);
		}

	}

	/**
	 *  计算分页
	 */
	public static int calculatePageable(int totalRow) {
		int totalpage = 1;
		int page = (totalRow - onepagerow) % towpagerow == 0 ? (totalRow - onepagerow) / towpagerow + 1 : (totalRow - onepagerow) / towpagerow + 2;
		return totalRow < onepagerow ? totalpage : page;

	}


	/**
	 * 原封不动转换
	 * @param file
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static void createPdf(String file) throws IOException, DocumentException {
		// step 1
		Document document = new Document();
		// step 2
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
		writer.setInitialLeading(12);
		// step 3
		document.open();
		// step 4
		XMLWorkerHelper.getInstance().parseXHtml(writer, document,
				new FileInputStream(HTML), Charset.forName("UTF-8"));
		// step 5
		document.close();
	}

	public static void main(String[] args) throws Exception {
		Map<String,Object> map = new HashMap();
		map.put("name","李四");
		map.put("createDate","2018年1月1日");
		map.put("n1","晴朗");
		map.put("a1","打羽毛球");
		List<String> l1List = new ArrayList<>();
		l1List.add("l11测试");
		l1List.add("l12");
		l1List.add("l13");
		l1List.add("l14");
		l1List.add("l15");
		l1List.add("l16");
		l1List.add("l17");
		map.put("list_1",l1List);

		//表格 一行数据是一个list
		List<String> list = new ArrayList<String>();
		list.add("客户名称");
		list.add("销售");
		list.add("商机编号");
		list.add("商机额（万元）");
		list.add("预计落单时间");
		list.add("防守投入");
		list.add("产品");
		list.add("服务");
		List<List<String>> List = new ArrayList<List<String>>();
		List.add(list);
//		List<List<String>> temp = new ArrayList<List<String>>();
		Random rand = new Random(10000);
		for (int i = 0; i < 100; i++) {
			List<String> tempList = new ArrayList<>();
			tempList.add(i+ "测试数据1" + rand.nextInt());
			tempList.add(i+ "测试数据2" + rand.nextInt());
			tempList.add(i+ "测试数据3" + rand.nextInt());
			tempList.add(i+ "测试数据4" + rand.nextInt());
			tempList.add(i+ "测试数据5" + rand.nextInt());
			tempList.add(i+ "测试数据6" + rand.nextInt());
			tempList.add(i+ "测试数据7" + rand.nextInt());
			tempList.add(i+ "测试数据8" + rand.nextInt());
			List.add(tempList);
		}
		Map<String, List<List<String>>> listMap = new HashMap<String, List<List<String>>>();
		listMap.put("table", List);

//		List<String> list2 = new ArrayList<String>();
//		list2.add("2021-08-27");
//		list2.add("100000");


//		List.add(list2);
//		list.addAll(temp);

		Map<String,String> map2 = new HashMap();
		map2.put("img","D:\\Learning\\export\\1.jpg");

		Map<String,Object> o=new HashMap();
		o.put("datemap",map);
		o.put("imgmap",map2);
		o.put("tableList", listMap);
		pdfout(o);
//		exportPDF(o);

//		createPdf("D:\\Learning\\export\\testHtml.pdf");
	}
}
