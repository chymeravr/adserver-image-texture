package co.chimeralabs.ad.server.apiDTO;

public class AnalyticsLogDTO {
	private Integer type;
	private String dtoObj;
	
	public AnalyticsLogDTO(){
		
	}
	
	public AnalyticsLogDTO(Integer type, String dtoObj){
		this.type = type;
		this.dtoObj = dtoObj;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getDtoObj() {
		return dtoObj;
	}
	public void setDtoObj(String dtoObj) {
		this.dtoObj = dtoObj;
	}
	
}
