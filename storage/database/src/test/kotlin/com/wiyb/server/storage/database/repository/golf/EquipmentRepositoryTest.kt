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
import com.wiyb.server.storage.database.entity.golf.EquipmentEvaluatedMetric
import com.wiyb.server.storage.database.entity.golf.constant.EquipmentType
import com.wiyb.server.storage.database.entity.golf.detail.Ball
import com.wiyb.server.storage.database.entity.golf.detail.Driver
import com.wiyb.server.storage.database.entity.golf.detail.Grip
import com.wiyb.server.storage.database.entity.golf.detail.Hybrid
import com.wiyb.server.storage.database.entity.golf.detail.Iron
import com.wiyb.server.storage.database.entity.golf.detail.Putter
import com.wiyb.server.storage.database.entity.golf.detail.Shaft
import com.wiyb.server.storage.database.entity.golf.detail.Wedge
import com.wiyb.server.storage.database.entity.golf.detail.Wood
import com.wiyb.server.storage.database.repository.golf.detail.BallRepository
import com.wiyb.server.storage.database.repository.golf.detail.DriverRepository
import com.wiyb.server.storage.database.repository.golf.detail.GripRepository
import com.wiyb.server.storage.database.repository.golf.detail.HybridRepository
import com.wiyb.server.storage.database.repository.golf.detail.IronRepository
import com.wiyb.server.storage.database.repository.golf.detail.PutterRepository
import com.wiyb.server.storage.database.repository.golf.detail.ShaftRepository
import com.wiyb.server.storage.database.repository.golf.detail.WedgeRepository
import com.wiyb.server.storage.database.repository.golf.detail.WoodRepository
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestMethodOrder

