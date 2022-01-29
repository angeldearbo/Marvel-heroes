package com.angeldearbo.marvelheroes.framework.data.model

import com.google.gson.annotations.SerializedName


data class MarvelResponse(
    @SerializedName("attributionHTML")
    var attributionHTML: String,
    @SerializedName("attributionText")
    var attributionText: String,
    @SerializedName("code")
    var code: Int,
    @SerializedName("copyright")
    var copyright: String,
    @SerializedName("data")
    var data: Data,
    @SerializedName("etag")
    var etag: String,
    @SerializedName("status")
    var status: String
)

data class Data(
    @SerializedName("count")
    var count: Int,
    @SerializedName("limit")
    var limit: Int,
    @SerializedName("offset")
    var offset: Int,
    @SerializedName("results")
    var results: List<Result>,
    @SerializedName("total")
    var total: Int
)

data class Result(
    @SerializedName("comics")
    var comics: Comics,
    @SerializedName("description")
    var description: String,
    @SerializedName("events")
    var events: Events,
    @SerializedName("id")
    var id: Int,
    @SerializedName("modified")
    var modified: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("resourceURI")
    var resourceURI: String,
    @SerializedName("series")
    var series: Series,
    @SerializedName("stories")
    var stories: Stories,
    @SerializedName("thumbnail")
    var thumbnail: Thumbnail,
    @SerializedName("urls")
    var urls: List<Url>
)

data class Comics(
    @SerializedName("available")
    var available: Int,
    @SerializedName("collectionURI")
    var collectionURI: String,
    @SerializedName("items")
    var items: List<Item>,
    @SerializedName("returned")
    var returned: Int
)

data class Events(
    @SerializedName("available")
    var available: Int,
    @SerializedName("collectionURI")
    var collectionURI: String,
    @SerializedName("items")
    var items: List<ItemX>,
    @SerializedName("returned")
    var returned: Int
)

data class Series(
    @SerializedName("available")
    var available: Int,
    @SerializedName("collectionURI")
    var collectionURI: String,
    @SerializedName("items")
    var items: List<ItemXX>,
    @SerializedName("returned")
    var returned: Int
)

data class Stories(
    @SerializedName("available")
    var available: Int,
    @SerializedName("collectionURI")
    var collectionURI: String,
    @SerializedName("items")
    var items: List<ItemXXX>,
    @SerializedName("returned")
    var returned: Int
)

data class Thumbnail(
    @SerializedName("extension")
    var extension: String,
    @SerializedName("path")
    var path: String
) {
    fun getHttpUrl(): String {
        val httpsPath = if (path.startsWith("https")) path
        else path.replaceFirst("http", "https")

        return "$httpsPath.$extension"
    }
}

data class Url(
    @SerializedName("type")
    var type: String,
    @SerializedName("url")
    var url: String
)

data class Item(
    @SerializedName("name")
    var name: String,
    @SerializedName("resourceURI")
    var resourceURI: String
)

data class ItemX(
    @SerializedName("name")
    var name: String,
    @SerializedName("resourceURI")
    var resourceURI: String
)

data class ItemXX(
    @SerializedName("name")
    var name: String,
    @SerializedName("resourceURI")
    var resourceURI: String
)

data class ItemXXX(
    @SerializedName("name")
    var name: String,
    @SerializedName("resourceURI")
    var resourceURI: String,
    @SerializedName("type")
    var type: String
)