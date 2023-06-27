package algonquin.cst2335.torunse;

import android.app.Application;
import android.content.Context;




import org.xutils.DbManager;
import org.xutils.x;



public class App extends Application {

    private static App singstance;

    private static Context context;
    public static DbManager dbManager;//数据库存储


    static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbVersion(1)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                }
            });

    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        return context;
    }

    public static App getInstance(){
        return singstance;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        singstance = this;
        context = this.getApplicationContext();

        x.Ext.init(this);
        if (dbManager == null) {
            dbManager = x.getDb(daoConfig);
        }
    }
}
