package com.instructure.canvasapi2.models

import kotlinx.android.parcel.Parcelize

@Parcelize
data class ModuleItemWrapper(
        var prev: ModuleItem? = null,
        var current: ModuleItem? = null,
        var next: ModuleItem? = null
) : CanvasModel<ModuleItemWrapper>()