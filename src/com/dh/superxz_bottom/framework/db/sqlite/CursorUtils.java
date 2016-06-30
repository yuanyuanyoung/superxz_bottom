package com.dh.superxz_bottom.framework.db.sqlite;

import java.util.Map;

import android.database.Cursor;

import com.dh.superxz_bottom.framework.db.table.Id;
import com.dh.superxz_bottom.framework.db.table.Property;
import com.dh.superxz_bottom.framework.db.table.TableInfo;

public class CursorUtils {

	public static <T> T getEntitiy(Class<T> clazz, Cursor cursor){
		try{
			if(null != cursor){
				TableInfo tableInfo = TableInfo.get(clazz);
				Id id = tableInfo.getId();
				T bean = clazz.newInstance();
				Map<String, Property> propertyMap = tableInfo.getPropertyMap();
				int columnCount = cursor.getColumnCount();
				for(int i=0;i<columnCount;i++){
					String columnName = cursor.getColumnName(i);
					String columnValue = cursor.getString(i);
					Property property = propertyMap.get(columnName);
					if(null != property){
						property.setValue(bean, columnValue);
					}else{
						if(null != id && id.getColumn().equals(columnName)){
							id.setValue(bean, columnValue);
						}
					}
				}
				return bean;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
