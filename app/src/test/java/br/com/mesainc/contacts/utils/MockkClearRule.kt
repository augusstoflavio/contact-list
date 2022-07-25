package br.com.mesainc.contacts.utils

import io.mockk.clearAllMocks
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MockkClearRule: TestWatcher() {

    override fun finished(description: Description?) {
        super.finished(description)
        clearAllMocks()
    }
}