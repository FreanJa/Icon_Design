# Icon Design

<img src="https://img.shields.io/badge/Mobile Application Development-Android-blue?style=flat&logo=Android"> <img src="https://img.shields.io/badge/Continuously updated-Homework-orange?style=flat&logo=Supabase">

> 移动应用开发课的作业，蹭下活跃度，持续更新中

----

</br>

## 🧐 Requests

### 2022/03/15

> Complete an app contained the **basis four widgets or more(optimal)** and put them into a related scenario, that means your app should have a topic. 
>
> For example, you can develop an app to introduce yourself, and you can design it by using the widgets as many as possible.
>
> Maybe you can add some explanation or voice in the demonstration video.

</br>

### 2022/03/22

> Practice to use the **ArrayAdapter&SimpleAdapter**, and implement the **spinner** and **List view**.
>
> Use the **BaseAdapter** to self-define your adpter and design a new look for ListView, such that the ListView can contain more widgets.
>
> You can add this to your developed previous app or you can create a new app that contains spinner and your self-defined ListView.

</br>

### 2022/03/30

>Add the **dynamic fragment** and **viewpager** to your previous developed application.
>
>If you can not combine the components into your app, you can implement them seperatively.

</br>

</br>

## 💡 Programming

### 2022/03/16

#### Process

四个 widgets 还要互相关联 一开始不知道做什么，无意间看到了之前装的俩app

<img src="https://pic.freanja.cn/images/2022/03/31/202203311355316.png" alt="inspiration" width=33% height=33% />

这俩 app 的作用就是可以给macOS的图标换皮不会还有人不知道macOS的app可以非常方便的换皮吧😏）

所以就决定做个可以自定义图标的app，相比 **an app to introduce yourself** 应该有趣一点🤏吧 🫣

数了下 *ImageView TextView EditText RadioGroup&RadioButton Spinner SeekBar*  **more than four widgets** 没毛病

#### Effect

> 最后的效果（因为是后面补的，所以从压缩的视频里面截的图，高糊警告⚠️：

<img src="https://pic.freanja.cn/images/2022/03/31/202203311356907.png" alt="food_icon_v1" width=33% height=33% />

大概实现的效果是：

- canvas区域 使用 RelativeLayout 叠了LinearLayout，**ImageView**，**TextView**展示效果
- **spinner** 选择使用的主图
- **seekBar** 调整图标
- **editText** 设置文字
- 两组 **radioButton**，一组控制背景色，一组控制前景色

没实现的功能：

- 图片的生成导出，浅搜了一下，没搜到，感觉功能差不多实现了就不搞了，毕竟就一个作业，不是课设，没必要没必要

  （那个时候的内心os大概是：可以了吧，大家没那么卷吧，不搞了不搞了，开摆🫠

</br>

### 2022/03/21

