package ds.ac.kr.dsbusapplication;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

public class TabFragment1 extends Fragment {
    private int busRouteId = 108900010; //도봉02

    private String serviceKey;
    private String arrivalUrl;
    private String positionUrl;

    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ArrivalInfo arrivalInfo = new ArrivalInfo();
    private ArrayList<ArrivalInfo> arrivalInfoArrayList = new ArrayList<>();
    private ArrivalAsync arrivalAsync;

    private PositionInfo positionInfo = new PositionInfo();
    private ArrayList<PositionInfo> positionInfoArrayList = new ArrayList<>();
    private PositionAsync positionAsync;

    private String arsId = ""; //정류소 고유번호
    private String firstTm = ""; //첫차시간
    private String lastTm = ""; //막차시간
    private String stNm = ""; //정류소명
    private String sectOrd1 = ""; //첫번째 도착예정 버스의 현재구간 순번

    private boolean bl_arsId = false;
    private boolean bl_firstTm = false;
    private boolean bl_lastTm = false;
    private boolean bl_stNm = false;
    private boolean bl_sectOrd1 = false;

    private String plainNo1 = ""; //차량번호
    private String plainNo2 = ""; //차량번호
    private String stopFlag = ""; //정류소 도착 여부
    private String sectOrd = "";

    private boolean bl_plainNo1 = false;
    private boolean bl_plainNo2 = false;
    private boolean bl_stopFlag = false;
    private boolean bl_sectOrd = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        serviceKey = "bW9CK79jlnvdyfjuSnyM7yBOUyzmxjNwS76gLougcTuDr25QNxX8MK%2BYWkFi0JQ63k3EcSDfdc5SD3eoUjNblQ%3D%3D";
        arrivalUrl = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll"+"?ServiceKey=" + serviceKey + "&busRouteId=" + busRouteId;
        positionUrl = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid"+"?ServiceKey=" + serviceKey + "&busRouteId=" + busRouteId;

        View view = inflater.inflate(R.layout.activity_dobong02, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        arrivalAsync = new ArrivalAsync() {
            @Override
            protected void onPostExecute(String arrivalUrl) {
                try {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();

                    xpp.setInput(new StringReader(arrivalUrl));
                    int eventType = xpp.getEventType();
                    while(eventType != XmlPullParser.END_DOCUMENT) {
                        if(eventType == XmlPullParser.START_DOCUMENT) {

                        } else if(eventType == XmlPullParser.START_TAG) {
                            String tagName = xpp.getName();

                            switch (tagName) {
                                case "arsId":
                                    bl_arsId = true;
                                    break;
                                case "firstTm":
                                    bl_firstTm = true;
                                    break;
                                case "lastTm":
                                    bl_lastTm = true;
                                case "stNm":
                                    bl_stNm = true;
                                    break;
                            }
                        } else if(eventType == XmlPullParser.TEXT) {

                            if(bl_arsId) {
                                arsId = xpp.getText();
                                arrivalInfo.setArsId(arsId);
                                bl_arsId = false;
                            }

                            if(bl_firstTm) {
                                firstTm = xpp.getText();
                                arrivalInfo.setFirstTm(firstTm);
                                bl_firstTm = false;
                            }

                            if(bl_lastTm) {
                                lastTm = xpp.getText();
                                arrivalInfo.setLastTm(lastTm);
                                bl_lastTm = false;
                            }

                            if(bl_stNm) {
                                stNm = xpp.getText();
                                arrivalInfo.setStNm(stNm);
                                bl_stNm = false;
                            }


                        } else if(eventType == XmlPullParser.END_TAG) {
                            String tagName = xpp.getName();

                            if(tagName.equals("itemList"))  {

                                arrivalInfoArrayList.add(arrivalInfo);

                                arrivalInfo = new ArrivalInfo();
//                                mAdapter = new MyAdapter(arrivalInfoArrayList, positionInfoArrayList);
//                                recyclerView.setAdapter(mAdapter);
                            }
                        }
                        eventType = xpp.next();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };

        positionAsync = new PositionAsync() {
            @Override
            protected void onPostExecute(String positionUrl) {
                try {
                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    XmlPullParser xpp = factory.newPullParser();

                    xpp.setInput(new StringReader(positionUrl));
                    int eventType = xpp.getEventType();
                    while(eventType != XmlPullParser.END_DOCUMENT) {
                        if(eventType == XmlPullParser.START_DOCUMENT) {

                        } else if(eventType == XmlPullParser.START_TAG) {
                            String tagName = xpp.getName();
                            switch (tagName) {
                                case "plainNo1":
                                    bl_plainNo1 = true;
                                    break;
                                case "plainNo2":
                                    bl_plainNo2 = true;
                                    break;
                                case "stopFlag":
                                    bl_stopFlag = true;
                                    break;
                                case "sectOrd":
                                    bl_sectOrd = true;
                                    break;
                            }

                        } else if(eventType == XmlPullParser.TEXT) {

                            if(bl_plainNo1) {
                                plainNo1 = xpp.getText();
                                positionInfo.setPlainNo1(plainNo1);
                                bl_plainNo1 = false;
                            }
                            if(bl_plainNo2) {
                                plainNo2 = xpp.getText();
                                positionInfo.setPlainNo2(plainNo2);
                                bl_plainNo2 = false;
                            }
                            if(bl_stopFlag) {
                                stopFlag = xpp.getText();
                                positionInfo.setStopFlag(stopFlag);
                                bl_stopFlag = false;
                            }

                            if(bl_sectOrd) {
                                sectOrd = xpp.getText();
                                positionInfo.setSectOrd(Integer.parseInt(sectOrd));
                                bl_sectOrd = false;
                            }

                        } else if(eventType == XmlPullParser.END_TAG) {

                            String tagName = xpp.getName();

                            if(tagName.equals("itemList"))  {
                                positionInfoArrayList.add(positionInfo);
                                positionInfo = new PositionInfo();

                                mAdapter = new MyAdapter(arrivalInfoArrayList, positionInfoArrayList);

                                recyclerView.setAdapter(mAdapter);
                            }
                        }

                        eventType = xpp.next();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        arrivalAsync.execute(arrivalUrl);
        positionAsync.execute(positionUrl);

/*        1. 지금 문제가 ArrayList에 값들이 안넣어져서 값들이 다 들어간 상태에서 Adapter쪽으로 보내지 못하고 있음.
          그래서 arrival은 다 들어간 상태인데 position이 다 들어간 상태가 아니어서 계속 화면을 새로
          만들다보니까 버스의 개수만큼 hey가 들어가는 상황.

          2. No adapter attached; skipping layout 이 에러도 계속 생김.

          3. 버스의 위치는 position의 sectord와 arrival의 sectord1, sectord2이걸로 비교하는 걸로
          한 번 해봐야할듯.

*/

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("Refresh", "리프레시 되었습니다.");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 2000);
            }
        });

        return view;
    }

}

