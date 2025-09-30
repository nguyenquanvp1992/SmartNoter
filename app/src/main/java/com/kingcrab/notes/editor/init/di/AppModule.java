package com.kingcrab.notes.editor.init.di;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;

import com.kingcrab.notes.editor.ulti.Const;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@InstallIn(SingletonComponent.class)
@Module
public class AppModule {

    @Singleton
    @Provides
    public SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        return context.getSharedPreferences(Const.SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public ContentResolver provideContentResolver(@ApplicationContext Context context) {
        return context.getContentResolver();
    }
}