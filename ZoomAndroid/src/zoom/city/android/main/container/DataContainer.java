package zoom.city.android.main.container;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

import zoom.city.android.main.data.DataItem;

public class DataContainer {
	// CONSTUCTOR
	private List<DataItem> kategorijaSearchItems = new ArrayList<DataItem>();
	private List<DataItem> drzaveItems = new ArrayList<DataItem>();
	private List<DataItem> jeziciItems = new ArrayList<DataItem>();
	
	private Map<String, List<DataItem>> dataItemList = new HashMap<String, List<DataItem>>();
	
	private Map<String, List<DataItem>> bigBanerItemList = new HashMap<String, List<DataItem>>();
	private Map<String, List<DataItem>> smallBanerItemList = new HashMap<String, List<DataItem>>();
	
	private Map<Integer, List<DataItem>> mapDataList = new HashMap<Integer, List<DataItem>>();
	
	private List<DataItem> taxiSmsList = new ArrayList<DataItem>();
	
	public static ArrayList<String> androSliderUrlList = new ArrayList<String>();
	
	public static HashMap<String, String> androTransitUrlList = new HashMap<String, String>();
	public static HashMap<String, Bitmap> androTransitImageList = new HashMap<String, Bitmap>();
	
	public static ArrayList<String> bigBanerUrlList = new ArrayList<String>();

	public List<DataItem> getTaxiSmsList() {
		return taxiSmsList;
	}

	public void setTaxiSmsList(List<DataItem> taxiSmsList) {
		this.taxiSmsList = taxiSmsList;
	}

	

	public Map<Integer, List<DataItem>> getMapDataList() {
		return mapDataList;
	}

	public void setMapDataList(Map<Integer, List<DataItem>> mapDataList) {
		this.mapDataList = mapDataList;
	}

	public Map<String, List<DataItem>> getSmallBanerItemList() {
		return smallBanerItemList;
	}

	public void setSmallBanerItemList(Map<String, List<DataItem>> smallBanerItemList) {
		this.smallBanerItemList = smallBanerItemList;
	}

	public Map<String, List<DataItem>> getBigBanerItemList() {
		return bigBanerItemList;
	}

	public void setBigBanerItemList(Map<String, List<DataItem>> banerItemList) {
		this.bigBanerItemList = banerItemList;
	}

	public Map<String, List<DataItem>> getDataItemList() {
		return dataItemList;
	}

	public void setDataItemList(Map<String, List<DataItem>> dataItemList) {
		this.dataItemList = dataItemList;
	}

	public List<DataItem> getJeziciItems() {
		return jeziciItems;
	}

	public void setJeziciItems(List<DataItem> jeziciItems) {
		this.jeziciItems = jeziciItems;
	}

	public List<DataItem> getDrzaveItems() {
		return drzaveItems;
	}

	public void setDrzaveItems(List<DataItem> drzaveItems) {
		this.drzaveItems = drzaveItems;
	}

	public List<DataItem> getKategorijaSearchItems() {
		return kategorijaSearchItems;
	}

	public void setKategorijaSearchItems(List<DataItem> kategorijaSearchItems) {
		this.kategorijaSearchItems = kategorijaSearchItems;
	}

	private static DataContainer instance = null;

	public DataContainer() {

	}

	public static DataContainer getInstance() {
		if (instance == null) {
			instance = new DataContainer();
		}
		return instance;
	}

	public static void setInstance(DataContainer instance) {
		DataContainer.instance = instance;
	}

}
