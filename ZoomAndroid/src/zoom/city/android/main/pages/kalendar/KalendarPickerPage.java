package zoom.city.android.main.pages.kalendar;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import zoom.city.android.main.R;
import zoom.city.android.main.adapter.CalendarAdapter;
import zoom.city.android.main.constant.ComponentInstance;

public class KalendarPickerPage extends Activity {

	public GregorianCalendar month, itemmonth;// calendar instances.

	public CalendarAdapter adapter;// adapter instance
	public ArrayList<String> items; // container to store calendar items which
	// needs showing the event marker
	ArrayList<String> event;
	LinearLayout rLayout;
	ArrayList<String> date;
	ArrayList<String> desc;
	String titleString;
	TextView txtTitle;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.page_kalendar_picker);

		
		ComponentInstance.inicTitleBar(this, "Kalendar");
		
		Bundle extras = getIntent().getExtras();
		if(extras!=null){
			titleString = extras.getString("title");
		}
		
		ComponentInstance.inicTopButton(this);
		ComponentInstance.inicBackTitleBar(this, titleString);
		
		Locale.setDefault(Locale.US);

		rLayout = (LinearLayout) findViewById(R.id.text);
		month = (GregorianCalendar) GregorianCalendar.getInstance();
		itemmonth = (GregorianCalendar) month.clone();

		items = new ArrayList<String>();

		adapter = new CalendarAdapter(this, month);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(adapter);

		txtTitle = (TextView) findViewById(R.id.title);
		txtTitle.setText(android.text.format.DateFormat.format("MMMM yyyy", month));

		RelativeLayout previous = (RelativeLayout) findViewById(R.id.previous);

		previous.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setPreviousMonth();
				refreshCalendar();
			}
		});

		RelativeLayout next = (RelativeLayout) findViewById(R.id.next);
		next.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				setNextMonth();
				refreshCalendar();

			}
		});

		gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView parent, View v, int position,
					long id) {
				// removing the previous view if added
				/*
				 * if (((LinearLayout) rLayout).getChildCount() > 0) {
				 * ((LinearLayout) rLayout).removeAllViews(); } desc = new
				 * ArrayList<String>(); date = new ArrayList<String>();
				 * ((CalendarAdapter) parent.getAdapter()).setSelected(v);
				 * String selectedGridDate = CalendarAdapter.dayString
				 * .get(position); String[] separatedTime =
				 * selectedGridDate.split("-"); String gridvalueString =
				 * separatedTime[2].replaceFirst("^0*", "");// taking last part
				 * of date. ie; 2 from 2012-12-02. int gridvalue =
				 * Integer.parseInt(gridvalueString); // navigate to next or
				 * previous month on clicking offdays. if ((gridvalue > 10) &&
				 * (position < 8)) { setPreviousMonth(); refreshCalendar(); }
				 * else if ((gridvalue < 7) && (position > 28)) {
				 * setNextMonth(); refreshCalendar(); } ((CalendarAdapter)
				 * parent.getAdapter()).setSelected(v);
				 * 
				 * 
				 * if (desc.size() > 0) { for (int i = 0; i < desc.size(); i++)
				 * { TextView rowTextView = new TextView(
				 * KalendarPickerPage.this);
				 * 
				 * // set some properties of rowTextView or something
				 * rowTextView.setText("Event:" + desc.get(i));
				 * rowTextView.setTextColor(Color.BLACK);
				 * 
				 * // add the textview to the linearlayout
				 * rLayout.addView(rowTextView);
				 * 
				 * }
				 * 
				 * }
				 * 
				 * desc = null;
				 */

				((CalendarAdapter) parent.getAdapter()).setSelected(v);
				String selectedGridDate = CalendarAdapter.dayString
						.get(position);
				String[] separatedTime = selectedGridDate.split("-");
				String gridvalueString = separatedTime[2].replaceFirst("^0*",
						"");// taking last part of date. ie; 2 from 2012-12-02.
				int gridvalue = Integer.parseInt(gridvalueString);
				// navigate to next or previous month on clicking offdays.
				if ((gridvalue > 10) && (position < 8)) {
					setPreviousMonth();
					refreshCalendar();
				} else if ((gridvalue < 7) && (position > 28)) {
					setNextMonth();
					refreshCalendar();
				}
				((CalendarAdapter) parent.getAdapter()).setSelected(v);

				Intent intent = new Intent(KalendarPickerPage.this,KalendarViewSwitcherActivity.class);
				intent.putExtra("title", titleString);
				intent.putExtra("dateTitle", gridvalueString+". "+txtTitle.getText()+".");
				
			//	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				intent.putExtra("date", selectedGridDate);
				
				startActivity(intent);
				finish();
				
			}

		});
	}

	protected void setNextMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMaximum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) + 1),
					month.getActualMinimum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) + 1);
		}

	}

	protected void setPreviousMonth() {
		if (month.get(GregorianCalendar.MONTH) == month
				.getActualMinimum(GregorianCalendar.MONTH)) {
			month.set((month.get(GregorianCalendar.YEAR) - 1),
					month.getActualMaximum(GregorianCalendar.MONTH), 1);
		} else {
			month.set(GregorianCalendar.MONTH,
					month.get(GregorianCalendar.MONTH) - 1);
		}

	}

	public void refreshCalendar() {
		TextView title = (TextView) findViewById(R.id.title);

		adapter.refreshDays();
		adapter.notifyDataSetChanged();

		title.setText(android.text.format.DateFormat.format("MMMM yyyy", month));
	}
	
}