package com.example.android.businesscard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.lang.reflect.Field;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageSwitcher imageSwitcher;
    ImageButton next;
    TextView textView1, textView2, textView3, textView4, textView5;
    RelativeLayout relativeLayout;
    int resID;
    int currentImage = 0;
    TextView[] textViewArray;
    int[] imgArr = new int[6];
    float[][] normalPos;
    Class rawClass;
    Field[] fields;
    int count;

    float xpos1;
    float xpos2;
    float xpos3;
    float xpos4;
    float xpos5;
    float ypos1;
    float ypos2;
    float ypos3;
    float ypos4;
    float ypos5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // INITIALIZE THE Views
        imageSwitcher = findViewById(R.id.image_switcher);
        next = findViewById(R.id.next);
        relativeLayout = findViewById(R.id.relative_layout);

        textView1 = findViewById(R.id.text1);
        textView2 = findViewById(R.id.text2);
        textView3 = findViewById(R.id.text3);
        textView4 = findViewById(R.id.text4);
        textView5 = findViewById(R.id.text5);

        textViewArray = new TextView[]{textView1, textView2, textView3, textView4, textView5};

        count = 0;

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setAdjustViewBounds(true);
                return imageView;
            }
        });
        CreateImageArray();
        imageSwitcher.setImageResource(imgArr[currentImage]);

        Animation in = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        Animation out = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        imageSwitcher.setInAnimation(in);
        imageSwitcher.setOutAnimation(out);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        if(hasFocus) {
            GetPositions();
        }
    }


    public void GetPositions() {
        xpos1 = textView1.getX();
        ypos1 = textView1.getY();

        xpos2 = textView2.getX();
        ypos2 = textView2.getY();

        xpos3 = textView3.getX();
        ypos3 = textView3.getY();

        xpos4 = textView4.getX();
        ypos4 = textView4.getY();

        xpos5 = textView5.getX();
        ypos5 = textView5.getY();

        normalPos = new float[][]{{xpos1, ypos1}, {xpos2, ypos2}, {xpos3, ypos3}, {xpos4, ypos4}, {xpos5, ypos5}};
    }


    public void CreateImageArray() {
        rawClass = R.raw.class;
        fields = rawClass.getFields();

        // FILL AN ARRAY WITH ONLY 10 PICS NAMED BY THE ID resID
        for (int i = 0; i < imgArr.length; i++) {
            try {
                resID = fields[i].getInt(rawClass);
                imgArr[i] = resID;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void nextImage(View view) {
        currentImage++;
        currentImage = currentImage % imgArr.length;
        imageSwitcher.setImageResource(imgArr[currentImage]);

        count++;
        count = count % 6;
        if (count == 0) {
            RestorePosition();
        } else {
            for (int i = 0; i < textViewArray.length; i++) {
                RandomPosition(textViewArray[i]);
            }
        }
    }

    public void RandomPosition(TextView textView) {

        Random rand = new Random();
        int x = rand.nextInt(400);
        int y = rand.nextInt(200);

        textView.setX(x);
        textView.setY(y + 70);
    }

    public void RestorePosition() {
        for (int i = 0; i < textViewArray.length; i++) {
            textViewArray[i].setX(normalPos[i][0]);
            textViewArray[i].setY(normalPos[i][1]);
        }
    }
}
