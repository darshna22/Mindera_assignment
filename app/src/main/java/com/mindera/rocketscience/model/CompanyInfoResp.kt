package com.mindera.rocketscience.model

import java.io.Serializable

data class CompanyInfoResp(
    val name: String,
    val founder: String,
    val founded: Int,
    val employees: Int,
    val launch_sites: Int,
    val valuation: Long,
) : Serializable