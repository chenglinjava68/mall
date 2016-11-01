package com.plateno.booking.internal.dao.pojo.bill;

import java.util.List;

public class OrderSelectReturn {
	
	private List<MBillReturn> mBillReturns;
	
	private int pageNum;
	
	private int total;
	
	private int pageTotle;
	
	public List<MBillReturn> getmBillReturns() {
		return mBillReturns;
	}

	public void setmBillReturns(List<MBillReturn> mBillReturns) {
		this.mBillReturns = mBillReturns;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageTotle() {
		return pageTotle;
	}

	public void setPageTotle(int pageTotle) {
		this.pageTotle = pageTotle;
	}
	
	public static class Rule{
		private String isChange;	//是否可退款
		private String aheadTime;	//可申请时间
		private String deductionType;	//扣费类型
		private double deductionValue;	//扣费值
		
		public String getIsChange() {
			return isChange;
		}
		public void setIsChange(String isChange) {
			this.isChange = isChange;
		}
		public String getAheadTime() {
			return aheadTime;
		}
		public void setAheadTime(String aheadTime) {
			this.aheadTime = aheadTime;
		}
		public String getDeductionType() {
			return deductionType;
		}
		public void setDeductionType(String deductionType) {
			this.deductionType = deductionType;
		}
		public double getDeductionValue() {
			return deductionValue;
		}
		public void setDeductionValue(double deductionValue) {
			this.deductionValue = deductionValue;
		}
	}
}

