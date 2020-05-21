#1.Notification简单概述
    Notification ,是一种具有全局效果的通知，可以在系统的通知栏中显示。当app向系统发出通知时，它将先以图标的形式显示在通知栏中。
    用户可以下拉通知栏查看通知的详细信息。通过栏和抽屉式通知栏均是由系统控制，用户可以随时查看。
#2.Notification通知用途
    显示是接受到的短信，及时消息（QQ、微信...）
    显示客户端推送的消息，（新闻、广告....）第三方有：JPush、个推、
    显示正在进行的事物，eg:后台音乐播放、下载进度....
#3.Notification基本操作
##3.1 Notification 创建的必要属性
    小图标，setSmallIcon()
    标题，setContentTitle()
    内容，setContentText()
##3.2创建步骤

