package jpro.memories.models

data class MemoryModel (
        val id: Int,
        val name: String,
        val description: String,
        val date: String,
        val location: String,
        val latitude: Double,
        val longitude: Double
        )