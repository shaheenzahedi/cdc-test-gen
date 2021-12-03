import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration.options
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer
import core.service.callback.CallbackMapper
import core.service.io.FileDialog
import core.service.io.resource.file.FileResource
import core.service.mapper.ContractMapper
import core.service.mapper.GeneralMapper
import service.stub.StubGenerator
import java.util.*
import javax.swing.filechooser.FileNameExtensionFilter


fun main() {
    println("Could not find the contract, select contract file?")
    val path = FileDialog().open("Contract JSON file", false, FileNameExtensionFilter("Contract JSON or groovy", "json","groovy"))
    requireNotNull(path) { throw Exception("You need to select a contract in order to continue, closing...") }
    val contract = GeneralMapper(FileResource()).makeGeneralContract(path)
    val model = ContractMapper(contract).extreactReadyToTestModel()
    val port = 8080
    val wireMockServer = WireMockServer(
        options().port(port).notifier(ConsoleNotifier(true)).extensions(ResponseTemplateTransformer(true))
    )
    wireMockServer.start()
    wireMockServer.importStubs(StubGenerator(model).createAllStubs())
    println(
        """
        Mock server is running! Access URLs:
        Local:      http://localhost:$port/
        External:   http://127.0.1.1:$port/
        Press CTRL+C to stop
    """.trimIndent()
    )
    requireNotNull(model) {
        throw Exception("We could not extract model from the contract, check that you're contract is in standard format")
    }
    val mapper = CallbackMapper(model)
    mapper.callbacks().forEach {
        val result = if (it.callback.invoke())"OK" else "Not Working... Please Consider reporting this to the maintainers"
        println("${it.tagName}\t$result")
    }
    Scanner(System.`in`).next()
}


