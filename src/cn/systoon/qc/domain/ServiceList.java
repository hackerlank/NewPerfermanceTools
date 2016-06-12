package cn.systoon.qc.domain;

public class ServiceList {
	private Integer id;
	private String category;
	private Enum code;
	private String glaxy;
	private Integer priority;
	private String redis;
	private String developer;
	private String projectCN;
	private String project;
	private String warname;
	private String publiced;
	private String prdomain;
	private String virtualname;
	private String datasource;
	private String dbname;
	private String mycatsource;
	private String mycatdata;
	private String svnurl;
	private String procomment;
	private String devIp;
	private String devPublicIp;
	private String testIp;
	private String testPublicIp;
	private String pressIp;
	private String devSvnUrl;
	private String testSvnUrl;
	private String pressSvnUrl;
	private String preproIp;
	private String preproPubIp;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Enum getCode() {
		return code;
	}
	public void setCode(Enum code) {
		this.code = code;
	}
	public String getGlaxy() {
		return glaxy;
	}
	public void setGlaxy(String glaxy) {
		this.glaxy = glaxy;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getRedis() {
		return redis;
	}
	public void setRedis(String redis) {
		this.redis = redis;
	}
	public String getDeveloper() {
		return developer;
	}
	public void setDeveloper(String developer) {
		this.developer = developer;
	}
	public String getProjectCN() {
		return projectCN;
	}
	public void setProjectCN(String projectCN) {
		this.projectCN = projectCN;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getWarname() {
		return warname;
	}
	public void setWarname(String warname) {
		this.warname = warname;
	}
	public String getPubliced() {
		return publiced;
	}
	public void setPubliced(String publiced) {
		this.publiced = publiced;
	}
	public String getPrdomain() {
		return prdomain;
	}
	public void setPrdomain(String prdomain) {
		this.prdomain = prdomain;
	}
	public String getVirtualname() {
		return virtualname;
	}
	public void setVirtualname(String virtualname) {
		this.virtualname = virtualname;
	}
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	public String getDbname() {
		return dbname;
	}
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}
	public String getMycatsource() {
		return mycatsource;
	}
	public void setMycatsource(String mycatsource) {
		this.mycatsource = mycatsource;
	}
	public String getMycatdata() {
		return mycatdata;
	}
	public void setMycatdata(String mycatdata) {
		this.mycatdata = mycatdata;
	}
	public String getSvnurl() {
		return svnurl;
	}
	public void setSvnurl(String svnurl) {
		this.svnurl = svnurl;
	}
	public String getProcomment() {
		return procomment;
	}
	public void setProcomment(String procomment) {
		this.procomment = procomment;
	}
	public String getDevIp() {
		return devIp;
	}
	public void setDevIp(String devIp) {
		this.devIp = devIp;
	}
	public String getDevPublicIp() {
		return devPublicIp;
	}
	public void setDevPublicIp(String devPublicIp) {
		this.devPublicIp = devPublicIp;
	}
	public String getTestIp() {
		return testIp;
	}
	public void setTestIp(String testIp) {
		this.testIp = testIp;
	}
	public String getTestPublicIp() {
		return testPublicIp;
	}
	public void setTestPublicIp(String testPublicIp) {
		this.testPublicIp = testPublicIp;
	}
	public String getPressIp() {
		return pressIp;
	}
	public void setPressIp(String pressIp) {
		this.pressIp = pressIp;
	}
	public String getDevSvnUrl() {
		return devSvnUrl;
	}
	public void setDevSvnUrl(String devSvnUrl) {
		this.devSvnUrl = devSvnUrl;
	}
	public String getTestSvnUrl() {
		return testSvnUrl;
	}
	public void setTestSvnUrl(String testSvnUrl) {
		this.testSvnUrl = testSvnUrl;
	}
	public String getPressSvnUrl() {
		return pressSvnUrl;
	}
	public void setPressSvnUrl(String pressSvnUrl) {
		this.pressSvnUrl = pressSvnUrl;
	}
	public String getPreproIp() {
		return preproIp;
	}
	public void setPreproIp(String preproIp) {
		this.preproIp = preproIp;
	}
	public String getPreproPubIp() {
		return preproPubIp;
	}
	public void setPreproPubIp(String preproPubIp) {
		this.preproPubIp = preproPubIp;
	}
	public ServiceList(Integer id, String category, Enum code, String glaxy, Integer priority, String redis,
			String developer, String projectCN, String project, String warname, String publiced, String prdomain,
			String virtualname, String datasource, String dbname, String mycatsource, String mycatdata, String svnurl,
			String procomment, String devIp, String devPublicIp, String testIp, String testPublicIp, String pressIp,
			String devSvnUrl, String testSvnUrl, String pressSvnUrl, String preproIp, String preproPubIp) {
		super();
		this.id = id;
		this.category = category;
		this.code = code;
		this.glaxy = glaxy;
		this.priority = priority;
		this.redis = redis;
		this.developer = developer;
		this.projectCN = projectCN;
		this.project = project;
		this.warname = warname;
		this.publiced = publiced;
		this.prdomain = prdomain;
		this.virtualname = virtualname;
		this.datasource = datasource;
		this.dbname = dbname;
		this.mycatsource = mycatsource;
		this.mycatdata = mycatdata;
		this.svnurl = svnurl;
		this.procomment = procomment;
		this.devIp = devIp;
		this.devPublicIp = devPublicIp;
		this.testIp = testIp;
		this.testPublicIp = testPublicIp;
		this.pressIp = pressIp;
		this.devSvnUrl = devSvnUrl;
		this.testSvnUrl = testSvnUrl;
		this.pressSvnUrl = pressSvnUrl;
		this.preproIp = preproIp;
		this.preproPubIp = preproPubIp;
	}
	public ServiceList() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "ServiceList [id=" + id + ", category=" + category + ", code=" + code + ", glaxy=" + glaxy
				+ ", priority=" + priority + ", redis=" + redis + ", developer=" + developer + ", projectCN="
				+ projectCN + ", project=" + project + ", warname=" + warname + ", publiced=" + publiced + ", prdomain="
				+ prdomain + ", virtualname=" + virtualname + ", datasource=" + datasource + ", dbname=" + dbname
				+ ", mycatsource=" + mycatsource + ", mycatdata=" + mycatdata + ", svnurl=" + svnurl + ", procomment="
				+ procomment + ", devIp=" + devIp + ", devPublicIp=" + devPublicIp + ", testIp=" + testIp
				+ ", testPublicIp=" + testPublicIp + ", pressIp=" + pressIp + ", devSvnUrl=" + devSvnUrl
				+ ", testSvnUrl=" + testSvnUrl + ", pressSvnUrl=" + pressSvnUrl + ", preproIp=" + preproIp
				+ ", preproPubIp=" + preproPubIp + "]";
	}
	
	
	

}

