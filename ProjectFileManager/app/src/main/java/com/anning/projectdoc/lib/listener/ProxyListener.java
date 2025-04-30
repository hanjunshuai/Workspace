package com.anning.projectdoc.lib.listener;

import android.content.Intent;

public interface ProxyListener {
    void onResult(int requestCode, int resultCode, Intent data);
}
