package zoom.city.android.main.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import zoom.city.android.main.R;
import zoom.city.android.main.container.ImageContainer;
import zoom.city.android.main.data.DataItem;
import zoom.city.android.main.pages.previewitem.PreviewItemPage;

public class SearchListViewAdapter extends ArrayAdapter<DataItem> {

	private Context context;
	private int layoutResourceId;
	private List<DataItem> data = new ArrayList<DataItem>();
	private String language;

	public SearchListViewAdapter(Context context, int layoutResourceId,
			List<DataItem> data, String language) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.language = language;
		this.data=data;

		
		/*
		 * 
		 * 
		 * if (data != null) { for (DataItem pomDataItem : data) {
		 * 
		 * if (pomDataItem.getCategory().equalsIgnoreCase(pomTitleFirst)) {
		 * 
		 * if (pomDataItem.getCompany().equalsIgnoreCase( pomTitleSecond)) {
		 * 
		 * this.data.add(pomDataItem);
		 * 
		 * } else {
		 * 
		 * this.data.add(new DataItem(pomDataItem.getCompany(), "3"));
		 * this.data.add(pomDataItem); pomTitleSecond =
		 * pomDataItem.getCompany();
		 * 
		 * }
		 * 
		 * } else {
		 * 
		 * this.data.add(new DataItem(pomDataItem.getCategory(), "2"));
		 * pomTitleFirst = pomDataItem.getCategory();
		 * 
		 * this.data.add(new DataItem(pomDataItem.getCompany(), "3"));
		 * pomTitleSecond = pomDataItem.getCompany();
		 * 
		 * this.data.add(pomDataItem);
		 * 
		 * } } }
		 */

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.data.size();
	}

	/*
	 * @Override public int getViewTypeCount() { // TODO Auto-generated method
	 * stub return 3; }
	 * 
	 * @Override public int getItemViewType(int position) {
	 * 
	 * if (data.get(position).getTitleIndicator().equalsIgnoreCase("1")) {
	 * return 0; } else if
	 * (data.get(position).getTitleIndicator().equalsIgnoreCase("2")) { return
	 * 1; } else { return 2; } }
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View row = convertView;
		MessageHolder holder = null;
		// ispitujemo da li je pozicija parna i u zavisnosti od toga postavljamo
		// view item

		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);

			holder = new MessageHolder();

			holder.txtTitle = (TextView) row
					.findViewById(R.id.textViewItemTitle);
			holder.txtDate = (TextView) row.findViewById(R.id.textViewItemDate);

			holder.imageView = (ImageView) row.findViewById(R.id.imageViewItem);

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

		if (dataItem.getDate() != null) {
			holder.txtDate.setText(dataItem.getDate());
		} else {
			holder.txtDate.setText("");
		}

		if (dataItem.getImage() != null) {
			ImageContainer.getInstance().getImageDownloader()
					.download(dataItem.getImage(), holder.imageView);
		}

		row.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub

				Intent intent = new Intent(view.getContext(),
						PreviewItemPage.class);

				intent.putExtra("id", dataItem.getId());
				intent.putExtra("type", dataItem.getCategory());
				intent.putExtra("language", language);

				((Activity) context).startActivity(intent);
			}
		});

		return row;

	}

	static class MessageHolder {
		TextView txtTitle, txtDate;
		ImageView imageView;
	}
}