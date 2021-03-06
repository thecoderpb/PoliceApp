package com.ksp.donut.uca.auth;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FirestoreHandler {

    private FirebaseFirestore db;
    private Context context;
    private DocumentSnapshot snapshot;
    private String fieldValue;
    public static Map<String, Object> user = new HashMap<>();

    public FirestoreHandler(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }

    public void addData(String uid, String fName,String lName,String collections) { //later add params here

        //Values sent to firestore server are only valid questions with format (key) => (value) : question name => weight
//        user.put(PROFILE_TASK_STR_1, "John");
//        user.put(PROFILE_TASK_STR_2, "Doe");
//        user.put(PROFILE_TASK_STR_3, 1988);
//        user.put(PROFILE_TASK_STR_4, 1988);
//        user.put(PROFILE_TASK_STR_5, 1988);
        user.put("uid",uid);
        user.put("firstName", fName);
        user.put("lastName", lName);


        // Add a new document with a generated ID
        db.collection(collections)
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    public void getAllData(String docId,String collections) {

        db.collection(collections)
                .document(docId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            snapshot = task.getResult();
                            for (Map.Entry<String, Object> document : snapshot.getData().entrySet()) {
                                Log.d(TAG, document.getKey() + " => " + document.getValue());
                                Toast.makeText(context, "Success. Check logcat for details", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed with exception" + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });


    }

    public String getData(String docId, final String key) {

        db.collection("tasks")
                .document(docId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        fieldValue = (String) task.getResult().get(key);
                    }
                });

        if (fieldValue != null)
            return fieldValue;
        return "";
    }

  /* public boolean checkIfUserExistInDB(final String uid,final String userName) {

        db.collection("users")
                .whereEqualTo("uid",uid)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        if(queryDocumentSnapshots.getDocuments().size() == 0){
                            addData(uid,userName);
                        }else{
                            DocumentSnapshot snapshot = queryDocumentSnapshots.getDocuments().get(0);
                            for (Map.Entry<String, Object> document : snapshot.getData().entrySet()) {
                                Log.d(TAG, document.getKey() + " => " + document.getValue());
                        }

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //user does not exist in db. create a new field in db

                        addData(uid,userName);
                    }
                });

    }*/
}
