package com.cv.perseo.screens.ordersoptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cv.perseo.model.database.ServiceOrder
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.PerseoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersOptionsScreenViewModel @Inject constructor(
    private val dbRepository: DatabaseRepository,
    private val perseoRepository: PerseoRepository
) :
    ViewModel() {


    init {
        viewModelScope.launch {
            dbRepository.deleteServiceOrders()
            dbRepository.getGeneralData().distinctUntilChanged()
                .collect { generalData ->
                    val response = perseoRepository.getServiceOrders(
                        userId = generalData[0].idUser,
                        enterpriseId = generalData[0].idMunicipality
                    )
                    if (response.isSuccessful) {
                        if (response.body()?.responseCode == 200) {
                            val orders = response.body()?.responseBody
                            for (order in orders!!) {
                                dbRepository.insertServiceOrders(
                                    ServiceOrder(
                                        osId = order.osId,
                                        zone = order.zone,
                                        rubroIcon = order.rubroIcon,
                                        rubro = order.rubro,
                                        motivo = order.motivo,
                                        sector = order.sector,
                                        street = order.street,
                                        outdoorNumber = order.outdoorNumber,
                                        indoorNumber = order.indoorNumber,
                                        preCumDate = order.preCumDate,
                                        scheduleDate = order.scheduleDate,
                                        hourFrom = order.hourFrom,
                                        hourUntil = order.hourUntil,
                                        scheduleDetail = order.scheduleDetail
                                    )
                                )
                            }
                        }
                    }
                }
        }
    }
}