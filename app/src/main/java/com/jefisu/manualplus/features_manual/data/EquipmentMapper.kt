package com.jefisu.manualplus.features_manual.data

import com.jefisu.manualplus.features_manual.domain.Equipment

fun EquipmentDto.toEquipment(): Equipment {
    return Equipment(
        name = name,
        description = description,
        steps = steps.toList(),
        serialNumber = serialNumber,
        releaseYear = releaseYear,
        rate = rate,
        imageRemotePath = picture,
        category = category,
        id = _id.toString()
    )
}