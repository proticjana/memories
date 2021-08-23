package jpro.memories.models

import android.os.Parcel
import android.os.Parcelable

data class MemoryModel(
    val id: Int,
    val name: String?,
    val description: String?,
    val date: String?,
    val location: String?,
    val latitude: Double,
    val longitude: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(date)
        parcel.writeString(location)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MemoryModel> {
        override fun createFromParcel(parcel: Parcel): MemoryModel {
            return MemoryModel(parcel)
        }

        override fun newArray(size: Int): Array<MemoryModel?> {
            return arrayOfNulls(size)
        }
    }
}