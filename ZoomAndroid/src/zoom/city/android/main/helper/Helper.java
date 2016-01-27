package zoom.city.android.main.helper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import zoom.city.android.main.R;
import zoom.city.android.main.constant.ComponentInstance;

public class Helper {
	
	public static final String YOUTUBE_DEVELOPER_KEY = "AIzaSyD4QKpnCh_oWYMce6OeyZBPL-44T-480kU";

    public static boolean isBlank(String string) {
        if (string == null || string.length() == 0 || string.equals("null"))
            return true;

        int l = string.length();
        for (int i = 0; i < l; i++) {
            if (!Character.isWhitespace(string.codePointAt(i)))
                return false;
        }
        return true;
    }
    
    public static String extractYTId(String ytUrl) {
        String vId = null;
        String pattern = "https?:\\/\\/(?:[0-9A-Z-]+\\.)?(?:youtu\\.be\\/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|<\\/a>))[?=&+%\\w]*";

        Pattern compiledPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = compiledPattern.matcher(ytUrl);
        while(matcher.find()) {
            return matcher.group(1);
        }
        return vId;
    }
    
    public static void inicActionBar(Context context, String title) {
		try{
			Toolbar toolbar = null;
			if(((AppCompatActivity) context).findViewById(R.id.toolbar) != null){
				toolbar = (Toolbar) ((AppCompatActivity) context).findViewById(R.id.toolbar); 
				if(!Helper.isBlank(title)){
					((TextView)toolbar.findViewById(R.id.toolbar_title)).setText(title);
				}else{
					((TextView)toolbar.findViewById(R.id.toolbar_title)).setText("");
				}
			}else{
				Log.d("MYTAG","Null toolbar");
			}
			((AppCompatActivity) context).setSupportActionBar(toolbar); 
			
			((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			((AppCompatActivity) context).getSupportActionBar().setDisplayShowTitleEnabled(false);
			}catch(Exception ex){
				Log.d("MYERROR", "ActionBar error: " + ex.getMessage());
			}
	}
    
    public static void inicMainPageActionBar(Context context, Toolbar toolbar) {
		try{
			if(((AppCompatActivity) context).findViewById(R.id.toolbar) != null){ 
				((ImageView)toolbar.findViewById(R.id.toolbar_image)).setImageResource(R.drawable.logo);
			}else{
				Log.d("MYTAG","Null toolbar");
			}
			((AppCompatActivity) context).setSupportActionBar(toolbar); 
			
			((AppCompatActivity) context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			((AppCompatActivity) context).getSupportActionBar().setDisplayShowTitleEnabled(false);
			
			((AppCompatActivity) context).getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white);
			}catch(Exception ex){
				Log.d("MYERROR", "ActionBar error: " + ex.getMessage());
			}
	}
   
	
}
