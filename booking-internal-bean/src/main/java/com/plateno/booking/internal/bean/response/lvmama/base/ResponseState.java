package com.plateno.booking.internal.bean.response.lvmama.base;

/**
 * @author user
 * 
 * 驴妈妈返回信息
 *
 */
public class ResponseState {
	
	private State state;
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}

	public static class State{
		private String code;
		
		private String message;
		
		private String solution;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public String getSolution() {
			return solution;
		}

		public void setSolution(String solution) {
			this.solution = solution;
		}

		@Override
		public String toString() {
			return "ResponseState [code=" + code + ", message=" + message
					+ ", solution=" + solution + "]";
		}
	}

}