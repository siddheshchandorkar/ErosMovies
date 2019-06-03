package com.example.erostest.model

import com.google.gson.annotations.SerializedName

open class BaseKotlinVO
{
//    constructor()
    @SerializedName("status")
    var status: String? = null

    @SerializedName("message")
    var message: String? = null

  /*  {
        "status_code": 34,
        "status_message": "The resource you requested could not be found.",
        "success": false
    }*/
}
