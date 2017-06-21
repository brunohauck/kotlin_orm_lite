package com.mentorandroid.loginorm.model

import java.io.Serializable

import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable

@DatabaseTable(tableName = "person")
class Person : Serializable {
    @DatabaseField(generatedId = true)
    var contactId: Long? = null
    @DatabaseField(columnName = "name")
    var name: String? = null
    @DatabaseField(columnName = "password")
    var password: String? = null
    @DatabaseField(columnName = "email")
    var email: String? = null

   
}