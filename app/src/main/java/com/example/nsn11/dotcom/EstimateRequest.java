package com.example.nsn11.dotcom;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class EstimateRequest extends AppCompatActivity{

    ImageView estimateImageView,btnGotoEstimate;
    Button choiceText;
    TextView explaintext,separateText,LocateText,LocateChoice,choice,text_selectdevice;
    EditText explainTextView,editTitle;
    CheckBox radioBtnComputerClean,radioBtnFormat,radioBtnWindow,radioBtnService;
    Toolbar toolbar;
    private final static int PICK_IMAGE_REQUEST=1;
    private final static int LOCATION_INTENT=2;
    ImageView imageView=null;
    public String CheckBoxList;
    public String LocationName;
    private String mCurrentPhotoPath=null;
    private Uri filePath=null;
    private String UID=null;
    public String pictureFilePath=null;
    String shopID;
    String today;
    SharedPreferences sharedPreferences;
    SharedPreferences sf;
    SharedPreferences.Editor editor;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    String text_choice=null;
    String text_device=null;
    String EditText_explainTextView=null;
    Boolean CheckBox_radioBtnComputerClean=false;
    Boolean CheckBox_radioBtnFormat=false;
    Boolean CheckBox_radioBtnWindow=false;
    Boolean CheckBox_radioBtnService=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.estimate_request);
        super.onCreate(savedInstanceState);

        //Intent 설정
        final Intent intent = new Intent(this,NaverMap.class);
        final Intent getIntent = getIntent();
        final Intent requestToRepairShop = new Intent(this,MainActivity.class);

        //위젯 설정
        estimateImageView = (ImageView)findViewById(R.id.imgview_emptyimage);
        explaintext = (TextView)findViewById(R.id.explainText);
        explainTextView = (EditText)findViewById(R.id.explainTextView);
        editTitle = (EditText)findViewById(R.id.edtext_title);
        separateText = (TextView)findViewById(R.id.separateText);
        text_selectdevice = (TextView)findViewById(R.id.text_selectdevice);
        choice = (TextView) findViewById(R.id.choice);
        LocateText = (TextView)findViewById(R.id.LocateText);
        LocateChoice = (TextView)findViewById(R.id.LocateChoice);
        btnGotoEstimate=(ImageView)findViewById(R.id.btn_complete);
        radioBtnComputerClean=(CheckBox)findViewById(R.id.radioBtnComputerClean);
        radioBtnFormat=(CheckBox)findViewById(R.id.radioBtnFormat);
        radioBtnService=(CheckBox)findViewById(R.id.radioBtnService);
        radioBtnWindow=(CheckBox)findViewById(R.id.radioBtnWindow);



        //체크박스 설정
        final CheckBox checkBoxComputerClean = radioBtnComputerClean;
        final CheckBox checkBoxFormat = radioBtnFormat;
        final CheckBox checkBoxService = radioBtnService;
        final CheckBox checkBoxWindow = radioBtnWindow;
        //Firebase 설정
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        UID = firebaseUser.getUid().toString();

        LocateChoice.setText("터치하면 지도로 이동합니다");
        LocateChoice.setTextColor(Color.WHITE);

        //사진을 올리기 위해서 갤러리로 이동하는 코드
        estimateImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intentPic = new Intent(Intent.ACTION_GET_CONTENT);
                intentPic.setType("image/*");
                startActivityForResult(Intent.createChooser(intentPic,"사진을 선택하세요!"),PICK_IMAGE_REQUEST);
                */
                Intent intentPic = new Intent(Intent.ACTION_PICK);
                intentPic.setDataAndType(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*");
                startActivityForResult(intentPic,PICK_IMAGE_REQUEST);
                //imageText.setVisibility(TextView.INVISIBLE);
            }
        });

        //수리점 위치 설정하는 코드
        LocateChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent,LOCATION_INTENT);
            }
        });


        //분류 설정하는 코드
        //choice.setBackgroundColor(Color.parseColor("#FF3c4353"));
        text_selectdevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] items = {"데스크탑","노트북","모니터","스피커"};
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EstimateRequest.this);

                //제목
                alertDialogBuilder.setTitle("터치해서 종류를 선택하세요!");
                alertDialogBuilder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        text_selectdevice.setText(items[which]);
                        text_choice= (String) items[which];
                    }
                });
                alertDialogBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //requestToRepairShop.putExtra("part","part");
                        //Toast.makeText(getApplicationContext(),choice.getText().toString()+" 선택",Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.show();
                text_choice = choice.getText().toString();
                requestToRepairShop.putExtra(choice.getText().toString(),"choiceItem");
            }
        });

        //어떤 자가진단을 이용했는지 선택하는 코드
        //choiceText.setBackgroundColor(Color.parseColor("#FF3c4353"));
        choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"부팅","모니터","재부팅","인터넷"};
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(EstimateRequest.this);

                //제목
                alertDialogBuilder.setTitle("터치해서 종류를 선택하세요!");
                alertDialogBuilder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        choice.setText(items[which]);
                    }
                });
                alertDialogBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestToRepairShop.putExtra("choiceText","choiceText");
                        //Toast.makeText(getApplicationContext(),choiceText.getText().toString()+" 선택",Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialogBuilder.show();
            }
        });
        /*
        RadioButton.OnClickListener radioButtonClickListener = new RadioButton.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(radioBtnComputerClean.isChecked())
                {
                    Toast.makeText(EstimateRequest.this,radioBtnComputerClean.getText().toString()+"클릭됨",Toast.LENGTH_SHORT).show();
                    CheckBoxList += radioBtnComputerClean.getText().toString()+" ";
                }
                if(radioBtnFormat.isChecked())
                {
                    Toast.makeText(EstimateRequest.this,radioBtnFormat.getText().toString()+"클릭됨",Toast.LENGTH_SHORT).show();
                    CheckBoxList += radioBtnFormat.getText().toString()+" ";
                }
                if(radioBtnService.isChecked())
                {
                    Toast.makeText(EstimateRequest.this,radioBtnService.getText().toString()+"클릭됨",Toast.LENGTH_SHORT).show();
                    CheckBoxList += radioBtnService.getText().toString()+" ";
                }
                if(radioBtnWindow.isChecked())
                {
                    Toast.makeText(EstimateRequest.this,radioBtnWindow.getText().toString()+"클릭됨",Toast.LENGTH_SHORT).show();
                    CheckBoxList += radioBtnWindow.getText().toString()+" ";
                }

        requestToRepairShop.putExtra(CheckBoxList,"CheckBoxList");
    }
};
        */

        //견적 요청하기 버튼 눌렀을 시 실행되는 코드
        btnGotoEstimate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!explainTextView.getText().toString().equals("") && !(choice.getText().toString().equals("")) && !(text_selectdevice.getText().toString().equals(""))
                        && !(LocateChoice.getText().toString().equals(""))) {
                    CheckBoxList="";
                    if(radioBtnWindow.isChecked()==true)
                        CheckBoxList += radioBtnWindow.getText().toString()+". ";
                    if(radioBtnService.isChecked()==true)
                        CheckBoxList += radioBtnService.getText().toString()+". ";
                    if(radioBtnFormat.isChecked()==true)
                        CheckBoxList += radioBtnFormat.getText().toString()+". ";
                    if(radioBtnComputerClean.isChecked()==true)
                        CheckBoxList += radioBtnComputerClean.getText().toString()+". ";
                    //Toast.makeText(EstimateRequest.this,CheckBoxList+" 클릭됨",Toast.LENGTH_SHORT).show();
                    uploadFile();
                }
                else{
                    Toast.makeText(getApplicationContext(),"비어있는 칸이 있습니다",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //견적요청서가 다른 액티비티에 의해 가려질 떄 적어놓았던 정보들을 저장해두는 코드
    private void SharedFunction(){
        sf = getSharedPreferences("File",MODE_PRIVATE);
        text_choice = sf.getString("choice","");
        text_device = sf.getString("choiceText","");
        EditText_explainTextView = sf.getString("explainTextView","");

        CheckBox_radioBtnWindow = sf.getBoolean("radioBtnWindow",false);
        CheckBox_radioBtnService = sf.getBoolean("radioBtnService",false);
        CheckBox_radioBtnComputerClean = sf.getBoolean("radioBtnComputerClean",false);
        CheckBox_radioBtnFormat = sf.getBoolean("radioBtnFormat",false);

        choice.setText(text_choice);
        text_selectdevice.setText(text_device);
        explainTextView.setText(EditText_explainTextView);

        radioBtnFormat.setChecked(CheckBox_radioBtnFormat);
        radioBtnComputerClean.setChecked(CheckBox_radioBtnComputerClean);
        radioBtnService.setChecked(CheckBox_radioBtnService);
        radioBtnWindow.setChecked(CheckBox_radioBtnWindow);
    }

    //Intent 결과값 받아오는 코드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //갤러리 사진 가져오는 코드
        if(resultCode==RESULT_OK && requestCode == PICK_IMAGE_REQUEST)
        {
            filePath = data.getData();
            try {
                // 선택한 이미지에서 비트맵 생성
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                estimateImageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //수리점 위치정보 가져오는 코드
        else if(resultCode == RESULT_OK && requestCode == LOCATION_INTENT)
        {
            LocationName = data.getStringExtra("ShopLocation");
            //CheckBoxList += LocationName;
            LocateChoice.setText(LocationName);
            shopID = data.getStringExtra("shopID");
            //Toast.makeText(getApplicationContext(),shopID.toString(),Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //FireStoreage에 사진을 저장하는 코드
    //Firebase database에 EstimateRequest를 저장하는 코드
    private void uploadFile()
    {
        if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            final Estimate estimate = new Estimate();
            //FirebaseStorage storage = FirebaseStorage.getInstance("gs://dotcom-f8c8a.appspot.com");

            StorageReference storageReference = FirebaseStorage.getInstance().getReference();
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd.HH.mm");
            Date now = new Date();
            String filename = format.format(now) + ".png";
            final String estimateUID = getRandomString();

            final StorageReference reference = storageReference.child(UID).child("images/" + filename);
            //pictureFilePath = reference.getDownloadUrl().toString();
            //pictureFilePath = reference.toString();

            if (!explainTextView.getText().toString().equals("") && !(choice.getText().toString().equals("")) && !(text_selectdevice.getText().toString().equals(""))
                    && !(LocateChoice.getText().toString().equals("")) && !(CheckBoxList.toString().equals(""))) {
                /*
                estimate.userUID = UID;
                //estimate.pictureFilePath = pictureFilePath.toString();
                pictureFilePath = reference.getDownloadUrl().getResult().toString();
                estimate.pictureFilePath = pictureFilePath;;
                estimate.explainEditText = explainTextView.getText().toString();
                estimate.separateText = choice.getText().toString();
                estimate.selfRepairText = choiceText.getText().toString();
                estimate.locationText = LocateChoice.getText().toString();
                estimate.moreOption = CheckBoxList.toString();

                estimate.setUserUID(UID);
                estimate.setExplainEditText(explainTextView.getText().toString());
                estimate.setSeparateText(choice.getText().toString());
                estimate.setSelfRepairText(choiceText.getText().toString());
                estimate.setLocationText(LocateChoice.getText().toString());
                estimate.setMoreOption(CheckBoxList.toString());
                estimate.setPictureFilePath(pictureFilePath);

                EstimateNum estimateNum = new EstimateNum();
                estimateNum.estimateNum = estimateUID.toString();
                estimateNum.setEstimateNum(estimateUID.toString());

                FirebaseDatabase.getInstance().getReference().child("user_Estimate").child(estimateUID).setValue(estimate);
                FirebaseDatabase.getInstance().getReference().child("Estimate_Num").push().setValue(estimateNum);

                //reference.getDownloadUrl().getResult().toString();
                */

                reference.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                progressDialog.dismiss();   //업로드 진행 상자 닫기

                                /*
                                AlertDialog.Builder builder = new AlertDialog.Builder(EstimateRequest.this);
                                builder.setTitle("업로드 완료!");
                                builder.setMessage("업로드가 완료되었습니다! 수리점과 매칭이 성사되면 알림으로 알려드리겠습니다");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(EstimateRequest.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                });
                                builder.show();
                                 */
                                NotificationActivity notificationActivity = new NotificationActivity(getApplicationContext());
                                notificationActivity.Notification();
                                Intent intent = new Intent(EstimateRequest.this,Estimate_Complete_Activity.class);
                                startActivity(intent);
                                finish();
                                //Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "업로드 실패", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage("Uploaded" + ((int) progress) + "%...");
                            }
                        });
                Task<Uri> uriTask = reference.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        return reference.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        Uri downloadUri = task.getResult();
                        SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일 hh시 mm분");
                        Date date = new Date();
                        today = df.format(date);
                        estimate.userUID = UID;
                        //estimate.pictureFilePath = pictureFilePath.toString();
                        pictureFilePath = downloadUri.toString();
                        estimate.pictureFilePath = pictureFilePath;;
                        estimate.explainEditText = explainTextView.getText().toString();
                        estimate.editTitle = editTitle.getText().toString();
                        estimate.separateText = choice.getText().toString();
                        estimate.selfRepairText = text_selectdevice.getText().toString();
                        estimate.locationText = LocateChoice.getText().toString();
                        estimate.moreOption = CheckBoxList.toString();
                        estimate.nowState ="수리점 확인중";
                        estimate.date = today.toString();

                        estimate.setUserUID(UID);
                        estimate.setExplainEditText(explainTextView.getText().toString());
                        estimate.setSeparateText(choice.getText().toString());
                        estimate.setSelfRepairText(text_selectdevice.getText().toString());
                        estimate.setLocationText(LocateChoice.getText().toString());
                        estimate.setMoreOption(CheckBoxList.toString());
                        estimate.setPictureFilePath(pictureFilePath);
                        estimate.setNowState("수리점 확인중");
                        estimate.setDate(today.toString());
                        estimate.setEditTitle(editTitle.getText().toString());

                        EstimateNum estimateNum = new EstimateNum();
                        estimateNum.estimateNum = estimateUID.toString();
                        estimateNum.setEstimateNum(estimateUID.toString());

                        FirebaseDatabase.getInstance().getReference().child("user_Estimate").child(estimateUID).setValue(estimate);
                        FirebaseDatabase.getInstance().getReference().child("Estimate_Num").push().setValue(estimateNum);
                        FirebaseDatabase.getInstance().getReference().child("users").child(UID.toString()).child("user_Estimate").child("user_Estimate_Number").push().child(estimateUID).setValue(estimateNum);
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "입력 칸을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //EstimateUID 생성 코드
    public String getRandomString()
    {
        StringBuffer buffer=new StringBuffer();
        Random random = new Random();

        String chars[] = ("A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z," +
                "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z," +
                "1,2,3,4,5,6,7,8,9,0").split(",");
        for(int i=0;i<30;i++)
            buffer.append(chars[random.nextInt(chars.length)]);
        return buffer.toString();
    }

    /*
    public String getImageNameToUri(Uri data)
    {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data,proj,null,null,null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);

        return imgName;
    }
    */

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences = getSharedPreferences("File", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        text_choice = choice.getText().toString();
        text_device = text_selectdevice.getText().toString();
        EditText_explainTextView = explainTextView.getText().toString();
        CheckBox_radioBtnFormat = radioBtnFormat.isChecked();
        CheckBox_radioBtnComputerClean = radioBtnComputerClean.isChecked();
        CheckBox_radioBtnService = radioBtnService.isChecked();
        CheckBox_radioBtnWindow = radioBtnWindow.isChecked();

        editor.putString("choice", text_choice);
        editor.putString("choiceText", text_device);
        editor.putString("explainTextView", EditText_explainTextView);
        editor.putBoolean("radioBtnFormat", CheckBox_radioBtnFormat);
        editor.putBoolean("radioBtnComputerClean", CheckBox_radioBtnComputerClean);
        editor.putBoolean("radioBtnService", CheckBox_radioBtnService);
        editor.putBoolean("radioBtnWindow", CheckBox_radioBtnWindow);
        editor.commit();
    }


    @Override
    protected void onStop() {
        super.onStop();
        SharedFunction();
    }


}
