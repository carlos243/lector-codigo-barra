package cl.carlostapia.lector_codigo_barra;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.dm7.barcodescanner.zbar.BarcodeFormat;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class BarcodeActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler{

    /** Atributos publicos **/
    public static final String OUTPUT_VALUE = "OUTPUT_VALUE";

    /** Atributos privados **/
    private ZBarScannerView mScannerView;
    private List<BarcodeFormat> formats = new ArrayList<BarcodeFormat>(Arrays.asList(
                    BarcodeFormat.CODE128, //Utilizado en las simcards
                    BarcodeFormat.CODE93 // Utilizado en los imeis
            ));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZBarScannerView(this);
        mScannerView.setFormats(formats);
    }

    @Override
    public void handleResult(Result result) {
        mScannerView.stopCamera();
        mScannerView.resumeCameraPreview(this);
        mScannerView.stopCameraPreview();
        sendActivityResult(result.getContents());
    }

    private void sendActivityResult(String value){
        Intent resultIntent = new Intent();
        /** Se le envia el resultado a la activity padre **/
        resultIntent.putExtra(OUTPUT_VALUE, value);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onResume(){
        super.onResume();
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}
