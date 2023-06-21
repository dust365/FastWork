package com.dust.small.manager;

import android.util.Pair;

import com.dust.small.utils.ProjectUtils;
import com.intellij.openapi.project.Project;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.concurrent.TimeUnit;

public class NetworkManager {

    private OkHttpClient mOkHttpClient;

    private NetworkManager() {
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
    }

    private static NetworkManager INSTANCE = new NetworkManager();

    public static NetworkManager getInstance() {
        return INSTANCE;
    }

    public OkHttpClient getClient() {
        return mOkHttpClient;
    }

    @SuppressWarnings("StringBufferReplaceableByString")
    public void getWithAtomParam(Project project, String host, Callback callback) {
        String requestUrl = new StringBuilder(host).append("?")
                .append("project=").append(project.getName().toLowerCase())
                .append("&name=").append(System.getProperty("user.name"))
                .append("&version=").append(ProjectUtils.getPluginVersion())
                .toString();
        get(requestUrl, callback);
    }

    public void get(String requestUrl, Callback callback) {
        Request request = new Request.Builder()
                .get()
                .url(requestUrl)
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    public void delete(String requestUrl, Pair<String, String> header, Callback callback) {
        Request request = new Request.Builder()
                .url(requestUrl)
                .header(header.first, header.second)
                .delete()
                .build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }
}
