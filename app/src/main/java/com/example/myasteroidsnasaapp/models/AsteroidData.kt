package com.example.myasteroidsnasaapp.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "asteroid_table")
data class AsteroidData(
    @PrimaryKey var id: Long,
    var codename: String,
    var closeApproachDate:String,
    var absoluteMagnitude: Double,
    var estimatedDiameter: Double,
    var relativeVelocity: Double,
    var distanceFromEarth: Double,
    var isPotentiallyHazardous: Boolean
) : Parcelable


