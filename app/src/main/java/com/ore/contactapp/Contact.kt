package com.ore.contactapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    var id: Int = 0,
    val name: String = "",
    val email: String = "",
    val phone: String = ""
) : Parcelable
