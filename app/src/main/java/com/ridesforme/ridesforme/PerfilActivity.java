package com.ridesforme.ridesforme;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.ridesforme.ridesforme.basicas.Usuario;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PerfilActivity extends AppCompatActivity {
    final static int REQUEST_TAKE_PHOTO = 1;
    final static  String APP_KEY = "64k16giccxzy2po";
    final static  String APP_SECRET = "d91zsk6qhqoauy1";
    UserSessionManager mUserSessionManager;
    DropboxAPI<AndroidAuthSession> mDBApi;
    Usuario mUsuario;
    TextView mTextViewUsername;
    ImageView mImageViewEditarImagem;
    ImageView mImageViewEditarNome;
    ImageView mImageView;
    String mCurrentPhotoPath;


    public PerfilActivity() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        mTextViewUsername = (TextView) findViewById(R.id.perfil_username);
        mImageView = (ImageView) findViewById(R.id.perfil_imageView);
        mImageViewEditarImagem = (ImageView) findViewById(R.id.perfil_edit_imagemView);
        mImageViewEditarNome = (ImageView) findViewById(R.id.perfil_botao_editar_nome);

        mUsuario = (Usuario) getIntent().getSerializableExtra("usuario");

        mUserSessionManager = new UserSessionManager(this);

        mTextViewUsername.setText(mUsuario.getNome());
        Picasso.with(PerfilActivity.this)
                .load("http://i.imgur.com/DvpvklR.png")
                .noFade().into(mImageView);

        mTextViewUsername.setText(mUsuario.getNome());

        mImageViewEditarNome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(PerfilActivity.this, EditarUsernameActivity.class);
                it.putExtra("usuario", mUsuario);
                startActivity(it);
            }
        });

       /* mImageViewEditarImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        mDBApi = new DropboxAPI<AndroidAuthSession>(session);
        mDBApi.getSession().startOAuth2Authentication(PerfilActivity.this);*/

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mDBApi.getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                mDBApi.getSession().finishAuthentication();
                String accessToken = mDBApi.getSession().getOAuth2AccessToken();
                mUserSessionManager.createDropBoxAccessToken(accessToken);
            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
            }
        }
    }

    private void uploadImageFile(File file) {
        FileInputStream inputStream = null;

        try {
            inputStream = new FileInputStream(file);
            DropboxAPI.Entry response = mDBApi.putFile(mCurrentPhotoPath, inputStream,
                    file.length(), null, null);
        } catch (DropboxException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();

        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                uploadImageFile(photoFile);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            handleCameraPhoto();
        }
    }

    private void setPic() {
		/* There isn't enough memory to open up more than a couple camera photos */
		/* So pre-scale the target bitmap into which the file is decoded */

		/* Get the size of the ImageView */
        int targetW = mImageView.getWidth();
        int targetH = mImageView.getHeight();

		/* Get the size of the image */
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

		/* Figure out which way needs to be reduced less */
        int scaleFactor = 1;
        if ((targetW > 0) || (targetH > 0)) {
            scaleFactor = Math.min(photoW/targetW, photoH/targetH);
        }

		/* Set bitmap options to scale the image decode target */
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */
        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);

		/* Associate the Bitmap to the ImageView */
        mImageView.setImageBitmap(bitmap);
    }

    private void handleCameraPhoto() {
        if (mCurrentPhotoPath != null) {
            setPic();
            mCurrentPhotoPath = null;
        }
    }
}
