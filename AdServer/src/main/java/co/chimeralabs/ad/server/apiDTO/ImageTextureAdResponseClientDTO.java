package co.chimeralabs.ad.server.apiDTO;

public class ImageTextureAdResponseClientDTO {
	private String resourceURL;
	private String errorMsg;
	private Integer resourceErrorCode;
	
	public String getResourceURL() {
		return resourceURL;
	}

	public void setResourceURL(String resourceURL) {
		this.resourceURL = resourceURL;
	}

	public Integer getResourceErrorCode() {
		return resourceErrorCode;
	}

	public void setResourceErrorCode(Integer resourceErrorCode) {
		this.resourceErrorCode = resourceErrorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
