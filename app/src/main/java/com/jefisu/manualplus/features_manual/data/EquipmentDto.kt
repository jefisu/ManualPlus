package com.jefisu.manualplus.features_manual.data

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class EquipmentDto : RealmObject {
    @PrimaryKey
    var _id = ObjectId()
    var name = ""
    var description = ""
    var steps = realmListOf<String>()
    var serialNumber = 0
    var releaseYear = 0
    var rate: Float? = null
    var picture = ""
    var category = ""
}