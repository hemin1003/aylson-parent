package com.aylson.dc.base.common;

import java.text.SimpleDateFormat;
import java.util.Date;


public class BillSNCreateFactory {

	//当前订单编号流水
	private volatile static long currentMaxBillId = 0;
	private static Integer SN_LENGTH = 5;
	
	public static synchronized long createBillSN(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String SNDate = sdf.format(new Date());
		String CurrentSNDate = "";
		
		if(String.valueOf(currentMaxBillId).length()>8){
			CurrentSNDate = String.valueOf(currentMaxBillId).substring(0, 8);
		}
		if(CurrentSNDate.equals(SNDate)){
			return ++currentMaxBillId;
		}else{
			SerialNumber serial = new BillSNEveryDaySerialNumber(SN_LENGTH);
			String id = serial.getSerialNumber(1); 
			currentMaxBillId = Long.valueOf(id);
			return currentMaxBillId;
		}
	}
	
	
	public static void setCurrentMaxBillSN(long value) {
		currentMaxBillId = value ;
	}
	
		
	public static void main(String[] args) {
		String aa = "XJQ2017022200012";
		BillSNCreateFactory.setCurrentMaxBillSN(Long.parseLong(aa.substring(3)));
		for(int i=0;i<12;i++){
			System.out.println(BillSNCreateFactory.createBillSN());
		}
		
	}
}
