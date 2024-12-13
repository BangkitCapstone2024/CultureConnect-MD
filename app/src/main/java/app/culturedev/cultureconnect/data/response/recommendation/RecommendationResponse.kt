package app.culturedev.cultureconnect.data.response.recommendation

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class RecommendationResponse(

    @field:SerializedName("Quote")
    val quote: String? = null,

    @field:SerializedName("Cafe Recommendation")
    val cafeRecommendation: List<CafeRecommendationItem?>? = null,

    @field:SerializedName("Confidence Scores")
    val confidenceScores: ConfidenceScores? = null,

    @field:SerializedName("Predicted Mood")
    val predictedMood: String? = null
)

data class CafeRecommendationItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("Category")
    val category: String? = null,

    @field:SerializedName("Opsi_Layanan")
    val opsiLayanan: String? = null,

    @field:SerializedName("Parkir")
    val parkir: String? = null,

    @field:SerializedName("Address")
    val address: String? = null,

    @field:SerializedName("Page_URL")
    val pageURL: String? = null,

    @field:SerializedName("Pilihan_Makanan")
    val pilihanMakanan: String? = null,

    @field:SerializedName("Rating")
    val rating: String? = null,

    @field:SerializedName("Title")
    val title: String? = null,

    @field:SerializedName("Tipe_pengunjung")
    val tipePengunjung: String? = null,

    @field:SerializedName("Phone_Number")
    val phoneNumber: String? = null,

    @field:SerializedName("Penawaran")
    val penawaran: String? = null,

    @field:SerializedName("Perencanaan")
    val perencanaan: String? = null,


    @field:SerializedName("Pembayaran")
    val pembayaran: String? = null,

    @field:SerializedName("Reviews")
    val reviews: Int? = null,

    @field:SerializedName("Plus_code")
    val plusCode: String? = null,

    @field:SerializedName("Price")
    val price: String? = null,

    @field:SerializedName("anak_anak")
    val anakAnak: String? = null,

    @field:SerializedName("Suasana")
    val suasana: String? = null,

    @field:SerializedName("Jadwal")
    val jadwal: String? = null,

    @field:SerializedName("Fasilitas")
    val fasilitas: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(image)
        parcel.writeString(category)
        parcel.writeString(opsiLayanan)
        parcel.writeString(parkir)
        parcel.writeString(address)
        parcel.writeString(pageURL)
        parcel.writeString(pilihanMakanan)
        parcel.writeString(rating)
        parcel.writeString(title)
        parcel.writeString(tipePengunjung)
        parcel.writeString(phoneNumber)
        parcel.writeString(penawaran)
        parcel.writeString(perencanaan)
        parcel.writeString(pembayaran)
        parcel.writeValue(reviews)
        parcel.writeString(plusCode)
        parcel.writeString(price)
        parcel.writeString(anakAnak)
        parcel.writeString(suasana)
        parcel.writeString(jadwal)
        parcel.writeString(fasilitas)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CafeRecommendationItem> {
        override fun createFromParcel(parcel: Parcel): CafeRecommendationItem {
            return CafeRecommendationItem(parcel)
        }

        override fun newArray(size: Int): Array<CafeRecommendationItem?> {
            return arrayOfNulls(size)
        }
    }
}


data class ConfidenceScores(

    @field:SerializedName("Anger")
    val anger: Any? = null,

    @field:SerializedName("Fear")
    val fear: Any? = null,

    @field:SerializedName("Joy")
    val joy: Any? = null,

    @field:SerializedName("Love")
    val love: Any? = null,

    @field:SerializedName("Surprise")
    val surprise: Any? = null,

    @field:SerializedName("Sadness")
    val sadness: Any? = null
)
