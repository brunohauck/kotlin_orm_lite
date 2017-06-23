package com.mentorandroid.loginorm.model

import com.j256.ormlite.field.DatabaseField

/**
 * Created by brunodelhferreira on 22/06/17.
 */

class Items {

    @DatabaseField(generatedId = true)
    var itemId: Long? = null

    @DatabaseField(columnName = "name")
    var title: String? = null
}
