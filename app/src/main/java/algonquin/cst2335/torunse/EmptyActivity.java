package algonquin.cst2335.torunse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import algonquin.cst2335.torunse.databinding.ActivityEmptyBinding;

public class EmptyActivity extends AppCompatActivity {
    ActivityEmptyBinding binding;

    TestData.ResultsBean testData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_empty);

        testData = ( TestData.ResultsBean) getIntent().getSerializableExtra("data");
        replaceFragment(new DetailsFragment());
    }

    public void replaceFragment(Fragment fragment){
        Bundle bundle = new Bundle();
        //设置数据
        bundle.putSerializable("data", testData);
        //绑定 Fragment
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout,fragment);//framelayout1使用的是帧布局中的id
        //模拟返回栈
        transaction.addToBackStack(null); //模拟返回栈,再次点击按钮的时候，返回原本的布局，但是我这里没起作用，不知道为什么。
        transaction.commit();
    }

}