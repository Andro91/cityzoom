package zoom.city.android.main.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataItem implements Comparable<DataItem>, Serializable {

	private String id;
	private String title;
	private String description;
	private String image;
	private String date;
	private String category;
	private String company;
	private String titleIndicator;
	private String microlocation;
	private String genre;
	private String street;
	private String web;
	private String city;
	private String author;
	private String time;
	private String image2;
	private String zona;
	private String x;
	private String y;
	private String logo;
	private String fax;
	private String previewtype;
	private String phone1;
	private String phone2;
	private String phone3;
	private String email;
	private String image3;
	private String companyId;
	private String share;
	private ArrayList<String> slider;
	private String officialWebpage;
	private ArrayList<String> logoSlider;
	private String video;
	
	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public ArrayList<String> getLogoSlider() {
		return logoSlider;
	}

	public void setLogoSlider(ArrayList<String> logoSlider) {
		this.logoSlider = logoSlider;
	}

	public String getOfficialWebpage() {
		return officialWebpage;
	}

	public void setOfficialWebpage(String officialWebpage) {
		this.officialWebpage = officialWebpage;
	}

	public ArrayList<String> getSlider() {
		return slider;
	}

	public void setSlider(ArrayList<String> slider) {
		this.slider = slider;
	}

	public String getShare() {
		return share;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public void setShare(String share) {
		this.share = share;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPreviewtype() {
		return previewtype;
	}

	public void setPreviewtype(String previewtype) {
		this.previewtype = previewtype;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getX() {
		return x;
	}

	public void setX(String x) {
		this.x = x;
	}

	public String getY() {
		return y;
	}

	public void setY(String y) {
		this.y = y;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	private List<DataItem> listDataItem = new ArrayList<DataItem>();

	public DataItem(String id) {
		this.id = id;
	}

	public DataItem() {
	}
	public DataItem(String title,String titleIndicator) {
		this.title = title;
		this.titleIndicator = titleIndicator;
	}
	
	public String getTitleIndicator() {
		return titleIndicator;
	}

	public void setTitleIndicator(String titleIndicator) {
		this.titleIndicator = titleIndicator;
	}

	public String getMicrolocation() {
		return microlocation;
	}

	public void setMicrolocation(String microlocation) {
		this.microlocation = microlocation;
	}

	public String getCategory() {
		return category;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public List<DataItem> getListDataItem() {
		return listDataItem;
	}

	public void setListDataItem(List<DataItem> listDataItem) {
		this.listDataItem = listDataItem;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DataItem copy() {
		DataItem copy = new DataItem();
		copy.id = id;
		return copy;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(title);
		sb.append('\n');
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		DataItem other = (DataItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;

		return true;
	}

	@Override
	public int compareTo(DataItem another) {
		if (another == null)
			return 1;
		// sort descending, most recent first
		return another.id.compareTo(id);
	}

}