#NewsGetTogether
---
学习`Android`开发，模仿了几个开源完整的项目之后，就自己也做一个新闻聚合的`App`,包含主界面，侧滑页，侧滑页包含两个功能“看博客”和“去看图”，各个部分都写成`Fragment`，这样就不用创建多个`Activity`,而是创建一个宿主`FragmentActivity`,选择功能后替换宿主`FragmentActivty`布局中的`FrameLayout` 部分以显示其他内容，进入App直接显示新闻界面，新闻界面显示`TabLayout+ViewPager`展示各个新闻来源的新闻,每个`Pager`页面显示一个新闻来源的新闻信息。

 实际效果看下面图片展示（文件中包含apk文件，可安装测试）： 

 ![photo1](https://github.com/JakeyYe/NewsGetTogether/blob/master/art/photo1.jpg)

  ![photo3](https://github.com/JakeyYe/NewsGetTogether/blob/master/art/photo2.jpg)

 ![photo3](https://github.com/JakeyYe/NewsGetTogether/blob/master/art/photo3.jpg) 

![photo3](https://github.com/JakeyYe/NewsGetTogether/blob/master/art/photo4.jpg)