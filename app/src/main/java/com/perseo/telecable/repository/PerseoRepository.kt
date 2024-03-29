package com.perseo.telecable.repository

import android.util.Log
import com.perseo.telecable.model.perseoresponse.*
import com.perseo.telecable.network.PerseoApi
import retrofit2.Response
import javax.inject.Inject

class PerseoRepository @Inject constructor(private val api: PerseoApi) {

    suspend fun login(userId: String, password: String): Response<LoginResponse> {
        return api.login(0, userId, password)
    }

    suspend fun getServiceOrders(userId: String, enterpriseId: Int, osId: Int = -1): Response<ServiceOrdersResponse> {
        return api.serviceOrders(1, userId, enterpriseId, osId)
    }

    suspend fun getCordsOrders(userId: String, enterpriseId: Int): Response<CordsOrdersResponse> {
        return api.cordsOrders(2, userId, enterpriseId)
    }

    suspend fun getInventory(enterpriseId: Int, userId: String): Response<InventoryResponse> {
        return api.inventory(3, enterpriseId, userId)
    }

    suspend fun motivoOrders(motivoId: String, enterpriseId: Int): Response<MotivosResponse> {
        return api.motivoOrders(4, motivoId, enterpriseId)
    }

    suspend fun validateEquipment(enterpriseId: Int, parameters: String): Response<ValidateEquipmentResponse> {
        return api.validateEquipment(5, enterpriseId, parameters)
    }

    suspend fun getRoutersCT(enterpriseId: Int): Response<RoutersCTResponse> {
        return api.getRoutersCT(6, enterpriseId)
    }

    suspend fun finalizarOrdenServicio(empresa_id: Int, ordenes_info_cumplimiento: String, fotos: String, equipos: String, orden: Int, fecha: String, materiales: String, parametros: String, info_cumplimiento: String): Response<String> {
        return api.finalizarOrdenServicio(7, empresa_id, ordenes_info_cumplimiento, fotos, equipos, orden, fecha, materiales, parametros, info_cumplimiento)
    }

    suspend fun cancelOrder(enterpriseId: Int, cancelReason: String): Response<CancelOrder> {
        return api.cancelOrder(8, enterpriseId, cancelReason)
    }

    suspend fun getSubscriberImages(enterpriseId: Int, requestNumber: Int): Response<SubscriberImagesResponse> {
        return api.getSubscriberImages(9, enterpriseId, requestNumber)
    }

    suspend fun verifyVersion(version: String): Response<VerifyVersionResponse> {
        return api.verifyVersion(10, version)
    }

    suspend fun oSBlockCompliance(enterpriseId: Int, date: String, osIdsArray: String): Response<String> {
        return api.oSBlockCompliance(11, enterpriseId = enterpriseId, date = date, osIdsArray = osIdsArray)
    }

    suspend fun getAllMaterials(enterpriseId: Int): Response<MaterialResponse> {
        return api.getAllMaterials(12, enterpriseId)
    }

    suspend fun signDocument(signature: String, idOs:String, enterpriseId:Int) : Response<SignDocumentResponse> {
        return api.signDocument(13,signature, idOs, enterpriseId)
    }
}