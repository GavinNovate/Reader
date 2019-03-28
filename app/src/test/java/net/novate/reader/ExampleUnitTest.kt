package net.novate.reader

import okhttp3.OkHttpClient
import okhttp3.Request
import okio.BufferedSink
import okio.buffer
import okio.sink
import org.jsoup.Jsoup
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test() {
//        val retrofit = Retrofit.Builder().build()
        //href="https://www.qiushuzw.com/t/51748/14002109.html"
        val client = OkHttpClient.Builder().build()

//        val response = client.newCall(Request.Builder().url("https://www.80txt.com/txtml_51748.html").build()).execute()
//        println(response)
//        println(response.body()?.string())

        // https://m.qiushuzw.com/51748/31482144.html
        // https://www.qiushuzw.com/t/51748/14002109.html
        // https://m.qiushuzw.com/51748/page-86.html
        // https://m.qiushuzw.com/51748/14002109.html
        // https://www.bhzw.cc/book/127/127514/29381308.html


        // 目录 https://m.qiushuzw.com/51748/
        val response2 =
            client.newCall(Request.Builder().url("https://m.qiushuzw.com/51748/").build()).execute()
//        println(response2)
//        println(response2.body()?.string())

//        val file = File("/home/gavin/Test/1.txt")

//        response2.body()?.byteStream()?.source()?.buffer()?.readAll(file.sink())

        val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(response2.body()?.byteStream())
    }

    @Test
    fun jsoup() {
        val document = Jsoup.connect("https://m.qiushuzw.com/51748/").get()
        // 状态数据
        val map = HashMap<String, String>()
        document.select("meta").forEach {
            if (it.hasAttr("property") && it.hasAttr("content")) {
                map[it.attr("property").trim()] = it.attr("content").trim()
            }
        }
        println(map)


        // 章节分页数据
        val pages = ArrayList<String>()
        document
            .select("div.cover")
            .select("div.listpage")
            .select("select")
            .select("option")
            .forEach {
                pages.add(it.attr("value"))
            }
    }

    @Test
    fun chapterTest() {
        chapter("https://m.qiushuzw.com/51748/page-1.html")
    }

    private fun chapter(url: String) {
        val document = Jsoup.connect(url).get()
        val chapters = ArrayList<Pair<String, String>>()
        document
            .select("div.cover")
            .select("ul.chapter")[1]
            .select("a").forEach {
                chapters.add(Pair(it.attr("href").trim(), it.text().trim()))
            }
        File("/home/gavin/Test/novel.txt").sink().buffer().use { sink ->
            chapters.forEach {
                content("https://m.qiushuzw.com/" + it.first, sink)
            }
        }
    }

    @Test
    fun contentTest() {
        content("https://m.qiushuzw.com/51748/14002109.html", null)
    }

    private fun content(url: String, sink: BufferedSink?) {
        val document = Jsoup.connect(url).get()
        val content = StringBuilder()

        content.append(document.select("div.header").select("h1").text().trim()).append("\n")
        document.select("div.nr_nr")[0].children().select("div")
            .forEach {
                var html = it.html()
                html = html
                    .replace(Regex("<.*>"), "")
                    .replace(Regex("[ \n]"), "")
                    .replace(Regex("&nbsp;&nbsp;&nbsp;&nbsp;"), "\n　　")
                    .trim()
                content.append("    ").append(html).append("\n\n")
            }
        print(content.toString())
        sink?.writeUtf8(content.toString())
    }
}
