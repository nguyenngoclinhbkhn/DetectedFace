package com.pear.facedetector;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pear.facedetector.custom.StickerImageView;
import com.pear.facedetector.custom.StickerView;
import com.pear.facedetector.object.ObjectFace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class Solution1Activity extends AppCompatActivity implements FragmentChooseSticker.OnFragmentListener {
    private FaceOverlayView mFaceOverlayView;
    private ProgressBar mProgress;
    private FrameLayout frameLayout;
    private float xLeftEye;
    private float yLeftEye;
    private float xRightEye;
    private float yRightEye;
    private float xNose;
    private float yNose;
    private int count;
    private StickerImageView stickerImageView;
    private StickerImageView stickerMouth;
    private StickerImageView stickerMustache;
    private StickerImageView stickerHat;
    private Bitmap bitmapTest;
    private LinearLayout linearFrame;
    private float rotationSum;
    private int widthScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution1);
        mFaceOverlayView = findViewById(R.id.face_overlay);
        mProgress = findViewById(R.id.progressBar);
        frameLayout = findViewById(R.id.frameLayout);
        linearFrame = findViewById(R.id.linearFrame);

        stickerImageView = new StickerImageView(this);
        stickerMouth = new StickerImageView(this);
        stickerHat = new StickerImageView(this);
        stickerMustache = new StickerImageView(this);

        stickerMouth.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.m3_01));
        stickerImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.f4_1));
        stickerMustache.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.r1_01));
        stickerHat.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.v3_01));

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        widthScreen = size.x;
        int height = size.y;


        frameLayout.addView(stickerImageView);
        frameLayout.addView(stickerMouth);
        frameLayout.addView(stickerMustache);
        frameLayout.addView(stickerHat);



    }

    public void Eyes(View view) {
        int widthSticker = 150 * (widthScreen / 540);

        stickerImageView.getLayoutParams().width = widthSticker;
        stickerImageView.getLayoutParams().height = widthSticker;

        stickerMustache.getLayoutParams().width = widthSticker;
        stickerMustache.getLayoutParams().height = widthSticker;

        stickerMouth.getLayoutParams().width = widthSticker;
        stickerMouth.getLayoutParams().height = widthSticker;

        stickerHat.getLayoutParams().width = widthSticker;
        stickerHat.getLayoutParams().height = widthSticker;

        stickerImageView.requestLayout();
        stickerHat.requestLayout();
        stickerMustache.requestLayout();
        stickerMouth.requestLayout();
        Log.e("TAG", "width sticker " + stickerImageView.getWidth() + " height sticker " + stickerImageView.getHeight());
//        for (ObjectFace o : FaceOverlayView.list) {
//            Log.e("TAG", "name " + o.getName());
//        }
//        xLeftEye = FaceOverlayView.list.get(0).getX();
//        yLeftEye = FaceOverlayView.list.get(0).getY();
//        xRightEye = FaceOverlayView.list.get(1).getX();
//        yRightEye = FaceOverlayView.list.get(1).getY();
//        xNose = FaceOverlayView.list.get(2).getX();
//        yNose = FaceOverlayView.list.get(2).getY();
//
//
//
////        stickerImageView.setScaleX((float) FaceOverlayView.scaleFinal);
////        stickerImageView.setScaleY((float) FaceOverlayView.scaleFinal);
//
//
//        if (yLeftEye < yRightEye) {
//            double m = Math.sqrt(Math.abs((xLeftEye - xRightEye) * (xLeftEye - xRightEye)) + Math.abs((yLeftEye - yRightEye) * (yLeftEye - yRightEye)));
//            double b = Math.sqrt(Math.abs((xLeftEye - (m + xLeftEye)) * (xLeftEye - (m + xLeftEye))));
//            double c = Math.sqrt(Math.abs((xRightEye - (m + xLeftEye)) * (xRightEye - (m + xLeftEye))) + Math.abs((yRightEye - yLeftEye) * (yRightEye - yLeftEye)));
//
//            double p = (m + b + c) / 2;
//            double S = Math.sqrt(p * (p - m) * (p - b) * (p - c));
//            double sin = 2 * S / (m * m);
//            stickerImageView.setRotation((float) (Math.asin(sin) * 180 / Math.PI));
//            Log.e("TAG", " sin " + Math.asin(0.5) * 180 / Math.PI);
//        } else if (yLeftEye >= yRightEye) {
//            double m = Math.sqrt(Math.abs((xLeftEye - xRightEye) * (xLeftEye - xRightEye)) + Math.abs((yLeftEye - yRightEye) * (yLeftEye - yRightEye)));
//            double b = Math.sqrt(Math.abs((xLeftEye - (m + xLeftEye)) * (xLeftEye - (m + xLeftEye))));
//            double c = Math.sqrt(Math.abs((xRightEye - (m + xLeftEye)) * (xRightEye - (m + xLeftEye))) + Math.abs((yRightEye - yLeftEye) * (yRightEye - yLeftEye)));
//
//            double p = (m + b + c) / 2;
//            double S = Math.sqrt(p * (p - m) * (p - b) * (p - c));
//            double sin = 2 * S / (m * m);
//            stickerImageView.setRotation((float) (-Math.asin(sin) * 180 / Math.PI));
//        }
//        stickerImageView.setControlItemsHidden(true);
//
//        double distance = Math.sqrt(Math.abs((xLeftEye - xRightEye) * (xLeftEye - xRightEye)) +
//                Math.abs((yLeftEye - yRightEye) * (yLeftEye - yRightEye)));
//
//
//        double xScale = distance / ((stickerImageView.getWidth()) / 2);
//
//        int newWidth = (int) (stickerImageView.getWidth() * xScale);
//        stickerImageView.getLayoutParams().width = (int) newWidth;
//        stickerImageView.getLayoutParams().height = (int) newWidth;
//
//        stickerImageView.requestLayout();
//
//        int widthTrueSticker = stickerImageView.getWidth() - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);
//        int heightTrueSticker = stickerImageView.getHeight() - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);
//        float xMiddle = (xLeftEye + xRightEye) / 2;
//        float yMiddle = (yLeftEye + yRightEye) / 2;
//
//        double coordinateX = xMiddle - newWidth / 2;
//        double coordinateY = yMiddle - newWidth / 2;
//
//        stickerImageView.setX((float) coordinateX);
//        stickerImageView.setY((float) coordinateY);
//
//        Log.e("TAG", "mat trai " + "x = " + xLeftEye + ": y = " + yLeftEye);
//        Log.e("TAG", "mat phai " + "x = " + xRightEye + ": y = " + yRightEye);
//        Log.e("TAG", "mui " + "x = " + xNose + ": y = " + yNose);

    }

    public void Select(View view) {
        FaceOverlayView.list.clear();
        count++;
        if (count == 1) {
            mFaceOverlayView.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.f22201914237));
        } else if (count == 2) {
            mFaceOverlayView.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.f622201914262));
        } else if (count == 3) {
            mFaceOverlayView.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.f6222019142823));
        } else if (count == 4) {
            mFaceOverlayView.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.f6222019171440));
        } else if (count >= 5) {
            count = 0;
        }
        stickerHat.getLayoutParams().width = 150;
        stickerHat.getLayoutParams().height = 150;
        stickerMustache.getLayoutParams().width = 150;
        stickerMustache.getLayoutParams().height = 150;
        stickerImageView.getLayoutParams().width = 150;
        stickerImageView.getLayoutParams().height = 150;
        stickerMouth.getLayoutParams().width = 150;
        stickerMouth.getLayoutParams().height = 150;

        stickerMouth.requestLayout();
        stickerMustache.requestLayout();
        stickerImageView.requestLayout();
        stickerHat.requestLayout();
        Log.e("TAG", "Width " + mFaceOverlayView.getWidth() + " height : " + mFaceOverlayView.getHeight());
