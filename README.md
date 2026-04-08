# PhotoHook - 生产运行系统拍照替换模块

这是一个针对 `com.cnten.productionoperation`（生产运行系统）定制的 LSPosed 模块，用于将拍照请求替换为相册中的指定图片。

## 功能特点

- 自动拦截 APP 的拍照请求
- 将拍照结果替换为指定路径的图片
- 极简设计，稳定可靠

## 配置

修改 `app/src/main/java/com/example/photohook/PhotoHook.java` 中的：

```java
private static final String TARGET_PACKAGE = "com.cnten.productionoperation";  // 目标APP
private static final String REPLACE_IMAGE_PATH = "/sdcard/DCIM/photo_to_upload.jpg";  // 替换图片路径
```

## 使用方法

### 方法一：GitHub Actions 在线编译（推荐）

1. Fork 或上传此项目到 GitHub
2. 确保 Actions 功能已启用
3. 推送代码后，Actions 自动编译
4. 在 Actions 页面下载 `app-debug.apk`

### 方法二：本地编译

1. 用 Android Studio 打开项目
2. Build → Build APK(s)
3. 在 `app/build/outputs/apk/debug/` 找到 APK

## 安装配置

1. 安装编译好的 APK
2. 在 LSPosed 中启用模块
3. 配置作用域：勾选「生产运行系统」
4. 将您要上传的图片放到：`/sdcard/DCIM/photo_to_upload.jpg`
5. 重启目标 APP

## 验证

打开 LSPosed → 日志，筛选标签 `PhotoHook`，点击拍照时应该看到：
```
PhotoHook: 目标APP已加载: com.cnten.productionoperation
PhotoHook: Hook 完成
PhotoHook: 拦截到拍照请求，替换图片: /sdcard/DCIM/photo_to_upload.jpg
```
