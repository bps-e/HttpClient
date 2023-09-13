# HttpClient

Jetpack Compose環境向けのjava.net.HttpURLConnectionを使用したHttpClientのAndroid Libraryです</br>
</br>
responceDataをJson形式で返すWebApiに対して任意のdata classに変更して返す機能もサポートしています</br>
</br>

## 対応Http Method

・GET

## 依存関係

### org.jetbrains.kotlinx:kotlinx-serialization-json

・build.gradle.kts(Project)
```kotlin
plugins {
    kotlin("plugin.serialization") version "1.9.0"
}
```

・build.gradle.kts(Module)
```kotlin
plugins {
    id("kotlinx-serialization")
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
}
```

## 使い方

GitHub(https://github.com/bps-e/HttpClient)releaseから.aarをダウンロードして以下にコピーします</br>
(project)/(module)/libs/com.bps-e.HttpClient_x.x.x.aar

・build.gradle.kts(Module)
```kotlin
dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", "*.aar"))))
}
```

## 実装例

### Get

```kotlin
val scope = rememberCoroutineScope()
var job: Job? by remember { mutableStateOf(null) }
val url = "xxx"

if (job == null || job!!.isCompleted || job!!.isCancelled) {
    job = scope.launch {
        HttpClient.Get(url) { code, data ->
            if (code == HttpClient.OK) {
                val text = String(data, Charsets.UTF_8)
                // ...
            }
        }
    }
}
```

### Api

```kotlin
import kotlinx.serialization.*

@Serializable
data class Datas (
    @SerialName("name") val name: String
)
```

```kotlin
val scope = rememberCoroutineScope()
var job: Job? by remember { mutableStateOf(null) }
val url = "xxx"
var text by remember { mutableStateOf("") }

if (job == null || job!!.isCompleted || job!!.isCancelled) {
    job = scope.launch {
        HttpClient.Api<Datas>(url) { code, datas ->
            datas?.let {
                text = it.name
            }
        }
    }
}
```