//        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
//        photoPickerIntent.setType("image/*");
//        startActivityForResult(photoPickerIntent, 1);
    }


    private void setupMouthSticker() {

    }

    private static int convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            try {
                final Uri imageUri = data.getData();
//                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
//                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);


                String path = getRealPathFromURI(Solution1Activity.this, imageUri);
                Bitmap selectedImage = BitmapFactory.decodeFile(path);
//                Bitmap bitmap = processBitmap(selectedImage, path);
//
//                final int width = mFaceOverlayView.getWidth();
//                final int height = mFaceOverlayView.getHeight();
//                int widthBitmap = bitmap.getWidth();
//                int heightBitmap = bitmap.getHeight();
//                if (widthBitmap >= heightBitmap) {
//                    final int heightFind = heightBitmap * width / widthBitmap;
//                    //correct
//                    bitmapTest = Bitmap.createScaledBitmap(bitmap,
//                            width,
//                            heightFind,
//                            true);
//                    mFaceOverlayView.getLayoutParams().height = heightFind;
//                    frameLayout.getLayoutParams().width = width;
//                    frameLayout.getLayoutParams().height = heightFind;
//                    mFaceOverlayView.requestLayout();
//                    frameLayout.requestLayout();
//                } else {
//                    final int widthFind = widthBitmap * height / heightBitmap;
//                    // correct
//                    bitmapTest = Bitmap.createScaledBitmap(bitmap,
//                            widthFind,
//                            height,
//                            true);
//                    mFaceOverlayView.getLayoutParams().height = height;
//                    mFaceOverlayView.getLayoutParams().width = widthFind;
//                    frameLayout.getLayoutParams().width = widthFind;
//                    frameLayout.getLayoutParams().height = height;
//                    mFaceOverlayView.requestLayout();
//                    frameLayout.requestLayout();
//                }
                mFaceOverlayView.setBitmap(selectedImage);
                stickerHat.getLayoutParams().width = 150;
                stickerHat.getLayoutParams().height = 150;
                stickerMustache.getLayoutParams().width = 150;
                stickerMustache.getLayoutParams().height = 150;
                stickerImageView.getLayoutParams().width = 150;
                stickerImageView.getLayoutParams().height = 150;
                stickerMouth.getLayoutParams().width = 150;
                stickerMouth.getLayoutParams().height = 150;

                stickerMouth.requestLayout();
                stickerMustache.requestLayout();
                stickerImageView.requestLayout();
                stickerHat.requestLayout();
            } catch (Exception e) {

            }

        } else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemFragmentClicked(int item) {
        stickerImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), item));
    }


    private Bitmap processBitmap(Bitmap bitmap, String path) {
        Bitmap rotatedBitmap = null;
        try {
            ExifInterface ei = new ExifInterface(path);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                    rotatedBitmap = bitmap;
                    break;
                default:
                    rotatedBitmap = bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public void choose(View view) {
        stickerHat.setControlItemsHidden(false);
        stickerMustache.setControlItemsHidden(false);
        stickerMouth.setControlItemsHidden(false);
        stickerImageView.setControlItemsHidden(false);
//        sticker
//        new FragmentChooseSticker().show(getSupportFragmentManager(), null);

        int count = 0;
        double degreeSum = 0;

        xLeftEye = FaceOverlayView.list.get(0).getX();
        yLeftEye = FaceOverlayView.list.get(0).getY();
        xRightEye = FaceOverlayView.list.get(1).getX();
        yRightEye = FaceOverlayView.list.get(1).getY();
        xNose = FaceOverlayView.list.get(2).getX();
        yNose = FaceOverlayView.list.get(2).getY();

        float xMiddleEye = (xLeftEye + xRightEye) / 2;
        float yMiddleEye = (yLeftEye + yRightEye) / 2;


        float muti = (float) stickerImageView.getWidth() / 100;

//        stickerImageView.setScaleX((float) FaceOverlayView.scaleFinal);
//        stickerImageView.setScaleY((float) FaceOverlayView.scaleFinal);


        if (yLeftEye < yRightEye) {
            double m = Math.sqrt(Math.abs((xLeftEye - xRightEye) * (xLeftEye - xRightEye)) + Math.abs((yLeftEye - yRightEye) * (yLeftEye - yRightEye)));
            double b = Math.sqrt(Math.abs((xLeftEye - (m + xLeftEye)) * (xLeftEye - (m + xLeftEye))));
            double c = Math.sqrt(Math.abs((xRightEye - (m + xLeftEye)) * (xRightEye - (m + xLeftEye))) + Math.abs((yRightEye - yLeftEye) * (yRightEye - yLeftEye)));

            double p = (m + b + c) / 2;
            double S = Math.sqrt(p * (p - m) * (p - b) * (p - c));
            double sin = 2 * S / (m * m);
            degreeSum = Math.asin(sin);
            stickerImageView.setRotation((float) (Math.asin(sin) * 180 / Math.PI));
            Log.e("TAG", " sin " + Math.asin(0.5) * 180 / Math.PI);
        } else if (yLeftEye >= yRightEye) {
            double m = Math.sqrt(Math.abs((xLeftEye - xRightEye) * (xLeftEye - xRightEye)) + Math.abs((yLeftEye - yRightEye) * (yLeftEye - yRightEye)));
            double b = Math.sqrt(Math.abs((xLeftEye - (m + xLeftEye)) * (xLeftEye - (m + xLeftEye))));
            double c = Math.sqrt(Math.abs((xRightEye - (m + xLeftEye)) * (xRightEye - (m + xLeftEye))) + Math.abs((yRightEye - yLeftEye) * (yRightEye - yLeftEye)));

            double p = (m + b + c) / 2;
            double S = Math.sqrt(p * (p - m) * (p - b) * (p - c));
            double sin = 2 * S / (m * m);
            degreeSum = -Math.asin(sin);
            stickerImageView.setRotation((float) (-Math.asin(sin) * 180 / Math.PI));
        }
        stickerImageView.setControlItemsHidden(true);

        double distance = Math.sqrt(Math.abs((xLeftEye - xRightEye) * (xLeftEye - xRightEye)) +
                Math.abs((yLeftEye - yRightEye) * (yLeftEye - yRightEye)));


        double xScale = distance / ((stickerImageView.getWidth()) / 2);

        int newWidthEye = (int) (stickerImageView.getWidth() * xScale);

        stickerImageView.getLayoutParams().width = newWidthEye;
        stickerImageView.getLayoutParams().height = newWidthEye;

        stickerImageView.requestLayout();


        // mouth
        float xLeftMouth = FaceOverlayView.list.get(4).getX();
        float yLeftMouth = FaceOverlayView.list.get(4).getY();

        float xRightMouth = FaceOverlayView.list.get(5).getX();
        float yRightMouth = FaceOverlayView.list.get(5).getY();

        float xUnderMouth = FaceOverlayView.list.get(3).getX();
        float yUnderMouth = FaceOverlayView.list.get(3).getY();

        float xMiddleMouth = (xLeftMouth + xRightMouth) / 2;
        float yMiddleMouth = (yLeftMouth + yRightMouth) / 2;

        double degree = 0;
        stickerMouth.setRotation((float) (degreeSum * 180 / Math.PI));
        stickerMouth.setControlItemsHidden(true);

        double distanceMouth = Math.sqrt(Math.abs((xLeftMouth - xRightMouth) * (xLeftMouth - xRightMouth)) +
                Math.abs((yLeftMouth - yRightMouth) * (yLeftMouth - yRightMouth)));
        int widthTrueStickerMouth = stickerMouth.getWidth() - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);
        int heightTrueStickerMouth = stickerMouth.getHeight() - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);

        double scaleMouth = (float) (distanceMouth / widthTrueStickerMouth);
        int newWidthMouth = (int) (stickerMouth.getWidth() * scaleMouth);


        stickerMouth.getLayoutParams().width = newWidthMouth;
        stickerMouth.getLayoutParams().height = newWidthMouth;
        stickerMouth.requestLayout();


        //hat
        float xTopLeftEar = FaceOverlayView.list.get(8).getX();
        float yTopLeftEar = FaceOverlayView.list.get(8).getY();

        float xTopRightEar = FaceOverlayView.list.get(9).getX();
        float yTopRightEar = FaceOverlayView.list.get(9).getY();

        float xLeftEar = FaceOverlayView.list.get(6).getX();
        float yLeftEar = FaceOverlayView.list.get(6).getY();

        float xRightEar = FaceOverlayView.list.get(7).getX();
        float yRightEar = FaceOverlayView.list.get(7).getY();

        float xLeftHat = xLeftEar;
        float yLeftHat = yTopLeftEar;

        float xRightHat = xRightEar;
        float yRightHat = yTopRightEar;


        stickerHat.setRotation((float) (degreeSum * 180 / Math.PI));
        double distanceHat = Math.sqrt(Math.abs(xLeftHat - xRightHat) * Math.abs(xLeftHat - xRightHat) +
                Math.abs(yLeftHat - yRightHat) * Math.abs(yLeftHat - yRightHat));
        float distanceSticker = (stickerHat.getWidth()) / 4;
        double mutiHat = (distanceHat / (distanceSticker * 2));

        if (mutiHat <= 1) {
            mutiHat = 1;
        } else {
            mutiHat =Math.ceil(mutiHat);
        }
        int newWidthHat = (int) (stickerHat.getWidth() * mutiHat);
        stickerHat.getLayoutParams().width = newWidthHat;
        stickerHat.getLayoutParams().height = newWidthHat;
        stickerHat.requestLayout();

        float xMiddleHat = (xLeftHat + xRightHat) / 2;
        float yMiddleHat = (yLeftHat + yRightHat) / 2;

        int widthTrueStickerHat = newWidthHat - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);
        int heightTrueStickerHat = newWidthHat - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);

        double valueYHat = (newWidthHat) * ((Math.sin(degreeSum) * Math.cos(degreeSum)) /
                (Math.sin(degreeSum) + Math.cos(degreeSum) + 1));
        double coordinateXHat;
        double coordinateYHat;
            coordinateXHat = xMiddleHat - (newWidthHat ) / 2 + valueYHat;
            coordinateYHat = yMiddleHat - newWidthHat * 3 / 4  ;


        double coordinateXHatVer2 = xLeftHat + (newWidthHat - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this)) / 2;
        stickerHat.setX((float) coordinateXHat);
        stickerHat.setY((float) coordinateYHat);


        //mutache

        float xLeftMouth1 = FaceOverlayView.list.get(4).getX();
        float yLeftMouth1 = FaceOverlayView.list.get(4).getY();

        float xRightMouth1 = FaceOverlayView.list.get(5).getX();
        float yRightMouth1 = FaceOverlayView.list.get(5).getY();

        float xLeftCheek = FaceOverlayView.list.get(10).getX();
        float yLeftCheek = FaceOverlayView.list.get(10).getY();

        float xRightCheek = FaceOverlayView.list.get(11).getX();
        float yRightCheek = FaceOverlayView.list.get(11).getY();

        float xNose = FaceOverlayView.list.get(2).getX();
        float yNose = FaceOverlayView.list.get(2).getY();

        float xUnderMouth1 = FaceOverlayView.list.get(3).getX();
        float yUnderMouth1 = FaceOverlayView.list.get(3).getY();

        double distanceCheek = Math.sqrt(Math.abs((xLeftCheek - xRightCheek) * (xLeftCheek - xRightCheek)) +
                Math.abs((yLeftCheek - yRightCheek) * (yLeftCheek - yRightCheek)));

