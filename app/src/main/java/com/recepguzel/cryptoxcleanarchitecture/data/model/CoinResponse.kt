package com.recepguzel.cryptoxcleanarchitecture.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class CoinResponse(
    @ColumnInfo("data")
    val data: CryptoDataWrapper,
    @ColumnInfo("status")
    val status: Status
)

@Parcelize
data class CryptoDataWrapper(
    @ColumnInfo("cryptoCurrencyList")
    val cryptoCurrencyList: List<CryptoData>,
    @ColumnInfo("totalCount")
    val totalCount: String
) : Parcelable

@Entity(tableName = "favorite_table")
@Parcelize
data class CryptoData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("tableId")
    val tableId: Long,
    @ColumnInfo("id")
    val id: Long,
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("symbol")
    val symbol: String,
    @ColumnInfo("slug")
    val slug: String,
    @ColumnInfo("tags")
    val tags: List<String>,
    @SerializedName("cmcRank")
    @ColumnInfo("rank")
    val rank: Int,
    @ColumnInfo("marketPairCount")
    val marketPairCount: Int,
    @ColumnInfo("circulatingSupply")
    val circulatingSupply: Double,
    @ColumnInfo("selfReportedCirculatingSupply")
    val selfReportedCirculatingSupply: Double,
    @ColumnInfo("totalSupply")
    val totalSupply: Double,
    @ColumnInfo("maxSupply")
    val maxSupply: Double,
    @ColumnInfo("isActive")
    val isActive: Int,
    @ColumnInfo("lastUpdated")
    val lastUpdated: String,
    @ColumnInfo("dateAdded")
    val dateAdded: String,
    @ColumnInfo("quotes")
    val quotes: List<Quote>,
    @ColumnInfo("isAudited")
    val isAudited: Boolean,
    @ColumnInfo("badges")
    val badges: List<Int>,
    val isFav: Boolean = false
) : Parcelable

@Parcelize
data class Quote(
    @ColumnInfo("name")
    val name: String,
    @ColumnInfo("price")
    val price: Double,
    @ColumnInfo("volume24h")
    val volume24h: Double,
    @ColumnInfo("marketCap")
    val marketCap: Double,
    @SerializedName("percentChange1h")
    @ColumnInfo("percentChange1Hour")
    val percentChange1Hour: Double,
    @SerializedName("percentChange24h")
    @ColumnInfo("percentChange24Hours")
    val percentChange24Hours: Double,
    @SerializedName("percentChange7d")
    @ColumnInfo("percentChange7Days")
    val percentChange7Days: Double,
    @ColumnInfo("lastUpdated")
    val lastUpdated: String,
    @SerializedName("percentChange30d")
    @ColumnInfo("percentChange30Days")
    val percentChange30Days: Double,
    @SerializedName("percentChange60d")
    @ColumnInfo("percentChange60Days")
    val percentChange60Days: Double,
    @SerializedName("percentChange90d")
    @ColumnInfo("percentChange90Days")
    val percentChange90Days: Double,
    @SerializedName("fullyDilluttedMarketCap")
    @ColumnInfo("fullyDilutedMarketCap")
    val fullyDilutedMarketCap: Double,
    @SerializedName("marketCapByTotalSupply")
    @ColumnInfo("marketCapByTotalSupply")
    val marketCapByTotalSupply: Double,
    @ColumnInfo("dominance")
    val dominance: Double,
    @ColumnInfo("turnover")
    val turnover: Double,
    @SerializedName("ytdPriceChangePercentage")
    @ColumnInfo("ytdPriceChangePercentage")
    val ytdPriceChangePercentage: Double,
    @SerializedName("percentChange1y")
    @ColumnInfo("percentChange1Year")
    val percentChange1Year: Double
) : Parcelable

@Parcelize
data class Status(
    @ColumnInfo("timestamp")
    val timestamp: String,
    @ColumnInfo("errorCode")
    val errorCode: String,
    @ColumnInfo("errorMessage")
    val errorMessage: String,
    @ColumnInfo("elapsed")
    val elapsed: String,
    @ColumnInfo("creditCount")
    val creditCount: Int
) : Parcelable
