package com.kumarsunil17.opengldemo

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import com.kumarsunil17.opengldemo.controller.SceneLoader
import com.kumarsunil17.opengldemo.views.ModelSurfaceView
import org.andresoviedo.util.android.ContentUtils
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_LOAD_TEXTURE = 1000
    private val FULLSCREEN_DELAY = 10000
    private val backgroundColor = floatArrayOf(0.7f, 0.2f, 0.2f, 1.0f)

    private var gLView: ModelSurfaceView? = null

    private var scene: SceneLoader? = null
    private val immersiveMode = true

    private var handler: Handler? = null
    private var paramUri: Uri? = null
    private var paramType = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gLView = findViewById(R.id.gl_surface)

        paramUri = Uri.parse("assets://$packageName/models/spider.gltf")
        paramType =  -1
        handler = Handler(mainLooper)
        scene = SceneLoader(this)
        scene!!.init()
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        try {
          } catch (e: Exception) {
            Toast.makeText(
                this, """
     Error loading OpenGL view:
     ${e.message}
     """.trimIndent(), Toast.LENGTH_LONG
            ).show()
        }
    }

    fun getParamUri(): Uri? {
        return paramUri
    }

    fun getParamType(): Int {
        return paramType
    }

    fun getBackgroundColor(): FloatArray {
        return backgroundColor
    }

    fun getScene(): SceneLoader? {
        return scene
    }

    fun getGLView(): ModelSurfaceView? {
        return gLView
    }

    fun applyFilter1(view: android.view.View) {
        try {
            ContentUtils.setThreadActivity(this)
            Log.e("TAG", "applyFilter1: "+scene!!.objects.size )

            scene!!.loadTexture(scene!!.objects[0], Uri.parse("assets://$packageName/models/Wolf_Body.jpg"))
        } catch (ex: IOException) {
            Log.e("MainActivity", "Error loading texture: " + ex.message, ex)
            Toast.makeText(
                this, "Error loading texture . " + ex
                    .message, Toast.LENGTH_LONG
            ).show()
        } finally {
            ContentUtils.setThreadActivity(null)
        }
    }
    fun applyFilter2(view: android.view.View) {
        try {
            ContentUtils.setThreadActivity(this)
            scene!!.loadTexture(null, Uri.parse("assets://$packageName/models/Wolf_Eyes_1.jpg"))
        } catch (ex: IOException) {
            Log.e("MainActivity", "Error loading texture: " + ex.message, ex)
            Toast.makeText(
                this, "Error loading texture . " + ex
                    .message, Toast.LENGTH_LONG
            ).show()
        } finally {
            ContentUtils.setThreadActivity(null)
        }
    }
    fun applyFilter3(view: android.view.View) {
        try {
            ContentUtils.setThreadActivity(this)
            scene!!.loadTexture(null, Uri.parse("assets://$packageName/models/Wolf_Eyes_2.jpg"))
        } catch (ex: IOException) {
            Log.e("MainActivity", "Error loading texture: " + ex.message, ex)
            Toast.makeText(
                this, "Error loading texture . " + ex
                    .message, Toast.LENGTH_LONG
            ).show()
        } finally {
            ContentUtils.setThreadActivity(null)
        }
    }
    fun applyFilter4(view: android.view.View) {
        try {
            ContentUtils.setThreadActivity(this)
            scene!!.loadTexture(null, Uri.parse("assets://$packageName/models/Wolf_Fur.jpg"))
        } catch (ex: IOException) {
            Log.e("MainActivity", "Error loading texture: " + ex.message, ex)
            Toast.makeText(
                this, "Error loading texture . " + ex
                    .message, Toast.LENGTH_LONG
            ).show()
        } finally {
            ContentUtils.setThreadActivity(null)
        }
    }
}