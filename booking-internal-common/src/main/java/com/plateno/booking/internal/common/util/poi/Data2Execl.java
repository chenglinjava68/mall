package com.plateno.booking.internal.common.util.poi;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.plateno.booking.internal.bean.contants.ConstEnum.ChannelEnum;
import com.plateno.booking.internal.bean.contants.OrderStatusEnum;
import com.plateno.booking.internal.bean.contants.PayTypeEnum;
import com.plateno.booking.internal.bean.contants.ViewStatusEnum;
import com.plateno.booking.internal.bean.request.common.EmailExecl;
import com.plateno.booking.internal.common.util.LogUtils;
import com.plateno.booking.internal.common.util.date.DateUtil;

/**
 * poi 导出数据工具类
 * 
 * @author user
 *
 */
public class Data2Execl {

	/**
	 * @param list		导出对象
	 * @param fileName	文件名
	 * @throws Exception 
	 */
	public static void saveWithDisks(List<EmailExecl> list,String fileName) throws Exception {
		// 创建Excel的工作书册 Workbook,对应到一个excel文档
		HSSFWorkbook wb = new HSSFWorkbook();

		// 创建Excel的工作sheet,对应到一个excel文档的tab
		HSSFSheet sheet = wb.createSheet("邮件发送附件");
		
		for (int i = 0; i < 12; i++) {
			sheet.setColumnWidth(i, 6500);		//设置单元格的宽度
		}
		
		HSSFCellStyle setBorder = wb.createCellStyle();

		//设置字体样式
		HSSFFont font = wb.createFont();
		font.setFontName("微软雅黑");
		font.setFontHeightInPoints((short) 12);
		setBorder.setFont(font);

		// 设置背景色
		setBorder.setFillForegroundColor(HSSFColor.YELLOW.index);
		setBorder.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		//设置字体居中
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		//设置边框
		setBorder.setBorderBottom(HSSFCellStyle.BORDER_THIN); 
		setBorder.setBorderRight(HSSFCellStyle.BORDER_THIN);

		HSSFCellStyle setBorder2 = wb.createCellStyle();

		HSSFFont font2 = wb.createFont();
		font2.setFontName("微软雅黑");
		font2.setFontHeightInPoints((short) 12);// 设置字体大小
		setBorder2.setFont(font);// 选择需要用到的字体格式
		setBorder2.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中

		// 创建Excel的sheet的一行
		HSSFRow row = sheet.createRow(0);

		// 创建一个Excel的单元格
		HSSFCell cell0 = row.createCell(0);
		cell0.setCellValue("铂涛订单号");
		cell0.setCellStyle(setBorder);

		HSSFCell cell1 = row.createCell(1);
		cell1.setCellValue("OTA订单号");
		cell1.setCellStyle(setBorder);

		HSSFCell cell2 = row.createCell(2);
		cell2.setCellValue("支付类型");
		cell2.setCellStyle(setBorder);

		HSSFCell cell3 = row.createCell(3);
		cell3.setCellValue("供应商");
		cell3.setCellStyle(setBorder);

		HSSFCell cell4 = row.createCell(4);
		cell4.setCellValue("下单时间");
		cell4.setCellStyle(setBorder);

		HSSFCell cell5 = row.createCell(5);
		cell5.setCellValue("状态更新时间");
		cell5.setCellStyle(setBorder);

		HSSFCell cell6 = row.createCell(6);
		cell6.setCellValue("底层状态");
		cell6.setCellStyle(setBorder);

		HSSFCell cell7 = row.createCell(7);
		cell7.setCellValue("前端状态");
		cell7.setCellStyle(setBorder);

		HSSFCell cell8 = row.createCell(8);
		cell8.setCellValue("订单金额(单位：元)");
		cell8.setCellStyle(setBorder);

		HSSFCell cell9 = row.createCell(9);
		cell9.setCellValue("取票人姓名");
		cell9.setCellStyle(setBorder);

		HSSFCell cell10 = row.createCell(10);
		cell10.setCellValue("取票人手机号");
		cell10.setCellStyle(setBorder);

		HSSFCell cell11 = row.createCell(11);
		cell11.setCellValue("备注信息");
		cell11.setCellStyle(setBorder);

		for (int i = 0; i < list.size(); i++) {
			HSSFRow row2 = sheet.createRow(i + 1);
			
			EmailExecl emailExecl = list.get(i);
			HSSFCell cell00 = row2.createCell(0);
			cell00.setCellValue(emailExecl.getTradeNo());
			cell00.setCellStyle(setBorder2);

			HSSFCell cell01 = row2.createCell(1);
			cell01.setCellValue(emailExecl.getPartnerOrderId());
			cell01.setCellStyle(setBorder2);

			HSSFCell cell02 = row2.createCell(2);
			Integer payId = emailExecl.getPayType();
			cell02.setCellValue(payId == 0 ? "未支付" : PayTypeEnum.getPayType(payId).getCodeName());
			cell02.setCellStyle(setBorder2);

			HSSFCell cell03 = row2.createCell(3);
			cell03.setCellValue(ChannelEnum.getChannel(emailExecl.getChannel()).getChannelName());
			cell03.setCellStyle(setBorder2);

			HSSFCell cell04 = row2.createCell(4);
			cell04.setCellValue(emailExecl.getCreateTime());
			cell04.setCellStyle(setBorder2);

			HSSFCell cell05 = row2.createCell(5);
			cell05.setCellValue(emailExecl.getUpdateTime());
			cell05.setCellStyle(setBorder2);

			HSSFCell cell06 = row2.createCell(6);
			cell06.setCellValue(OrderStatusEnum.getOrderStatus(emailExecl.getStatus()) == null ? null : OrderStatusEnum.getOrderStatus(emailExecl.getStatus()).getCodeName());
			cell06.setCellStyle(setBorder2);

			HSSFCell cell07 = row2.createCell(7);
			cell07.setCellValue(emailExecl.getViewStatus() == null ? null : ViewStatusEnum.getViewStatus(emailExecl.getViewStatus()).getCodeName());
			cell07.setCellStyle(setBorder2);

			HSSFCell cell08 = row2.createCell(8);
			cell08.setCellValue(emailExecl.getMoney().toString());
			cell08.setCellStyle(setBorder2);

			HSSFCell cell09 = row2.createCell(9);
			cell09.setCellValue(emailExecl.getUserName());
			cell09.setCellStyle(setBorder2);

			HSSFCell cell010 = row2.createCell(10);
			cell010.setCellValue(emailExecl.getMobile());
			cell010.setCellStyle(setBorder2);

			HSSFCell cell011 = row2.createCell(11);
			cell011.setCellValue(emailExecl.getRemark());
			cell011.setCellStyle(setBorder2);
		}
		String time = DateUtil.dateToFormatStr(new Date(), DateUtil.YYYY_MM_DD);
        LogUtils.sysErrorLoggerInfo("文件存放位置：" + System.getProperty("user.dir")+ System.getProperty("file.separator") +"send" + System.getProperty("file.separator") +fileName+"_"+time+".xls");
        FileOutputStream os = new FileOutputStream(System.getProperty("user.dir")+ System.getProperty("file.separator") + "send" + System.getProperty("file.separator") + fileName+"_"+time+".xls");
		wb.write(os);
		os.close();
	}

}
