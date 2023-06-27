package algonquin.cst2335.torunse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import algonquin.cst2335.torunse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    String  _id ="";
    boolean isFinish = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        CatImages catImages = new CatImages();
        catImages.execute(100);
    }

    class  CatImages extends AsyncTask<Integer,Integer,String> {
        //后面尖括号内参数分别是（线程休息时间，进度，返回类型）


        @Override
        protected void onPreExecute() {
            //第一个执行方法
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {

            //第二个执行方法
            for (int i = 0; i < 100; i++) {
                try {
                    binding.progress.setProgress(i);
                    publishProgress(i);
                    Thread.sleep(30);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            loop();
            return "结束";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            //执行doInBackground中publishProgress时触发
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            //doInBackground 返回时触发，执行完触发
            super.onPostExecute(s);
            isFinish = false;
        }
    }

    private void loop(){
        while (isFinish){
            getImage();
        }
    }
    private void getImage(){
        //请求地址
        String url="https://cataas.com/cat?json=true";
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                CatImg catImg = GsonUtils.getInstance().fromJson(result,CatImg.class);
                if (catImg !=null){
                    String url = "https://cataas.com"+catImg.getUrl();
                    if (!_id.equals(catImg.get_id())){//如果id不相等
                        requestWebPhotoBitmap(url);
                    }

                }
            }
            //请求异常后的回调方法
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
            }
            //主动调用取消请求的回调方法
            @Override
            public void onCancelled(CancelledException cex) {
            }
            @Override
            public void onFinished() {
            }
        });
    }


    /**
     * 通过 网络图片 url 获取图片 Bitmap
     * @param photoUrl 网络图片 url
     */
    private void requestWebPhotoBitmap(String photoUrl) {
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                URL bitmapUrl = new URL(photoUrl);
                connection = (HttpURLConnection) bitmapUrl.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                // 判断是否请求成功
                if (connection.getResponseCode() == 200) {
                    InputStream inputStream = connection.getInputStream();
                   Bitmap imgBitmap = BitmapFactory.decodeStream(inputStream);
                   runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           binding.image.setImageBitmap(imgBitmap);
                       }
                   });
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) connection.disconnect();
            }
        }).start();
    }
}