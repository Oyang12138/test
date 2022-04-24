package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * ContentProvider
 * 四大组件之一，主要用于不同应用程序之间实现数据共享功能
 * ContentResolver   是数据的调用者，ContentProvider将数据发布出来，通过ContentResolver对象结合Uri进行调用。通过ContentResolver调用ContentProvider的增删改查
 * Uri               通用资源标识符，代表数据操作的地址，每一个ContentProvider发布数据时都会有唯一的地址
 * <p>
 * 创建自定义ContentProvider的步骤：
 * 使用SQLite技术创建数据库和数据表
 * 新建类继承ContentProvider
 * 重写六个抽象方法
 * 创建UriMatcher，定义Uri规则
 * 在Manifest中注册Provider
 * ContentResolver对ContentProvider中共享的数据进行增删改查操作
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button insert;
    private EditText textId;
    private EditText edit;
    private Button delete;
    private Button update;
    private Button query;
    private TextView list;

    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insert = findViewById(R.id.insert);
        textId = findViewById(R.id.id);
        edit = findViewById(R.id.name);
        delete = findViewById(R.id.delete);
        query = findViewById(R.id.query);
        update = findViewById(R.id.update);
        list = findViewById(R.id.list);

        insert.setOnClickListener(this);
        delete.setOnClickListener(this);
        query.setOnClickListener(this);
        update.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.insert:
                String name = edit.getText().toString();
                String id = textId.getText().toString();
                Uri uri = Uri.parse("content://com.example.contentprovider.database.provider/userDemo");
                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("id",id);
                getContentResolver().insert(uri, values);
                break;
            case R.id.delete:
                String name1 = edit.getText().toString();
                Uri uri1 = Uri.parse("content://com.example.contentprovider.database.provider/userDemo");
                getContentResolver().delete(uri1, "name=?", new String[]{name1});
                break;
            case R.id.query:
                Uri uri2 = Uri.parse("content://com.example.contentprovider.database.provider/userDemo");
                Cursor cursor = getContentResolver().query(uri2, null, null, null, null);
                cursor.moveToFirst();
                do {
                    @SuppressLint("Range") String name2 =cursor.getString(cursor.getColumnIndex("name"));
                }while (cursor.moveToNext());
                cursor.moveToFirst();
                String va = "";
                while (cursor.moveToNext()){
                    String i = cursor.getString(0);
                    String s = cursor.getString(1);
                    String space = "                                    ";
                    String l = i + space + s;
                    va = va + l + "\n";
                }
                list.setText(va);
                cursor.close();
                break;
            case R.id.update:
                String updateStr = edit.getText().toString();
                Uri uri3 = Uri.parse("content://com.example.contentprovider.database.provider/userDemo");
                ContentValues values1 = new ContentValues();
                values1.put("name","重置");
                getContentResolver().update(uri3, values1, "name=?",new String[]{updateStr});
                break;
            default:
                break;
        }
    }
}