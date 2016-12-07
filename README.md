# SunnyAndroidLib
#包含了一些常用方法、常用组件
## 常用类、组件
###build.gradle中添加：
` compile 'com.ikkong:sunnylibrary:1.0.7' `
##图片预览
###build.gradle中添加：
` compile 'com.ikkong:imagepreview:1.0.8' `

* 使用方法
    ` KKImagePreviewActivity.goPreview(this,1,"http://x.cn/a.jpg","http://x.cn/b.jpg"); `
* 参数： 上下文context , 默认显示第几张图片 , 图片路径 支持网络图片和本地图片
![效果预览](http://ikkong.qiniudn.com/f.gif)
##日志记录
###build.gradle中添加：
` compile 'com.ikkong:log2file:1.0.1' `

* 使用方法
    * 1.配置参数 详见ILog类
    * 2.调用 ILog.init()初始化
    * 3.使用 ILog