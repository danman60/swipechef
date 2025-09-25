package com.swipechef.app.data.remote

import kotlinx.coroutines.delay

// Simplified Supabase client implementation for compilation
object SupabaseClient {
    const val SUPABASE_URL = "YOUR_SUPABASE_PROJECT_URL"
    const val SUPABASE_ANON_KEY = "YOUR_SUPABASE_ANON_KEY"

    val client = MockSupabaseClient()
}

class MockSupabaseClient {
    fun from(table: String): MockTable {
        return MockTable(table)
    }

    val storage: MockStorage = MockStorage()
}

class MockTable(private val tableName: String) {
    fun select(columns: String = "*"): MockQuery {
        return MockQuery(tableName, columns)
    }

    suspend fun insert(data: Map<String, Any?>): MockResponse {
        delay(100) // Simulate network delay
        return MockResponse(success = true, data = listOf(data))
    }

    suspend fun update(data: Map<String, Any?>): MockUpdateBuilder {
        return MockUpdateBuilder(data)
    }

    fun delete(): MockDeleteBuilder {
        return MockDeleteBuilder()
    }
}

class MockQuery(private val tableName: String, private val columns: String) {
    fun eq(column: String, value: Any): MockQuery {
        return this
    }

    fun order(column: String, ascending: Boolean = true): MockQuery {
        return this
    }

    suspend fun execute(): MockResponse {
        delay(100) // Simulate network delay
        return MockResponse(success = true, data = emptyList())
    }
}

class MockUpdateBuilder(private val data: Map<String, Any?>) {
    fun eq(column: String, value: Any): MockUpdateExecutor {
        return MockUpdateExecutor(data)
    }
}

class MockUpdateExecutor(private val data: Map<String, Any?>) {
    suspend fun execute(): MockResponse {
        delay(100)
        return MockResponse(success = true, data = listOf(data))
    }
}

class MockDeleteBuilder {
    fun eq(column: String, value: Any): MockDeleteExecutor {
        return MockDeleteExecutor()
    }
}

class MockDeleteExecutor {
    suspend fun execute(): MockResponse {
        delay(100)
        return MockResponse(success = true, data = emptyList())
    }
}

class MockStorage {
    fun from(bucket: String): MockBucket {
        return MockBucket(bucket)
    }
}

class MockBucket(private val bucketName: String) {
    suspend fun upload(path: String, data: ByteArray): MockResponse {
        delay(200)
        return MockResponse(success = true, data = listOf(mapOf("path" to path)))
    }

    suspend fun remove(files: List<String>): MockResponse {
        delay(100)
        return MockResponse(success = true, data = emptyList())
    }
}

data class MockResponse(
    val success: Boolean,
    val data: List<Map<String, Any?>>
)