![reply_1](https://pic.freanja.cn/images/2022/03/31/202203311405416.png)

原来这个 **basis four widgets or more** 指的是上课讲的四种 Widgets，我这波属于是当大聪明了🫥

上课老师展示了下优秀作业，md都这么卷的？？？我以为这个 **Maybe you can add some explanation or voice in the demonstration video** 就是说说罢了，怎么都配音了啊，这波属于是被卷死了，怎么这么多100啊55555，我现在把 Button 和 ProgressBar 加上去可以给我100么☹️，不行了我也要卷🫤

</br>

### 2022/03/26

#### Process

> 这次我必要卷死别人

这得先把 **Button** 和 **ProgressBar** 加上，第一反应就是想到把 **图片生成** 给搞了（没办法别的我是真的想不到可以用到ProgressBar了）

**ListView** 的话搞个展示页？差不多就这样吧，搞起来搞起来🫡

#### Effect

> 内卷要不得啊，虽然这次学了蛮多的，把上学期欠下的SD存储的坑也填上了，但真的太累了，通了一个晚上，最终效果还算满意，多图预警⚠️

<img src="https://pic.freanja.cn/images/2022/03/31/202203311429679.png" alt="homePage" width=25% height=25% /><img src="https://pic.freanja.cn/images/2022/03/31/202203311431125.png" alt="ProgressBar" width=25% height=25%  /><img src="https://pic.freanja.cn/images/2022/03/31/202203311433451.png" alt="generated" width=25% height=25%  />

##### ⬆️MainActivity部分

<img src="https://pic.freanja.cn/images/2022/03/31/202203311434012.png" alt="ListView" width=25% height=25% /><img src="https://pic.freanja.cn/images/2022/03/31/202203311435593.png" alt="DetailDialog" width=25% height=25%/><img src="https://pic.freanja.cn/images/2022/03/31/202203311436376.png" alt="SystemPhotoAlbum" width=25% height=25% />

##### ⬆️ListActivity&Album部分

这次还是加了蛮多东西的，看得到的地方是 ProgressBar 和 ListView 以及页面跳转的Animate

这些也稍微加了点小心思，比如ProcessBar换了个皮，用了个线程计时，ListView有个元素删除自动刷新



看不到的地方是页面跳转的信息传递，因为图片生成的是bitmap，直接用 *intent.putExtra*不太行，查了资料之后决定直接存在内存里面，listView直接读内存。这里是个大坑，~~也不排除也是因为上学期的课一直在划水~~

一开始查到的资料是说 需要在AndroidMainifest.xml 里面添加俩权限

```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

然后MainActivity.java里确认/请求权限

```java
private void checkNeedPermissions() {
        int permission_write = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_read = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (!permission_storage) {
            Toast.makeText(this, "Requesting file management permission", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION), 0);
        }
        else {
            Toast.makeText(this, "Already get management all files permission", Toast.LENGTH_SHORT).show();
        }
    }
```

但这些搞完了之后，还是不停的报空指针异常，那个时候大概是凌晨两点↔️，属于是脑子也不大清晰，一边查资料，一边debug了大概一个小时。

后来发现stackoverflow上有个回答提到了一句文件访问权限，我就去看下了，确实模拟器上app现在的权限是 **Allow access to media only**，死马当活马医，又查了下资料怎么开启文件管理权限，因为我用的模拟器是 Pixel 4 默认是不显示文件权限的，搞了一波之后，软件信息页总算是显示了，然后手动给权限，终于过了，喜极而泣🤧

过两天之后翻到官方文档，补了下页面跳转授权，提升下用户体验 ~~（用户，指自己）~~，最后实现的代码⬇️

```xml
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
```

```java
@RequiresApi(api = Build.VERSION_CODES.R)
    private void checkNeedPermissions() {
        boolean permission_storage = Environment.isExternalStorageManager();
        int permission_write = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permission_read = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (!permission_storage) {
            Toast.makeText(this, "Requesting file management permission", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION), 0);
        }
        else {
            Toast.makeText(this, "Already get management all files permission", Toast.LENGTH_SHORT).show();
        }

        if (permission_write!=PackageManager.PERMISSION_GRANTED
        || permission_read!=PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Requesting media permission", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        else {
            Toast.makeText(this, "Already get media permission", Toast.LENGTH_SHORT).show();
        }
    }
```

</br>

### 2022/03/31

![reply2](https://pic.freanja.cn/images/2022/03/31/202203311504638.png)

***OHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH 🥳***



## 🏗 UPDATING……

</br>

</br>

## 🤝 Connect with Me

[<img src="https://img.shields.io/badge/MyBlog-blog.freanja.cn-critical?style=flat&logo=about.me&logoColor=3498db">](https://blog.freanja.cn) [<img src="https://img.shields.io/badge/Email-freanja.l@gmail.com-critical?style=flat&logo=Gmail&logoColor=3498db">](mailto:freanja.l@gamil.com) [<img src="https://img.shields.io/badge/Github-FreanJa-critical?style=flat&logo=github&logoColor=3498db">](https://www.github.com/freanja)

</br>

</br>







