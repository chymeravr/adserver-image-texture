package co.chimeralabs.ad.server.apiDTO;

public class AdRequestDTO {
	private AdType adType;
	private String adUnitId;
	private Integer nDistinctAds;
	
	public AdType getAdType() {
		return adType;
	}

	public void setAdType(AdType adType) {
		this.adType = adType;
	}

	public String getAdUnitId() {
		return adUnitId;
	}

	public void setAdUnitId(String adUnitId) {
		this.adUnitId = adUnitId;
	}

	public Integer getnDistinctAds() {
		return nDistinctAds;
	}

	public void setnDistinctAds(Integer nDistinctAds) {
		this.nDistinctAds = nDistinctAds;
	}

}
