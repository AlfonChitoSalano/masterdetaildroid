package com.haiyangrpdev.apptmasterdetail.ui;

import java.util.List;
import com.haiyangrpdev.apptmasterdetail.dummy.DummyContent;
import com.haiyangrpdev.apptmasterdetail.ui.base.BaseViewModel;
import com.haiyangrpdev.apptmasterdetail.model.AppITunes;

public class ItemListActivityViewModel extends BaseViewModel {

    public List<AppITunes> getData() {
        return null;
    }

    //this is only dummy
    public List<DummyContent.DummyItem> getDummyData(){
        return DummyContent.ITEMS;
    }
}
