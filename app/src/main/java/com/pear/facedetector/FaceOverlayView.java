package com.pear.facedetector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.face.Landmark;
import com.pear.facedetector.object.ObjectFace;

import java.util.ArrayList;
import java.util.List;

public class FaceOverlayView extends View {

    private Bitmap mBitmap;
    private SparseArray<Face> mFaces;
    public static List<ObjectFace> list;
    public static double scaleFinal;

    public FaceOverlayView(Context context) {
        this(context, null);
        init();
    }

    public FaceOverlayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public FaceOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setBitmap(Bitmap bitmap) {
        mBitmap = bitmap;
        FaceDetector detector = new FaceDetector.Builder(getContext())
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        if (!detector.isOperational()) {
            //Handle contingency
        } else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            mFaces = detector.detect(frame);
            detector.release();
        }
//        logFaceData();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if ((mBitmap != null) && (mFaces != null)) {
            double scale = drawBitmap(canvas);
            //drawFaceLandmarks(canvas, scale);
            drawFaceBox(canvas, scale);
            drawFaceLandmarks(canvas, scale);
        }
    }

    private double drawBitmap(Canvas canvas) {
        double viewWidth = canvas.getWidth();
        double viewHeight = canvas.getHeight();
        double imageWidth = mBitmap.getWidth();
        double imageHeight = mBitmap.getHeight();
        double scale = Math.min(viewWidth / imageWidth, viewHeight / imageHeight);
        scaleFinal = scale;
        Rect destBounds = new Rect(0, 0, (int) (imageWidth * scale), (int) (imageHeight * scale));
        canvas.drawBitmap(mBitmap, null, destBounds, null);
        return scale;
    }

    private void init(){
        list = new ArrayList<>();
    }

    private void drawFaceBox(Canvas canvas, double scale) {
        //This should be defined as a member variable rather than
        //being created on each onDraw request, but left here for
        //emphasis.
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        float left = 0;
        float top = 0;
        float right = 0;
        float bottom = 0;

        for (int i = 0; i < mFaces.size(); i++) {
            Face face = mFaces.valueAt(i);

            left = (float) (face.getPosition().x * scale);
            top = (float) (face.getPosition().y * scale);
            right = (float) scale * (face.getPosition().x + face.getWidth());
            bottom = (float) scale * (face.getPosition().y + face.getHeight());

            canvas.drawRect(left, top, right, bottom, paint);
        }
    }

    private void drawFaceLandmarks(Canvas canvas, double scale) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(3);


        for (int i = 0; i < mFaces.size(); i++) {
            Face face = mFaces.valueAt(i);

            for (Landmark landmark : face.getLandmarks()) {
                int cx = (int) (landmark.getPosition().x * scale);
                int cy = (int) (landmark.getPosition().y * scale);
                String kind = "";
                if (landmark.getType() == 4) {
                    kind = "mắt trái";
                    list.add(new ObjectFace("Mắt trái", cx, cy, Kind.MT));
                } else if (landmark.getType() == 6) {
                    kind = "mũi";
                    list.add(new ObjectFace("mũi", cx, cy, Kind.M));
                } else if (landmark.getType() == 10) {
                    kind = "mắt phải";
                    list.add(new ObjectFace("mắt phải", cx, cy, Kind.MP));
                }else if (landmark.getType() == 5){
                    kind = "miệng trái";
                    list.add(new ObjectFace("miệng trái", cx, cy, Kind.LeftMouth));
                }else if (landmark.getType() == 11){
                    list.add(new ObjectFace("Mieng phai", cx, cy, Kind.RightMouth));
                }else if (landmark.getType() == 0){
                    list.add(new ObjectFace("Mieng duoi", cx, cy, Kind.UnderMouth));
                }else if (landmark.getType() == 1){
                    list.add(new ObjectFace("Ma trai", cx, cy, Kind.LeftCheek));
                }else if (landmark.getType() == 7){
                    list.add(new ObjectFace("Ma phai", cx, cy, Kind.RightCheek));
                }else if (landmark.getType() == 2){
                    list.add(new ObjectFace("Chop tai trai", cx, cy, Kind.TopLeftEar ));
                }else if (landmark.getType() == 8 ){
                    list.add(new ObjectFace("Chop tai phai", cx, cy, Kind.TopRightEar));
                }else if (landmark.getType() == 3){
                    list.add(new ObjectFace("Tai trai", cx, cy, Kind.LeftEar));
                }else if (landmark.getType() == 9){
                    list.add(new ObjectFace("tai phai", cx, cy, Kind.RightEar));
                }
                if (!kind.isEmpty()) {
                    Log.e("TAG", "" + kind + landmark.getType() + "( x = " + cx + ", y = " + cy + ")");
                }
                //0: môi dưới, 1: má trái, 3: tai trái, 2: chóp tai trái, 4: mắt trái,5: khóe miệng trái
                //6: mũi, 7: má phải, 9: tai phải, 8: chóp tai phải, 10: mắt phải,11: khóe miệng phải

                    canvas.drawPoint(cx, cy, paint);

            }

            for (int j = 0; j < list.size(); j++){
                Log.e("TAG", "vi tri " + j  + " : " + list.get(j).getName() + " x : " + list.get(j).getX() + " y : " + list.get(j).getY() );
            }

        }
    }



    public void logFaceData() {
        float smilingProbability;
        float leftEyeOpenProbability;
        float rightEyeOpenProbability;
        float eulerY;
        float eulerZ;
        for (int i = 0; i < mFaces.size(); i++) {
            Face face = mFaces.valueAt(i);

            smilingProbability = face.getIsSmilingProbability();
            leftEyeOpenProbability = face.getIsLeftEyeOpenProbability();
            rightEyeOpenProbability = face.getIsRightEyeOpenProbability();
            eulerY = face.getEulerY();
            eulerZ = face.getEulerZ();
            Log.e("Face Detection", "Smiling: " + smilingProbability);
            Log.e("Face Detection", "Left eye open: " + leftEyeOpenProbability);
            Log.e("Face Detection", "Right eye open: " + rightEyeOpenProbability);
            Log.e("Face Detection", "Euler Y: " + eulerY);
            Log.e("Face Detection", "Euler Z: " + eulerZ);
        }
    }
}
