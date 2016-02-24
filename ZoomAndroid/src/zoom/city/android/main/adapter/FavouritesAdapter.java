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
import zoom.city.android.main.parser.ParserItem;
import zoom.city.android.main.service.LocationService;

public class FavouritesAdapter extends ArrayAdapter<DataItem> {

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

	public FavouritesAdapter(Context context, int layoutResourceId,
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
		
		if (data != null) {
			for (DataItem pomDataItem : data) {
						this.data.add(pomDataItem);
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MessageHolder holder = null;

			final DataItem dataItem = data.get(position);
			
			holder = new MessageHolder();
			
			mainThread = new Thread() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();

					try {
						dataForDist = ParserItem.parseDistData(dataItem.getId(), language, type);
//						dist = ParserDistance.uzmiDistancu(
//								Double.toString(locationService.getLatitude()),
//								Double.toString(locationService.getLongitude()),
//								dataForDist.getX(),dataForDist.getY());
						float[] distances = new float[1];
						Location.distanceBetween(locationService.getLatitude(), locationService.getLongitude(), 
								Double.parseDouble(dataForDist.getX()), Double.parseDouble(dataForDist.getY()), distances);
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
				
				if (dataItem.getPreviewtype().equalsIgnoreCase("company")){
					//Company
					Log.d("ADAPTER", "company");
					row = inflater.inflate(layoutResourceId, parent, false);
					
					//mainThread.start();
				}else {
					//Event
					Log.d("ADAPTER", "event");
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
			
			while(mainThread.isAlive());
			
			if ((dataItem.getDate() != null)&&(dataItem.getDate()!="")) {
				holder.txtDate.setText(dataItem.getDate());
			} else {
				holder.txtDate.setText(dist);
			}
			Log.d("FAVADAPTER", "company/event if");
			
			ImageContainer.getInstance().getImageDownloader().download(dataItem.getImage(), holder.imageView);
			
//			if(dataItem.getPreviewtype().equalsIgnoreCase("event")){
//				if (!Helper.isBlank(dataItem.getImage())) {
//					ImageContainer.getInstance().getImageDownloader()
//							.download(dataItem.getImage(), holder.imageView);
//				}
//			}else{
//				Log.d("FAVADAPTER", "company");
//				if (!Helper.isBlank(dataItem.getLogo())) {
//					ImageContainer.getInstance().getImageDownloader()
//							.download(dataItem.getLogo(), holder.imageView);
//				}
//			}
			
			row.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View view) {
					// TODO Auto-generated method stub

					Intent intent = new Intent(view.getContext(),
							PreviewItemPage.class);

					intent.putExtra("id", dataItem.getId());
					intent.putExtra("type", dataItem.getPreviewtype());
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
	}

	class MessageHolder {
		TextView txtTitle, txtDate, txtSubTitle;
		ImageView imageView;
	}