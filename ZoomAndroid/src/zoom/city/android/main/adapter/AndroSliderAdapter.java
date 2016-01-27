package zoom.city.android.main.adapter;

import java.util.ArrayList;

import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.squareup.picasso.Picasso;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import zoom.city.android.main.R;
import zoom.city.android.main.container.DataContainer;
import zoom.city.android.main.helper.Helper;
import zoom.city.android.main.pages.previewitem.PreviewItemPage;

public class AndroSliderAdapter extends PagerAdapter {

    private Context mContext;
    ArrayList<String> mList;

    public AndroSliderAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = null;
        
        String item = mList.get(position);
        
        if(item.contains("youtube") || item.contains("youtu.be")){
            layout = (ViewGroup) inflater.inflate(R.layout.andro_slider_pager_item, collection, false);
            
            ImageView imageView = (ImageView) layout.findViewById(R.id.ImageView_Video);
            
            final String youTubeVideoId = Helper.extractYTId(item);
            
            String image = "http://img.youtube.com/vi/" + youTubeVideoId + "/0.jpg";
            
            Picasso.with(mContext).load(image).into(imageView);
            
            imageView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = null;
	  				intent = YouTubeStandalonePlayer.createVideoIntent(
	  				          (PreviewItemPage) mContext, Helper.YOUTUBE_DEVELOPER_KEY, youTubeVideoId, 0, true, true);
	  				((PreviewItemPage) mContext).startActivityForResult(intent, 1);
				}
			});
            
//            final WebView webView = (WebView) layout.findViewById(R.id.webview);
//            final String youTubeVideoId = Helper.extractYTId(mList.get(position));
//            webView.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    ((PreviewItemPage) mContext).swipeTimer.cancel();
//                    ((PreviewItemPage) mContext).swipeTimer.purge();
//                    Toast.makeText(mContext, "Clicked webview", Toast.LENGTH_SHORT).show();
//                    Intent intent = null;
//    				intent = YouTubeStandalonePlayer.createVideoIntent(
//    				          (PreviewItemPage) mContext, Helper.YOUTUBE_DEVELOPER_KEY, youTubeVideoId, 0, false, true);
//    				((PreviewItemPage) mContext).startActivityForResult(intent, 1);
//                    return false;
//                }
//            });
//            String html_start = "<html><body style=\"margin: 0; padding: 0\">" +
//                    "<iframe class=\"youtube-player\" style='width: 100%; height: 100%' frameborder=\"0\" framespacing=\"0\" src=\"https://www.youtube.com/embed/";
//            String html_end = "\"></iframe></body></html>";
//            String mime = "text/html";
//            String encoding = "utf-8";
//
//            webView.getSettings().setJavaScriptEnabled(true);
//            webView.getSettings().setDomStorageEnabled(true);
//            webView.loadDataWithBaseURL(null, html_start + youTubeVideoId + html_end, mime, encoding, null);
        }else{
            layout = (ViewGroup) inflater.inflate(R.layout.andro_slider_image_item, collection, false);
            ImageView imageView = (ImageView) layout.findViewById(R.id.imageView1);
            //Log.d("MYTAG", "Picasso image " + mList.get(position));
            String image = mList.get(position);
            Picasso.with(mContext).load(image).into(imageView);
        }

        collection.addView(layout);
        
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    
    public void fetchData() {
		mList = DataContainer.androSliderUrlList;
		notifyDataSetChanged();
	}
}
