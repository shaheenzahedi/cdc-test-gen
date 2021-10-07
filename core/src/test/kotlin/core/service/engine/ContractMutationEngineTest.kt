package core.service.engine

import core.domain.contract.contractor.Contract
import core.domain.ready_to_generate.ReadyToTestModel
import core.service.io.resource.file.FileResource
import core.service.mapper.ContractMapper
import core.service.mapper.JsonMapper
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNotSame

internal class ContractMutationEngineTest {

    private var contracts: List<ReadyToTestModel>? = null
    lateinit var mutateContract: ContractMutationEngine

    @BeforeEach
    fun setUp() {
        val contractPath = "../sample-contract.json"
        val generalContract = JsonMapper(FileResource()).makeGeneralContract(path = contractPath) as Contract
        contracts = ContractMapper(generalContract).extreactReadyToTestModel()
        mutateContract = ContractMutationEngine(contracts)
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `Check list of status mutation is not empty`() {
        assert(!contracts.isNullOrEmpty())
        val statusMutations = mutateContract.generateStatusMutation()
        assert(!statusMutations.isNullOrEmpty())
    }

    @Test
    fun `Check status mutations generated by the engine is not the same as our status code`() {
        assert(!contracts.isNullOrEmpty())
        val statusMutations = mutateContract.generateStatusMutation()
        val status = contracts?.get(0)?.status
        assertNotNull(status)
        statusMutations.forEach { assertNotSame(status, it) }
    }

    @Test
    fun `Check list of method mutation is not empty`() {
        assert(!contracts.isNullOrEmpty())
        val methodMutations = mutateContract.generateMethodMutations()
        assert(!methodMutations.isNullOrEmpty())
    }

    @Test
    fun `Check method mutations generated by the engine is not the same as our method`() {
        assert(!contracts.isNullOrEmpty())
        val methodMutations = mutateContract.generateMethodMutations()
        val method = contracts?.get(0)?.method
        assertNotNull(method)
        methodMutations.forEach { assertNotSame(it, method) }
    }

    @Test
    fun `Check body response mutation is not empty and is not the same generated by the engine`() {
        assert(!contracts.isNullOrEmpty())
        val bodyMutations = mutateContract.generateResponseBodyMutations(0)
        assert(!bodyMutations.isNullOrEmpty())
        val body = contracts?.get(0)?.response?.body
        assert(!body.isNullOrEmpty())
        bodyMutations?.forEach { assertNotEquals(it, body) }
    }
    @Test
    fun `Check body request mutation is not empty and is not the same generated by the engine`() {
        assert(!contracts.isNullOrEmpty())
        val bodyMutations = mutateContract.generateRequestBodyMutations(0)
        assert(!bodyMutations.isNullOrEmpty())
        val body = contracts?.get(0)?.request?.body
        assert(!body.isNullOrEmpty())
        bodyMutations?.forEach { assertNotEquals(it, body) }
    }

    @Test
    fun `Check header mutation is not empty and is not the same generated by the engine`(){
        assert(!contracts.isNullOrEmpty())
        val headerMutations = mutateContract.generateRequestHeaderMutations(0)
        assert(!headerMutations.isNullOrEmpty())
        val headers = contracts?.get(0)?.response?.headers
        assert(!headers.isNullOrEmpty())
        headerMutations?.forEach { assertNotEquals(it, headers) }
    }

    @Test
    fun `Check params mutation is not empty and is not the same generated by the engine`(){
        assert(!contracts.isNullOrEmpty())
        val paramsMutations = mutateContract.generateParamsMutations(0)
        assert(!paramsMutations.isNullOrEmpty())
        val params = contracts?.get(0)?.request?.params
        assert(!params.isNullOrEmpty())
        paramsMutations?.forEach { assertNotEquals(it, params) }
    }

    @Test
    fun `Check cookies mutation is not empty and is not the same generated by the engine`(){
        assert(!contracts.isNullOrEmpty())
        val cookiesMutations = mutateContract.generateCookiesMutations(0)
        assert(!cookiesMutations.isNullOrEmpty())
        val cookies = contracts?.get(0)?.request?.cookies
        assert(!cookies.isNullOrEmpty())
        cookiesMutations?.forEach { assertNotEquals(it, cookies) }
    }


}