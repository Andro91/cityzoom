package zoom.city.android.main.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import zoom.city.android.main.R;
import zoom.city.android.main.container.ImageContainer;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.helper.Helper;
import zoom.city.android.main.pages.previewitem.PreviewItemPage;
import zoom.city.android.main.service.LocationService;

public class ListViewAdapter extends ArrayAdapter<DataItem> {

	private Context context;
	private int layoutResourceId;
	private List<DataItem> data = new ArrayList<DataItem>();
	private String type;
	private String language;
	private String title;
	private LocationService locationService;
	private Thread mainThread;
	private String dist = "No GPS!";
	private DataItem dataForDist;
	private Handler handler;

	public ListViewAdapter(Context context, int layoutResourceId,
			List<DataItem> data, String type, String language,String firstTitleString) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.type = type;
		this.language = language;
		this.title = firstTitleString;
		

		locationService = new LocationService(context);
		locationService.getLocation();
		
		handler = new Handler();
		
		// Ubacivanje naslova
		String pomTitleFirst = "";
		String pomTitleSecond = "";
		
		if (data != null) {
			for (DataItem pomDataItem : data) {

				if (pomDataItem.getCategory().equalsIgnoreCase(pomTitleFirst)) {

					if (pomDataItem.getCompany().equalsIgnoreCase(
							pomTitleSecond)) {
						
						this.data.add(pomDataItem);

					} else {

//						//DRUGI NASLOVI
//						DataItem pomCompanyDataItem = new DataItem(pomDataItem.getCompany(), "3");
//						pomCompanyDataItem.setCompanyId(pomDataItem.getCompanyId());
//						this.data.add(pomCompanyDataItem);
						this.data.add(pomDataItem);
//						pomTitleSecond = pomDataItem.getCompany();
//						
					}

				} else {

//					//PRVI NASLOV
//					if(!pomDataItem.getCategory().equalsIgnoreCase(firstTitleString)){
//					this.data.add(new DataItem(pomDataItem.getCategory(), "2"));
//					}
//					pomTitleFirst = pomDataItem.getCategory();
//
//					//DRUGI NASLOVI
//					DataItem pomCompanyDataItem = new DataItem(pomDataItem.getCompany(), "3");
//					pomCompanyDataItem.setCompanyId(pomDataItem.getCompanyId());
//					this.data.add(pomCompanyDataItem);
//					pomTitleSecond = pomDataItem.getCompany();
					
					//ITEMI
					this.data.add(pomDataItem);

				}
			}
		}

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.data.size();
	}

	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public int getItemViewType(int position) {

		if (data.get(position).getTitleIndicator().equalsIgnoreCase("1")) {
			return 0;
		} else if (data.get(position).getTitleIndicator().equalsIgnoreCase("2")) {
			return 1;
		} else {
			return 2;
		}
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MessageHolder holder = null;
		// ispitujemo da li je pozicija parna i u zavisnosti od toga postavljamo
		// view item
		// if (row == null) {
		if (getItemViewType(position) == 0) {
			
			final DataItem dataItem = data.get(position);
			
			holder = new MessageHolder();
			
			mainThread = new Thread() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();

					try {
//						dataForDist = ParserItem.parseDistData(dataItem.getId(), language, type);
//						dist = ParserDistance.uzmiDistancu(
//								Double.toString(locationService.getLatitude()),
//								Double.toString(locationService.getLongitude()),
//								dataForDist.getX(),dataForDist.getY());
						float[] distances = new float[1];
						Location.distanceBetween(locationService.getLatitude(), locationService.getLongitude(), 
								Double.parseDouble(dataItem.getX()), Double.parseDouble(dataItem.getY()), distances);
						//String.valueOf(distances[0] / 1000).substring(0, 4);
						dist = String.valueOf(distances[0] / 1000).substring(0, 4) + "km";
						

					} catch (Exception e) {
						// TODO: handle exception
					} finally {
						handler.sendEmptyMessage(0);
					}
				}

			};
			
			mainThread.start();
			
			if (row == null) {
				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				
				if (dataItem.getCategory().equalsIgnoreCase("") || dataItem.getCategory() == null){
					//Company
					row = inflater.inflate(layoutResourceId, parent, false);
				}else {
					//Event
					row = inflater.inflate(R.layout.andro_list_item_event, parent, false);
					holder.txtSubTitle = (TextView) row
							.findViewById(R.id.textViewItemSubTitle);
					if (dataItem.getCategory() != null) {
						holder.txtSubTitle.setText(dataItem.getCategory());
					} else {
						holder.txtSubTitle.setText("");
					}
				}
				
				
				holder.txtTitle = (TextView) row
						.findViewById(R.id.textViewItemTitle);
				holder.txtDate = (TextView) row
						.findViewById(R.id.textViewItemDate);

				holder.imageView = (ImageView) row
						.findViewById(R.id.imageViewItem);

				row.setTag(holder);
				
			} else {
				holder = (MessageHolder) row.getTag();
			}

			if (dataItem.getTitle() != null) {
				holder.txtTitle.setText(dataItem.getTitle());
			} else {
				holder.txtTitle.setText("");
			}
			
			if (dataItem.getCategory().equalsIgnoreCase("") || dataItem.getCategory() == null){
				//Company
			}else {
				//Event
				holder.txtSubTitle = (TextView) row
						.findViewById(R.id.textViewItemSubTitle);
				if (dataItem.getCategory() != null) {
					holder.txtSubTitle.setText(dataItem.getCategory());
				} else {
					holder.txtSubTitle.setText("");
				}
			}
			
			while(mainThread.isAlive());
			
			String pomDate = null;
			if ((dataItem.getDate() != null)&&(dataItem.getDate()!="")) {
				pomDate = dataItem.getDate();
			}
			try{
				String[] pomDateArray = new String[2];
				pomDateArray = pomDate.split(" - ");
				if(pomDateArray[0].trim().equals(pomDateArray[1].trim())){
					pomDate = pomDateArray[0].trim();
				}
			}catch(Exception ex){
				
			}
			
			if (!Helper.isBlank(pomDate)) {
				holder.txtDate.setText(pomDate);
			} else {
				holder.txtDate.setText(dist);
			}
			