//        float distanceCheekPx = convertDpToPixel((float) distanceCheek, this);

        double distanceMouth1 = Math.sqrt(Math.abs((xLeftMouth1 - xRightMouth1) * (xLeftMouth1 - xRightMouth1)) +
                Math.abs((yLeftMouth1 - yRightMouth1) * (yLeftMouth1 - yRightMouth1)));


        float rotation1 = 0;
        double degree1 = 0;
        stickerMustache.setRotation((float) (degreeSum * 180 / Math.PI));


        float distanceStickerPx = stickerMustache.getWidth();
        stickerMustache.setControlItemsHidden(true);
        float scale1 = (float) (distanceCheek / distanceStickerPx);
        if (scale1 <= 1) {
            scale1 = 1;
        }
        float xCuttingPoint = 0;
        float yCuttingPoint = 0;
        if (xNose - xUnderMouth1 != 0) {
            float aMouthToUnder = (yNose - yUnderMouth1) / (xNose - xUnderMouth1);
            float bMouthToUnder = yNose - ((yNose - yUnderMouth1) * xNose) / (xNose - xUnderMouth1);

            float aLeftToRightMouth = (yLeftMouth1 - yRightMouth1) / (xLeftMouth1 - xRightMouth1);
            float bLeftToRightMouth = yLeftMouth1 - ((yLeftMouth1 - yRightMouth1) * xLeftMouth1) / (xLeftMouth1 - xRightMouth1);

            xCuttingPoint = (bLeftToRightMouth - bMouthToUnder) / (aMouthToUnder - aLeftToRightMouth);
            yCuttingPoint = ((bLeftToRightMouth - bMouthToUnder) * aMouthToUnder) / (aMouthToUnder - aLeftToRightMouth) + bMouthToUnder;

        } else {

            xCuttingPoint = xNose;
            yCuttingPoint = (xCuttingPoint * (yLeftMouth1 - yRightMouth1)) / (xLeftMouth1 - xRightMouth1) + yLeftMouth1
                    - ((yLeftMouth1 - yRightMouth1) / (xLeftMouth1 - xRightMouth1)) * ((yLeftMouth1 - yRightMouth1) / (xLeftMouth1 - xRightMouth1));
//
        }

        float xMiddlePoint = (xCuttingPoint + xNose) / 2;
        float yMiddlePoint = (yCuttingPoint + yNose) / 2;
        int newWidthMustache = (int) (stickerMustache.getWidth() * scale1);

        stickerMustache.getLayoutParams().width = newWidthMustache;
        stickerMustache.getLayoutParams().height = newWidthMustache;

        stickerMustache.requestLayout();


        //setup coordinate eye
        int widthTrueSticker = newWidthEye - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);
        int heightTrueSticker = newWidthEye - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);
        int distanceX = widthTrueSticker / 4;
        int distanceY = heightTrueSticker / 2;

        double valueEye = newWidthEye * ((Math.sin(degreeSum) * Math.cos(degreeSum)) /
                (Math.sin(degree) + Math.cos(degree) + 1));

        double coordinateX = xLeftEye - distanceX - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this) / 2;
        double coordinateY = yLeftEye - distanceY - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this) / 2;
        double coordinateXVer1 = xMiddleEye - newWidthEye / 2;
        double coordinateYVer1 = yMiddleEye - newWidthEye / 2;
        stickerImageView.setX((float) coordinateXVer1);
        stickerImageView.setY((float) coordinateYVer1);

        //setup coordinate mouth
        double valueY = newWidthMouth * ((Math.sin(degree) * Math.cos(degree)) /
                (Math.sin(degree) + Math.cos(degree) + 1));

        double coordinateXMouth = xLeftMouth - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);
        double coordinateYMouth = yLeftMouth - newWidthMouth / 2;

        double coordinateXMouthVer2 = xMiddleMouth - newWidthMouth / 2 - valueY;
        double coordinateYMouthVer2 = yMiddleMouth - newWidthMouth / 2 - valueY;
        stickerMouth.setX((float) coordinateXMouthVer2);
        stickerMouth.setY((float) coordinateYMouthVer2);




        //setup coordinate mustache
        int widthTrue = newWidthMustache - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);
        int heightTrue = newWidthMustache - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);
        double valueYMutache = heightTrue * ((Math.sin(degree1) * Math.cos(degree1)) /
                (Math.sin(degree) + Math.cos(degree) + 1));
        double coordinateX1 = xMiddlePoint - newWidthMustache / 2;
        double coordinateY1 = yMiddlePoint - heightTrue / 2;
        stickerMustache.setX((float) coordinateX1);
        stickerMustache.setY((float) coordinateY1);
        Log.e("TAG", "coordinate mutache " + coordinateX1);
        Log.e("TAG", "coordinate mutache " + coordinateY1);


    }

    public void mouth(View view) {


        for (int i = 0; i < FaceOverlayView.list.size(); i++) {
            Log.e("TAG", " vi tri " + i + " : " + FaceOverlayView.list.get(i).getName());
        }


        float xLeftMouth = FaceOverlayView.list.get(4).getX();
        float yLeftMouth = FaceOverlayView.list.get(4).getY();

        float xRightMouth = FaceOverlayView.list.get(5).getX();
        float yRightMouth = FaceOverlayView.list.get(5).getY();

        float xUnderMouth = FaceOverlayView.list.get(3).getX();
        float yUnderMouth = FaceOverlayView.list.get(3).getY();

        float xMiddleMouth = (xLeftMouth + xRightMouth) / 2;
        float yMiddleMouth = (yLeftMouth + yRightMouth) / 2;
        double degree = 0;
        if (yLeftMouth < yRightMouth) {
            double m = Math.sqrt(Math.abs((xLeftMouth - xRightMouth) * (xLeftMouth - xRightMouth)) +
                    Math.abs((yLeftMouth - yRightMouth) * (yLeftMouth - yRightMouth)));

            double b = Math.sqrt(Math.abs((xLeftMouth - (m + xLeftMouth)) * (xLeftMouth - (m + xLeftMouth))));

            double c = Math.sqrt(Math.abs((xRightMouth - (m + xLeftMouth)) * (xRightMouth - (m + xLeftMouth))) +
                    Math.abs((yRightMouth - yLeftMouth) * (yRightMouth - yLeftMouth)));

            double p = (m + b + c) / 2;
            double S = Math.sqrt(p * (p - m) * (p - b) * (p - c));
            double sin = 2 * S / (m * m);
            rotationSum = (float) (Math.asin(sin) * 180 / Math.PI);
            degree = Math.asin(sin);
            stickerMouth.setRotation((float) (Math.asin(sin) * 180 / Math.PI));
            Log.e("TAG", " sin " + Math.asin(0.5) * 180 / Math.PI);
        } else if (yLeftEye >= yRightEye) {
            double m = Math.sqrt(Math.abs((xLeftMouth - xRightMouth) * (xLeftMouth - xRightMouth)) +
                    Math.abs((yLeftMouth - yRightMouth) * (yLeftMouth - yRightMouth)));

            double b = Math.sqrt(Math.abs((xLeftMouth - (m + xLeftMouth)) * (xLeftMouth - (m + xLeftMouth))));

            double c = Math.sqrt(Math.abs((xRightMouth - (m + xLeftMouth)) * (xRightMouth - (m + xLeftMouth))) +
                    Math.abs((yRightMouth - yLeftMouth) * (yRightMouth - yLeftMouth)));

            double p = (m + b + c) / 2;
            double S = Math.sqrt(p * (p - m) * (p - b) * (p - c));
            double sin = 2 * S / (m * m);
            rotationSum = -((float) (Math.asin(sin) * 180 / Math.PI));
            degree = Math.asin(sin);
            stickerMouth.setRotation((float) (-Math.asin(sin) * 180 / Math.PI));
        }
        stickerMouth.setControlItemsHidden(true);

        if (Math.abs(degree) > Math.PI / 2) {
            degree = Math.PI - Math.abs(degree);
        }

        double distanceMouth = Math.sqrt(Math.abs((xLeftMouth - xRightMouth) * (xLeftMouth - xRightMouth)) +
                Math.abs((yLeftMouth - yRightMouth) * (yLeftMouth - yRightMouth)));
        int widthTrueSticker = stickerMouth.getWidth() - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);
        int heightTrueSticker = stickerMouth.getHeight() - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);

