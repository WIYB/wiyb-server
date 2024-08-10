package com.wiyb.server.storage.database.repository.golf

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.auth.Credentials
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import com.wiyb.server.storage.DatabaseContextTest
import com.wiyb.server.storage.database.entity.golf.Brand
import com.wiyb.server.storage.database.entity.golf.Equipment
import com.wiyb.server.storage.database.entity.golf.EquipmentDetail
import com.wiyb.server.storage.database.entity.golf.constant.Difficulty
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.user.constant.Gender
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder
import kotlin.test.Test

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class EquipmentRepositoryTest(
    private val brandRepository: BrandRepository,
    private val equipmentRepository: EquipmentRepository,
    private val equipmentDetailRepository: EquipmentDetailRepository
) : DatabaseContextTest() {
    private val sheetId = "1zE3ctDFHg-tu05EiGCL9tOIdLxCbv-nCJi-_7Oq4AcQ"
    private val service =
        Sheets
            .Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                HttpCredentialsAdapter(
                    GoogleCredentials
                        .fromStream(
                            Credentials::class.java.getResourceAsStream("/google_secret.json")
                        ).createScoped(listOf(SheetsScopes.SPREADSHEETS_READONLY))
                )
            ).setApplicationName("golf")
            .build()
    private val brands = mutableMapOf<String, Brand>()

    private fun getCol(
        row: List<String>,
        index: Int
    ): String? =
        if (row.size > index && row[index].isNotBlank()) {
            row[index].trim()
        } else {
            null
        }

    @BeforeAll
    fun beforeAll() {
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Brand!B2:B52")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = (range.getValues() as List<List<String>>).flatten()

        values.forEach { brands[it] = Brand(name = it) }
    }

    @Test
    @Order(1)
    fun brand() {
        brandRepository.saveAllAndFlush(brands.values)
    }

    @Test
    @Order(2)
    fun driver() {
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Driver!B1:T98")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val equipmentDetails = mutableListOf<EquipmentDetail>()

        values
            .filterIndexed { index, _ -> index != 0 }
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.DRIVER,
                        name = getCol(row, 1)!!,
                        releasedYear = getCol(row, 10),
                        imageUrls = getCol(row, 11)?.split(",")
                    )
                val equipmentDetail =
                    EquipmentDetail(
                        equipment,
                        launch = getCol(row, 17),
                        spin = getCol(row, 18),
                        gender = null,
                        color = getCol(row, 6),
                        weight = getCol(row, 7),
                        // head
                        headProduceType = getCol(row, 3),
                        headDesignType = getCol(row, 4),
                        headNumber = getCol(row, 13),
                        headShape = getCol(row, 5),
                        headDifficulty = getCol(row, 8)?.let { enumValueOf<Difficulty>(it.uppercase()) },
                        headLoftDegree = getCol(row, 9),
                        driverVolume = getCol(row, 12)?.toFloat(),
                        iron7LoftDegree = null,
                        ironPLoftDegree = null,
                        putterNeckShape = null,
                        // shaft
                        shaftStrength = null,
                        shaftKickPoint = null,
                        shaftTorque = null,
                        shaftTexture = null,
                        // grip
                        gripType = null,
                        gripRound = null,
                        // ball
                        ballPiece = null,
                        ballCover = null
                    )
                equipments.add(equipment)
                equipmentDetails.add(equipmentDetail)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        equipmentDetailRepository.saveAllAndFlush(equipmentDetails)

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
    }

    @Test
    @Order(3)
    fun wood() {
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Wood!B1:S73")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val equipmentDetails = mutableListOf<EquipmentDetail>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .filterIndexed { index, _ -> index != 0 }
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.WOOD,
                        name = getCol(row, 1)!!,
                        releasedYear = getCol(row, 10),
                        imageUrls = getCol(row, 11)?.split(",")
                    )
                val equipmentDetail =
                    EquipmentDetail(
                        equipment,
                        gender = getCol(row, 17)?.let { enumValueOf<Gender>(it.uppercase()) },
                        color = getCol(row, 6),
                        weight = getCol(row, 7),
                        // head
                        headProduceType = getCol(row, 3),
                        headDesignType = getCol(row, 4),
                        headNumber = getCol(row, 13),
                        headShape = getCol(row, 5),
                        headDifficulty = getCol(row, 8)?.let { enumValueOf<Difficulty>(it.uppercase()) },
                        headLoftDegree = getCol(row, 9),
                        driverVolume = getCol(row, 12)?.toFloat(),
                        iron7LoftDegree = getCol(row, 14),
                        ironPLoftDegree = getCol(row, 15),
                        putterNeckShape = getCol(row, 16)
                    )
                equipments.add(equipment)
                equipmentDetails.add(equipmentDetail)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        equipmentDetailRepository.saveAllAndFlush(equipmentDetails)
    }

    @Test
    @Order(4)
    fun utility() {
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Utility!B1:S47")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val equipmentDetails = mutableListOf<EquipmentDetail>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .filterIndexed { index, _ -> index != 0 }
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.UTILITY,
                        name = getCol(row, 1)!!,
                        releasedYear = getCol(row, 10),
                        imageUrls = getCol(row, 11)?.split(",")
                    )
                val equipmentDetail =
                    EquipmentDetail(
                        equipment,
                        gender = getCol(row, 17)?.let { enumValueOf<Gender>(it.uppercase()) },
                        color = getCol(row, 6),
                        weight = getCol(row, 7),
                        // head
                        headProduceType = getCol(row, 3),
                        headDesignType = getCol(row, 4),
                        headNumber = getCol(row, 13),
                        headShape = getCol(row, 5),
                        headDifficulty = getCol(row, 8)?.let { enumValueOf<Difficulty>(it.uppercase()) },
                        headLoftDegree = getCol(row, 9),
                        driverVolume = getCol(row, 12)?.toFloat(),
                        iron7LoftDegree = getCol(row, 14),
                        ironPLoftDegree = getCol(row, 15),
                        putterNeckShape = getCol(row, 16)
                    )
                equipments.add(equipment)
                equipmentDetails.add(equipmentDetail)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        equipmentDetailRepository.saveAllAndFlush(equipmentDetails)
    }

    @Test
    @Order(5)
    fun iron() {
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Iron!B1:Q182")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val equipmentDetails = mutableListOf<EquipmentDetail>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .filterIndexed { index, _ -> index != 0 }
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.IRON,
                        name = getCol(row, 1)!!,
                        releasedYear = getCol(row, 10),
                        imageUrls = getCol(row, 11)?.split(",")
                    )
                val equipmentDetail =
                    EquipmentDetail(
                        equipment,
                        color = getCol(row, 6),
                        weight = getCol(row, 7),
                        // head
                        headProduceType = getCol(row, 3),
                        headDesignType = getCol(row, 4),
                        headNumber = getCol(row, 13),
                        headShape = getCol(row, 5),
                        headDifficulty = getCol(row, 8)?.let { enumValueOf<Difficulty>(it.uppercase()) },
                        headLoftDegree = getCol(row, 9),
                        driverVolume = getCol(row, 12)?.toFloat(),
                        iron7LoftDegree = getCol(row, 14),
                        ironPLoftDegree = getCol(row, 15),
                        putterNeckShape = getCol(row, 16)
                    )
                equipments.add(equipment)
                equipmentDetails.add(equipmentDetail)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        equipmentDetailRepository.saveAllAndFlush(equipmentDetails)
    }

    @Test
    @Order(6)
    fun wedge() {
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Wedge!B1:O321")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val equipmentDetails = mutableListOf<EquipmentDetail>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .filterIndexed { index, _ -> index != 0 }
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.WEDGE,
                        name = getCol(row, 1)!!,
                        releasedYear = getCol(row, 10),
                        imageUrls = getCol(row, 11)?.split(",")
                    )
                val equipmentDetail =
                    EquipmentDetail(
                        equipment,
                        color = getCol(row, 6),
                        weight = getCol(row, 7),
                        launch = null,
                        spin = null,
                        gender = null,
                        bounce = getCol(row, 12),
                        grind = getCol(row, 13),
                        // head
                        headProduceType = getCol(row, 3),
                        headDesignType = getCol(row, 4),
                        headNumber = null,
                        headShape = getCol(row, 5),
                        headDifficulty = getCol(row, 8)?.let { enumValueOf<Difficulty>(it.uppercase()) },
                        headLoftDegree = getCol(row, 9),
                        driverVolume = null,
                        iron7LoftDegree = null,
                        ironPLoftDegree = null,
                        putterNeckShape = null,
                        // shaft
                        shaftStrength = null,
                        shaftKickPoint = null,
                        shaftTorque = null,
                        shaftTexture = null,
                        // grip
                        gripType = null,
                        gripRound = null,
                        // ball
                        ballPiece = null,
                        ballCover = null
                    )
                equipments.add(equipment)
                equipmentDetails.add(equipmentDetail)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        equipmentDetailRepository.saveAllAndFlush(equipmentDetails)
    }

    // todo: 2번 빠짐
    @Test
    @Order(7)
    fun putter() {
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Putter!B1:S209")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val equipmentDetails = mutableListOf<EquipmentDetail>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .filterIndexed { index, _ -> index != 0 }
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.WEDGE,
                        name = getCol(row, 1)!!,
                        releasedYear = getCol(row, 10),
                        imageUrls = getCol(row, 11)?.split(",")
                    )
                val equipmentDetail =
                    EquipmentDetail(
                        equipment,
                        gender = getCol(row, 17)?.let { enumValueOf<Gender>(it.uppercase()) },
                        color = getCol(row, 6),
                        weight = getCol(row, 7),
                        // head
                        headProduceType = getCol(row, 3),
                        headDesignType = getCol(row, 4),
                        headNumber = getCol(row, 13),
                        headShape = getCol(row, 5),
                        headDifficulty = getCol(row, 8)?.let { enumValueOf<Difficulty>(it.uppercase()) },
                        headLoftDegree = getCol(row, 9),
                        driverVolume = getCol(row, 12)?.toFloat(),
                        iron7LoftDegree = getCol(row, 14),
                        ironPLoftDegree = getCol(row, 15),
                        putterNeckShape = getCol(row, 16)
                    )
                equipments.add(equipment)
                equipmentDetails.add(equipmentDetail)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        equipmentDetailRepository.saveAllAndFlush(equipmentDetails)
    }

    @Test
    @Order(8)
    fun shaft() {
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Shaft!B1:N1809")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val equipmentDetails = mutableListOf<EquipmentDetail>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .filterIndexed { index, _ -> index != 0 }
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.SHAFT,
                        name = getCol(row, 1)!!,
                        releasedYear = null,
                        imageUrls = getCol(row, 12)?.split(",")
                    )
                val equipmentDetail =
                    EquipmentDetail(
                        equipment,
                        weight = getCol(row, 6),
                        launch = getCol(row, 10),
                        spin = getCol(row, 9),
                        shaftStrength = getCol(row, 2),
                        shaftKickPoint = getCol(row, 3),
                        shaftTorque = getCol(row, 4),
                        shaftTexture = getCol(row, 5),
                        shaftTipDiameter = getCol(row, 7),
                        shaftButtDiameter = getCol(row, 8),
                        shaftBend = getCol(row, 11)
                    )
                equipments.add(equipment)
                equipmentDetails.add(equipmentDetail)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        equipmentDetailRepository.saveAllAndFlush(equipmentDetails)
    }

    @Test
    @Order(9)
    fun ball() {
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Ball!B1:J26")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val equipmentDetails = mutableListOf<EquipmentDetail>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .filterIndexed { index, _ -> index != 0 }
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.SHAFT,
                        name = getCol(row, 1)!!,
                        releasedYear = null,
                        imageUrls = getCol(row, 8)?.split(",")
                    )
                val equipmentDetail =
                    EquipmentDetail(
                        equipment,
                        spin = getCol(row, 3),
                        launch = getCol(row, 4),
                        ballPiece = getCol(row, 2)?.toInt(),
                        ballCover = getCol(row, 7),
                        ballFeel = getCol(row, 5),
                        ballDimple = getCol(row, 6)
                    )
                equipments.add(equipment)
                equipmentDetails.add(equipmentDetail)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        equipmentDetailRepository.saveAllAndFlush(equipmentDetails)
    }
}
