/*
 * Copyright (c) 2012 The Linux Foundation. All Rights Reserved.
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.com.unifront.linko;


import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceFragment;

public class ApplicationSettings extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new ApplicationSettingsFragment()).commit();
        ActionBar actionBar = getActionBar();
        actionBar.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.ic_titlebar));
        actionBar.setTitle("应用");
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.show();
    }
    
    public static class ApplicationSettingsFragment extends PreferenceFragment{
        @Override
        public void onCreate(Bundle savedInstanceState) {
            // TODO Auto-generated method stub
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.layout.application_settings);
        }
        
        
    }
    
}
