
package cn.com.unifront.autostart;

import android.os.Parcel;
import android.os.Parcelable;

public class AutoLaunchAppInfo implements Parcelable {

    private String packageName;
    private int launchFlag;
    private int appFlag;

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel paramParcel, int flags) {
        paramParcel.writeString(this.packageName);
        paramParcel.writeInt(this.launchFlag);
        paramParcel.writeInt(this.appFlag);
    }

    public static final Parcelable.ClassLoaderCreator<AutoLaunchAppInfo> CREATOR = new ClassLoaderCreator<AutoLaunchAppInfo>() {

        @Override
        public AutoLaunchAppInfo[] newArray(int size) {
            // TODO Auto-generated method stub
            return new AutoLaunchAppInfo[size];
        }

        @Override
        public AutoLaunchAppInfo createFromParcel(Parcel paramParcle) {
            // TODO Auto-generated method stub
            return new AutoLaunchAppInfo(paramParcle);
        }

        @Override
        public AutoLaunchAppInfo createFromParcel(Parcel source, ClassLoader loader) {
            // TODO Auto-generated method stub
            return null;
        }
    };

    public AutoLaunchAppInfo() {

    }

    public AutoLaunchAppInfo(Parcel paramParcel) {
        this.packageName = paramParcel.readString();
        this.launchFlag = paramParcel.readInt();
        this.appFlag = paramParcel.readInt();
    }

    public AutoLaunchAppInfo(String packageName, int launchFlag, int appFlag) {
        this.packageName = packageName;
        this.launchFlag = launchFlag;
        this.appFlag = appFlag;
    }

    public String toString() {
        return "packageName:" + this.packageName + "launchFlag:" + this.launchFlag + "appFlag:"
                + appFlag;
    }

}
