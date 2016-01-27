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
import zoom.city.android.main.pages.taxisms.TaxiSMSPreviewPage;

public class TaxiSmsAdapter extends ArrayAdapter<DataItem> {

	private Context context;
	private int layoutResourceId;
	private List<DataItem> data = new ArrayList<DataItem>();

	public TaxiSmsAdapter(Context context, int layoutResourceId,
			List<DataItem> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.data.size();
	}

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

		DataItem dataItem = data.get(position);

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
				Intent intent = new Intent(view.getContext(),TaxiSMSPreviewPage.class);
				intent.putExtra("position", position);
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