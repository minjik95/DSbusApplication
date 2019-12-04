package ds.ac.kr.dsbusapplication;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TabAdapter tabAdapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        tabAdapter = new TabAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(new TabFragment1(), "도봉02");
        tabAdapter.addFragment(new TabFragment2(), "120");
        tabAdapter.addFragment(new TabFragment3(), "153");

        viewPager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}

//bW9CK79jlnvdyfjuSnyM7yBOUyzmxjNwS76gLougcTuDr25QNxX8MK%2BYWkFi0JQ63k3EcSDfdc5SD3eoUjNblQ%3D%3D 버스위치정보조회
//getArrInfoByRouteAllList 경유노선 전체 정류소 도착 예정 정보 조회
//bW9CK79jlnvdyfjuSnyM7yBOUyzmxjNwS76gLougcTuDr25QNxX8MK%2BYWkFi0JQ63k3EcSDfdc5SD3eoUjNblQ%3D%3D 버스도착정보조회
//노선 ID로 차량들의 위치정보를 조회