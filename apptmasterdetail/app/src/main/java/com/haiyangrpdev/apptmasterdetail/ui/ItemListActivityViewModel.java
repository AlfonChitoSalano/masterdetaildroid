package com.haiyangrpdev.apptmasterdetail.ui;

import java.util.ArrayList;
import java.util.List;
import com.haiyangrpdev.apptmasterdetail.dummy.DummyContent;
import com.haiyangrpdev.apptmasterdetail.ui.base.BaseViewModel;
import com.haiyangrpdev.apptmasterdetail.model.AppITunes;

public class ItemListActivityViewModel extends BaseViewModel {

    public List<AppITunes> getData() {
        //set dummy data
        AppITunes app1 = new AppITunes();
        app1.setArtwork("Art 1");
        app1.setGenre("Genre 1");
        app1.setTrackName("TrackName 1");
        app1.setPrice(100.10);

        AppITunes app2 = new AppITunes();
        app2.setArtwork("Art 2");
        app2.setGenre("Genre 2");
        app2.setTrackName("TrackName 2");
        app2.setPrice(200.10);


        //return list of data
        List<AppITunes> appITunesList = new ArrayList<> ();
        appITunesList.add(app1);
        appITunesList.add(app2);
        return appITunesList;
    }

    //this is only dummy
    public List<DummyContent.DummyItem> getDummyData(){
        return DummyContent.ITEMS;
    }
}
