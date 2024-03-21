package com.example.tareadesarrollomovil

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.widget.Toast
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.core.Preview
import androidx.camera.core.CameraSelector
import android.util.Log
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.VideoRecordEvent
import androidx.core.content.PermissionChecker
import com.example.tareadesarrollomovil.databinding.ActivityCameraBinding
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.Locale

typealias LumaListener = (luma: Double) -> Unit

class CameraActivity : AppCompatActivity() {

    //Se usa el viewBinding para facilitar la interacción de un layout con su clase asociada
    //Al usar viewBinding, se genera una clase para "enlazar" el layout con la clase base
    private lateinit var viewBinding: ActivityCameraBinding

    private var imageCapture: ImageCapture? = null

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Infla el archivo de layout XML y lo asigna a viewBinding para un fácil acceso a los componentes de la UI.
        viewBinding = ActivityCameraBinding.inflate(layoutInflater)

        // Establece la vista de contenido de la actividad en la vista raíz del layout inflado.
        // Esto hace que los elementos de la UI definidos en el XML estén disponibles para tu actividad.
        setContentView(viewBinding.root)

        // Verifica si la app tiene todos los permisos requeridos (como CAMERA y RECORD_AUDIO).
        // Si los permisos ya están concedidos, inicializa la cámara.
        // Si no, solicita los permisos necesarios al usuario.
        if (allPermissionsGranted()) {
            startCamera() // Inicializa la cámara porque los permisos están concedidos.
        } else {
            // Solicita los permisos necesarios al usuario.
            // Esto es asíncrono y mostrará al usuario diálogos de solicitud de permisos.
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Configura un listener de clic en el botón de 'captura de imagen'.
        // Cuando se hace clic en el botón, se llama a la función 'takePhoto'.
        // Esta función es responsable de capturar una foto con la cámara.
        viewBinding.imageCaptureButton.setOnClickListener { takePhoto() }

        // Configura un listener de clic en el botón de 'captura de video'.
        // Cuando se hace clic en el botón, se supone que llama a la función 'captureVideo'.
        // Esta función manejaría el inicio y la detención de la grabación de video.
        // Nota: La llamada a la función 'captureVideo()' está comentada,
        // lo que significa que esta funcionalidad podría estar planificada pero aún no implementada.
        // viewBinding.videoCaptureButton.setOnClickListener { captureVideo() }

        // Inicializa un executor de un solo hilo.
        // Este executor se usa para ejecutar operaciones de la cámara fuera del hilo principal,
        // asegurando una operación de UI suave sin bloqueos.
        cameraExecutor = Executors.newSingleThreadExecutor()
    }


    private fun takePhoto() {
        //Obtiene una referencia estable del caso de uso de la imagen modificable
        val imageCapture = imageCapture ?: return

        //Utilizando algunos miembros de companion object, crea la entrada de la imagen en el almacenamiento
        // Se crea de manera que el nombre de cada imagen sea único
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US) //Formato de fecha
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name) //Nombre de la imagen
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg") //Formato de la imagen
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    "Pictures/CameraX-Image"
                ) //Lugar en donde se guardarán las fotos
            }
        }

        // Crea un objeto OutputFileOptions
        // En él es donde podemos especificar cosas acerca de cómo queremos que sea nuestro resultado
        // Queremos que los resultados se guarden en MediaStore para que otras apps puedan mostrarlos, así que agrega nuestra entrada de MediaStore
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        // Llama a takePicture() en el objeto imageCapture
        // Pasa outputOptions, el ejecutor y una devolución de llamada para cuando se guarde la imagen
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                //En caso de que falle la captura de imágenes o de que no se guarde, agrega un caso de error para registrar que falló
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }

                //Si la captura no falla, la foto se tomó correctamente
                // Guarda la foto en el archivo que creamos antes, presenta un aviso para informar al usuario que se realizó correctamente e imprime una instrucción de registro.
                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    savedPicture = output.savedUri
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                    finish()
                }
            }
        )
    }

    //private fun captureVideo() {}

    private fun startCamera() {

        //Crea una instancia de ProcessCameraProvider
        // Se usa para vincular el ciclo de vida de las cámaras al propietario del ciclo de vida
        // Esto elimina la tarea de abrir y cerrar la cámara, ya que CameraX se adapta al ciclo de vida.
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            //Se utiliza para vincular el ciclo de vida de las cámaras al dueño del ciclo de vida
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Vista previa de la cámara
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder()
                .build()

            // Selecciona la cámara trasera de forma predeterminada
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            //Se crea un bloque try. Dentro de ese bloque, no debe haber nada vinculado a cameraProvider
            // luego se vincula el cameraSelector y el objeto de vista previa a cameraProvider.
            try {
                // Desvincula todos los casos de uso antes de la revinculación
                cameraProvider.unbindAll()

                // Vincula los casos de uso a la cámara
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            }
            //Este código puede fallar de varias maneras, por ejemplo, si la app ya no está en primer plano.
            // este código ayuda a registrar si hay una falla.
            catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    //Revisa si todos los permisos fueron concedidos, en caso de que sean concedidos todos, entonces procede a la ejecución de las funciones
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    //Se utiliza un companion object para definir todos los permisos necesarios,
    //así como distintos valores requeridos por la clase, solamente accesibles por la clase
    //CameraActivity
    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

    //Esta función se llama como resultado de proveer de los permisos necesarios a la aplicación

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //Si el código de permisos es el correcto
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            //Si el usuario da los permisos entonces se inicia la cámara
            if (allPermissionsGranted()) {
                startCamera()
            }

            //De lo contrario solamente muestra un mensaje
            else {
                Toast.makeText(
                    this,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
}