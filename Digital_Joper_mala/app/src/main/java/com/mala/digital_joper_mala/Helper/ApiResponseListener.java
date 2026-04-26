package com.mala.digital_joper_mala.Helper;

import com.mala.digital_joper_mala.Model.Api_links;


public interface ApiResponseListener {

    void onApiResponse(Api_links apiLinks);
    void onApiFailed(String error);

}
