package lpzmrc.test.djungle.io

import lpzmrc.test.djungle.io.core.di.apiModule
import lpzmrc.test.djungle.io.core.di.coreModule
import lpzmrc.test.djungle.io.data.di.dataModule

val appModules = listOf(dataModule, coreModule, apiModule)