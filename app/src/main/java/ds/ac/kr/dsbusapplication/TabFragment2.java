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

public class TabFragment2 extends Fragment {
    private int busRouteId = 100100017; //120

    private String serviceKey;
    private String arrivalUrl;
    private String positionUrl;

    private RecyclerView recyclerView2;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout2;

    private ArrivalInfo arrivalInfo = new ArrivalInfo();
    private ArrayList<ArrivalInfo> arrivalInfoArrayList = new ArrayList<>();

    private PositionInfo positionInfo = new PositionInfo();
    private ArrayList<PositionInfo> positionInfoArrayList = new ArrayList<>();

    private String arsId = ""; //정류소 고유번호
    private String firstTm = ""; //첫차시간
    private String lastTm = ""; //막차시간
    private String stNm = ""; //정류소명

    boolean bl_arsId = false;
    boolean bl_firstTm = false;
    boolean bl_lastTm = false;
    boolean bl_stNm = false;

    private String plainNo1 = ""; //차량번호
    private String plainNo2 = ""; //차량번호
    private String stopFlag = ""; //정류소 도착 여부
    private String sectOrd = "";

    boolean bl_plainNo1 = false;
    boolean bl_plainNo2 = false;
    boolean bl_stopFlag = false;
    boolean bl_sectOrd = false;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        serviceKey = "bW9CK79jlnvdyfjuSnyM7yBOUyzmxjNwS76gLougcTuDr25QNxX8MK%2BYWkFi0JQ63k3EcSDfdc5SD3eoUjNblQ%3D%3D";
        arrivalUrl = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll"+"?ServiceKey=" + serviceKey + "&busRouteId=" + busRouteId;
        positionUrl = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid"+"?ServiceKey=" + serviceKey + "&busRouteId=" + busRouteId;

        View view =  inflater.inflate(R.layout.activity_120, container, false);

        mSwipeRefreshLayout2 = view.findViewById(R.id.swipeRefreshLayout2);

        recyclerView2 = view.findViewById(R.id.recyclerView2);
        recyclerView2.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(mLayoutManager);

        ArrivalAsync arrivalAsync = new ArrivalAsync() {
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
                            }
                        }
                        eventType = xpp.next();

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };

        PositionAsync positionAsync = new PositionAsync() {
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
                                recyclerView2.setAdapter(mAdapter);

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

        mSwipeRefreshLayout2.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d("Refresh", "리프레시 되었습니다.");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeRefreshLayout2.setRefreshing(false);
                    }
                }, 2000);
            }
        });



        return view;
    }
}
