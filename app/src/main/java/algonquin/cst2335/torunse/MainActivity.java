package algonquin.cst2335.torunse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import algonquin.cst2335.torunse.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    TextAdapter textAdapter;
    TestData _testData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        TextList textList = new TextList();
        textList.execute(100);
        binding.lvData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FrameLayout frameLayout  =view.findViewById(R.id.frame_layout);
                if (frameLayout ==null){
                    Intent intent = new Intent(MainActivity.this,EmptyActivity.class);
                    intent.putExtra("data",_testData.getResults().get(position));
                    startActivity(intent);
                }
            }
        });

        replaceFragment(new DetailsFragment());
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout,fragment);//framelayout1使用的是帧布局中的id
        //模拟返回栈
        transaction.addToBackStack(null); //模拟返回栈,再次点击按钮的时候，返回原本的布局，但是我这里没起作用，不知道为什么。
        transaction.commit();
    }
    class  TextList extends AsyncTask<Integer,Integer,String> {
        //后面尖括号内参数分别是（线程休息时间，进度，返回类型）


        @Override
        protected void onPreExecute() {
            //第一个执行方法
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... integers) {

            getTextList();
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

        }
    }

    private void getTextList() {
        //请求地址
        String url = "https://swapi.dev/api/people/?format=json";
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                TestData testData = GsonUtils.getInstance().fromJson(result,TestData.class);
                if (testData!=null){
                    _testData = testData;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textAdapter = new TextAdapter(MainActivity.this,testData.getResults());
                            binding.lvData.setAdapter(textAdapter);
                        }
                    });
                }
                Log.d("输出数据",result);
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
    class TextAdapter extends BaseAdapter{
        private Context context;
        private List<TestData.ResultsBean> stringList;

        public TextAdapter(Context context,List<TestData.ResultsBean> stringList){
            this.context = context;
            this.stringList = stringList;
        }

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public Object getItem(int position) {
            return stringList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView ==null){
                holder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_txt,null);
                holder.textView = convertView.findViewById(R.id.textView);
                holder.gender = convertView.findViewById(R.id.gender);
                holder.mass = convertView.findViewById(R.id.mass);
                holder.height = convertView.findViewById(R.id.height);
                holder.birth_year = convertView.findViewById(R.id.birth_year);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.textView.setText("Name："+stringList.get(position).getName());
            holder.gender.setText("Gender："+stringList.get(position).getGender());
            holder.mass.setText("Mass："+stringList.get(position).getMass());
            holder.height.setText("Height："+stringList.get(position).getHeight());
            holder.birth_year.setText("Birth_year："+stringList.get(position).getBirth_year());
            return convertView;
        }
        class ViewHolder{
            TextView textView;
            TextView gender;
            TextView mass;
            TextView height;
            TextView birth_year;
        }
    }
}