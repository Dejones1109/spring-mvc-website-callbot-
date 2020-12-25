package vnteleco.com.export;

import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import vnteleco.com.entity.dto.ExportDataDto;

@Component
public class ExportReportData {

	public SXSSFWorkbook exportBigDataExcel(List<ExportDataDto> listExportDataDto) {
		long startTime = System.currentTimeMillis(); // start time
		System.out.println("start execute time: " + startTime);

		// 1.Create A Workbook
		SXSSFWorkbook wb = new SXSSFWorkbook(1000);

		// 2.stay Workbook Add a sheet,Corresponding Excel In the document sheet
		Sheet sheet = wb.createSheet();

		// 3.Setting Styles and Font Styles
		CellStyle titleCellStyle = createTitleCellStyle(wb);
		CellStyle cellStyle = createCellStyle(wb);

		// 4.Create titles, headers, contents, and merge cells
		int rowNum = 0;// Line number

		// The title line
		Row row0 = sheet.createRow(rowNum++);
		row0.setHeight((short) 700);
		String[] rows = { "Call ID", "Hotline", "Số gọi đến", "Thời gian", "Tình trạng", "Thời lượng", "Nội dung hội thoại","File ghi âm" };
		for (int i = 0; i < rows.length; i++) {
			Cell tempCell = row0.createCell(i);
			tempCell.setCellValue(rows[i]);
			tempCell.setCellStyle(titleCellStyle);
		}		
		
		// data processing
		for (ExportDataDto exportDataDto : listExportDataDto) {
			Row tempRow = sheet.createRow(rowNum++);
			tempRow.setHeight((short) 500);
			
			for (int j = 0; j < 8; j++) {
								
				Cell tempCell = tempRow.createCell(j);
				tempCell.setCellStyle(cellStyle);
				String tempValue;
				
				if (j == 0) {
					tempValue = exportDataDto.getConversationId();
				} else if (j == 1) {
					tempValue = exportDataDto.getHotline();
				} else if (j == 2) {
					tempValue = exportDataDto.getMsisdn();
				} else if (j == 3) {
					tempValue = exportDataDto.getCallAt();
				} else if (j == 4) {
					tempValue = exportDataDto.getStatus();
				} else if (j == 5) {
					tempValue = exportDataDto.getCallTime();
				} else if (j == 6) {
					tempValue = exportDataDto.getContent();
				} else {
					tempValue = exportDataDto.getAudioFileUrl();
				}
				
				tempCell.setCellValue(tempValue);

			}
		}

		// Setting column widths must follow cell values
		sheet.setColumnWidth(0, 4000);// Name
		sheet.setColumnWidth(1, 3000);// Number of registrations(people)
		sheet.setColumnWidth(2, 3000);// Total number of certificates(people)
		sheet.setColumnWidth(3, 3000);// Certification rate(%)
		sheet.setColumnWidth(4, 3000);// Number of registered households(household)
		sheet.setColumnWidth(5, 6000);// Registration time
		sheet.setColumnWidth(6, 4000);// Remarks
		sheet.setColumnWidth(7, 4000);// Remarks

		long finishedTime = System.currentTimeMillis(); // Processing completion time
		System.out.println("finished execute  time: " + (finishedTime - startTime) / 1000 + "m");

		return wb;

	}

	private static CellStyle createTitleCellStyle(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setTopBorderColor(IndexedColors.BLACK.index);

		cellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}

	private static CellStyle createHeadCellStyle(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setTopBorderColor(IndexedColors.BLACK.index);

		cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		return cellStyle;
	}

	private static CellStyle createCellStyle(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBottomBorderColor(IndexedColors.BLACK.index);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setLeftBorderColor(IndexedColors.BLACK.index);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setRightBorderColor(IndexedColors.BLACK.index);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setTopBorderColor(IndexedColors.BLACK.index);

		return cellStyle;
	}
}
