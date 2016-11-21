package co.chimeralabs.ad.server.apiDTO;

public class AdResourceMetadata {
	private String adServingId;
	private String diffuseTextureImageUrl;
	private Integer errorCode;
	private String errorMsg;
	public String getAdServingId() {
		return adServingId;
	}
	public void setAdServingId(String adServingId) {
		this.adServingId = adServingId;
	}
	public String getDiffuseTextureImageUrl() {
		return diffuseTextureImageUrl;
	}
	public void setDiffuseTextureImageUrl(String diffuseTextureImageUrl) {
		this.diffuseTextureImageUrl = diffuseTextureImageUrl;
	}
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
