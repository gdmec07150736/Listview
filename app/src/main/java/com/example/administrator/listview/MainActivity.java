package com.example.administrator.listview;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "arrayadapter");
        menu.add(0, 2, 0, "simplecursoradapter");
        menu.add(0, 3, 0, "simpleadapter");
        menu.add(0, 4, 0, "baseadapter");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                arrayadapter();
                break;
            case 2:
                simplecursoradapter();
                break;
            case 3:
                simpleadapter();
                break;
            case 4:
                baseadapter();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void arrayadapter() {
        final String[] weeks = {"Sun", "Mon", "Tue", "Wes", "Thu", "Fri", "Sat"};
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_list_item_1, weeks);
        lv.setAdapter(aa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, weeks[position], Toast.LENGTH_LONG).show();
            }
        });
    }

    private void simplecursoradapter() {
        final Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_expandable_list_item_1, c,
                new String[]{ContactsContract.Contacts.DISPLAY_NAME}, new int[]{android.R.id.text1}, 0);
        lv.setAdapter(sca);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)),
                        Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void simpleadapter() {
        final List<Map<String, Object>> l = new ArrayList<>();
        Map<String, Object> m = new HashMap<>();
        m.put("title", "G1");
        m.put("info", "google1");
        m.put("img", R.drawable.icon1);
        l.add(m);
        m = new HashMap<>();
        m.put("title", "G2");
        m.put("info", "google2");
        m.put("img", R.drawable.icon2);
        l.add(m);
        m = new HashMap<>();
        m.put("title", "G3");
        m.put("info", "google3");
        m.put("img", R.drawable.icon3);
        l.add(m);
        SimpleAdapter sa = new SimpleAdapter(this, l, R.layout.sa_layout, new String[]{"img", "title", "info"},
                new int[]{R.id.imgv, R.id.title, R.id.info});
        lv.setAdapter(sa);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, l.get(position).get("title").toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    static class holder {
        public ImageView img;
        public TextView title;
        public TextView info;
        public Button b;
        public LinearLayout layout;
    }

    private void baseadapter() {
        class mybase extends BaseAdapter {
            private List<Map<String, Object>> data;
            private Context c;
            private LayoutInflater lif;

            public mybase(Context c, List<Map<String, Object>> data) {
                super();
                this.data = data;
                this.c = c;
                this.lif = lif.from(c);
            }


            @Override
            public int getCount() {
                return data.size();
            }

            @Override
            public Object getItem(int position) {
                return data.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(final int position, View convertView, ViewGroup parent) {
                holder h = null;
                if (convertView == null) {
                    h = new holder();
                    convertView = lif.inflate(R.layout.l1, parent, false);
                    h.img = (ImageView) convertView.findViewById(R.id.img);
                    h.title = (TextView) convertView.findViewById(R.id.title);
                    h.info = (TextView) convertView.findViewById(R.id.info);
                    h.b = (Button) convertView.findViewById(R.id.button);
                    h.layout = (LinearLayout) convertView.findViewById(R.id.l1);
                    convertView.setTag(h);
                } else {
                    h = (holder) convertView.getTag();
                }
                h.img.setImageResource(Integer.parseInt(data.get(position).get("img").toString()));
                h.title.setText(data.get(position).get("title").toString());
                h.info.setText(data.get(position).get("info").toString());
                if (position % 2 == 1) {
                    h.layout.setBackgroundColor(ContextCompat.getColor(c, R.color.colorAccent));
                } else {
                    h.layout.setBackgroundColor(ContextCompat.getColor(c, R.color.colorPrimary));
                }
                h.b.setOnClickListener(new AdapterView.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast.makeText(c, "click" + position, Toast.LENGTH_SHORT).show();
                    }
                });
                return convertView;
            }
        }
        final List<Map<String, Object>> l = new ArrayList<>();
        Map<String, Object> m = new HashMap<>();
        m.put("title", "G1");
        m.put("info", "google1");
        m.put("img", R.drawable.icon1);
        l.add(m);
        m = new HashMap<>();
        m.put("title", "G2");
        m.put("info", "google2");
        m.put("img", R.drawable.icon2);
        l.add(m);
        m = new HashMap<>();
        m.put("title", "G3");
        m.put("info", "google3");
        m.put("img", R.drawable.icon3);
        l.add(m);
        mybase myb = new mybase(this, l);
        lv.setAdapter(myb);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,l.get(position).get("title").toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}