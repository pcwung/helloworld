package com.practice.sensor;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Instrumentation;
//import android.app.Instrumentation;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SensorDemo extends Activity implements View.OnClickListener {

    private String SERVICE_NAME = Context.SENSOR_SERVICE;

    private SensorManager sensorManager;

    private Sensor orientSensor;
    private Sensor teampSensor;
    private Sensor psSensor;
    private Sensor lightSensor;
    private Sensor humiditySensor;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        config();


        final Button button1 = (Button) findViewById(R.id.button);
        button1.setText("please get bt ");
        button1.setOnClickListener(this);

        Intent serviceIntent = new Intent(this, MyService.class);
        startService(serviceIntent);

        final Button button3 = (Button) findViewById(R.id.button3);
        button3.setText("send a key report by Instrumentation");
        //监听button事件
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {

                    @Override
                    public void run() {

                        Instrumentation inst = new Instrumentation();
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_UNKNOWN);
                        //inst.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_DOWN);

                        System.out.println("test by Wade send KEYCODE_A");

                    }
                }).start();
//
//                new Thread(new Runnable() {
//
//                    @Override
//                    public void run() {
//
//                        Instrumentation inst = new Instrumentation();
//                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_UNKNOWN);
//                        //inst.sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_DOWN);
//
//                        System.out.println("test by Wade send KEYCODE_A");
//
//                    }
//                }).start();


            }
        });


        final Button button2 = (Button) findViewById(R.id.button2);
        button2.setText("make a dialog");

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SensorDemo.this);
                builder.setTitle("普通的对话框的标题");
                builder.setMessage("这是一个普通的对话框的内容");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SensorDemo.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SensorDemo.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog dialog = builder.create();
                //dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                dialog.show();

            }
        });


        final TextView goto_music = (TextView) findViewById(R.id.textView6);
        goto_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("test by Wade start an activity !");
                Intent intent;
                intent = new Intent(SensorDemo.this, musicActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "send pause", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction("com.xcz1899.smart.pause");
        sendBroadcast(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /* */
        // sensorManager.registerListener(mySensorListener3, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);

        sensorManager.registerListener(mySensorListener, orientSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(mySensorListener1, teampSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(mySensorListener2, psSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(mySensorListener3, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(mySensorListener4, humiditySensor, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause() {
        super.onPause();
        //???岽???????????????????
        sensorManager.unregisterListener(mySensorListener);
        sensorManager.unregisterListener(mySensorListener2);
        sensorManager.unregisterListener(mySensorListener3);
        sensorManager.unregisterListener(mySensorListener1);
        sensorManager.unregisterListener(mySensorListener4);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void config() {
        //??????????SensorManager????
        sensorManager = (SensorManager) getSystemService(SERVICE_NAME);
        //????????????????????
        orientSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        //teampSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

        psSensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        teampSensor = sensorManager.getDefaultSensor(Sensor.TYPE_TEMPERATURE);
        humiditySensor = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);


        if (teampSensor == null) {
            System.out.println("teampSensor do not support ");
        }
        if (orientSensor == null) {
            System.out.println("orientSensor do not support ");
        }
        if (psSensor == null) {
            System.out.println("psSensor do not support ");
        }
        if (lightSensor == null) {
            System.out.println("lightSensor do not support ");
        }
        if (humiditySensor == null) {
            System.out.println("humiditySensor do not support ");
        }
    }


    SensorEventListener mySensorListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            //System.out.println("pm2.5 is :" + x);
            final TextView tx2 = (TextView) findViewById(R.id.textView);

            tx2.setText("Now PM2.5 is " + x);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    SensorEventListener mySensorListener1 = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0] / 65536 * 165 - 40;
            //System.out.println("Temperature is :" + x );
            float temp = x;

            final TextView tx3 = (TextView) findViewById(R.id.textView1);

            tx3.setText("Now Temperature is " + x + " C");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    SensorEventListener mySensorListener2 = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            //System.out.println("PROXIMITY is :" + x);
            final TextView tx4 = (TextView) findViewById(R.id.textView2);

            tx4.setText("Now PROXIMITY is " + x);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };
    SensorEventListener mySensorListener3 = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            // System.out.println("light is :" + x);
            final TextView tx5 = (TextView) findViewById(R.id.textView3);

            tx5.setText("Now light is " + x);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    SensorEventListener mySensorListener4 = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0] / 65536 * 100;
            //System.out.println("humidity is :" + x);
            final TextView tx6 = (TextView) findViewById(R.id.textView4);

            tx6.setText("Now humidity is " + x + "%");
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

}