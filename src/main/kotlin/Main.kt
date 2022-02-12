// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import java.io.File
import java.io.FileReader
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.name

@Composable
@Preview
fun App() {
    var counter by remember { mutableStateOf(0) }

    MaterialTheme {
        Button(onClick = {
            counter++
        }) {
            Text("$counter")
        }
    }
}

data class Config(val rootPath: String, val currentComponent: String)
val GSON: Gson = Gson()
val CONFIG_PATH: String = "D:\\GithubProjects\\WODExplorer\\src\\main\\resources\\config.json";
var rootPath: String? = null;
var currentComponent: String? = null;
// PROJECTS
val PROJECTS: MutableList<String> = mutableListOf<String>();
var currProject: String? = null;

// COMPONENTS
val COMPONENTS: MutableList<String> = mutableListOf<String>();
var currComponent: String? = null;

fun main() {
    // load the config
    println("""Welcome to WODExplorer! (Hope this really runs in CEDES one day...\n
        |
    """.trimMargin())
    val configReader: JsonReader = GSON.newJsonReader(FileReader(File(CONFIG_PATH)))
    configReader.beginObject()
    assert(configReader.nextName() == "rootPath")
    rootPath = configReader.nextString();
    assert(configReader.nextName() == "currentComponent")
    currentComponent = configReader.nextString();

    assert(rootPath != null)
    // load projects
    val rootPathObj: Path = Paths.get(rootPath)
    for (dir: Path in rootPathObj.iterator()) {
        if (dir.startsWith("COAST") && dir.endsWith("WI")) {
            PROJECTS.add(dir.toString())
        }
    }

    if (PROJECTS.size > 0) {
        currProject = PROJECTS[0]
    }







    /**val configJsonObject = kotlinx.<Config>("""
        {
          "rootPath": "D:\\GithubProjects\\WODExplorer\\src\\main\\resources\\coast",
          "currentComponent": ""
        }
    """.trimIndent()) */


    // draw the UI
    application {

        Window(onCloseRequest = ::exitApplication) {
            App()
        }

    // end  application {
    }
}
