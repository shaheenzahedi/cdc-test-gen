package service.generators.callback

import domain.ready_to_generate.HTTPMethod
import domain.ready_to_generate.ReadyToTestModel
import khttp.responses.Response

class HTTPHandler(private val model: ReadyToTestModel) {
    fun retrieveResponse(): Response {
        val pathToCall =
            "${model.baseUrl}${if (model.port != null) ":${model.port}" else ""}/${model.path}"
        return performCall(pathToCall)
    }

    private fun performCall(path: String): Response {

        return when (model.method) {
            HTTPMethod.PUT -> khttp.put(
                url = path,
                headers = model.request?.headers ?: mapOf(),
                params = model.request?.params ?: mapOf(),
                cookies = model.request?.cookies ?: mapOf(),
                json = model.request?.body,
                data = model.request?.urlEncoded
            )
            HTTPMethod.POST -> khttp.post(
                url = path,
                headers = model.request?.headers ?: mapOf(),
                params = model.request?.params ?: mapOf(),
                cookies = model.request?.cookies ?: mapOf(),
                json = model.request?.body,
                data = model.request?.urlEncoded
            )
            HTTPMethod.GET -> khttp.get(
                url = path,
                headers = model.request?.headers ?: mapOf(),
                params = model.request?.params ?: mapOf(),
                cookies = model.request?.cookies ?: mapOf(),
                json = model.request?.body,
                data = model.request?.urlEncoded
            )
            HTTPMethod.DELETE -> khttp.delete(
                url = path,
                headers = model.request?.headers ?: mapOf(),
                params = model.request?.params ?: mapOf(),
                cookies = model.request?.cookies ?: mapOf(),
                json = model.request?.body,
                data = model.request?.urlEncoded
            )
            HTTPMethod.PATCH -> khttp.patch(
                url = path,
                headers = model.request?.headers ?: mapOf(),
                params = model.request?.params ?: mapOf(),
                cookies = model.request?.cookies ?: mapOf(),
                json = model.request?.body,
                data = model.request?.urlEncoded
            )
        }
    }
}