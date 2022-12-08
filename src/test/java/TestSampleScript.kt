import com.cereal.licensechecker.LicenseChecker
import com.cereal.licensechecker.LicenseState
import com.cereal.script.sample.SampleConfiguration
import com.cereal.script.sample.SampleScript
import com.cereal.test.TestScriptRunner
import com.cereal.test.components.TestComponentProvider
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import kotlinx.coroutines.runBlocking
import org.junit.Test

class TestSampleScript {

    @Test
    fun testSuccess() = runBlocking {
        // Initialize script and the test script runner.
        val script = SampleScript()
        val scriptRunner = TestScriptRunner(script)

        // Mock the LicenseChecker
        mockkConstructor(LicenseChecker::class)
        coEvery { anyConstructed<LicenseChecker>().checkAccess() } returns LicenseState.Licensed

        // Mock the configuration values
        val configuration = mockk<SampleConfiguration> {
            every { keyString() } returns "Some random string"
            every { keyBoolean() } returns true
            every { keyInteger() } returns 100
            every { keyFloat() } returns 101.0f
            every { keyDouble() } returns 102.0
        }
        val componentProvider = TestComponentProvider()

        // Run our script
        scriptRunner.run(configuration, componentProvider)
    }

}
