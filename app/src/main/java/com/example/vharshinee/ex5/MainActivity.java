package com.example.vharshinee.ex5;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    EditText empId,name,salary;
    Button add,view,delete,modify;
    SQLiteDatabase mydatabase = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        empId = (EditText) findViewById(R.id.editEmpid);
        name = (EditText) findViewById(R.id.editName);
        salary = (EditText) findViewById(R.id.editsalary);
        add = (Button) findViewById(R.id.btnAdd);
        view = (Button) findViewById(R.id.btnView);
        delete = (Button) findViewById(R.id.btnDelete);
        modify = (Button) findViewById(R.id.btnModify);
        mydatabase = openOrCreateDatabase("db",MODE_PRIVATE,null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS employee(empid number,name VARCHAR(20),salary number);");
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(empId.getText().toString());
                int esalary = Integer.parseInt(salary.getText().toString().trim());
                mydatabase.execSQL("UPDATE employee SET name='"+name.getText().toString().trim()+"',salary="+esalary+" WHERE empid="+id);
                        Toast.makeText(getApplicationContext(),"Successfully modified",Toast.LENGTH_LONG).show();
                                empId.setText("");
                name.setText("");
                salary.setText("");
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(empId.getText().toString().equals("")||name.getText().toString().equals("")||salary
                        .getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(),"Fill all the boxes",Toast.LENGTH_LONG).show();
                else {
                    int id = Integer.parseInt(empId.getText().toString().trim());
                    String ename = name.getText().toString().trim();
                    int esalary = Integer.parseInt(salary.getText().toString());
                    mydatabase.execSQL("INSERT INTO employee VALUES(" + id + ",'" +
                            ename + "'," + esalary + ");");
                    Toast.makeText(getApplicationContext(), "successfully inserted",
                            Toast.LENGTH_LONG).show();
                    empId.setText("");
                    name.setText("");
                    salary.setText("");
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = Integer.parseInt(empId.getText().toString().trim());
                empId.setText("");
                name.setText("");
                salary.setText("");
                mydatabase.execSQL("delete from employee where empid=" + id);
                Toast.makeText(getApplicationContext(), "Successfully deleted",
                        Toast.LENGTH_LONG).show();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//int id = Integer.parseInt(empId.getText().toString().trim());
                int id = Integer.parseInt(empId.getText().toString().trim());
                String query = "Select * FROM employee WHERE empid ="+id;
                Cursor cursor=mydatabase.rawQuery(query,null);
                if ((cursor != null) && (cursor.getCount() > 0)) {
                    cursor.moveToFirst();
                    String ID = cursor.getString(0);
                    String esalary = cursor.getString(2);
                    String ename = cursor.getString(1);
                    empId.setText(ID);
                    name.setText(ename);
                    salary.setText(esalary);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Employee id not found",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
