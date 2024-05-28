package com.microservices.projectfinal.data.sql

object AvailabilityScheduleService {
    fun getAvailabilitySchedule(): String {
        return """
            SELECT * FROM availabilities AS av JOIN dim_time 
            ON av.dim_time_key = dim_time.time_key 
            AND av.dim_time_key >= :timeKey 
            AND av.tutor_id=:tutorId;
        """.trimIndent()
    }
}