@Disabled
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class EquipmentRepositoryTest(
    private val brandRepository: BrandRepository,
    private val equipmentRepository: EquipmentRepository,
    private val equipmentEvaluatedMetricRepository: EquipmentEvaluatedMetricRepository,
    private val driverRepository: DriverRepository,
    private val woodRepository: WoodRepository,
    private val hybridRepository: HybridRepository,
    private val ironRepository: IronRepository,
    private val wedgeRepository: WedgeRepository,
    private val putterRepository: PutterRepository,
    private val shaftRepository: ShaftRepository,
    private val gripRepository: GripRepository,
    private val ballRepository: BallRepository
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
                        ).createScoped(listOf(SheetsScopes.SPREADSHEETS))
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

    private fun setSheetId(
        sheet: String,
        start: Int = 2,
        end: Int,
        data: List<Long>
    ) {
//        service
//            .spreadsheets()
//            .values()
//            .update(
//                sheetId,
//                "$sheet!A$start:A$end",
//                ValueRange().setValues(
//                    data.map {
//                        listOf(it.toString())
//                    }
//                )
//            ).setValueInputOption("RAW")
//            .execute()
        println(sheet)
        println(start)
        println(end)
        println(data.size)
    }

    @BeforeAll
    fun beforeAll() {
        val start = 2
        val end = 52
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Brand!B$start:C$end")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>

        values.forEach { row ->
            brands[row[0]] =
                Brand(
                    name = getCol(row, 0)!!,
                    nameKo = getCol(row, 1)
                )
        }
    }

    @Test
    @Order(1)
    fun brand() {
        val start = 2
        val end = 52
        brandRepository.saveAllAndFlush(brands.values)
        setSheetId("Brand", start, end, brands.values.map { it.id })
    }

    @Test
    @Order(2)
    fun driver() {
        val start = 2
        val end = 98
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Driver!B$start:H$end")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val drivers = mutableListOf<Driver>()
        val metrics = mutableListOf<EquipmentEvaluatedMetric>()

        values
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.DRIVER,
                        name = getCol(row, 1)!!,
                        releasedYear = getCol(row, 3),
                        imageUrls = getCol(row, 4)?.split(",")
                    )
                val driver =
                    Driver(
                        equipment,
                        volume = getCol(row, 5)?.toFloat(),
                        loftDegree = getCol(row, 2)?.split(",") ?: emptyList(),
                        isLoftChangeable = getCol(row, 7) == "O",
                        isWeightChangeable = getCol(row, 8) == "O",
                        isWeightMovable = getCol(row, 6) == "O"
                    )
                val metric = EquipmentEvaluatedMetric(equipment, 5f)
                "TaylorMade"
                equipments.add(equipment)
                drivers.add(driver)
                metrics.add(metric)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        driverRepository.saveAllAndFlush(drivers)
        equipmentEvaluatedMetricRepository.saveAllAndFlush(metrics)

        setSheetId("Driver", start, end, drivers.map { it.id })

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
    }

    @Test
    @Order(3)
    fun wood() {
        val start = 2
        val end = 73
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Wood!B$start:I$end")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val woods = mutableListOf<Wood>()
        val metrics = mutableListOf<EquipmentEvaluatedMetric>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.WOOD,
                        name = getCol(row, 1)!!,
                        releasedYear = getCol(row, 3),
                        imageUrls = getCol(row, 4)?.split(",")
                    )

                val primitiveNumber = getCol(row, 5)?.split(",")
                val primitiveLoftDegree = getCol(row, 2)?.split(",")
                val primitiveLieAngle = getCol(row, 6)?.split(",")

                val maxSize =
                    maxOf(
                        primitiveNumber?.size ?: 0,
                        primitiveLoftDegree?.size ?: 0,
                        primitiveLieAngle?.size ?: 0
                    )
                val numbers = primitiveNumber ?: List(maxSize) { "" }
                val loftDegree = primitiveLoftDegree ?: List(maxSize) { "" }
                val lieAngle = primitiveLieAngle ?: List(maxSize) { "" }

                val wood =
                    Wood(
                        equipment,
                        numbers = numbers,
                        loftDegree = loftDegree,
                        // todo: 나중에 시트 업데이트되면 한 번 더 확인 필요!!
                        lieAngle = lieAngle,
                        isLoftChangeable = getCol(row, 7)?.isNotBlank(),
                        isWeightChangeable = getCol(row, 8)?.isNotBlank()
                        // todo: 여기까지
                    )

                val metric = EquipmentEvaluatedMetric(equipment, 5f)

                equipments.add(equipment)
                woods.add(wood)
                metrics.add(metric)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        woodRepository.saveAllAndFlush(woods)
        equipmentEvaluatedMetricRepository.saveAllAndFlush(metrics)

        setSheetId("Wood", start, end, woods.map { it.id })
    }

    @Test
    @Order(4)
    fun hybrid() {
        val start = 2
        val end = 47
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Utility!B$start:J$end")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val hybrids = mutableListOf<Hybrid>()
        val metrics = mutableListOf<EquipmentEvaluatedMetric>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.HYBRID,
                        name = getCol(row, 1)!!,
                        releasedYear = getCol(row, 3),
                        imageUrls = getCol(row, 4)?.split(",")
                    )

                val primitiveNumber = getCol(row, 5)?.split(",")
                val primitiveLoftDegree = getCol(row, 2)?.split(",")
                val primitiveLieAngle = getCol(row, 6)?.split(",")

                val maxSize =
                    maxOf(
                        primitiveNumber?.size ?: 0,
                        primitiveLoftDegree?.size ?: 0,
                        primitiveLieAngle?.size ?: 0
                    )
                val numbers = primitiveNumber ?: List(maxSize) { "" }
                val loftDegree = primitiveLoftDegree ?: List(maxSize) { "" }
                val lieAngle = primitiveLieAngle ?: List(maxSize) { "" }

                val hybrid =
                    Hybrid(
                        equipment,
                        numbers = numbers,
                        loftDegree = loftDegree,
                        // todo: 나중에 시트 업데이트되면 한 번 더 확인 필요!!
                        lieAngle = lieAngle,
                        isLoftChangeable = getCol(row, 7)?.isNotBlank(),
                        isWeightChangeable = getCol(row, 8)?.isNotBlank()
                        // todo: 여기까지
                    )

                val metric = EquipmentEvaluatedMetric(equipment, 5f)

                equipments.add(equipment)
                hybrids.add(hybrid)
                metrics.add(metric)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        hybridRepository.saveAllAndFlush(hybrids)
        equipmentEvaluatedMetricRepository.saveAllAndFlush(metrics)

        setSheetId("Utility", start, end, hybrids.map { it.id })
    }

    @Test
    @Order(5)
    fun iron() {
        val start = 2
        val end = 182
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Iron!B$start:M$end")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val irons = mutableListOf<Iron>()
        val metrics = mutableListOf<EquipmentEvaluatedMetric>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.IRON,
                        name = getCol(row, 1)!!,
                        releasedYear = getCol(row, 5),
                        imageUrls = getCol(row, 6)?.split(",")
                    )
                val primitiveNumber = getCol(row, 7)?.split(",")
                val primitiveLoftDegree = getCol(row, 4)?.split(",")
                val primitiveLieAngle = getCol(row, 10)?.split(",")

                val maxSize =
                    maxOf(
                        primitiveNumber?.size ?: 0,
                        primitiveLoftDegree?.size ?: 0,
                        primitiveLieAngle?.size ?: 0
                    )

                val numbers = primitiveNumber ?: List(maxSize) { "" }
                val loftDegree = primitiveLoftDegree?.toMutableList() ?: MutableList(maxSize) { "" }
                val lieAngle = primitiveLieAngle ?: List(maxSize) { "" }
                val loft7Degree = getCol(row, 8) ?: ""
                val loftPDegree = getCol(row, 9) ?: ""

                if (numbers.isNotEmpty()) {
                    loftDegree.forEachIndexed { idx, elem ->
                        if (loft7Degree.isNotBlank() && elem == "7") {
                            loftDegree[idx] = loft7Degree
                        } else if (loftPDegree.isNotBlank() && elem.lowercase() == "p") {
                            loftDegree[idx] = loftPDegree
                        }
                    }
                }

                val iron =
                    Iron(
                        equipment,
                        produceType = getCol(row, 2),
                        designType = getCol(row, 3),
                        numbers = numbers,
                        loftDegree = loftDegree,
                        lieAngle = lieAngle
                    )

                val metric = EquipmentEvaluatedMetric(equipment, 5f)

                equipments.add(equipment)
                irons.add(iron)
                metrics.add(metric)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        ironRepository.saveAllAndFlush(irons)
        equipmentEvaluatedMetricRepository.saveAllAndFlush(metrics)

        setSheetId("Iron", start, end, irons.map { it.id })
    }

    @Test
    @Order(6)
    fun wedge() {
        val start = 2
        val end = 321
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Wedge!B$start:J$end")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val wedges = mutableListOf<Wedge>()
        val metrics = mutableListOf<EquipmentEvaluatedMetric>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.WEDGE,
                        name = getCol(row, 1)!!,
                        releasedYear = getCol(row, 4),
                        imageUrls = getCol(row, 5)?.split(",")
                    )

                val primitiveLoftDegree = getCol(row, 3)?.split(",")
                val primitiveBounce = getCol(row, 6)?.split(",")
                val primitiveGrind = getCol(row, 7)?.split(",")

                val maxSize =
                    maxOf(
                        primitiveLoftDegree?.size ?: 0,
                        primitiveBounce?.size ?: 0,
                        primitiveGrind?.size ?: 0
                    )

                val loftDegree = primitiveLoftDegree ?: List(maxSize) { "" }
                val bounce = primitiveBounce ?: List(maxSize) { "" }
                val grind = primitiveGrind ?: List(maxSize) { "" }
                val model =
                    loftDegree.mapIndexed { idx, loft ->
                        if (loft.isBlank() || bounce[idx].isBlank()) {
                            ""
                        } else {
                            "$loft.${bounce[idx]}"
                        }
                    }

                val wedge =
                    Wedge(
                        equipment,
                        produceType = getCol(row, 2),
                        model = model,
                        loftDegree = loftDegree,
                        bounce = bounce,
                        grind = grind
                    )

                val metric = EquipmentEvaluatedMetric(equipment, 5f)

                equipments.add(equipment)
                wedges.add(wedge)
                metrics.add(metric)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        wedgeRepository.saveAllAndFlush(wedges)
        equipmentEvaluatedMetricRepository.saveAllAndFlush(metrics)

        setSheetId("Wedge", start, end, wedges.map { it.id })
    }

    // todo: 2번 빠짐
    @Test
    @Order(7)
    fun putter() {
        val start = 2
        val end = 209
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Putter!B$start:J$end")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val putters = mutableListOf<Putter>()
        val metrics = mutableListOf<EquipmentEvaluatedMetric>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.PUTTER,
                        name = getCol(row, 1)!!,
                        releasedYear = getCol(row, 4),
                        imageUrls = getCol(row, 5)?.split(",")
                    )
                val putter =
                    Putter(
                        equipment,
                        loftDegree = getCol(row, 7),
                        weight = getCol(row, 3),
                        neckShape = getCol(row, 6)
                    )

                val metric = EquipmentEvaluatedMetric(equipment, 4f)

                equipments.add(equipment)
                putters.add(putter)
                metrics.add(metric)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        putterRepository.saveAllAndFlush(putters)
        equipmentEvaluatedMetricRepository.saveAllAndFlush(metrics)

        setSheetId("Putter", start, end, putters.map { it.id })
    }

    @Test
    @Order(8)
    fun shaft() {
        val start = 2
        val end = 1809
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Shaft!B$start:M$end")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val shafts = mutableListOf<Shaft>()
        val metrics = mutableListOf<EquipmentEvaluatedMetric>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.SHAFT,
                        name = getCol(row, 1)!!,
                        releasedYear = null,
                        imageUrls = getCol(row, 11)?.split(",")
                    )
                val shaft =
                    Shaft(
                        equipment,
                        weight = getCol(row, 6),
                        strength = getCol(row, 2),
                        kickPoint = getCol(row, 3),
                        torque = getCol(row, 4),
                        texture = getCol(row, 5),
                        tipDiameter = getCol(row, 7),
                        buttDiameter = getCol(row, 8),
                        spin = getCol(row, 9),
                        launch = getCol(row, 10)
                    )

                val metric = EquipmentEvaluatedMetric(equipment, 3f)

                equipments.add(equipment)
                shafts.add(shaft)
                metrics.add(metric)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        shaftRepository.saveAllAndFlush(shafts)
        equipmentEvaluatedMetricRepository.saveAllAndFlush(metrics)

        setSheetId("Shaft", start, end, shafts.map { it.id })
    }

    @Test
    @Order(9)
    fun grip() {
        val start = 2
        val end = 299
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Grip!B$start:I$end")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val grips = mutableListOf<Grip>()
        val metrics = mutableListOf<EquipmentEvaluatedMetric>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.GRIP,
                        name = getCol(row, 1)!!,
                        releasedYear = null,
                        imageUrls = null
                    )
                val grip =
                    Grip(
                        equipment,
                        weight = getCol(row, 3),
                        size = getCol(row, 2),
                        coreSize = getCol(row, 4),
                        feel = null,
                        material = getCol(row, 5),
                        torque = getCol(row, 6),
                        diameter = getCol(row, 7)
                    )

                val metric = EquipmentEvaluatedMetric(equipment, 3f)

                equipments.add(equipment)
                grips.add(grip)
                metrics.add(metric)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        gripRepository.saveAllAndFlush(grips)
        equipmentEvaluatedMetricRepository.saveAllAndFlush(metrics)

        setSheetId("Grip", start, end, grips.map { it.id })
    }

    @Test
    @Order(10)
    fun ball() {
        val start = 2
        val end = 26
        val range =
            service
                .spreadsheets()
                .values()
                .get(sheetId, "Ball!B$start:J$end")
                .execute()

        @Suppress("UNCHECKED_CAST")
        val values = range.getValues() as List<List<String>>
        val equipments = mutableListOf<Equipment>()
        val balls = mutableListOf<Ball>()
        val metrics = mutableListOf<EquipmentEvaluatedMetric>()

        println(values[0].mapIndexed { index, value -> "$index: $value" })
        println(values[0].size)
        println(values[1].let { if (it.size > 18 && it[18].isNotBlank()) it[18] else null })
        println(values[2].size)
        println("\n======================================\n")

        values
            .forEachIndexed { index, row ->
                println("$index - $row")
                val equipment =
                    Equipment(
                        brand = brands[getCol(row, 0)]!!,
                        type = EquipmentType.BALL,
                        name = getCol(row, 1)!!,
                        releasedYear = null,
                        imageUrls = getCol(row, 8)?.split(",")
                    )
                val ball =
                    Ball(
                        equipment,
                        piece = getCol(row, 2),
                        spin = getCol(row, 3),
                        launch = getCol(row, 4),
                        feel = getCol(row, 5),
                        dimple = getCol(row, 6),
                        texture = getCol(row, 7)
                    )

                val metric = EquipmentEvaluatedMetric(equipment, 0f)

                equipments.add(equipment)
                balls.add(ball)
                metrics.add(metric)
            }

        equipmentRepository.saveAllAndFlush(equipments)
        ballRepository.saveAllAndFlush(balls)
        equipmentEvaluatedMetricRepository.saveAllAndFlush(metrics)

        setSheetId("Ball", start, end, balls.map { it.id })
    }
}