//			try{
//			Log.d("MYTAG", "ListAdapter - getImage(" + position + ") " + dataItem.getImage());
//			}catch(NullPointerException ex){
//				Log.d("MYTAG", "Null");
//			}
			//&& !dataItem.getImage().contains("noimage")
			Log.d("MYTAG","Type + " + type);
			if(type.equalsIgnoreCase("event")){
				if(dataItem.getSlider() != null){
					ImageContainer.getInstance().getImageDownloader()
							.download(dataItem.getSlider().get(0), holder.imageView);
				}else if(!Helper.isBlank(dataItem.getImage())) {
					ImageContainer.getInstance().getImageDownloader()
							.download(dataItem.getImage(), holder.imageView);
				}
			}else{
				if (!Helper.isBlank(dataItem.getImage()) && !dataItem.getImage().contains("noimage")) {
					ImageContainer.getInstance().getImageDownloader()
							.download(dataItem.getImage(), holder.imageView);
				}else if(dataItem.getLogoSlider() != null){
					String url = dataItem.getLogoSlider().get(0).replace(" ", "%20");
					ImageContainer.getInstance().getImageDownloader()
					.download(url, holder.imageView);
				}else{
					ImageContainer.getInstance().getImageDownloader()
						.download(dataItem.getImage(), holder.imageView);
				}
			}
			

			row.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub

					Intent intent = new Intent(view.getContext(),
							PreviewItemPage.class);

					intent.putExtra("id", dataItem.getId());
					intent.putExtra("type", type);
					intent.putExtra("language", language);
					intent.putExtra("title", title);
					if (dataItem.getCategory() != null) {
						intent.putExtra("category", dataItem.getCategory());
					}

					((Activity) context).startActivity(intent);
				}
			});

			return row;

		}
		else if (getItemViewType(position) == 1) {
			
			if (row == null) {
				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				row = inflater.inflate(R.layout.list_item_title1, parent, false);

				holder = new MessageHolder();

				holder.txtTitle = (TextView) row
						.findViewById(R.id.textViewItemTitle);

				row.setTag(holder);
			}

			else {
				holder = (MessageHolder) row.getTag();
			}

			final DataItem dataItem = data.get(position);

			if (dataItem.getTitle() != null) {
				holder.txtTitle.setText(dataItem.getTitle());
			} else {
				holder.txtTitle.setText("");
			}

			return row;
		} else {
			
			if (row == null) {
				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				row = inflater.inflate(R.layout.list_item_title2, parent, false);

				holder = new MessageHolder();

				holder.txtTitle = (TextView) row
						.findViewById(R.id.textViewItemTitle);

				row.setTag(holder);
			}

			else {
				holder = (MessageHolder) row.getTag();
			}

			final DataItem dataItemSecond = data.get(position);

			if (dataItemSecond.getTitle() != null) {
				holder.txtTitle.setText(dataItemSecond.getTitle());
			} else {
				holder.txtTitle.setText("");
			}
			
			row.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(view.getContext(),
							PreviewItemPage.class);

					intent.putExtra("id", dataItemSecond.getCompanyId());
					intent.putExtra("type", "company");
					intent.putExtra("language", language);
					intent.putExtra("title", title);

					((Activity) context).startActivity(intent);
				}
			});

			return row;
		}
	}

	static class MessageHolder {
		TextView txtTitle, txtDate, txtSubTitle;
		ImageView imageView;
	}
}