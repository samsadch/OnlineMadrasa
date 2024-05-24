package com.onlinemadrasa.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StoryModel(
    var storyTitle: String = "",
    var storyList: List<StoryItem>? = null,
    var createdDate: String = "",
    var updatedDate: String = "",
    var refrence: String? = "",
    var refrences: List<String>? = null
) : Parcelable

@Parcelize
data class StoryItem(
    var title: String = "",
    var partTitle: String? = "",
    var content: String = "",
    var addedDate: String = ""
) : Parcelable