//        float scale = distancePx / distanceStickerPx;
        stickerMouth.getLayoutParams().width = (int) (stickerMouth.getWidth() * distanceMouth / widthTrueSticker);
        stickerMouth.getLayoutParams().height = (int) (stickerMouth.getHeight() * distanceMouth / heightTrueSticker);
        stickerMouth.requestLayout();

        double valueY = stickerMouth.getHeight() * ((Math.sin(degree) * Math.cos(degree)) /
                (Math.sin(degree) + Math.cos(degree) + 1));

        double coordinateX = xMiddleMouth - stickerMouth.getWidth() / 2;
        double coordinateY = yMiddleMouth - stickerMouth.getWidth() / 2;

        stickerMouth.setX((float) coordinateX);
        stickerMouth.setY((float) coordinateY);
    }

    public void Hat(View view) {

        float xTopLeftEar = FaceOverlayView.list.get(8).getX();
        float yTopLeftEar = FaceOverlayView.list.get(8).getY();

        float xTopRightEar = FaceOverlayView.list.get(9).getX();
        float yTopRightEar = FaceOverlayView.list.get(9).getY();

        float xLeftEar = FaceOverlayView.list.get(6).getX();
        float yLeftEar = FaceOverlayView.list.get(6).getY();

        float xRightEar = FaceOverlayView.list.get(7).getX();
        float yRightEar = FaceOverlayView.list.get(7).getY();

        float xLeftHat = xLeftEar;
        float yLeftHat = yTopLeftEar;

        float xRightHat = xRightEar;
        float yRightHat = yTopRightEar;

        float xLeftEye = FaceOverlayView.list.get(0).getX();
        float yLeftEye = FaceOverlayView.list.get(0).getY();
        float xRightEye = FaceOverlayView.list.get(1).getX();
        float yRightEye = FaceOverlayView.list.get(1).getY();
        double degree = 0;

        if (yLeftEye < yRightEye) {
            double m = Math.sqrt(Math.abs((xLeftEye - xRightEye) * (xLeftEye - xRightEye)) + Math.abs((yLeftEye - yRightEye) * (yLeftEye - yRightEye)));
            double b = Math.sqrt(Math.abs((xLeftEye - (m + xLeftEye)) * (xLeftEye - (m + xLeftEye))));
            double c = Math.sqrt(Math.abs((xRightEye - (m + xLeftEye)) * (xRightEye - (m + xLeftEye))) + Math.abs((yRightEye - yLeftEye) * (yRightEye - yLeftEye)));

            double p = (m + b + c) / 2;
            double S = Math.sqrt(p * (p - m) * (p - b) * (p - c));
            double sin = 2 * S / (m * m);
            degree = Math.asin(sin);
            stickerHat.setRotation((float) (Math.asin(sin) * 180 / Math.PI));
            Log.e("TAG", " sin " + Math.asin(0.5) * 180 / Math.PI);
        } else if (yLeftEye >= yRightEye) {
            double m = Math.sqrt(Math.abs((xLeftEye - xRightEye) * (xLeftEye - xRightEye)) + Math.abs((yLeftEye - yRightEye) * (yLeftEye - yRightEye)));
            double b = Math.sqrt(Math.abs((xLeftEye - (m + xLeftEye)) * (xLeftEye - (m + xLeftEye))));
            double c = Math.sqrt(Math.abs((xRightEye - (m + xLeftEye)) * (xRightEye - (m + xLeftEye))) + Math.abs((yRightEye - yLeftEye) * (yRightEye - yLeftEye)));

            double p = (m + b + c) / 2;
            double S = Math.sqrt(p * (p - m) * (p - b) * (p - c));
            double sin = 2 * S / (m * m);
            degree = -Math.asin(sin);
            stickerHat.setRotation((float) (-Math.asin(sin) * 180 / Math.PI));
        }


//        if (Math.abs(degree) > Math.PI / 2) {
//            degree = Math.PI - Math.abs(degree);
//        }

        int count = 0;
//        while(true){

        double distance = Math.sqrt(Math.abs(xLeftHat - xRightHat) * Math.abs(xLeftHat - xRightHat) +
                Math.abs(yLeftHat - yRightHat) * Math.abs(yLeftHat - yRightHat));
        float distanceSticker = (stickerHat.getWidth() - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this)) / 4;
        float muti = (float) (distance / (distanceSticker * 2));

        stickerHat.getLayoutParams().width = (int) ((stickerHat.getWidth()) * (muti));
        stickerHat.getLayoutParams().height = (int) ((stickerHat.getHeight()) * (muti));
        stickerHat.requestLayout();


