package com.medikeen.patient;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@SuppressLint("NewApi")
public class AttachPrescription extends AppCompatActivity implements OnClickListener {

    public static final String GridViewDemo_ImagePath = Environment
            .getExternalStorageDirectory().getAbsolutePath() + "/GridView/";
    private static final String TAG = "CameraFragment";
    private static final int PICK_FROM_CAMERA = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int PICK_FROM_FILE = 3;
    private static final boolean DEBUG = false; // Set to true to enable logging
    Uri selectedImageUri;
    String selectedPath;
    Button buttonUploadPrescription, buttonNext;
    BitmapFactory.Options bfOptions;
    FileInputStream fs = null;
    Bitmap bm;
    ImageView imageViewClose;
    HorizontalScrollView objHorizontalScrollView;
    LinearLayout layout;
    String filePath, fileName, encodedImage;
    TextView textViewNote, textViewUploadedPrescrition;
    private SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");
    private List<String> listOfImagesPath;
    private final int MY_PERMISSIONS_REQUEST_TAKE_PICTURE = 0;
    private final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth,
                                                     int reqHeight) { // BEST
        // QUALITY
        // MATCH

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            // if(Math.round((float)width / (float)reqWidth) > inSampleSize) //
            // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     * @author paulburke
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     * @author paulburke
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     * @author paulburke
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                if (DEBUG)
                    DatabaseUtils.dumpCursor(cursor);

                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_attach_prescription);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {

        layout = (LinearLayout) findViewById(R.id.root);
        objHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView1);

        textViewNote = (TextView) findViewById(R.id.textViewNote);
        textViewUploadedPrescrition = (TextView) findViewById(R.id.textViewUploadedPrescription);
        buttonUploadPrescription = (Button) findViewById(R.id.buttonUPloadPrescription);
        buttonNext = (Button) findViewById(R.id.buttonNext);

        File imageDirectory = new File(GridViewDemo_ImagePath);

        if (!imageDirectory.exists()) {
            imageDirectory.mkdirs();
        }

        listOfImagesPath = null;
        listOfImagesPath = RetriveCapturedImagePath();

        refreshImageGridView();

        if (listOfImagesPath != null && listOfImagesPath.size() != 0) {
        } else {
            textViewUploadedPrescrition
                    .setText("No Prescription Uploded Yet!!!");
        }

        buttonUploadPrescription.setOnClickListener(this);
        buttonNext.setOnClickListener(this);

        bfOptions = new BitmapFactory.Options();
        bfOptions.inDither = false; // Disable Dithering mode
        bfOptions.inPurgeable = true; // Tell to gc that whether it needs free
        // memory, the Bitmap can be cleared
        bfOptions.inInputShareable = true; // Which kind of reference will be
        // used to recover the Bitmap data
        // after being clear, when it will
        // be used in the future
        bfOptions.inTempStorage = new byte[32 * 1024];

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.buttonUPloadPrescription) {
            selectImage();
        } else if (id == R.id.buttonSendPrescription) {
            deleteExistingDirectory();
        } else if (id == R.id.buttonNext) {

            Intent addressPrescriptionIntent = new Intent(
                    AttachPrescription.this, AddressPrescription.class);
            startActivity(addressPrescriptionIntent);

            // AddressPrescription newFragment = new AddressPrescription();
            // Bundle bundle = new Bundle();
            // newFragment.setArguments(bundle);
            // FragmentTransaction transaction = getFragmentManager()
            // .beginTransaction();
            // // Replace whatever is in the fragment_container view with this
            // // fragment,
            // // and add the transaction to the back stack so the user can
            // // navigate back
            // transaction.replace(R.id.content_frame, newFragment);
            // transaction.addToBackStack(null);
            // // Commit the transaction
            // transaction.commit();
            NewUploadPrescription.tabIndex = 2;
        }
    }

    private void deleteExistingDirectory() {

        File dir = new File(GridViewDemo_ImagePath);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.app.Fragment#onDestroy()
     */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        deleteExistingDirectory();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_FROM_CAMERA && resultCode == Activity.RESULT_OK) {

            // Bitmap photo = (Bitmap) data.getExtras().get("data");
            //
            // String imgcurTime = dateFormat.format(new Date());
            // File imageDirectory = new File(GridViewDemo_ImagePath);
            // imageDirectory.mkdirs();
            // String _path = GridViewDemo_ImagePath + imgcurTime+".jpg";
            //
            // Log.d("_path", "_path"+_path);
            // try {
            // FileOutputStream out = new FileOutputStream(_path);
            // photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
            // out.close();
            // } catch (FileNotFoundException e) {
            // e.getMessage();
            // } catch (IOException e) {
            // e.printStackTrace();
            // }

            File fileTemp = new File(GridViewDemo_ImagePath + "image.jpg");
            Bitmap photo = decodeSampledBitmapFromFile(
                    fileTemp.getAbsolutePath(), 480, 800);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] image = stream.toByteArray();

            String filename = GridViewDemo_ImagePath
                    + dateFormat.format(new Date()) + ".jpg";
            FileOutputStream os = null;
            boolean success = true;

            File file = new File(filename);

            try {
                os = new FileOutputStream(file);
                os.write(image);
            } catch (Exception e) {
                Log.e(TAG, "Error writing to file " + filename, e);
                success = false;
            } finally {
                try {
                    if (os != null)
                        os.close();
                } catch (Exception e) {
                    Log.e(TAG, "Error closing file " + filename, e);
                    success = false;
                }
            }

            if (success) {
                Log.i(TAG, "Jpeg saved at " + filename);

                fileTemp.delete();

            }

            listOfImagesPath = RetriveCapturedImagePath();

            Log.d("lisofImages",
                    "size in camera view" + listOfImagesPath.size());

            refreshImageGridView();
        } else if (resultCode == RESULT_OK && requestCode == PICK_FROM_FILE) {

            layout.removeAllViews();
            if (data.getData() != null) {

                selectedImageUri = data.getData();
                // todo: here we have to copy the file to destination folder.

                createDirIfNotExists();
                String imgcurTime = dateFormat.format(new Date());
                String _path = GridViewDemo_ImagePath + imgcurTime + ".jpg";

                Log.d("_path", "_path" + _path);
                File destinationFile = new File(_path);

                String sourceFilePath = getPath(selectedImageUri, this);

                copyFile(sourceFilePath, destinationFile);

                selectedPath = destinationFile.getAbsolutePath();
                // selectedPath = getPath(selectedImageUri,getActivity());

                // listOfImagesPath=RetriveCapturedImagePath();
                listOfImagesPath.add(selectedPath);

                // todo: compress the copied file

                Log.d("lisofImages", "size" + listOfImagesPath.size());
                Log.d("lisofImages", "size" + listOfImagesPath);

                refreshImageGridView();
            }
        }
    }

    protected void refreshImageGridView() {
        layout.removeAllViews();

        if (listOfImagesPath != null && listOfImagesPath.size() != 0) {

            buttonNext.setVisibility(View.VISIBLE);
            textViewUploadedPrescrition.setVisibility(View.GONE);

            for (int i = 0; i < listOfImagesPath.size(); i++) {

                File file = new File(listOfImagesPath.get(i).toString());

                long size = file.length();

                if (file.length() == 0) {
                    file.delete();
                    listOfImagesPath.remove(i);
                    i--;
                    continue;
                }

                if (file.exists() && file.length() > 500000) {
                    double compressionRatio = (500000.0 / (double) file
                            .length()) * 100.0;

                    Bitmap bMap = BitmapFactory.decodeFile(listOfImagesPath
                            .get(i).toString());

                    FileOutputStream out = null;
                    try {
                        out = new FileOutputStream(listOfImagesPath.get(i)
                                .toString());
                    } catch (FileNotFoundException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    bMap.compress(Bitmap.CompressFormat.JPEG,
                            (int) compressionRatio, out);
                    try {
                        out.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    file = new File(listOfImagesPath.get(i).toString());
                }
                // Let's create the missing ImageView

                // Now the layout parameters, these are a little tricky at first
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        400, 500);

                FrameLayout framelayout = new FrameLayout(this);
                framelayout.setLayoutParams(new LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));

                ImageView image = new ImageView(this);
                image.setScaleType(ImageView.ScaleType.FIT_XY);
                image.setPadding(10, 0, 10, 0);

                try {
                    fs = new FileInputStream(file);

                    if (fs != null) {
                        bm = BitmapFactory.decodeFileDescriptor(fs.getFD(),
                                null, bfOptions);
                        image.setImageBitmap(bm);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fs != null) {
                        try {
                            fs.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                ImageView imageView = new ImageView(this);
                imageView.setScaleType(ImageView.ScaleType.MATRIX);
                imageView.setImageResource(R.drawable.ic_close_white_18dp);
                LinearLayout.LayoutParams layoutParamsImage = new LinearLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                layoutParamsImage.gravity = Gravity.LEFT;
                imageView.setLayoutParams(layoutParamsImage);
                imageView.setId(i);

                framelayout.addView(image);
                framelayout.addView(imageView);

                layout.addView(framelayout, 0, params);

                imageView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        int idImageView = v.getId();
                        Log.d("id is:", "id" + idImageView);

                        deleteRespectiveImage(idImageView, v);

                    }
                });

            }
            buttonUploadPrescription.setText("Add another prescription");

        } else {
            buttonNext.setVisibility(View.GONE);
            textViewUploadedPrescrition.setText("Uploded Prescriptions");
        }
    }

    private void copyFile(String sourceFilePath, File destination) {
        try {
            File source = new File(sourceFilePath);
            FileChannel src = new FileInputStream(source).getChannel();
            FileChannel dst = new FileOutputStream(destination).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    protected void deleteRespectiveImage(int position, View view) {

        String imagePath = listOfImagesPath.get(position);
        File imageFile = new File(imagePath);
        imageFile.delete();

        listOfImagesPath.remove(position);

        Log.d("remove view", "removing view at position: " + position);

        refreshImageGridView();

        // ViewGroup parentLayout = (ViewGroup)
        // objHorizontalScrollView.getChildAt(0);
        //
        // View frameLayoutView = (View)view.getParent();
        // parentLayout.removeView(frameLayoutView);

        // Log.d("parent", "new image count "+parentLayout.getChildCount());

        // if (parentLayout.getChildCount() > 0) {
        //
        // Log.d("parent", "parent"+parentLayout.getChildCount());
        //
        // if(parentLayout.getChildCount() ==1 ){
        //
        // parentLayout.removeView(parentLayout.getChildAt(position-1));
        // Log.d("parent", "parent after"+parentLayout.getChildCount());
        //
        // }else{
        // Log.d("parent", ""+(position-1));
        // parentLayout.removeView(parentLayout.getChildAt((parentLayout.getChildCount()-1)-(position-1)));
        // Log.d("parent", "parent after"+parentLayout.getChildCount());
        //
        // }
        // }
    }

    private List<String> RetriveCapturedImagePath() {
        List<String> tFileList = new ArrayList<String>();
        File f = new File(GridViewDemo_ImagePath);
        if (f.exists()) {
            File[] files = f.listFiles();
            if (files != null) {
                Arrays.sort(files);

                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    if (file.isDirectory())
                        continue;
                    if (file.length() > 0) {
                        tFileList.add(file.getPath());
                    } else {
                        file.delete();
                    }
                }
            }
        }
        return tFileList;
    }

    public String getPath(Uri uri, Activity context) {

		/*
         * Log.d("uri", ""+uri);
		 *
		 * String result; Cursor cursor = getActivity().getContentResolver().
		 * query(uri, null, null, null, null); if (cursor == null) { // Source
		 * is Dropbox or other similar local file path result = uri.getPath(); }
		 * else { cursor.moveToFirst(); int idx =
		 * cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA); result =
		 * cursor.getString(idx); cursor.close(); } return result; if( uri ==
		 * null ) { // TODO perform some logging or show user feedback return
		 * null; } // try to retrieve the image from the media store first //
		 * this will only work for images selected from gallery String[]
		 * projection = { MediaStore.Images.Media.DATA }; Cursor cursor =
		 * getActivity().getContentResolver().query(uri, projection, null, null,
		 * null); if( cursor != null ){ int column_index = cursor
		 * .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		 * cursor.moveToFirst(); return cursor.getString(column_index); } //
		 * this is our fallback here return uri.getPath();
		 *
		 *
		 * String[] largeFileProjection = {MediaStore.Images.ImageColumns.DATA};
		 * String largeFileSort = MediaStore.Images.ImageColumns._ID + "!= 0";
		 * Cursor myCursor = getActivity().getContentResolver().query( uri,
		 * largeFileProjection, null, null, null); String largeImagePath = "";
		 * try { myCursor.moveToFirst(); largeImagePath = myCursor
		 * .getString(myCursor
		 * .getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA)); }
		 * finally { myCursor.close(); } return largeImagePath;
		 */

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // LocalStorageProvider
            /*
             * if (isLocalStorageDocument(uri)) { // The path is the id return
			 * DocumentsContract.getDocumentId(uri); }
			 */
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        } else {

            String result;
            Cursor cursor = this.getContentResolver().query(uri, null, null,
                    null, null);
            if (cursor == null) { // Source is Dropbox or other similar local
                // file path
                result = uri.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor
                        .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }
            return result;
        }

        return null;
    }

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    if (ContextCompat.checkSelfPermission(AttachPrescription.this,
                            Manifest.permission.READ_CONTACTS)
                            != PackageManager.PERMISSION_GRANTED) {
                        Log.d(TAG, "Camera permission not yet granted");

                        // Should we show an explanation?
                        if (ActivityCompat.shouldShowRequestPermissionRationale(AttachPrescription.this,
                                Manifest.permission.READ_CONTACTS)) {
                            Log.d(TAG, "Showing toast that camera permission is needed");

                            Toast.makeText(AttachPrescription.this, "Camera permission is needed to upload an image of prescription.", Toast.LENGTH_LONG).show();

                        } else {

                            Log.d(TAG, "Requesting camera permission");
                            // No explanation needed, we can request the permission.

                            ActivityCompat.requestPermissions(AttachPrescription.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    MY_PERMISSIONS_REQUEST_TAKE_PICTURE);

                            Log.d(TAG, "Requested camera permission. Waiting for user response");

                            // MY_PERMISSIONS_REQUEST_TAKE_PICTURE is an
                            // app-defined int constant. The callback method gets the
                            // result of the request.
                        }
                    } else {
                        Log.d(TAG, "Camera permission already granted");
                        StartCameraCaptureIntent();
                    }

                    // Intent cameraIntent = new Intent(AttachPrescription.this,
                    // CameraActivity.class);

                    // File imageDirectory = createDirIfNotExists();
                    //
                    // File tempFile;
                    // try {
                    // tempFile = File.createTempFile("my_app" +
                    // dateFormat.format(new Date()) , ".jpg", imageDirectory);
                    // if(tempFile == null)
                    // {
                    // Log.d("Warning: ","the file was not created");
                    // }
                    //
                    // fileName = tempFile.getAbsolutePath();
                    // Log.d("_path", fileName);
                    // Uri uri = Uri.fromFile(tempFile);
                    //
                    // cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    // } catch (IOException e) {
                    // // TODO Auto-generated catch block
                    // e.printStackTrace();
                    // }

                } else if (items[item].equals("Choose from Library")) {

                    if (ContextCompat.checkSelfPermission(AttachPrescription.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        RequestStoragePermission(MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    } else {
                        Log.d(TAG, "Storage permission already granted");
                        StartIntentForSelectingImageFromFile();
                    }

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();

    }

    private void RequestStoragePermission(int requestCode) {
        Log.d(TAG, "Storage permission not yet granted");

        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(AttachPrescription.this,
                Manifest.permission.READ_CONTACTS)) {
            Log.d(TAG, "Showing toast that Storage permission is needed");

            Toast.makeText(AttachPrescription.this, "Storage permission is needed to upload an image of prescription.", Toast.LENGTH_LONG).show();

        } else {

            Log.d(TAG, "Requesting camera permission");
            // No explanation needed, we can request the permission.

            ActivityCompat.requestPermissions(AttachPrescription.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    requestCode);

            Log.d(TAG, "Requested storage permission. Waiting for user response");

            // MY_PERMISSIONS_REQUEST_TAKE_PICTURE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    private void StartIntentForSelectingImageFromFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Select File"),
                PICK_FROM_FILE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_TAKE_PICTURE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG,"Camera permission granted by the user and triggered onRequestPermissionsResult");

                    if (ContextCompat.checkSelfPermission(AttachPrescription.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        RequestStoragePermission(MY_PERMISSIONS_REQUEST_TAKE_PICTURE);
                    } else {

                        Log.d(TAG, "Storage permission already granted");
                        StartCameraCaptureIntent();
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    StartIntentForSelectingImageFromFile();

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void StartCameraCaptureIntent() {
        Intent intent = new Intent(
                "android.media.action.IMAGE_CAPTURE");
        File file = new File(GridViewDemo_ImagePath + "image.jpg");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    private File createDirIfNotExists() {
        File imageDirectory = new File(GridViewDemo_ImagePath);
        if (!imageDirectory.exists()) {
            imageDirectory.mkdirs();
        }
        return imageDirectory;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
