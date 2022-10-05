package by.itacademy.matveenko.jd2.dao.connectionpool;

import java.util.ResourceBundle;
import by.itacademy.matveenko.jd2.dao.connectionpool.DBResourceManager;

public final class DBResourceManager {
	private static final String DATABASE_NAME = "db";    
	private static final DBResourceManager instance = new DBResourceManager();
    
    private DBResourceManager(){}
    
    private ResourceBundle bundle = ResourceBundle.getBundle(DATABASE_NAME);

    public static DBResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}