package app.culturedev.cultureconnect.data.response.listcafe

import com.google.gson.annotations.SerializedName

data class ListCafeResponse(

    @field:SerializedName("Cafe Data")
    val cafeData: List<CafeDataItem?>? = null
)

data class CafeDataItem(

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
)
