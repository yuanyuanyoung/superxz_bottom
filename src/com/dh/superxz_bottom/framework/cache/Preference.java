package com.dh.superxz_bottom.framework.cache;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
/****
 * SharedPreferences的一个代理类 
 * @author YUHAN
 */
public class Preference {

	private static SharedPreferences pref; 
	
	private Context mContext;
	
	public Preference(Context context){
		mContext = context;
		pref = PreferenceManager.getDefaultSharedPreferences(mContext);
	}
	
	public void putString(String key,String value){
		Editor editor = pref.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public void putInt(String key,int value){
		Editor editor = pref.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	
	public void putBoolean(String key,boolean value){
		Editor editor = pref.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public int getInt(String key){
		return pref.getInt(key, 0);
	}

	public int getInt(String key,int default_value){
		return pref.getInt(key,default_value);
	}
	
	public String getString(String key){
		return pref.getString(key, "");
	}
	
	public String getString(String key,String default_value){
		return pref.getString(key, default_value);
	} 
	
	public boolean getBoolean(String key,boolean defValue){
		return pref.getBoolean(key, defValue);
	}
}