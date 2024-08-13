package com.wiyb.server.storage.database.entity.golf.constant

import com.wiyb.server.storage.database.converter.AbstractCodedEnumConverter
import com.wiyb.server.storage.database.converter.CodedEnum
import jakarta.persistence.Converter

enum class BrandName(
    private val code: String,
    private val codeKo: String? = null
) : CodedEnum<String> {
    TITLEIST("Titleist", "타이틀리스트"),
    FOURTEEN("Fourteen", "포틴"),
    CALLAWAY("Callaway", "캘러웨이"),
    HONMA("Honma", "혼마"),
    MIZUNO("Mizuno", "미즈노"),
    TALYERMADE("TalyerMade", "테일러메이드"),
    PING("Ping", "핑"),
    COBRA("Cobra", "코브라"),
    CLEVELAND("Cleveland", "클리브랜드"),
    BRIDGESTONE("Bridgestone", "브릿지스톤"),
    SRIXON("Srixon", "스릭슨"),
    NIKE("Nike", "나이키"),
    PXG("PXG", "피엑스지"),
    MIURA("Miura", "미우라"),
    YONEX("Yonex", "요넥스"),
    XXIO("XXIO", "젝시오"),
    EPON("Epon", "에폰"),
    ZODIA("Zodia", "조디아"),
    GOLF_PRIDE("Golf Pride", "골프프라이드"),
    LAMKIN("Lamkin", "램킨"),
    CAVIAR("Caviar", "캐비어"),
    DYNAMIC_GOLD("Dynamic Gold", "다이나믹골드"),
    IOMIC("Iomic", "이오믹"),
    PROJECT_X("PROJECT X", "프로젝트"),
    HZRDUS("HZRDUS", "해저더스"),
    AMT("AMT", "에이엠티"),
    DENALI("DENALI", "드날리"),
    KBS("KBS", "케이비에스"),
    TOUR_AD("Tour AD", "투어에이디"),
    SPEEDER("Speeder", "스피더"),
    VENTUS("Ventus", "벤투스"),
    MC_SERIES("MC Series"),
    TEMPEST("TEMPEST", "템페스트"),
    SUPERSTAR("SuperStar", "슈퍼스타"),
    DIAMANA("Diamana", "디아마나"),
    TENSEI("TENSEI", "텐세이"),
    VANQUISH("VANQUISH", "뱅퀴시"),
    KAI_LI("Kai'li", "카일리"),
    MMT("MMT", "엠엠티"),
    C6("C6"),
    GRAND_BASSARA("Grand Bassara", "바사라"),
    N_S_PRO("N.S.PRO", "엔에스프로"),
    ALDILA("ALDILA", "알딜라"),
    UST_MAMIYA("UST Mamiya", "마미야"),
    AEROTECH("Aerotech", "에어로테크"),
    WINN("winn", "윈"),
    SUPER_STROKE("Super Stroke", "슈퍼스트로크"),
    JUMBO_MAX("Jumbo Max", "점보맥스"),
    TRAVIL("TRAVIL", "트라빌"),
    ELEVATE("Elevate", "엘리베이트"),
    VECTOR("Vector", "벡터");

    companion object {
        fun find(code: String): BrandName? = entries.find { it.code.lowercase() == code.lowercase() }

        fun findKo(codeKo: String): BrandName? = entries.find { it.codeKo == codeKo }

        fun findContains(code: String): BrandName? = entries.find { it.code.lowercase().contains(code.lowercase()) }

        fun findContainsKo(codeKo: String): BrandName? = entries.find { it.codeKo?.contains(codeKo) == true }
    }

    override fun getCode(): String = code

    fun getCodeKo(): String? = codeKo

    @Converter(autoApply = true)
    class BrandNameConverter : AbstractCodedEnumConverter<BrandName, String>(BrandName::class.java)
}
