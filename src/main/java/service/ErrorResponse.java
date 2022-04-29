package service;

public class ErrorResponse {
	private String err;
	
	public ErrorResponse(String err) {
		this.setErr(err);
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}
	
}
