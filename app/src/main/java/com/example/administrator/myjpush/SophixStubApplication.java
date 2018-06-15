package com.example.administrator.myjpush;

import android.content.Context;
import android.support.annotation.Keep;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created by Administrator on 2018\6\14 0014.
 */

public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";
    static SophixStubApplication instance;

    // 此处SophixEntry应指定真正的Application，也就是你的应用中原有的主Application,并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {}

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//         如果需要使用MultiDex，需要在此处调用。
//         MultiDex.install(this);
        initSophix();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
         SophixManager.getInstance().queryAndLoadNewPatch();
         /** 补丁在后台发布之后, 并不会主动下行推送到客户端, 客户端通过调用queryAndLoadNewPatch方法查询后台补丁是否可用*/

    }

    private void initSophix() {
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("24931285",
                        "564ce97cef07bca74aab4db180b5a92e",
                        "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQC/ZWKXZuvlIW762z+aj5HJV9U/ntuhznYAhl9xREZ1+xAFLVYAoqv2YU0QDs4enHl/AmthDje5S1hVGHpplUO85ccCgOTMvlvy2frHnuJNFtquk+Xk2Jnj7wFGxINyZ7y+MZwcwgZlg4PQCWu6oaOCMNuNgY/wkDe/EJTEfO6HhPfaACKx/jYspJEKC7G+czOpOWbksZBTHHBT+zoybmlDnEAlo2zr95bYfkH6uld/5SE4Q8A2yrIARK2uDxvA7YtlhthaInM7/cd350iS2IhnIuxqdeh1rtvCZ+Je4r2T+PxCw27sj4Ke03l1sEbU3XHcTpx+uoVbCh9Ve9JRHToLAgMBAAECggEAPdBSrS8yVIgdvar9ub9QiERzvN5jdLinZ7f+Lyir0QLKw8FGm2HuQ95c8yi07TIAE0MGxwu+d3ucBa+oKV1pySI0ydwzCOUZ8S7xTXNFBzywV92wS96gpPrV4LMq+NIEJTEEiE0pn7N/6LqCLn5xvmRlJCiKAlmxh2WlhmgK6X07RdokiTILxE4etPB/156ULARZ14iCLZ7IPu4MnS3jEVjWpATzl8RpWbfk0KDRMR1UGFVCkuHUAdg9VHln35Ygp6qDZ4tmycnyMK2o5o0KJD4X2fVxXFmfH/0V1d/NB+5UOpf2I/qNlIg/qHzSGs3C/xDnh0uV2EQGqlNaL6UTsQKBgQDhgcjfmY8rWDIbBDARooa0phyvNakXOZtVm019kRyo2nhF8uguIcCZb5KRgnSbhb/7piRYoCne3ArjSuYEgDRItY5kzWVV+lBEK8KmuHMp5/smVIcUHAL31KXtmgTdzpB0Ht8+juSl4VBDiBmv5bHiVYoyOf0L7xqoqP9FCE3GPQKBgQDZRs5rSFi6TEjReLI6Mj7v8mQH02LTQNNBHS72xHYCUBTDG5Y3KVQsYn7ODCxDwUTL501zcIx4bxcKNyaFw4R/8DRO2m0pxb6wFtrbzyYCAPqWEYPGIRRrqX3dOIWxCi//micWv+ryO1mBV8BPSe6CF1OJ4dCYN4rAHAyk23tN5wKBgQCm18cu6AYtI4uuwWJvKbhQX7ZJS4JXVJ/Arq6egIabx0fFOPrfZsmAvDPMxKBRkJuCLGIn0BgqctL7Z6bXHaORSAKaQfz/p7tLNLVHdKCAGMqQiWmHT1JC2moJaTLAiNwHje/N8RrqP9GKpV+b7G2372HX11+JPNaUgrSa9pHUYQKBgEm6xEsVdr5pKoCig2BKMp870oPxUGS9Z7J907T7P8xTHmiC0PuQ87ZICh2jddXcHJtQtzCILbKkh934jwLvMOQ1P5rOdiKprqaUatoSNiSR7nbEJMIz8XkDP+m0x3EnMth6l+8ZWwGt8BZ7D5AYV24fukJctWsh1dyQ6B19OR9bAoGAfDtPzRml0W8w6d89KwFV2tkeahCx+vU+AUn++jIyIGdKkQNElmC4FAEnSbypqR14ONV8qLkWziMmqojPRQ8G4VpG8DFHPnebyJaCE2vkTebnB22aej6FfaDGmmr+lRFVcFlOydz3SbHZCDzyX3gGQpO1f8XgRj/gYDMcRhvETgg=")// 可以不在AndroidManifest设置而是用此函数来设置Secret。放到代码里面进行设置可以自定义混淆代码，更加安全，
                // 此函数的设置会覆盖AndroidManifest里面的设置，如果对应的值设为null，默认会在使用AndroidManifest里面的。
                // .setEnableDebug(true)//默认为false，设为true即调试模式下会输出日志以及不进行补丁签名校验. 线下调试此参数可以设置为true, 它会强制不对补丁进行签名校验,
                // 所有就算补丁未签名或者签名失败也发现可以加载成功. 但是正式发布该参数必须为false, false会对补丁做签名校验, 否则就可能存在安全漏洞风险。 .setEnableFullLog()
                .setEnableDebug(true)
                .setEnableFullLog()
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.i(TAG, "sophix load patch success!");

                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 如果需要在后台重启，建议此处用SharePreference保存状态。
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见接入文档-快速接入章节1.3.2.3
                            Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                            /** 不可以直接Process.killProcess(Process.myPid())来杀进程，这样会扰乱Sophix的内部状态。
                             * 因此如果需要杀死进程，建议使用这个方法，它在内部做一些适当处理后才杀死本进程。*/
                            instance.killProcessSafely();
                        }
                    }
              }).initialize();
    }

    public static SophixStubApplication getInstance() {
        return instance;
    }

    public void queryAndLoadNewPatch(){
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

}
