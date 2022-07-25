package com.cv.perseo.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

@Entity(tableName = "service_orders")
data class ServiceOrder(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "osId") val osId: Int,
    @ColumnInfo(name = "zone") val zone: String,
    @ColumnInfo(name = "rubro_icon") val rubroIcon: String,
    @ColumnInfo(name = "rubro") val rubro: String,
    @ColumnInfo(name = "motivo") val motivo: String,
    @ColumnInfo(name = "id_motivo") val motivoId: String,
    @ColumnInfo(name = "sector") val sector: String?,
    @ColumnInfo(name = "street") val street: String,
    @ColumnInfo(name = "outdoor_number") val outdoorNumber: String,
    @ColumnInfo(name = "indoor_number") val indoorNumber: String,
    @ColumnInfo(name = "pre_cum_date") val preCumDate: String,
    @ColumnInfo(name = "schedule_date") val scheduleDate: String?,
    @ColumnInfo(name = "hour_from") val hourFrom: String?,
    @ColumnInfo(name = "hour_until") val hourUntil: String?,
    @ColumnInfo(name = "schedule_detail") val scheduleDetail: String?
)