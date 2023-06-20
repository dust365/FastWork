# FastWork
idea插件

# 一、新手必备

### 1.IntelliJ IDEA 获取官网地址

https://www.jetbrains.com/idea/download/#section=mac 试用30天，或者直接使用Community版本。

### 2.Java Swing 图形界面开发基础知识

可以参考如下链接进行学习

https://blog.csdn.net/xietansheng/article/details/72814492

## 二、开始第一个插件

## 1.入口程序

### <1> 插件入口类 xml

![image](https://upload-images.jianshu.io/upload_images/7336931-599a165f7f0fd582.png?imageMogr2/auto-orient/strip|imageView2/2/w/1140/format/webp)

### <2>插件在应用市场上，显示的信息

name

description html

### <3>actions 程序入口，注册到idea 窗口中显示的位置，和快捷键的配置

## 2.如何绘制UI

### <1> 可以选中包名，右键new→Swing UI Designer→ GuI From或者 create Dialog class 来创建UI

![image](https://upload-images.jianshu.io/upload_images/7336931-3408df7e1c2d70d0.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

### 
<2> 如何实现布局

在Swing Designer 设计页面 通过右侧选中合适的控件拖动进行布局。

![image](https://upload-images.jianshu.io/upload_images/7336931-ef88428f3783946e.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

### <3>基础控件介绍
GUI|Android|解释|
--|:--:|--:
JButton|Button |按钮
JLable|EditText|输入控件
JTextArea |TextView|


### <4> 设置view的ID和文字

在1处设置控件的属性ID，在UI操作类中会生成对应的实例子，类似android 的findView

在2处设置空间站在界面上显示的文案。

其他属性可以在property属性列表页面根据需求进行配置。

![image](https://upload-images.jianshu.io/upload_images/7336931-4c096b6368884b35.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

### <5> UI 对应的操作类

![image](https://upload-images.jianshu.io/upload_images/7336931-ba1d19002b5f04d1.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

### <6> 实现点击事件
```
pathSelectButton.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {

    //todo someThing
     FlieSelectUtils.showFileOpenDialog(contentPane,pathTextShow,1);

}});
```
### <7> JFileChooser  系统类文件选择
```
JFileChooser fileChooser = new JFileChooser();

//

// fileChooser.setCurrentDirectory(new File("."));

fileChooser.setCurrentDirectory(new File("/Users/*/AndroidStudioProjects"));

//

// fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//

fileChooser.setMultiSelectionEnabled(false);

// FileNameExtensionFilter ,

// fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));

//

// fileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png","gif"));

// ,

int result = fileChooser.showOpenDialog(parent);
```
### <8> 如何实现类似SP存储功能
```
PropertiesComponent 存储组件～= sp存储

PropertiesComponent.getInstance().setValue(TRACK_PATH_KEY,currentPath);

String projectPath= PropertiesComponent.getInstance().getValue(MAIN_PROJECT_PATH_KEY);
```
# 三、插件的调试方法

# 四、插件打包和安装

打包:在Gradle工具栏中运行assemble任务，即可在/build/distribution/{插件名称}-{插件版本}.zip路径下找到打包好的插件zip包。

本地安装:还没将插件发布到插件市场前我们可以选择安装本地插件，打开AS菜单栏/Android Studio/Preference/Plugins/Install

Plugin from Disk... 安装后即可使用。

# 1.使用idea右边栏Gradle，如图双击buildPlugin

![image](https://upload-images.jianshu.io/upload_images/7336931-0bcf5a0b9c0dc475.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

## 2.打包后的插件就在\build\distributions目录下

![image](https://upload-images.jianshu.io/upload_images/7336931-dad61676adad4310.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

### 3.插件的安装

![image](https://upload-images.jianshu.io/upload_images/7336931-0e036931881e9422.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

# 五、插件发布/上架

## <1>注册账号登录如下网址注册相应的账号:

https://plugins.jetbrains.com

## <2>上传插件，按照下图步骤上传插件zip包

![image](https://upload-images.jianshu.io/upload_images/7336931-c5101649b0c35676.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

## <3>上传完成后等待审核，可以补充相关软件的使用截图

https://plugins.jetbrains.com/plugin/19579-fastworker

![image](https://upload-images.jianshu.io/upload_images/7336931-59a6c3787cd8575e.png?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

# 六、插件的使用和演示

其他:

插件的第一个版本都需要在网站手动上传，之后的版本可以使用hub账号中的token自动更新。

