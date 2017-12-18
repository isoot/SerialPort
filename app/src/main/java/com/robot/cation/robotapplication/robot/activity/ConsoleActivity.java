/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.robot.cation.robotapplication.robot.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.robot.cation.robotapplication.R;
import com.robot.cation.robotapplication.robot.utils.LogUtils;

import java.io.IOException;

public class ConsoleActivity extends SerialPortActivity {

    EditText mReception;
    EditText Emission;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.console);

        //		setTitle("Loopback test");
        mReception = (EditText) findViewById(R.id.EditTextReception);

        Emission= (EditText) findViewById(R.id.EditTextEmission);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LogUtils.w("写内容:" +Emission.getText().toString());
                    mOutputStream.write(Emission.getText().toString().getBytes());
                    mOutputStream.write('\n');
                } catch (IOException e) {
                    LogUtils.w(e.getLocalizedMessage());
                }
            }
        });
    }

    @Override
    protected void onDataReceived(final byte[] buffer, final int size) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (mReception != null) {
                    String content = new String(buffer, 0, size);
                    LogUtils.w("接收内容:" + content);
                    mReception.append(content);
                }
            }
        });
    }
}
