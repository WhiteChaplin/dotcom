package com.example.nsn11.dotcom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TestUploadVideo extends AppCompatActivity {
    Button btnSetVideo,btnUpload;
    VideoView videoView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private final static int PICK_IMAGE_REQUEST=1;
    private Uri filePath=null;
    private String UID=null;
    SharedPreferences sharedPreferences;
    SharedPreferences sf;
    SharedPreferences.Editor editor;
    private final int SELECT_MOVIE = 2;
    String today;
    public String videoFilePath=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_upload__video_activity);

        btnSetVideo = (Button)findViewById(R.id.btnSetVideo);
        btnUpload = (Button)findViewById(R.id.btnUpload);
        videoView = (VideoView)findViewById(R.id.Videoview);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        UID = firebaseAuth.getUid().toString();


        btnSetVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("video/*");
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                try {
                    startActivityForResult(i, SELECT_MOVIE);
                } catch (android.content.ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filePath != null) {
                    final ProgressDialog progressDialog = new ProgressDialog(TestUploadVideo.this);
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
                                    Intent intent = new Intent(TestUploadVideo.this,Estimate_Complete_Activity.class);
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
                            videoFilePath = downloadUri.toString();
                            estimate.pictureFilePath = videoFilePath.toString();

                            estimate.setUserUID(UID);

                            EstimateNum estimateNum = new EstimateNum();
                            estimateNum.estimateNum = estimateUID.toString();
                            estimateNum.setEstimateNum(estimateUID.toString());

                            FirebaseDatabase.getInstance().getReference().child("user_Video").child(estimateUID).setValue(estimate);
                            FirebaseDatabase.getInstance().getReference().child("Estimate_Num").push().setValue(estimateNum);
                            FirebaseDatabase.getInstance().getReference().child("users").child(UID.toString()).child("user_Estimate").child("user_Estimate_Video_Number").push().child(estimateUID).setValue(estimateNum);
                        }
                    });

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        filePath = data.getData();

        String path = getRealPath(filePath);

        String name = getName(filePath);

        String uriId = getUriId(filePath);
        try{
            videoView.setVideoPath(path);
            MediaController controller = new MediaController(this);
            videoView.setMediaController(controller);
            //videoView.requestFocus();
            videoView.start();
            videoView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    controller.show(1);
                    videoView.pause();
                }
            },100);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
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
        super.onActivityResult(requestCode, resultCode, data);
    }
    */

    // 실제 경로 찾기

    private String getRealPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };

        Cursor cursor = managedQuery(uri, projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(column_index);

    }



    // 파일명 찾기

    private String getName(Uri uri) {

        String[] projection = { MediaStore.Images.ImageColumns.DISPLAY_NAME };

        Cursor cursor = managedQuery(uri, projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DISPLAY_NAME);

        cursor.moveToFirst();

        return cursor.getString(column_index);

    }



    // uri 아이디 찾기

    private String getUriId(Uri uri) {

        String[] projection = { MediaStore.Images.ImageColumns._ID };

        Cursor cursor = managedQuery(uri, projection, null, null, null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID);

        cursor.moveToFirst();

        return cursor.getString(column_index);

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

}
