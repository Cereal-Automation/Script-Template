package com.cereal.script.sample

import com.cereal.api.script.Script
import com.cereal.api.script.TaskStatus
import com.cereal.component.ComponentProvider

class SampleScript : Script<SampleConfiguration> {
    override suspend fun onStart(configuration: SampleConfiguration, provider: ComponentProvider): Boolean {
        return true
    }

    override suspend fun loop(configuration: SampleConfiguration, provider: ComponentProvider): TaskStatus {
        provider.logger().info("Found boolean config value: ${configuration.keyBoolean()}")
        provider.logger().info("Found integer config value: ${configuration.keyInteger()}")
        provider.logger().info("Found float config value: ${configuration.keyFloat()}")
        provider.logger().info("Found string config value: ${configuration.keyString()}")
        provider.logger().info("Found enum config value: ${configuration.dropdownOption()}")

        return TaskStatus.Success("Printed configuration")
    }

    override suspend fun onFinish(configuration: SampleConfiguration, provider: ComponentProvider) {
    }
}
