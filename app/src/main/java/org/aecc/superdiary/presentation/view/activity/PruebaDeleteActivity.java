package org.aecc.superdiary.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.aecc.superdiary.R;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerExamComponent;
import org.aecc.superdiary.presentation.internal.di.components.ExamComponent;
import org.aecc.superdiary.presentation.model.ExamModel;
import org.aecc.superdiary.presentation.presenter.ExamDetailDeletePresenter;
import org.aecc.superdiary.presentation.view.PruebaDetailDeleteView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PruebaDeleteActivity extends BaseActivity implements PruebaDetailDeleteView, HasComponent<ExamComponent> {

    private static final String INTENT_EXTRA_PARAM_EXAM_ID = "org.aecc.INTENT_PARAM_EXAM_ID";
    private static final String INSTANCE_STATE_PARAM_EXAM_ID = "org.aecc.STATE_PARAM_EXAM_ID";

    private int examId;
    private ExamComponent examComponent;

    //views

    @Inject
    ExamDetailDeletePresenter examDetailDeletePresenter;


    @InjectView(R.id.nombrePrueba)
    TextView nombrePrueba;
    @InjectView(R.id.descripcionPrueba)
    TextView descripcionPrueba;
    @InjectView(R.id.fechaPrueba)
    TextView fechaPrueba;
    @InjectView(R.id.horaPrueba)
    TextView horaPrueba;
    @InjectView(R.id.borrarPrueb)
    Button borrarPrueba;
    @InjectView(R.id.fotoPrueba)
    ImageView foto;

    private String namePhoto;

    public static Intent getCallingIntent(Context context, int examId) {
        Intent callingIntent = new Intent(context, PruebaDeleteActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_EXAM_ID, examId);
        return callingIntent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_delete);
        ButterKnife.inject(this);

        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
        this.initialize();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putInt(INSTANCE_STATE_PARAM_EXAM_ID, this.examId);
        }
        super.onSaveInstanceState(outState);
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            this.examId = getIntent().getIntExtra(INTENT_EXTRA_PARAM_EXAM_ID, -1);
            //addFragment(R.id.fl_fragment, UserDetailsFragment.newInstance(this.userId));
        } else {
            this.examId = savedInstanceState.getInt(INSTANCE_STATE_PARAM_EXAM_ID);
        }
    }

    private void initializeInjector() {
        this.examComponent = DaggerExamComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    public void renderExam(ExamModel exam) {

        this.nombrePrueba.setText(exam.getName());
        this.descripcionPrueba.setText(exam.getDescription());
        this.fechaPrueba.setText(exam.getDateExam());
        this.horaPrueba.setText(exam.getHourExam());
        if (exam.getImage() != null){
            namePhoto = exam.getImage();
            File f = new File(Environment.getExternalStorageDirectory().toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals(exam.getImage())) {
                    f = temp;
                    break;
                }
            }
            try {
                Bitmap bitmap;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;


                // Calculate inSampleSize
                options.inSampleSize = calculateInSampleSize(options, 300, 300);

                // Decode bitmap with inSampleSize set
                options.inJustDecodeBounds = false;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inDither = true;

                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(), options);



                //viewImage.setImageBitmap(resizeImageForImageView(bitmap));
                //viewImage.setImageBitmap( decodeSampledBitmapFromResource(getResources(), R.id.viewImage, 100, 100));


                foto.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        byte[] byteArray = os.toByteArray();
        return Base64.encodeToString(byteArray, 0);
    }

    public Bitmap convertToBitmap(String base64String) {
        byte[] decodedString = Base64.decode(base64String, Base64.DEFAULT);
        Bitmap bitmapResult = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return bitmapResult;
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
// Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 3;
            final int halfWidth = width / 3;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    @Override
    public ExamComponent getComponent() {
        return examComponent;
    }

    @OnClick(R.id.borrarPrueb)
    public void deleteExam() {
        this.examDetailDeletePresenter.deleteExam(this.examId);
    }

    @Override public void onResume() {
        super.onResume();
        this.examDetailDeletePresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.examDetailDeletePresenter.pause();
    }

    @Override
    public void deleteExam(int examId) {

    }

    @Override
    public void showMessage(String message) {
        this.showToastMessage("La prueba se ha borrado correctamente.");
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    private void initialize() {
        this.getApplicationComponent().inject(this);
        this.getComponent().inject(this);
        this.examDetailDeletePresenter.setView(this);
        this.examDetailDeletePresenter.initialize(this.examId);
    }

    @Override
    public void showOkMessage() {
        this.showToastMessage("La prueba se ha borrado correctamente.");
    }

    @Override
    public void goToList() {
        this.onBackPressed();
    }
}
