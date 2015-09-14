package org.aecc.superdiary.presentation.view.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TimePicker;

import org.aecc.superdiary.R;
import org.aecc.superdiary.domain.Exam;
import org.aecc.superdiary.presentation.internal.di.HasComponent;
import org.aecc.superdiary.presentation.internal.di.components.DaggerExamComponent;
import org.aecc.superdiary.presentation.internal.di.components.ExamComponent;
import org.aecc.superdiary.presentation.model.ExamModel;
import org.aecc.superdiary.presentation.model.ExamModel;
import org.aecc.superdiary.presentation.presenter.ExamDetailEditPresenter;
import org.aecc.superdiary.presentation.view.PruebaDetailEditView;
import org.aecc.superdiary.presentation.view.activity.service.ScheduleClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PruebaEditActivity extends BaseActivity implements PruebaDetailEditView,HasComponent<ExamComponent>{

    private static final String INTENT_EXTRA_PARAM_EXAM_ID = "org.aecc.INTENT_PARAM_EXAM_ID";
    private static final String INSTANCE_STATE_PARAM_EXAM_ID = "org.aecc.STATE_PARAM_EXAM_ID";

    private int examId;
    private ExamComponent examComponent;

    @InjectView(R.id.nombrePrueba)
    EditText nombrePrueba;
    @InjectView(R.id.descripcionPrueba)
    EditText descripcionPrueba;
    @InjectView(R.id.fechaPrueba)
    EditText fechaPrueba;
    @InjectView(R.id.horaPrueba)
    EditText horaPrueba;
    @InjectView(R.id.guardarPrueba)
    Button guardarPrueba;

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    ImageButton btnSelect;
    ImageView ivImage;

    private String namePhoto;

    private DatePickerDialog fIniDatePickerDialog;
    private TimePickerDialog hIniTimePickerDialog;

    private SimpleDateFormat dateFormatter;
    
    @Inject
    ExamDetailEditPresenter examDetailEditPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.FRANCE);
        setDateTimeField();
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.activity_user_details);
        ButterKnife.inject(this);
        this.initializeInjector();
        this.initializeActivity(savedInstanceState);
        this.initialize();


        btnSelect = (ImageButton) findViewById(R.id.btnSelectPhoto);
        btnSelect.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        ivImage = (ImageView) findViewById(R.id.fotoPrueba);
    }

    public static Intent getCallingIntent(Context context, int examId) {
        Intent callingIntent = new Intent(context, PruebaEditActivity.class);
        callingIntent.putExtra(INTENT_EXTRA_PARAM_EXAM_ID, examId);

        return callingIntent;
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

    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
        int hour = newCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = newCalendar.get(Calendar.MINUTE);

        fIniDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fechaPrueba.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        hIniTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                horaPrueba.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
            } },hour , minute, true);
    }

    private void selectImage() {
        final CharSequence[] items = {"Hacer Foto", "Galeria",
                "Cancelar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(PruebaEditActivity.this);
        builder.setTitle("Cambiar Ima");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Hacer Foto")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Random randGenerator = new Random();
                    int name = randGenerator.nextInt(1000);
                    namePhoto = "exam-image-" + name + ".jpg";
                    File f = new File(Environment.getExternalStorageDirectory(), namePhoto);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[item].equals("Galeria")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);
                } else if (items[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        File f = new File(Environment.getExternalStorageDirectory().toString());
        for (File temp : f.listFiles()) {
            if (temp.getName().equals(namePhoto)) {
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


            ivImage.setImageBitmap(bitmap);

            File imagesFolder = new File(
                    Environment.getExternalStorageDirectory(), "Diario");
            imagesFolder.mkdirs();

            String path = android.os.Environment.getExternalStorageDirectory()+ File.separator+ "Diario";
            //f.delete();
            OutputStream outFile = null;
            File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
            try {
                outFile = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
                outFile.flush();
                outFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null,
                null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 256;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        ivImage.setImageBitmap(bm);
    }

    @Override
    public ExamComponent getComponent() {
        return examComponent;
    }

    @Override
    public void renderExam(ExamModel exam) {
        this.nombrePrueba.setText(exam.getName());
        this.descripcionPrueba.setText(exam.getDescription());
        this.fechaPrueba.setText(exam.getDateExam());
        this.horaPrueba.setText(exam.getHourExam());
        if (exam.getImage() != null) {
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


                ivImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.fechaPrueba)
    public void showPickerFechaR(){
        fIniDatePickerDialog.show();
    }

    @OnClick(R.id.horaPrueba)
    public void showPickerFechaA(){
        hIniTimePickerDialog.show();
    }

    @OnClick(R.id.guardarPrueba)
    void editClicked(){
        this.examDetailEditPresenter.editExam(this.examId);
    }

    @Override
    public void editExam(int examId) {
        Exam exam = new Exam(this.examId);
        exam.setName(this.nombrePrueba.getText().toString());
        exam.setDescription(this.descripcionPrueba.getText().toString());
        exam.setDateExam(this.fechaPrueba.getText().toString());
        exam.setHourExam(this.horaPrueba.getText().toString());
        exam.setImage(namePhoto);
        this.examDetailEditPresenter.saveExam(exam);
    }

    @Override
    public void showMessage(String message) {
        this.showToastMessage(message);
    }

    @Override
    public void goBack() {
        onBackPressed();
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
        this.examDetailEditPresenter.setView(this);
        this.examDetailEditPresenter.initialize(this.examId);
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

}
