package priv.wzb.excel;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.collection.CollUtil;
import com.google.common.base.Strings;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: Design_Pattern
 * @author: wangzibai01
 * @create: 2021-08-16 14:17
 * @description:
 **/

public class CuImportWithCyclicCheckTest {
	@Test
	public void importMajorCustomerLevel() throws Exception{
		String fileName = "test.xlsx";
		File file = new File(fileName);
		FileInputStream inputStream = new FileInputStream(file);
		MockMultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(), ContentType.APPLICATION_OCTET_STREAM.toString(), inputStream);

		ImportParams params = new ImportParams();
		params.setHeadRows(1);

		List<MajorUserLevelRelationExcelPO> excelList = ExcelImportUtil.importExcel(multipartFile.getInputStream(), MajorUserLevelRelationExcelPO.class, params);;
		List<MajorUserLevelRelationPO> saveList = new ArrayList<>();
		for (MajorUserLevelRelationExcelPO excel : excelList) {
			MajorUserLevelRelationPO po = new MajorUserLevelRelationPO();
			po.setMajorCustomerId(excel.getMajorCustomerId());
			po.setParentId(Objects.isNull(excel.getParentId())?0:excel.getParentId());
			po.setMajorCustomerType(Strings.isNullOrEmpty(excel.getMajorCustomerType())?
					5 : 1);
			saveList.add(po);
		}
		System.out.println("saveList = " + saveList);
		Map<Integer,Integer> loopMap = new HashMap<>();
		// 死循环判断
		for (MajorUserLevelRelationPO po : saveList) {
			if (Objects.isNull(po) || Objects.isNull(po.getMajorCustomerId())){
				continue;
			}
			List<Integer> upperNodeList = new ArrayList<>();
//			upperNodeList = saveList.stream().filter(e->e.getParentId().equals(0)).map(MajorUserLevelRelationPO::getMajorCustomerId).collect(Collectors.toList());
			upperNodeList.add(po.getMajorCustomerId());
			// 记录遍历过的子节点
			List<Integer> lowerNodeList = new ArrayList<>();
			List<Integer> temporaryLowerNodeList = new ArrayList<>();
			while (!CollUtil.isEmpty(upperNodeList)){
				// 查询下一级
				for (MajorUserLevelRelationPO node : saveList) {
					if (upperNodeList.contains(node.getParentId())){
						temporaryLowerNodeList.add(node.getMajorCustomerId());
					}
				}
				// 清空upperNodeList 将当前遍历出的作为下一次遍历的父节点
				upperNodeList.clear();
				upperNodeList.addAll(temporaryLowerNodeList);
				// 判断数据是否死循环
				List<Integer> intersection = lowerNodeList.stream().filter(temporaryLowerNodeList::contains).collect(Collectors.toList());
				if (!CollUtil.isEmpty(intersection)){
					throw new RuntimeException("数据出现循环,附近的大用户ID：" + intersection.toString());
//				break;
				}
				lowerNodeList.addAll(temporaryLowerNodeList);
				temporaryLowerNodeList.clear();
			}
		}



//		saveList.forEach(e->{
//			loopMap.put(e.getMajorCustomerId(),e.getParentId());
//		});
//		for (Map.Entry<Integer, Integer> entry : loopMap.entrySet()) {
//			Integer value = entry.getValue();
//			Integer value1 = loopMap.get(value);
//			if (Objects.nonNull()){
//
//			}
//		}

//		majorCustomerLevelRelationService.saveBatch(saveList);
	}
}