//        int count = 0;
//        while (count == 2){
        int widthTrueSticker = stickerHat.getWidth() - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);
        int heightTrueSticker = stickerHat.getHeight() - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);

        double valueY = stickerHat.getHeight() * ((Math.sin(degree) * Math.cos(degree)) /
                (Math.sin(degree) + Math.cos(degree) + 1));

        double coordinateX = xLeftHat - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this) - distanceSticker + valueY;
        double coordinateY = yLeftHat - heightTrueSticker * 3 / 4 - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);


        stickerHat.setX((float) coordinateX);
        stickerHat.setY((float) coordinateY);
//            if (count == 2) break;
//
//        }


    }


    //set up mustache (ria mep)
    public void Mustache(View view) {
        float xLeftMouth = FaceOverlayView.list.get(4).getX();
        float yLeftMouth = FaceOverlayView.list.get(4).getY();

        float xRightMouth = FaceOverlayView.list.get(5).getX();
        float yRightMouth = FaceOverlayView.list.get(5).getY();

        float xLeftCheek = FaceOverlayView.list.get(10).getX();
        float yLeftCheek = FaceOverlayView.list.get(10).getY();

        float xRightCheek = FaceOverlayView.list.get(11).getX();
        float yRightCheek = FaceOverlayView.list.get(11).getY();

        float xNose = FaceOverlayView.list.get(2).getX();
        float yNose = FaceOverlayView.list.get(2).getY();

        float xUnderMouth = FaceOverlayView.list.get(3).getX();
        float yUnderMouth = FaceOverlayView.list.get(3).getY();

        double distanceCheek = Math.sqrt(Math.abs((xLeftCheek - xRightCheek) * (xLeftCheek - xRightCheek)) +
                Math.abs((yLeftCheek - yRightCheek) * (yLeftCheek - yRightCheek)));

//        float distanceCheekPx = convertDpToPixel((float) distanceCheek, this);

        double distanceMouth = Math.sqrt(Math.abs((xLeftMouth - xRightMouth) * (xLeftMouth - xRightMouth)) +
                Math.abs((yLeftMouth - yRightMouth) * (yLeftMouth - yRightMouth)));
//        float distancePx = convertDpToPixel((float) distanceMouth, this);

        float distanceStickerPx = stickerMustache.getWidth();

        float rotation = 0;
        double degree = 0;
        if (yLeftMouth < yRightMouth) {
            double m = Math.sqrt(Math.abs((xLeftMouth - xRightMouth) * (xLeftMouth - xRightMouth)) +
                    Math.abs((yLeftMouth - yRightMouth) * (yLeftMouth - yRightMouth)));

            double b = Math.sqrt(Math.abs((xLeftMouth - (m + xLeftMouth)) * (xLeftMouth - (m + xLeftMouth))));

            double c = Math.sqrt(Math.abs((xRightMouth - (m + xLeftMouth)) * (xRightMouth - (m + xLeftMouth))) +
                    Math.abs((yRightMouth - yLeftMouth) * (yRightMouth - yLeftMouth)));

            double p = (m + b + c) / 2;
            double S = Math.sqrt(p * (p - m) * (p - b) * (p - c));
            double sin = 2 * S / (m * m);
            degree = Math.asin(sin);
            rotation = (float) (Math.asin(sin) * 180 / Math.PI);
            Log.e("TAG", " sin " + Math.asin(0.5) * 180 / Math.PI);
        } else if (yLeftEye >= yRightEye) {
            double m = Math.sqrt(Math.abs((xLeftMouth - xRightMouth) * (xLeftMouth - xRightMouth)) +
                    Math.abs((yLeftMouth - yRightMouth) * (yLeftMouth - yRightMouth)));


            double b = Math.sqrt(Math.abs((xLeftMouth - (m + xLeftMouth)) * (xLeftMouth - (m + xLeftMouth))));

            double c = Math.sqrt(Math.abs((xRightMouth - (m + xLeftMouth)) * (xRightMouth - (m + xLeftMouth))) +
                    Math.abs((yRightMouth - yLeftMouth) * (yRightMouth - yLeftMouth)));

            double p = (m + b + c) / 2;
            double S = Math.sqrt(p * (p - m) * (p - b) * (p - c));
            double sin = 2 * S / (m * m);
            degree = Math.asin(sin);
            rotation = -((float) (Math.asin(sin) * 180 / Math.PI));
        }

        if (Math.abs(degree) > Math.PI / 2) {
            degree = Math.PI - Math.abs(degree);
        }

        stickerMustache.setRotation(rotation);

        stickerMustache.setControlItemsHidden(true);
        float scale = (float) (distanceCheek / distanceStickerPx);
        if (scale <= 1) {
            scale = 1;
        }

//        stickerMustache.getLayoutParams().width = (int) (stickerMustache.getWidth() * distanceCheekPx / distanceStickerPx);
//        stickerMustache.getLayoutParams().height = (int) ( stickerMustache.getHeight() * distanceCheekPx / distanceStickerPx);
//
//        stickerMustache.requestLayout();

        float xCuttingPoint = 0;
        float yCuttingPoint = 0;
        if (xNose - xUnderMouth != 0) {
            float aMouthToUnder = (yNose - yUnderMouth) / (xNose - xUnderMouth);
            float bMouthToUnder = yNose - ((yNose - yUnderMouth) * xNose) / (xNose - xUnderMouth);

            float aLeftToRightMouth = (yLeftMouth - yRightMouth) / (xLeftMouth - xRightMouth);
            float bLeftToRightMouth = yLeftMouth - ((yLeftMouth - yRightMouth) * xLeftMouth) / (xLeftMouth - xRightMouth);

            xCuttingPoint = (bLeftToRightMouth - bMouthToUnder) / (aMouthToUnder - aLeftToRightMouth);
            yCuttingPoint = ((bLeftToRightMouth - bMouthToUnder) * aMouthToUnder) / (aMouthToUnder - aLeftToRightMouth) + bMouthToUnder;


//        float xCoordinate = (xCuttingPoint + xNose) / 2 - stickerMustache.getWidth() / 2 ;
//        float yCoordinate = (yCuttingPoint + yNose) / 2 - stickerMustache.getWidth() / 4 + StickerView.BUTTON_SIZE_DP;
        } else {

            xCuttingPoint = xNose;
            yCuttingPoint = (xCuttingPoint * (yLeftMouth - yRightMouth)) / (xLeftMouth - xRightMouth) + yLeftMouth
                    - ((yLeftMouth - yRightMouth) / (xLeftMouth - xRightMouth)) * ((yLeftMouth - yRightMouth) / (xLeftMouth - xRightMouth));
//
        }

        float xMiddlePoint = (xCuttingPoint + xNose) / 2;
        float yMiddlePoint = (yCuttingPoint + yNose) / 2;
//        float xCoordinate = (xCuttingPoint + xNose) / 2 - stickerMustache.getWidth() / 2 ;
//        float yCoordinate = (yCuttingPoint + yNose) / 2 - stickerMustache.getWidth() / 4 + StickerView.BUTTON_SIZE_DP;


        int newWidth = (int) (stickerMustache.getWidth() * scale);
        stickerMustache.getLayoutParams().width = (int) (newWidth);
        stickerMustache.getLayoutParams().height = (int) (newWidth);

        stickerMustache.requestLayout();

        int widthTrue = newWidth - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this) - 10;
        int heightTrue = newWidth - convertDpToPixel(StickerView.BUTTON_SIZE_DP, this);
        double valueY = newWidth * ((Math.sin(degree) * Math.cos(degree)) /
                (Math.sin(degree) + Math.cos(degree) + 1));
        double coordinateX = xMiddlePoint - newWidth / 2;
        double coordinateY = yMiddlePoint - heightTrue / 2;
        int count = 0;
//        while(true) {
        stickerMustache.setX((float) coordinateX);
        stickerMustache.setY((float) coordinateY);
//            count++;
//            if (count == 2) break;
//        }
        Log.e("TAG", "x " + stickerMustache.getX() + " : " + coordinateX);
        Log.e("TAG", "y" + stickerMustache.getY() + " : " + coordinateY);

    }
}
