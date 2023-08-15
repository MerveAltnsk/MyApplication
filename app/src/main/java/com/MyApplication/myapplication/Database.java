package com.MyApplication.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
}

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {                              //onCreate tablo veritabanında yoksa oluşturur
        //String qry1 = "create table users(username text,email text.password text)";
        //sqLiteDatabase.execSQL(qry1);                                                //sqLite Database
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {                 //upgrade sadece tabloyu günceller

    }

    public void register(String username, String email, String password){         //Kayıt fonksiyonu
        ContentValues cv = new ContentValues();                                  //Bir nesne oluşturduk
        cv.put("username",username);                                            //ilki sütun adı,ikincisi değişken adı
        cv.put("email",email);
        cv.put("password",password);
        SQLiteDatabase db = getWritableDatabase();                               //Yeni bir nesne oluşturduk, yazılabilir bir veritabanı
        db.insert("users",null,cv);                          //Ekleme yöntemi,tablo adı users ilk parametre ,ikinci parametre boş sütun istiyoruz ve üçüncü parametre ise ContentValues un nesnesi
        db.close();                                                            //db bağlantısını kapatıyoruz
    }

    //Giriş için oturum açma işlevi

    public int login(String username, String password){                      // kullanıcı adını ve şifreyi kontrol kontrol et ve geç
        return 1;
        /*
        int result=0;                                                         //Başlangıçdeğeri 0 veriririz
        String str[] = new String[2];                                         // Soru işareti için
        str[0] = username;                                                    //İlk indeks username için
        str[1] = password;                                                    //ikinci index şifre için

        SQLiteDatabase db = getReadableDatabase();                            //Kullanıcı adı ve şifre kontrolü için okunabilir veritabanına ,ihtiyacımız var, çünkü seçime ihtiyacım var
        Cursor c = db.rawQuery("Select * from users where username= ? and password= ?",str);

        if(c.moveToFirst()){
            result = 1;                                                       //Bazı kayıt girişleri olduğu anlamına gelir, sonuç 0 ise o kullanıcı mevcut değildir
        }

        return result;

         */
    }




}
